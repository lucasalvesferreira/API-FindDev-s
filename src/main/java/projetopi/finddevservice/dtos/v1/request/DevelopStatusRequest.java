package projetopi.finddevservice.dtos.v1.request;

import projetopi.finddevservice.enums.StatusPerfil;

import java.util.UUID;

public class DevelopStatusRequest {

    private UUID idUsuaio;

    private StatusPerfil statusDev;

    public DevelopStatusRequest() {

    }

    public UUID getIdUsuaio() {
        return idUsuaio;
    }

    public void setIdUsuaio(UUID idUsuaio) {
        this.idUsuaio = idUsuaio;
    }

    public StatusPerfil getStatus() {
        return statusDev;
    }

    public void setStatus(StatusPerfil status) {
        this.statusDev = status;
    }
}
