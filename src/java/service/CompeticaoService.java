package service;

import java.util.*;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import model.Competicao;
import org.hibernate.exception.ConstraintViolationException;
import util.JpaUtil;

@Path("/{parameter: competicoes}") //nome do caminho do webservice
// Exemplo: http://localhost/estoqueservice/grupos
public class CompeticaoService {
//efetuando a conexao ao SGBD

    EntityManager em = JpaUtil.getEntityManager();

    
    @Path("{usuario}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Competicao> listaTodos(@PathParam("usuario") long usuario) {
        ArrayList<Competicao> listagem;
        Query sql = em.createQuery(
                "select c from Competicao c inner join c.usuarios u where u.id = :usuario", Competicao.class);
        sql.setParameter("usuario", usuario);
        listagem = (ArrayList<Competicao>) sql.getResultList();
        return listagem;
    }

    @Path("{usuario}/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Competicao> listaPeloId(@PathParam("id") long id, @PathParam("usuario") long usuario) {
        ArrayList<Competicao> listagem;
        Query sql = em.createQuery(
                "select c from Competicao c inner join c.usuarios u where c.id = :id and u.id = :usuario", Competicao.class);
        sql.setParameter("id", id);
        sql.setParameter("usuario", usuario);
        listagem = (ArrayList<Competicao>) sql.getResultList();
        return listagem;
    }
    /* Verbo POST (insert) */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response incluir(Competicao competicao) throws Exception {
        //iniciamos a transacao no SGBD
        try {
            em.getTransaction().begin();
            em.persist(competicao); //gerará o insert no SGBD
            em.getTransaction().commit();
            //retornamos para o client o status OK (200)
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } 
        catch (ConstraintViolationException cve) {           
            em.getTransaction().rollback();
            throw new Exception("Violação de Restrição de Integridade!");            
        }
        catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    /* Verbo PUT (update) - E necessario receber o ID!*/

    @Path("{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response alterar(Competicao competicao) {
        try {
            em.getTransaction().begin();
            competicao = em.merge(competicao); //gerará o update no SGBD
            em.getTransaction().commit();
            System.out.println("Competição " + competicao.getDescricao() + " alterado com sucesso");
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    /* Verbo DELETE */

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluir(@PathParam("id") long id) {
        try {
            //localizando o registro a ser alterado
            Competicao competicao = em.find(Competicao.class, id);
            em.getTransaction().begin();
            em.remove(competicao); //efetua o delete
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
