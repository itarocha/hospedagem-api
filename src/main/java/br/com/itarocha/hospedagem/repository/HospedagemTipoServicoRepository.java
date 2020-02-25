package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.HospedagemTipoServico;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class HospedagemTipoServicoRepository extends AbstractRepository<HospedagemTipoServico, Long> {

    @Inject
    EntityManager em;

    public HospedagemTipoServicoRepository(EntityManager em) {
        super(em);
    }

}
