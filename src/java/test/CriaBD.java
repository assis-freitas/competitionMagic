package test;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import util.JpaUtil;
import model.*;
import service.CompeticaoService;

public class CriaBD {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            //inserindo um novo grupo
            em.getTransaction().begin();
            
            Usuario usuario = new Usuario();
            usuario.setLogin("assis_freitas");
            usuario.setSenha("123");
            usuario.setStatus(true);
            em.persist(usuario);
            
            em.getTransaction().commit();
            
        } catch (Exception e) {
            System.out.println("Erro ao conectar no BD: "
                    + e.getMessage());
        }
    }

}
