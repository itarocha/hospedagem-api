package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.Entidade;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class EntidadeRepository extends AbstractRepository<Entidade, Long> {

    @Inject
    EntityManager em;

    public EntidadeRepository(EntityManager em) {
        super(em);
    }

    public List<SelectValueVO> listSelect() {
        return super.getListSelect("id", "nome", "nome");
    }

    public List<Entidade> consultar(String texto) {
        return em.createQuery("SELECT model FROM Entidade model WHERE lower(model.nome) LIKE :texto ORDER BY model.nome", Entidade.class)
                .setParameter("texto", "%"+texto.toLowerCase()+"%")
                .getResultList();
    }

    public boolean entidadeCadastradaPorCampo(Long entidadeId, String campo, String valor) {

        if ("".equals(valor) || valor == null) {
            return false;
        }
        Long qtd = 0L;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT COUNT(*) ");
            sb.append("FROM entidade ");
            sb.append(String.format("WHERE %s = :%s ",campo,campo));
            sb.append("AND id <> :entidadeId ");

            Query query = em.createNativeQuery(sb.toString());
            qtd = ((Number)query.setParameter("entidadeId", entidadeId)
                    .setParameter(campo, valor)
                    .getSingleResult()).longValue();
        } catch (Exception e) {
            return false;
        }

        return qtd > 0;
    }

}
