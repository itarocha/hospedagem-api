package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.Hospedagem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@ApplicationScoped
public class HospedagemRepository extends AbstractRepository<Hospedagem, Long> {

    @Inject
    EntityManager em;

    public HospedagemRepository(EntityManager em) {
        super(em);
    }

    public Hospedagem findHospedagemByHospedeLeitoId(Long hospedeLeitoId){
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT  DISTINCT hospedagem \n");
        sb.append("FROM   Hospedagem hospedagem FETCH ALL PROPERTIES \n");
        sb.append("WHERE  EXISTS( \n");
        sb.append("				SELECT      hospedeLeito \n");
        sb.append("				FROM        HospedeLeito hospedeLeito \n");
        sb.append("				INNER JOIN  hospedeLeito.hospede hospede \n");
        sb.append("				INNER JOIN  hospede.hospedagem hpd \n");
        sb.append("				WHERE       hpd.id = hospedagem.id \n");
        sb.append("				AND         hospedeLeito.id = :hospedeLeitoId \n");
        sb.append(") ");

        Query query = em.createQuery(sb.toString())
                .setParameter("hospedeLeitoId", hospedeLeitoId);
        return (Hospedagem) query.getResultList();
    }

    public Hospedagem findHospedagemByHospedagemId(Long hospedagemId){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  hospedagem \n");
        sb.append("FROM   Hospedagem hospedagem FETCH ALL PROPERTIES \n");
        sb.append("WHERE  hospedagem.id = :hospedagemId");

        Query query = em.createQuery(sb.toString())
                .setParameter("hospedagemId", hospedagemId);
        return (Hospedagem) query.getResultList();
    }
}
