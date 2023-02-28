package projetopi.finddevservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetopi.finddevservice.exceptions.RequiredExistingObjectException;
import projetopi.finddevservice.exceptions.ResourceNotFoundException;
import projetopi.finddevservice.models.ArquivosModel;
import projetopi.finddevservice.models.EmpresaModel;
import projetopi.finddevservice.models.Vaga;
import projetopi.finddevservice.repositories.ArquivoRepository;
import projetopi.finddevservice.repositories.EmpresaRepository;
import projetopi.finddevservice.repositories.VagasRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ManipulacaoArquivo {

    @Autowired
    VagasRepository vagasRepository;
    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    ArquivoRepository arquivoRepository;


    public void gravaRegistro(String registro, String nomeArq) {
        BufferedWriter saida = null;

        // try-catch para abrir o arquivo
        try {
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            erro.printStackTrace();
        }

        // try-catch para gravar e fechar o arquivo
        try {
            saida.append(registro + "\n");
            saida.close();
        } catch (IOException erro) {
            System.out.println("Erro ao gravar o arquivo");
            erro.printStackTrace();
        }
    }


    public String gravaArquivoTxt(UUID uuid) {


        if(!empresaRepository.existsById(uuid)){
            throw new RequiredExistingObjectException("No records for this id!");
        }

        String nomeArq = "Vagas.txt";

        List<Vaga> lista = vagasRepository.findAll();
        int contaRegDados = 0;

        // Monta o registro de header
        String header = "00FIND-DEV20222";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";

        // Grava o registro de header
        gravaRegistro(header, nomeArq);

        // Monta e grava os registros de corpo (ou de dados)
        String corpo;
        for (Vaga a : lista) {
//            String empresa = empresaRepository.findById(a.getIdEmpresa()).get().getNome();

            corpo = "02";
            corpo += String.format("%-10.10s", a.getFuncao() == null ? "" : a.getFuncao());
            corpo += String.format("%05d", a.getId() == null ? 0 : a.getId());
            corpo += String.format("%-20.20s", a.getDesenvolvedorContratado() == null ? "" : a.getDesenvolvedorContratado().getNome());
            corpo += String.format("%-10.10s", a.getSenioridade() == null ? "" : a.getSenioridade());
            corpo += String.format("%-20.20s", uuid);
            corpo += String.format("%-50.50s", a.getTitulo().isEmpty() ? "" : a.getTitulo());
            corpo += String.format("%-50.50s", a.getDescricao().isEmpty() ? "" : a.getDescricao());
            gravaRegistro(corpo, nomeArq);
            contaRegDados++;
        }

        // Monta e grava o registro de trailer
        String trailer = "01";
        trailer += String.format("%010d", contaRegDados);

        gravaRegistro(trailer, nomeArq);


        Optional<EmpresaModel> entity = empresaRepository.findById(uuid);

        ArquivosModel arquivo = new ArquivosModel();
        arquivo.setUsuario(entity.get());
        ArquivosModel arq = arquivoRepository.save(arquivo);


        arquivoRepository.setRelatorio(arq.getId(), nomeArq.getBytes());

        String conteudoTexto = new String(arquivoRepository.getRelatorio(arq.getId()));
       return conteudoTexto;

    }

//    public static void leArquivoTxt(String nomeArq) {
//        BufferedReader entrada = null;
//        String registro, tipoRegistro;
//        String ra, nome, curso, disciplina;
//        Double media;
//        Integer qtdFalta;
//        int contaRegDadoLido = 0;
//        int qtdRegDadoGravado;
//
//        List<Aluno> listaLida = new ArrayList();
//
//        // try-catch para abrir o arquivo
//        try {
//            entrada = new BufferedReader(new FileReader(nomeArq));
//        }
//        catch (IOException erro) {
//            System.out.println("Erro ao abrir o arquivo");
//            erro.printStackTrace();
//        }
//
//        // try-catch para ler e fechar o arquivo
//        try {
//            // Leitura o 1o registro do arquivo
//            registro = entrada.readLine();
//
//            while (registro != null) {
//                // Obtém os 2 primeiros caracteres do registro
//                // 1234567890
//                // 01234567890
//                // 00NOTA20222
//                // substring - 1o arg = índice do ínicio do que eu quero obter
//                // substring - 2o arg = índice do final do que eu quero + 1
//                tipoRegistro = registro.substring(0, 2);
//                if (tipoRegistro.equals("00")) {
//                    System.out.println("Registro de header");
//                    System.out.println("Tipo de arquivo: " + registro.substring(2, 6));
//                    System.out.println("Ano e semestre: " + registro.substring(6, 11));
//                    System.out.println("Data e hora da gravação: " + registro.substring(11, 30));
//                    System.out.println("Versão do documento: " + registro.substring(30, 32));
//                }
//                else if (tipoRegistro.equals("01")) {
//                    System.out.println("Registro de trailer");
//                    qtdRegDadoGravado = Integer.parseInt(registro.substring(2, 12));
//                    System.out.println("Quantidade de reg de dados lidos: " + contaRegDadoLido);
//                    System.out.println("Quantidade de reg de dados gravados: " + qtdRegDadoGravado);
//                    if (contaRegDadoLido == qtdRegDadoGravado) {
//                        System.out.println("Quantidade de registros de dados lidos compatível com "
//                                + "quantidade de registros de dados gravados");
//                    }
//                    else {
//                        System.out.println("Quantidade de registros de dados lidos incompatível com "
//                                + "quantidade de registros de dados gravados");
//                    }
//                }
//                else if (tipoRegistro.equals("02")) {
//                    System.out.println("Registro de corpo");
//                    curso = registro.substring(2, 7).trim();
//                    ra = registro.substring(7, 15).trim();
//                    nome = registro.substring(15, 65).trim();
//                    disciplina = registro.substring(65, 105).trim();
//                    media = Double.valueOf(registro.substring(105,110).replace(',','.'));
//                    qtdFalta = Integer.valueOf(registro.substring(110,113));
//                    contaRegDadoLido++;
//
//                    // Instancia um objeto Aluno com as informações lidas
//                    Aluno a = new Aluno(ra, nome, curso, disciplina, media, qtdFalta);
//
//                    // No Projeto de PI, pode fazer
//                    // repository.save(a)
//
//                    // No nosso caso, como não estamos conectados ao banco
//                    // vamos adicionar o objeto a na listaLida
//                    listaLida.add(a);
//
//                }
//                else {
//                    System.out.println("Tipo de registro inválido!");
//                }
//
//                // Lê o próximo registro
//                registro = entrada.readLine();
//            }
//            // Fecha o arquivo
//            entrada.close();
//        }
//        catch (IOException erro) {
//            System.out.println("Erro ao ler o arquivo");
//            erro.printStackTrace();
//        }
//
//        // Exibe a lista lida
//        System.out.println("\nConteúdo da lista lida do arquivo:");
//        for (Aluno a : listaLida) {
//            System.out.println(a);
//        }
//
//    }


//    public static void main(String[] args) {
//        List<Vaga> lista = new ArrayList();
//
//
//
////        System.out.println("Lista original:");
////        for (Aluno a : lista) {
////            System.out.println(a);
////        }
//
//        gravaArquivoTxt( "vagas.txt");
////        leArquivoTxt("alunos.txt");
//    }
}





