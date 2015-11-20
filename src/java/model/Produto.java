package model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonProperty;
@XmlRootElement(name="produtos")
@Entity // Define que será criado uma tabela no SGBD

public class Produto implements Serializable {
    // atributo para a serializacao do objeto
    private static final long serialVersionUID = 1L;
    //atributos da classe
    @Id @GeneratedValue
    private long id;
    @Column(nullable=false, length=100, unique=true)
    private String descricao;
    @Column(name="preco", 
            columnDefinition = "decimal(10,2) default '0'")
    private Double preco;
    @JsonProperty("grupo")
    @ManyToOne //definimos que muitos produtos podem ter 1 grupo
    @JoinColumn(name="grupo_id")
    private Grupo grupo;
    private boolean status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;
     /* Tratando a data de inclusao e data de alteracao */
    @PrePersist
    protected void onCreate() {
        dataCriacao = new Date();
    }
 
    @PreUpdate
    protected void onUpdate(){
        dataAlteracao = new Date();
    }
    // Alt+Insert, selecionar getter e setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
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
