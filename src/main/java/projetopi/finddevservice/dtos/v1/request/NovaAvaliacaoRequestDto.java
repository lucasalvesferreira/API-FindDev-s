package projetopi.finddevservice.dtos.v1.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

public class NovaAvaliacaoRequestDto {

    private Integer idAvaliacao;
    private UUID idAvaliador;
    private UUID idAvaliado;
    @Min(1)
    @Max(5)
    private int nota;
    private String comentario;
    private boolean isCompany;

    public NovaAvaliacaoRequestDto() {
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

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
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

    public void setCompany(boolean iscompany) {
        isCompany = iscompany;
    }
}
