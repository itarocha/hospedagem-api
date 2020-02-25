package br.com.itarocha.hospedagem;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.itarocha.hospedagem.modelteste.Produto;

@Transactional
@Path("/produtos")
public class ProdutoResource {

    @Inject EntityManager em;

    @GET
    @Produces(value= MediaType.APPLICATION_JSON)
    public List<Produto> getProdutos(){
        return em.createQuery("select p from Produto p", Produto.class).getResultList();
        //em.persist(p)
        //Usar DTOs
    }


}