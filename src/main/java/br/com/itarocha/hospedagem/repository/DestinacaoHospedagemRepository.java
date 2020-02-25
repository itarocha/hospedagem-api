package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.DestinacaoHospedagem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class DestinacaoHospedagemRepository extends AbstractRepository<DestinacaoHospedagem, Long> {

    @Inject
    EntityManager em;

    public DestinacaoHospedagemRepository(EntityManager em) {
        super(em);
    }

    public List<DestinacaoHospedagem> getAllNative(){
        return em.createNativeQuery("SELECT tb.* FROM destinacao_hospedagem tb ORDER BY tb.descricao DESC", DestinacaoHospedagem.class)
                .getResultList();
    }

    public List<SelectValueVO> getListSelect(){
        return super.getListSelect("id", "descricao", "descricao");
    }
}
