package projetopi.finddevservice.dtos.v1.request;

import projetopi.finddevservice.enums.FuncaoDev;
import projetopi.finddevservice.enums.SenioridadeDev;

import javax.validation.constraints.NotNull;

public class FiltroRequest {

    @NotNull
    private FuncaoDev funcao;

    @NotNull
    private SenioridadeDev senioridade;

    public FuncaoDev getFuncao() {
        return funcao;
    }

    public void setFuncao(FuncaoDev funcao) {
        this.funcao = funcao;
    }

    public SenioridadeDev getSenioridade() {
        return senioridade;
    }

    public void setSenioridade(SenioridadeDev senioridade) {
        this.senioridade = senioridade;
    }
}
