package projetopi.finddevservice.dtos.v1.request;

import java.util.UUID;

public class ContratacaoRequest {

    private int idVaga;

    private UUID idDesenvolvedor;

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }

    public UUID getIdDesenvolvedor() {
        return idDesenvolvedor;
    }

    public void setIdDesenvolvedor(UUID idDesenvolvedor) {
        this.idDesenvolvedor = idDesenvolvedor;
    }
}
