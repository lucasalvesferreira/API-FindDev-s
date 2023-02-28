package projetopi.finddevservice.models;

import projetopi.finddevservice.enums.FuncaoDev;
import projetopi.finddevservice.enums.SenioridadeDev;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private UUID idEmpresa;

    @OneToOne
    @JoinColumn(name = "idDesenvolvedor")
    private DesenvolvedorModel desenvolvedorContratado;

    private String titulo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "funcao")
    private FuncaoDev funcao;

    @Enumerated(EnumType.STRING)
    @Column(name = "senioridade")
    private SenioridadeDev senioridade;

    private boolean encerrado;

    private boolean avaliado;

    public Vaga(
        String titulo,
        String descricao,
        FuncaoDev funcao,
        SenioridadeDev senioridade
    ) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.funcao = funcao;
        this.senioridade = senioridade;
    }

    public Vaga() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(UUID idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public DesenvolvedorModel getDesenvolvedorContratado() {
        return desenvolvedorContratado;
    }

    public void setDesenvolvedorContratado(DesenvolvedorModel desenvolvedorContratado) {
        this.desenvolvedorContratado = desenvolvedorContratado;
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

    public boolean isEncerrado() {
        return encerrado;
    }

    public void setEncerrado(boolean encerrado) {
        this.encerrado = encerrado;
    }

    public boolean isAvaliado() {
        return avaliado;
    }

    public void setAvaliado(boolean avaliado) {
        this.avaliado = avaliado;
    }
}