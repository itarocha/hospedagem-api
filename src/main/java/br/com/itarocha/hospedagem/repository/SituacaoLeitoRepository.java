package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.SituacaoLeito;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class SituacaoLeitoRepository extends AbstractRepository<SituacaoLeito, Long> {

    @Inject
    EntityManager em;

    public SituacaoLeitoRepository(EntityManager em) {
        super(em);
    }

}
