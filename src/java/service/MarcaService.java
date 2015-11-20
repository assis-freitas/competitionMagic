package service;

import java.util.*;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import model.Marca;
import util.JpaUtil;

@Path("/{parameter: marcas|brands}") //nome do caminho do webservice
// Exemplo: http://localhost/estoqueservice/marcas
public class MarcaService {
//efetuando a conexao ao SGBD

    EntityManager em = JpaUtil.getEntityManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Marca> listaTodos() {
        ArrayList<Marca> listagem;
        Query sql = em.createQuery(
                "select g from Marca g order by g.descricao", Marca.class);
        listagem = (ArrayList<Marca>) sql.getResultList();
        return listagem;
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Marca> listaPeloId(@PathParam("id") long id) {
        ArrayList<Marca> listagem;
        Query sql = em.createQuery(
                "select g from Marca g where g.id = :id", Marca.class);
        sql.setParameter("id", id);
        listagem = (ArrayList<Marca>) sql.getResultList();
        return listagem;
    }
    /* Verbo POST (insert) */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response incluir(Marca marca) {
        //iniciamos a transacao no SGBD
        try {
            em.getTransaction().begin();
            em.persist(marca); //gerará o insert no SGBD
            em.getTransaction().commit();
            //retornamos para o client o status OK (200)
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    /* Verbo PUT (update) - E necessario receber o ID!*/

    @Path("{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response alterar(Marca marca) {
        try {
            em.getTransaction().begin();
            marca = em.merge(marca); //gerará o update no SGBD
            em.getTransaction().commit();
            System.out.println("Marca " + marca.getDescricao() + "alterado!");
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
            Marca marca = em.find(Marca.class, id);
            em.getTransaction().begin();
            em.remove(marca); //efetua o delete
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
