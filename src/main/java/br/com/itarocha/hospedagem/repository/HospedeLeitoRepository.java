package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.HospedeLeito;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class HospedeLeitoRepository extends AbstractRepository<HospedeLeito, Long> {

    @Inject
    EntityManager em;

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

    /*
    @Query(value = "SELECT COUNT(*) FROM  hospede h WHERE h.baixado = 'N' AND h.hospedagem_id = :hospedagemId", nativeQuery = true)
    Long countHospedesNaoBaixadosByHospedagemId(@Param("hospedagemId") Long hospedagemId);


    @Query(value = "SELECT     MAX(hl.data_entrada) data_entrada "+
            "FROM       hospede h "+
            "INNER JOIN hospede_leito hl "+
            "ON         hl.hospede_id = h.id "+
            "WHERE      h.hospedagem_id = :hospedagemId ", nativeQuery = true)
    LocalDate ultimaDataEntradaByHospedagemId(@Param("hospedagemId") Long hospedagemId);

    @Query(value = "SELECT     MAX(hl.data_entrada) data_entrada "+
            "FROM       hospede h "+
            "INNER JOIN hospede_leito hl "+
            "ON         hl.hospede_id = h.id "+
            "WHERE      h.hospedagem_id = :hospedagemId " +
            "AND        h.id = :hospedeId ", nativeQuery = true)
    LocalDate ultimaDataEntradaByHospedagemId(@Param("hospedagemId") Long hospedagemId, @Param("hospedeId") Long hospedeId);

    @Query(value = 	"SELECT     DISTINCT hl.leito_id "+
            "FROM       hospede_leito hl "+
            "WHERE      (((hl.data_entrada BETWEEN :dataIni AND :dataFim) OR (hl.data_saida BETWEEN :dataIni AND :dataFim)) "+
            "OR          ((hl.data_entrada <= :dataIni) AND (hl.data_saida >= :dataFim))) ",nativeQuery = true)
    List<BigInteger> leitosNoPeriodo(@Param("dataIni") LocalDate dataIni, @Param("dataFim") LocalDate dataFim);

    @Query(value = 	"SELECT     DISTINCT hl.leito_id "+
            "FROM       hospede_leito hl "+
            "INNER JOIN hospede h " +
            "ON         h.id = hl.hospede_id " +
            "WHERE      h.hospedagem_id = :hospedagemId " +
            "AND        (((hl.data_entrada BETWEEN :dataIni AND :dataFim) OR (hl.data_saida BETWEEN :dataIni AND :dataFim)) "+
            "OR          ((hl.data_entrada <= :dataIni) AND (hl.data_saida >= :dataFim))) ",nativeQuery = true)
    List<BigInteger> leitosNoPeriodoPorHospedagem(@Param("hospedagemId")Long hospedagemId, @Param("dataIni") LocalDate dataIni, @Param("dataFim") LocalDate dataFim);
    */

}
