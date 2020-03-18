package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.Encaminhador;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EncaminhadorRepository extends AbstractRepository<Encaminhador, Long> {

    @Inject EntityManager em;

    public EncaminhadorRepository(EntityManager em) {
        super(em);
    }

    public List<Encaminhador> findAll(Long entidadeId) {
        List<Encaminhador> retorno = em.createQuery("SELECT model FROM Encaminhador model INNER JOIN FETCH model.entidade etd WHERE etd.id = :entidadeId ORDER BY model.nome", Encaminhador.class)
                .setParameter("entidadeId", entidadeId)
                .getResultList();
        return retorno;
    }

    public List<SelectValueVO> listSelect(Long entidadeId) {
        List<SelectValueVO> retorno = new ArrayList<>();
        em.createQuery("SELECT model FROM Encaminhador model WHERE model.entidade.id = :entidadeId AND model.ativo = 'S' ORDER BY model.nome",Encaminhador.class)
                .setParameter("entidadeId", entidadeId)
                .getResultList()
                .forEach(x -> retorno.add(new SelectValueVO(x.getId(), x.getNome())));
        return retorno;
    }


}
