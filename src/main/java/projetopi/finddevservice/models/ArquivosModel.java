package projetopi.finddevservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "arquivos")
public class ArquivosModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private UsuarioModel usuario;

    @JsonIgnore // ignoramos no JSON pois não faz sentido retorna um vetor de bytes num JSON!
    @Column(length = 50 * 1024 * 1024) // 50 Mega Bytes
    private byte[] foto;

    @JsonIgnore // ignoramos no JSON pois não faz sentido retorna um vetor de bytes num JSON!
    @Column(length = 10 * 1024 * 1024) // 10 Mega Bytes
    private byte[] relatorioExcel;


    public ArquivosModel() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public byte[] getRelatorioExcel() {
        return relatorioExcel;
    }

    public void setRelatorioExcel(byte[] relatorioExcel) {
        this.relatorioExcel = relatorioExcel;
    }
}
