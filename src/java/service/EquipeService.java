package service;

import java.util.List;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import model.Equipe;
import org.hibernate.exception.ConstraintViolationException;
import util.JpaUtil;

@Path("/{parameter: equipes}")
public class EquipeService {
    private EntityManager em = JpaUtil.getEntityManager();
    
    @Path("{competicao}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Equipe> listaTodos(@PathParam("competicao") long competicao){
        Query q = em.createQuery("select e from Equipe e where e.competicao.id = :competicao", Equipe.class);
        q.setParameter("competicao", competicao);
        return q.getResultList();
    }
    
    @Path("{competicao}/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Equipe> listaPeloId(@PathParam("id") long id, @PathParam("competicao") long competicao){
        Query q = em.createQuery("select e from Equipe e where e.id = :id and e.competicao.id = :competicao", Equipe.class);
        q.setParameter("id", id);
        q.setParameter("competicao", competicao);
        return q.getResultList();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response incluir(Equipe equipe) throws Exception{
        try{
            System.out.println(equipe.getCompeticao().getDescricao());
            em.getTransaction().begin();
            em.persist(equipe);
            em.getTransaction().commit();
            
            return Response.status(Response.Status.OK).entity("true").build();
        }catch(ConstraintViolationException cve){
            em.getTransaction().rollback();
            throw new Exception("Violação de Restrição de Integridade");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    
    @Path("{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response alterar(Equipe equipe){
        try{
            em.getTransaction().begin();
            equipe = em.merge(equipe);
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).entity("true").build();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    
    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluir(@PathParam("id") long id) {
        try {
            //localizando o registro a ser alterado
            Equipe equipe = em.find(Equipe.class, id);
            em.getTransaction().begin();
            em.remove(equipe); //efetua o delete
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
