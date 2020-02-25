package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.TipoServico;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class TipoServicoRepository extends AbstractRepository<TipoServico, Long> {

    @Inject
    EntityManager em;

    public TipoServicoRepository(EntityManager em) {
        super(em);
    }

}
