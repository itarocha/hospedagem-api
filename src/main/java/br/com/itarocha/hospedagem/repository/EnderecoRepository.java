package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.Endereco;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class EnderecoRepository extends AbstractRepository<Endereco, Long> {

    @Inject
    EntityManager em;

    public EnderecoRepository(EntityManager em) {
        super(em);
    }

}
