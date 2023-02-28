package projetopi.finddevservice.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Candidatura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCandidatura;

    @OneToOne
    private DesenvolvedorModel desenvolvedor;

    private int idVaga;

    public int getIdCandidatura() {
        return idCandidatura;
    }

    public void setIdCandidatura(int idCandidatura) {
        this.idCandidatura = idCandidatura;
    }

    public DesenvolvedorModel getDesenvolvedor() {
        return desenvolvedor;
    }

    public void setDesenvolvedor(DesenvolvedorModel desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public int getIdEmpresa() {
        return idVaga;
    }

    public void setIdEmpresa(int idVaga) {
        this.idVaga = idVaga;
    }
}
