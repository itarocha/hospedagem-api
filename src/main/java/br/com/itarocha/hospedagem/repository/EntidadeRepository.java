package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.Entidade;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
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

    /*
    public List<Entidade> getAll(String orderField){

        //return em.createQuery("SELECT model FROM Entidade model LEFT JOIN FETCH model.encaminhadores ORDER BY model.nome", Entidade.class)
        //        .getResultList();
        System.out.println("chamando da própria classe Entidade");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entidade> root = cb.createQuery(Entidade.class);
        Root<Entidade> c = root.from(Entidade.class);

        //c.join("encaminhadores", JoinType.LEFT);
        c.fetch("encaminhadores", JoinType.LEFT);
        //c.fetch("observacoes", JoinType.LEFT);

        //c.join(c.getJoins(). _.encaminhadores, JoinType.INNER);
        root.select(c);
        root.orderBy(cb.asc(c.get(orderField)));
        List<Entidade> retorno = em.createQuery(root).getResultList();
        return retorno;
    }
    */

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
