package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.Hospede;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class HospedeRepository extends AbstractRepository<Hospede, Long> {

    @Inject
    EntityManager em;

    public HospedeRepository(EntityManager em) {
        super(em);
    }

}
