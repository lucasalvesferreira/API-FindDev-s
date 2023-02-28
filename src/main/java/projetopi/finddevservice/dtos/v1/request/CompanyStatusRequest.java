package projetopi.finddevservice.dtos.v1.request;

import projetopi.finddevservice.enums.StatusPerfil;

import java.util.UUID;

public class CompanyStatusRequest {

    private UUID idUsuaio;

    private StatusPerfil status;


    public CompanyStatusRequest() {
    }

    public UUID getIdUsuaio() {
        return idUsuaio;
    }

    public void setIdUsuaio(UUID idUsuaio) {
        this.idUsuaio = idUsuaio;
    }

    public StatusPerfil getStatus() {
        return status;
    }

    public void setStatus(StatusPerfil status) {
        this.status = status;
    }
}
