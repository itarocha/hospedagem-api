package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.TipoHospede;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class TipoHospedeRepository extends AbstractRepository<TipoHospede, Long> {

    @Inject
    EntityManager em;

    public TipoHospedeRepository(EntityManager em) {
        super(em);
    }

}
