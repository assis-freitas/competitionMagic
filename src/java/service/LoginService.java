package service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import model.Usuario;
import util.JpaUtil;

@Path("/{parameter: login}") //nome do caminho do webservice
/*
** Exemplo: http://localhost/estoqueservice/login/admin/123456
** Irá receber o usuário e a senha e retornará true ou false se estiver ok
** A query criptografa a senha recebida e verifica se é igual a senha que já está
** criptografada no SGBD
*/
public class LoginService {
//efetuando a conexao ao SGBD
    EntityManager em = JpaUtil.getEntityManager();

    @Path("{login}/{senha}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public boolean validaLogin(@PathParam("login") String login, @PathParam("senha") String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        boolean retorno = false;
        
        
        
        String sql = "select count(u.login) from Usuario u where u.login = :login and u.senha = :senha and u.status = 1";
        Query q = em.createQuery(sql);
        q.setParameter("login", login);
        String senhaCriptografada = new util.Util().criptografa(senha);
        
        q.setParameter("senha", senhaCriptografada);
        Long quantidade = (Long) q.getSingleResult();
        
        if (quantidade > 0) { //se o count retornou 1 é que o usuário existe no SGBD
            retorno = true;
        }
        return retorno;
    }
}