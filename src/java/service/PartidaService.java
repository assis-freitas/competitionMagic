package service;

import java.util.List;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import model.Partida;
import org.hibernate.exception.ConstraintViolationException;
import util.JpaUtil;

@Path("/{parameter: partidas}")
public class PartidaService {
    private EntityManager em = JpaUtil.getEntityManager();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Partida> listaTodos(){
        Query q = em.createQuery("select p from Partida p", Partida.class);
        return q.getResultList();
    }
    
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Partida> listaPeloId(@PathParam("id") long id){
        Query q = em.createQuery("select p from Partida p where p.id = :id", Partida.class);
        q.setParameter("id", id);
        return q.getResultList();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response incluir(Partida partida) throws Exception{
        try{
            em.getTransaction().begin();
            em.persist(partida);
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
    public Response alterar(Partida partida){
        try{
            em.getTransaction().begin();
            partida = em.merge(partida);
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
            Partida partida = em.find(Partida.class, id);
            em.getTransaction().begin();
            em.remove(partida); //efetua o delete
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
