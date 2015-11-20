
package model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="equipe")
@Entity
public class Equipe implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    private long id;
    
    @Column(nullable = false, length = 40)
    private String nome;
    
    @ManyToOne
    private Competicao competicao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Competicao getCompeticao() {
        return competicao;
    }

    public void setCompeticao(Competicao competicao) {
        this.competicao = competicao;
    }
    
    
}
