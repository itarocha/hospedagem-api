package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.Leito;
import br.com.itarocha.hospedagem.model.Logico;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class LeitoRepository extends AbstractRepository<Leito, Long> {

    @Inject
    EntityManager em;

    public LeitoRepository(EntityManager em) {
        super(em);
    }

    public List<Leito> findByQuartoId(Long id) {
        return em.createQuery("SELECT model FROM Leito model WHERE model.quarto.id = :id", Leito.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Leito> findAllWhereDisponivel(Logico situacao) {
        return em.createQuery("SELECT model " +
                                "FROM Leito model " +
                                "WHERE model.situacao.disponivel = :disponivel " +
                                "ORDER BY model.quarto.numero, model.numero", Leito.class)
                .setParameter("disponivel", situacao)
                .getResultList();
    }

    public void deleteWhereQuartoId(Long id) {
        int qtd = em.createQuery("DELETE FROM Leito model WHERE model.quarto.id = :quartoId ")
                .setParameter("quartoId", id)
                .executeUpdate();
        System.out.println(String.format("Foram excluídos %d quartos", qtd));
    }

}
