package model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="participante")
@Entity
public class Participante implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(length = 11)
    private String cpf;
    
    @Column(nullable = false, length = 40)
    private String nome;
    
    @Column(nullable = false, length = 30)
    private String cidade;
    
    @Column(nullable = false, length = 30)
    private String bairro;
    
    @Column(nullable = false)
    private String rua;
    
    @Column(nullable = false)
    private String numero;
    
    @ManyToOne
    private Equipe equipe;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
    
    
}
