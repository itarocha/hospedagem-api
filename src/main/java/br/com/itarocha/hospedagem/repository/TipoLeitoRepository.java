package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.TipoLeito;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class TipoLeitoRepository extends AbstractRepository<TipoLeito, Long> {

    @Inject
    EntityManager em;

    public TipoLeitoRepository(EntityManager em) {
        super(em);
    }

}
