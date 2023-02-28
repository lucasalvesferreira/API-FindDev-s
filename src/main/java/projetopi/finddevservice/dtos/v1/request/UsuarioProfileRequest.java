package projetopi.finddevservice.dtos.v1.request;

import java.util.UUID;

public class UsuarioProfileRequest {

    private UUID idUsuario;
    private String titulo;
    private String descricao;


    public UsuarioProfileRequest() {
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
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

}
