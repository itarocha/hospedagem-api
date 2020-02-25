package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.Pessoa;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@ApplicationScoped
public class PessoaRepository extends AbstractRepository<Pessoa, Long> {

    @Inject
    EntityManager em;

    public PessoaRepository(EntityManager em) {
        super(em);
    }

    public boolean pessoaCadastradaPorCampo(Long pessoaId, String campo, String valor) {

        if ("".equals(valor) || valor == null) {
            return false;
        }
        Long qtd = 0L;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT COUNT(*) ");
            sb.append("FROM pessoa ");
            sb.append(String.format("WHERE %s = :%s ",campo,campo));
            sb.append("AND id <> :pessoaId ");

            Query query = em.createNativeQuery(sb.toString());
            qtd = ((Number)query.setParameter("pessoaId", pessoaId)
                    .setParameter(campo, valor)
                    .getSingleResult()).longValue();
        } catch (Exception e) {
            return false;
        }

        return qtd > 0;
    }
}
