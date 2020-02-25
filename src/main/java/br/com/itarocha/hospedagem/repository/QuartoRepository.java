package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.Leito;
import br.com.itarocha.hospedagem.model.Quarto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class QuartoRepository extends AbstractRepository<Quarto, Long> {

    @Inject
    EntityManager em;

    public QuartoRepository(EntityManager em) {
        super(em);
    }

    public Collection<Leito> existeOutroLeitoComEsseNumero(Long leito_id, Long quartoId, Integer numero){
        return em.createQuery("SELECT o FROM Leito o WHERE (o.quarto.id = :quartoId) AND (o.id <> :id) AND (o.numero = :numero)")
                .setParameter("quartoId", quartoId)
                .setParameter("id", leito_id)
                .setParameter("numero", numero)
                .getResultList();
    }

    public Collection<Leito> existeOutroLeitoComEsseNumero(Long quartoId, Integer numero){
        return em.createQuery("SELECT o FROM Leito o WHERE (o.quarto.id = :quartoId) AND (o.numero = :numero)")
                .setParameter("quartoId", quartoId)
                .setParameter("numero", numero)
                .getResultList();
    }


    public List<Quarto> findAllOrderByQuartoNumero() {
        return em.createQuery("SELECT q FROM Quarto q ORDER BY q.numero")
                .getResultList();
    }

    public List<Quarto> existeOutroQuartoComEsseNumero(Long id, Integer numero){
        return em.createQuery("SELECT o FROM Quarto o WHERE (o.id <> :id) AND (o.numero = :numero)")
                .setParameter("id", id)
                .setParameter("numero", numero)
                .getResultList();

    }

    public List<Quarto> existeOutroQuartoComEsseNumero(Integer numero){
        return em.createQuery("SELECT o FROM Quarto o WHERE (o.numero = :numero)")
                .setParameter("numero", numero)
                .getResultList();
    }

    public List<Quarto> findByDestinacaoHospedagemId(Long id){
        return em.createQuery("SELECT q FROM Quarto q JOIN q.destinacoes d FETCH ALL PROPERTIES WHERE d.id = :id ORDER BY q.numero")
                .setParameter("id", id)
                .getResultList();
    }
}
