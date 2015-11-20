package test;

import javax.persistence.EntityManager;
import util.JpaUtil;
import model.*;


public class CriaBD {
    
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        try{
            //inserindo um novo grupo
            em.getTransaction().begin();
            Grupo grupo = new Grupo();
            grupo.setDescricao("Material de Embalagem");
            em.persist(grupo);
            
            Produto produto = new Produto();
            produto.setDescricao("Caixa de papel");
            produto.setPreco(4.90);
            produto.setStatus(true);
            produto.setGrupo(grupo);
            em.persist(produto);
            
            Usuario usuario = new Usuario();
            usuario.setLogin("admin");
            usuario.setSenha("123456");
            usuario.setStatus(true);
            em.persist(usuario);
            
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println("Erro ao conectar no BD: "+
                    e.getMessage());
        }
    }
    
}
