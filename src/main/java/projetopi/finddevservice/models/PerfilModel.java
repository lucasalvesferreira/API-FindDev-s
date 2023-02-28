package projetopi.finddevservice.models;

import projetopi.finddevservice.enums.StatusPerfil;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Perfil")
public class PerfilModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPerfil;
    @Size(max = 50)
    @Column(length = 50)
    private String titulo;
    @Size(max = 2600)
    @Column(length = 2600)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25)
    private StatusPerfil status;


    public PerfilModel() {
    }


    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusPerfil getStatus() {
        return status;
    }

    public void setStatus(StatusPerfil status) {
        this.status = status;
    }
}
