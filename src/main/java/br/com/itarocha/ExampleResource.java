package br.com.itarocha;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Transactional
public class ExampleResource {

    @Inject EntityManager em;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        /*
        TipoHospede th = new TipoHospede();
        th.setDescricao(Instant.now().toString());
        em.persist(th);
        */
        return "hello";
    }
}