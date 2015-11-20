package model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuario")
@Entity // Define que será criado uma tabela no SGBD
//definindo o map das colunas disponiveis da nossa classe
@SqlResultSetMapping(
        name = "colunasUsuario",
        columns = {
            @ColumnResult(name = "id"),
            @ColumnResult(name = "login"),
            @ColumnResult(name = "status"),
            @ColumnResult(name = "competicao_id")}
)

public class Usuario implements Serializable {

    // atributo para a serializacao do objeto
    private static final long serialVersionUID = 1L;

    //atributos da classe
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, length = 100, unique = true)
    private String login;
    private String senha;
    private boolean status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;
    @ManyToOne
    private Competicao competicao;
    /* Tratando a data de inclusao e data de alteracao */

    @PrePersist
    protected void onCreate() {
        dataCriacao = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAlteracao = new Date();
    }
    
    public Competicao getCompeticao() {
        return competicao;
    }

    // Alt+Insert, selecionar getter e setter
    public void setCompeticao(Competicao competicao) {    
        this.competicao = competicao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String senhaCriptografada = new util.Util().criptografa(senha);
        this.senha = senhaCriptografada;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

}
