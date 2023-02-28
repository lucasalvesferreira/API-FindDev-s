package projetopi.finddevservice.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "avaliacao")
public class AvaliacaoModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAvaliacao;
    private UUID idAvaliador;
    private UUID idAvaliado;
    @Min(1)
    @Max(5)
    private Integer nota;
    @Column(length = 2600)
    private String comentario;

    private boolean isCompany;

    @CreationTimestamp
    private LocalDateTime dataHoraAvaliacao;

    public AvaliacaoModel() {
    }

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public UUID getIdAvaliador() {
        return idAvaliador;
    }

    public void setIdAvaliador(UUID idAvaliador) {
        this.idAvaliador = idAvaliador;
    }

    public UUID getIdAvaliado() {
        return idAvaliado;
    }

    public void setIdAvaliado(UUID idAvaliado) {
        this.idAvaliado = idAvaliado;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isCompany() {
        return isCompany;
    }

    public void setCompany(boolean company) {
        isCompany = company;
    }

    public LocalDateTime getDataHoraAvaliacao() {
        return dataHoraAvaliacao;
    }

    public void setDataHoraAvaliacao(LocalDateTime dataHoraAvaliacao) {
        this.dataHoraAvaliacao = dataHoraAvaliacao;
    }
}
