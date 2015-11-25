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
//            Grupo grupo = new Grupo();
//            grupo.setDescricao("Material de Embalagem");
//            em.persist(grupo);
//            
//            Produto produto = new Produto();
//            produto.setDescricao("Caixa de papel");
//            produto.setPreco(4.90);
//            produto.setStatus(true);
//            produto.setGrupo(grupo);
//            em.persist(produto);
//            

            
            Usuario usuario = new Usuario();
            usuario.setLogin("assis_freitas");
            usuario.setSenha("123");
            usuario.setStatus(true);
            em.persist(usuario);
            
            ArrayList<Usuario> usuarioCompeticao = new ArrayList<Usuario>();
            usuarioCompeticao.add(usuario);
            
            Competicao competicao = new Competicao();
            competicao.setDescricao("Counter Strike: Global Ofensive");
            competicao.setUsuarios(usuarioCompeticao);
            em.persist(competicao);

            Equipe equipe = new Equipe();
            equipe.setNome("Killers");
            equipe.setCompeticao(competicao);
            em.persist(equipe);

            Equipe equipe2 = new Equipe();
            equipe2.setNome("The Fighters");
            equipe2.setCompeticao(competicao);
            em.persist(equipe2);

            Participante participante = new Participante();
            participante.setNome("Assis Freitas");
            participante.setCpf("1234578988");
            participante.setCidade("Itu");
            participante.setBairro("Centro");
            participante.setRua("Rua Santa Cruz");
            participante.setNumero("1086");
            participante.setEquipe(equipe);
            em.persist(participante);

            Participante participante2 = new Participante();
            participante2.setNome("João");
            participante2.setCpf("12345278988");
            participante2.setCidade("Itu");
            participante2.setBairro("Centro");
            participante2.setRua("Rua Santa Cruz");
            participante2.setNumero("1086");
            participante2.setEquipe(equipe);
            em.persist(participante2);

            Partida partida = new Partida();
            partida.setEquipeA(equipe);
            partida.setEquipeB(equipe2);
            partida.setPlacarA(0);
            partida.setPlacarB(0);
            partida.setRodada(1);
            partida.setCompeticao(competicao);
            em.persist(partida);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println("Erro ao conectar no BD: "
                    + e.getMessage());
        }
    }

}
