package projetopi.finddevservice.estrutura_de_dados;

import projetopi.finddevservice.models.Candidatura;

import java.util.ArrayList;
import java.util.List;

public class CandidaturaVaga {

    private final List<Candidatura> candidaturas;
    private final PilhaObj<Integer> pilhaDesfazer;
    private final PilhaObj<Candidatura> pilhaRefazer;
    private final FilaObj<Candidatura> filaSalvar;

    public CandidaturaVaga() {
        candidaturas = new ArrayList<>();
        pilhaRefazer = new PilhaObj<>(10);
        pilhaDesfazer = new PilhaObj<>(10);
        filaSalvar = new FilaObj<>(10);
    }

    public void salvar(Candidatura candidatura) {
        candidaturas.add(candidatura);
        pilhaDesfazer.push(candidatura.getIdCandidatura());
    }

    public void deletarPorId(int id, boolean refazer) {
        Candidatura candidatura = findById(id);

        if (refazer) pilhaRefazer.push(candidatura);

        candidaturas.remove(candidatura);
    }

    public void desfazer() {
        if (pilhaDesfazer.isEmpty()) throw new IllegalStateException();

        while(!pilhaDesfazer.isEmpty()) {
            int idProduto = pilhaDesfazer.pop();

            deletarPorId(idProduto, true);
        }
    }

    public void refazer() {
        if (pilhaRefazer.isEmpty()) throw new IllegalStateException();

        while(!pilhaRefazer.isEmpty()) {
            Candidatura candidatura = pilhaRefazer.pop();

            salvar(candidatura);
        }
    }

    public void agendarSalvar(Candidatura candidatura) {
        filaSalvar.insert(candidatura);
    }

    public void executarAgendado() {
        if (filaSalvar.isEmpty()) throw new IllegalStateException();

        while(!filaSalvar.isEmpty()) {
            Candidatura candidatura = filaSalvar.poll();

            salvar(candidatura);
        }
    }

    private Candidatura findById(int id) {
        return candidaturas.stream()
                .filter(p -> p.getIdCandidatura() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Candidatura> getCandidaturas() {
        return candidaturas;
    }

    public PilhaObj<Candidatura> getPilhaRefazer() {
        return pilhaRefazer;
    }

    public PilhaObj<Integer> getPilhaDesfazer() {
        return pilhaDesfazer;
    }

    public FilaObj<Candidatura> getFilaSalvar() {
        return filaSalvar;
    }
}
