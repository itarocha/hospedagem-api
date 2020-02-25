package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.HospedeLeito;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class HospedeLeitoRepository extends AbstractRepository<HospedeLeito, Long> {

    @Inject EntityManager em;

    public HospedeLeitoRepository(EntityManager em) {
        super(em);
    }

    public List<HospedeLeito> findUltimoByHospedagemId(Long hospedagemId){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT   hl.* \n");
        sb.append("FROM     hospede_leito hl \n");
        sb.append("WHERE    hl.hospede_id IN (SELECT hpd.id FROM hospede hpd WHERE hpd.hospedagem_id = :hospedagemId) \n");
        sb.append("AND      hl.data_entrada = (SELECT MAX(outro.data_entrada) FROM hospede_leito outro WHERE outro.hospede_id = hl.hospede_id) \n");
        sb.append("ORDER BY hl.data_entrada DESC");
        return em.createNativeQuery(sb.toString(), HospedeLeito.class)
                .setParameter("hospedagemId", hospedagemId)
                .getResultList();
    }

    // Retorna só um
    public List<HospedeLeito> findUltimoByHospedeId(Long hospedeId){
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT   hl.* \n");
        sb.append("FROM     hospede_leito hl \n");
        sb.append("WHERE    hl.hospede_id = :hospedeId \n");
        sb.append("AND      hl.data_entrada = (SELECT MAX(outro.data_entrada) FROM hospede_leito outro WHERE outro.hospede_id = hl.hospede_id) \n");
        sb.append("ORDER BY hl.data_entrada");

        return em.createNativeQuery(sb.toString(), HospedeLeito.class)
                .setParameter("hospedeId", hospedeId)
                .getResultList(); // getSingleResult ???
    }

    // Retorna se existe alguma hospedagem no leito no período
    public List<HospedeLeito> findByLeitoNoPeriodo(Long leitoId, LocalDate dataIni, LocalDate dataFim){
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT hl.* \n");
        sb.append("FROM   hospede_leito hl \n");
        sb.append("WHERE  (((hl.data_entrada BETWEEN :dataIni AND :dataFim) OR (hl.data_saida BETWEEN :dataIni AND :dataFim)) \n");
        sb.append("OR     ((hl.data_entrada <= :dataIni) AND (hl.data_saida >= :dataFim))) \n");
        sb.append("AND      hl.leito_id = :leitoId ");

        return em.createNativeQuery(sb.toString(), HospedeLeito.class)
                .setParameter("dataIni", dataIni)
                .setParameter("dataFim", dataFim)
                .setParameter("leitoId", leitoId)
                .getResultList(); // getSingleResult ???
    }

    public Long countHospedesNaoBaixadosByHospedagemId(Long hospedagemId){
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(h) FROM Hospede h WHERE h.baixado = 'N' AND h.hospedagem.id = :hospedagemId", Long.class);
        q.setParameter("hospedagemId", hospedagemId);
        return q.getSingleResult();
    }

    public LocalDate ultimaDataEntradaByHospedagemId(Long hospedagemId){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT     MAX(hl.data_entrada) data_entrada ");
        sb.append("FROM       hospede h ");
        sb.append("INNER JOIN hospede_leito hl ");
        sb.append("ON         hl.hospede_id = h.id ");
        sb.append("WHERE      h.hospedagem_id = :hospedagemId ");
        Query q = em.createNativeQuery(sb.toString(), LocalDate.class);
        q.setParameter("hospedagemId", hospedagemId);
        return (LocalDate)q.getSingleResult();
    }

    public LocalDate ultimaDataEntradaByHospedagemId(Long hospedagemId, Long hospedeId){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT     MAX(hl.data_entrada) data_entrada ");
        sb.append("FROM       hospede h ");
        sb.append("INNER JOIN hospede_leito hl ");
        sb.append("ON         hl.hospede_id = h.id ");
        sb.append("WHERE      h.hospedagem_id = :hospedagemId ");
        sb.append("AND        h.id = :hospedeId ");
        Query q = em.createNativeQuery(sb.toString(), LocalDate.class);
        q.setParameter("hospedagemId", hospedagemId);
        q.setParameter("hospedeId", hospedeId);
        return (LocalDate)q.getSingleResult();
    }

    public List<BigInteger> leitosNoPeriodo(LocalDate dataIni, LocalDate dataFim){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT     DISTINCT hl.leito_id ");
        sb.append("FROM       hospede_leito hl ");
        sb.append("WHERE      (((hl.data_entrada BETWEEN :dataIni AND :dataFim) OR (hl.data_saida BETWEEN :dataIni AND :dataFim)) ");
        sb.append("OR          ((hl.data_entrada <= :dataIni) AND (hl.data_saida >= :dataFim))) ");
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("dataIni", dataIni);
        q.setParameter("dataFim", dataFim);
        return (List<BigInteger>)q.getResultList();
    }

    public List<BigInteger> leitosNoPeriodoPorHospedagem(Long hospedagemId, LocalDate dataIni, LocalDate dataFim){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT     DISTINCT hl.leito_id ");
        sb.append("FROM       hospede_leito hl ");
        sb.append("INNER JOIN hospede h ");
        sb.append("ON         h.id = hl.hospede_id ");
        sb.append("WHERE      h.hospedagem_id = :hospedagemId ");
        sb.append("AND        (((hl.data_entrada BETWEEN :dataIni AND :dataFim) OR (hl.data_saida BETWEEN :dataIni AND :dataFim)) ");
        sb.append("OR          ((hl.data_entrada <= :dataIni) AND (hl.data_saida >= :dataFim))) ");

        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("hospedagemId", hospedagemId);
        q.setParameter("dataIni", dataIni);
        q.setParameter("dataFim", dataFim);
        return (List<BigInteger>)q.getResultList();
    }

    // APROVADO!!!
    public boolean pessoaLivreNoPeriodo(Long pessoaId, LocalDate dataIni, LocalDate dataFim) {
        StringBuilder sb = new StringBuilder();

        // Verificação em Hospedagem total (possui leito)
        sb.append("SELECT     count(*) ");
        sb.append("FROM       hospede h ");
        sb.append("INNER JOIN hospede_leito hl ");
        sb.append("ON         hl.hospede_id = h.id ");
        sb.append("WHERE      h.pessoa_id = :pessoaId  ");
        sb.append("AND        (((hl.data_entrada BETWEEN :dataIni AND :dataFim) OR (hl.data_saida BETWEEN :dataIni AND :dataFim))  ");
        sb.append("OR         ((hl.data_entrada <= :dataIni) AND (hl.data_saida >= :dataFim)))  ");

        Query query = em.createNativeQuery(sb.toString());
        Long qtd = ((Number)query.setParameter("pessoaId", pessoaId)
                .setParameter("dataIni", dataIni)
                .setParameter("dataFim", dataFim)
                .getSingleResult()).longValue();

        StringBuilder sbP = new StringBuilder();
        sbP.append("SELECT     count(*) ");
        sbP.append("FROM       hospedagem hpd ");
        sbP.append("INNER JOIN hospede h ");
        sbP.append("ON         h.hospedagem_id = hpd.id ");
        sbP.append("WHERE      h.pessoa_id = :pessoaId ");
        sbP.append("AND        hpd.tipo_utilizacao = :tipoUtilizacao ");
        sbP.append("AND        (((hpd.data_entrada BETWEEN :dataIni AND :dataFim) OR (COALESCE(hpd.data_efetiva_saida,hpd.data_prevista_saida) BETWEEN :dataIni AND :dataFim)) ");
        sbP.append("OR          ((hpd.data_entrada <= :dataIni) AND (COALESCE(hpd.data_efetiva_saida,hpd.data_prevista_saida) >= :dataFim))) ");

        Query queryP = em.createNativeQuery(sbP.toString());
        Long qtdP = ((Number)queryP.setParameter("pessoaId", pessoaId)
                .setParameter("tipoUtilizacao", "P")
                .setParameter("dataIni", dataIni)
                .setParameter("dataFim", dataFim)
                .getSingleResult()).longValue();


        return ((qtd <= 0) && (qtdP <= 0));
    }

    // APROVADO!!!
    public boolean leitoLivreNoPeriodo(Long leitoId, LocalDate dataIni, LocalDate dataFim) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(*) ");
        sb.append("FROM   hospede_leito hl ");
        sb.append("WHERE  hl.leito_id = :leitoId  ");
        sb.append("AND    (((hl.data_entrada BETWEEN :dataIni AND :dataFim) OR (hl.data_saida BETWEEN :dataIni AND :dataFim))  ");
        sb.append("OR     ((hl.data_entrada <= :dataIni) AND (hl.data_saida >= :dataFim)))  ");

        Query query = em.createNativeQuery(sb.toString());
        Long qtd = ((Number)query.setParameter("leitoId", leitoId)
                .setParameter("dataIni", dataIni)
                .setParameter("dataFim", dataFim)
                .getSingleResult()).longValue();

        return qtd <= 0;
    }

    // APROVADO!!!!
    public List<BigInteger> hospedagensNoPeriodo(Long leitoId, LocalDate dataIni, LocalDate dataFim) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT h.hospedagem_id ");
        sb.append("FROM   hospede_leito hl ");
        sb.append("INNER JOIN hospede h ");
        sb.append("ON         h.id = hl.hospede_id ");
        sb.append("WHERE  hl.leito_id = :leitoId  ");
        sb.append("AND    (((hl.data_entrada BETWEEN :dataIni AND :dataFim) OR (hl.data_saida BETWEEN :dataIni AND :dataFim))  ");
        sb.append("OR     ((hl.data_entrada <= :dataIni) AND (hl.data_saida >= :dataFim)))  ");

        Query query = em.createNativeQuery(sb.toString());
        List<BigInteger> lista = (List<BigInteger>)query.setParameter("leitoId", leitoId)
                .setParameter("dataIni", dataIni)
                .setParameter("dataFim", dataFim)
                .getResultList();
        return lista;
    }

}
