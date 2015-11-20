package model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="grupos")
@Entity // Define que será criado uma tabela no SGBD
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private long id;
    
    @NotNull(message="A descrição do grupo é obrigatória!")
    @Size(min=3, max=50, message=
            "A descrição deve ter entre {min} e {max} caracteres")
    @Column(nullable=false, length=50, unique=true)
    private String descricao;
    
    @Column(nullable = true, name="status")
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
