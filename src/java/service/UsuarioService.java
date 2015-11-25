package service;

import java.util.*;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import model.Usuario;
import util.JpaUtil;

@Path("/{parameter: usuarios|users}") //nome do caminho do webservice
// Exemplo: http://localhost/estoqueservice/usuarios
public class UsuarioService {
//efetuando a conexao ao SGBD

    EntityManager em = JpaUtil.getEntityManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> listaTodos() {
        ArrayList<Usuario> listagem;

        Query sql = em.createQuery("select u from Usuario u", Usuario.class);

        listagem = (ArrayList<Usuario>) sql.getResultList();
        return listagem;
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> listaPeloId(@PathParam("id") long id) {
        ArrayList<Usuario> listagem;
        Query sql = em.createNativeQuery("select u.id, u.login, u.status, u.competicao_id from Usuario u where u.id = :id",
                "colunasUsuario");
        sql.setParameter("id", id);
        listagem = (ArrayList<Usuario>) sql.getResultList();
        return listagem;
    }
    /* Verbo POST (insert) */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response incluir(Usuario usuario) {
        //iniciamos a transacao no SGBD
        try {
            em.getTransaction().begin();
            em.persist(usuario); //gerará o insert no SGBD
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
    public Response alterar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            usuario = em.merge(usuario); //gerará o update no SGBD
            em.getTransaction().commit();
            System.out.println("Usuario " + usuario.getLogin() + "alterado!");
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
            Usuario usuario = em.find(Usuario.class, id);
            em.getTransaction().begin();
            em.remove(usuario); //efetua o delete
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).
                    entity("true").build();
        } catch (Exception e) {
            System.out.println("Ocorreu o erro: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
