package projetopi.finddevservice.dtos.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;
import projetopi.finddevservice.models.DesenvolvedorModel;

import java.io.Serializable;

public class CandidaturaResponseDto extends RepresentationModel<CandidaturaResponseDto> implements Serializable {

    @Mapping("idCandidatura")
    @JsonProperty("idCandidatura")
    private int key;

    private DesenvolvedorModel desenvolvedor;

    private int idVaga;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public DesenvolvedorModel getDesenvolvedor() {
        return desenvolvedor;
    }

    public void setDesenvolvedor(DesenvolvedorModel desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }
}
