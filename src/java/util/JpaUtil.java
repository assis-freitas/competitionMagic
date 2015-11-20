package util;

import javax.persistence.*;


public class JpaUtil {
    /*
 chamamos a classe de Persistencia. o valor default ele procura
 por um arquivo chamado persistence.xml dentro da pasta meta-inf
    */  
private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("default");
// static indica que o atributo pertence a classe e nao ao objeto
public static EntityManager getEntityManager(){
    return emf.createEntityManager();
}
}
