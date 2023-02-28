package projetopi.finddevservice.dtos.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;
import projetopi.finddevservice.dtos.v1.PerfilDto;
import projetopi.finddevservice.enums.PlanoDev;
import projetopi.finddevservice.models.PerfilModel;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@JsonPropertyOrder(
        {"id", "nome", "email", "senha", "cpf", "estado", "cidade", "telefone","plano","perfil"}
)
public class DevelopResponseDto extends RepresentationModel<DevelopResponseDto> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Mapping("id")
    @JsonProperty("id")
    private UUID key;
    private String nome;
    private String email;
    private String senha;
    private String estado;
    private String cidade;
    private String telefone;
    private String cpf;
    private PlanoDev plano = PlanoDev.GRATUITO;
    private PerfilDto perfil;

    public DevelopResponseDto() {
    }

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String recuperaSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public PlanoDev getPlano() {
        return plano;
    }

    public void setPlano(PlanoDev plano) {
        this.plano = plano;
    }

    public PerfilDto getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilDto perfil) {
        this.perfil = perfil;
    }
}
