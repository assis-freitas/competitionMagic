/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import model.Participante;
import org.hibernate.exception.ConstraintViolationException;
import util.JpaUtil;

/**
 *
 * @author Assis de Freitas
 */

@Path("/{parameter: participantes}")
public class ParticipanteService {
    private EntityManager em = JpaUtil.getEntityManager();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Participante> listaTodos(){
        Query q = em.createQuery("select p from Participante p order by p.equipe", Participante.class);
        return q.getResultList();
    }
    
    @Path("{cpf}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Participante> listaPeloId(@PathParam("cpf") String cpf){
        Query q = em.createQuery("select p from Participante p where p.cpf = :cpf", Participante.class);
        q.setParameter("cpf", cpf);
        return q.getResultList();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response incluir(Participante participante) throws Exception{
        try{
            em.getTransaction().begin();
            em.persist(participante);
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
    
    @Path("{cpf}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response alterar(Participante participante){
        try{
            em.getTransaction().begin();
            participante = em.merge(participante);
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).entity("true").build();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    
    @Path("{cpf}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluir(@PathParam("cpf") String cpf) {
        try {
            //localizando o registro a ser alterado
            Participante participante = em.find(Participante.class, cpf);
            em.getTransaction().begin();
            em.remove(participante); //efetua o delete
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
