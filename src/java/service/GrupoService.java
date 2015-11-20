package service;

import java.util.*;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import model.Grupo;
import org.hibernate.exception.ConstraintViolationException;
import util.JpaUtil;

@Path("/{parameter: grupos|groups}") //nome do caminho do webservice
// Exemplo: http://localhost/estoqueservice/grupos
public class GrupoService {
//efetuando a conexao ao SGBD

    EntityManager em = JpaUtil.getEntityManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Grupo> listaTodos() {
        ArrayList<Grupo> listagem;
        Query sql = em.createQuery(
                "select g from Grupo g order by g.descricao", Grupo.class);
        listagem = (ArrayList<Grupo>) sql.getResultList();
        return listagem;
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Grupo> listaPeloId(@PathParam("id") long id) {
        ArrayList<Grupo> listagem;
        Query sql = em.createQuery(
                "select g from Grupo g where g.id = :id", Grupo.class);
        sql.setParameter("id", id);
        listagem = (ArrayList<Grupo>) sql.getResultList();
        return listagem;
    }
    /* Verbo POST (insert) */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response incluir(Grupo grupo) throws Exception {
        //iniciamos a transacao no SGBD
        try {
            em.getTransaction().begin();
            em.persist(grupo); //gerará o insert no SGBD
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
    public Response alterar(Grupo grupo) {
        try {
            em.getTransaction().begin();
            grupo = em.merge(grupo); //gerará o update no SGBD
            em.getTransaction().commit();
            System.out.println("Grupo " + grupo.getDescricao() + "alterado!");
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
            Grupo grupo = em.find(Grupo.class, id);
            em.getTransaction().begin();
            em.remove(grupo); //efetua o delete
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
