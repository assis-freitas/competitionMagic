package service;

import java.util.*;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import model.Produto;
import util.JpaUtil;

@Path("/{parameter: produtos|products}") //nome do caminho do webservice
// Exemplo: http://localhost/estoqueservice/produtos
public class ProdutoService {
//efetuando a conexao ao SGBD

    EntityManager em = JpaUtil.getEntityManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produto> listaTodos() {
        ArrayList<Produto> listagem;
        Query sql = em.createQuery(
                "select g from Produto g order by g.descricao", Produto.class);
        listagem = (ArrayList<Produto>) sql.getResultList();
        return listagem;
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produto> listaPeloId(@PathParam("id") long id) {
        ArrayList<Produto> listagem;
        Query sql = em.createQuery(
                "select g from Produto g where g.id = :id", Produto.class);
        sql.setParameter("id", id);
        listagem = (ArrayList<Produto>) sql.getResultList();
        return listagem;
    }
    /* Verbo POST (insert) */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response incluir(Produto produto) {
        //iniciamos a transacao no SGBD
        try {
            em.getTransaction().begin();
            em.persist(produto); //gerará o insert no SGBD
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
    public Response alterar(Produto produto) {
        try {
            em.getTransaction().begin();
            produto = em.merge(produto); //gerará o update no SGBD
            em.getTransaction().commit();
            System.out.println("Produto " + produto.getDescricao() + "alterado!");
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
            Produto produto = em.find(Produto.class, id);
            em.getTransaction().begin();
            em.remove(produto); //efetua o delete
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
