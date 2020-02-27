package br.com.itarocha.hospedagem.controller;

import br.com.itarocha.hospedagem.dto.HospedagemFullVO;
import br.com.itarocha.hospedagem.dto.HospedagemVO;
import br.com.itarocha.hospedagem.dto.HospedeVO;
import br.com.itarocha.hospedagem.dto.ResponseReturn;
import br.com.itarocha.hospedagem.dto.hospedagem.*;
import br.com.itarocha.hospedagem.exception.ValidationException;
import br.com.itarocha.hospedagem.report.RelatorioAtendimentos;
import br.com.itarocha.hospedagem.service.HospedagemService;
import br.com.itarocha.hospedagem.service.PlanilhaGeralService;
import br.com.itarocha.hospedagem.service.RelatorioGeralService;
import br.com.itarocha.hospedagem.validation.ItaValidator;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
//import org.springframework.core.io.InputStreamResource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;

@Transactional
@Path("/api/app/hospedagem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "movimentação")
public class HospedagemController {

	@Inject
	HospedagemService service;
	
	@Inject
	RelatorioGeralService relatorioService;

	@Inject	Validator validator;

	@POST
	////@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response gravar(@RequestBody HospedagemVO model) {
		ItaValidator<HospedagemVO> v = new ItaValidator<HospedagemVO>(model).validate(validator);
		
		if (model.getHospedes().size() == 0) {
			v.addError("id", "É necessário pelo menos um hóspede");
		} else {
			for (HospedeVO h : model.getHospedes()) {
				if ("T".equals(model.getTipoUtilizacao()) && (h.getAcomodacao() == null)) {
					v.addError("id", String.format("É necessário informar o Leito para o Hóspede [%s]", h.getPessoaNome()));
				}
				if ("T".equals(model.getTipoUtilizacao()) && (h.getAcomodacao() != null) &&
					(h.getAcomodacao().getLeitoId() != null) && 
					(model.getDataEntrada() != null) && (model.getDataPrevistaSaida() != null) )
				{
					Long leitoId = h.getAcomodacao().getLeitoId();
					LocalDate dataIni = model.getDataEntrada();
					LocalDate dataFim = model.getDataPrevistaSaida();
					Integer leitoNumero = h.getAcomodacao().getLeitoNumero();
					Integer quartoNumero = h.getAcomodacao().getQuartoNumero();
					
					if (!service.leitoLivreNoPeriodo(leitoId, dataIni, dataFim)) {
						v.addError("id", String.format("Quarto %s Leito %s está ocupado no perído", quartoNumero, leitoNumero ));
					}
				}
			}
		}
		
		if (!v.hasErrors() ) {
			return Response.status(BAD_REQUEST).entity(v.getErrors()).build();
		}
		
		try {
			service.create(model);
		    return Response.status(OK).entity(model).build();
		} catch (ValidationException e) {
			return Response.status(BAD_REQUEST).entity(e.getRe()).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	}

	@POST
	@Path("/mapa")
	////@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response mapa(@RequestBody MapaHospedagemRequest model)
	{
		MapaRetorno retorno = service.buildMapaRetorno(model.data);
		return Response.status(OK).entity(retorno).build();
	}

	@POST
	@Path("/mapa/linhas")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response mapaLinhas(@RequestBody MapaHospedagemRequest model)
	{
		MapaLinhas retorno = service.buildMapaLinhas(model.data);
		return Response.status(OK).entity(retorno).build();
	}

	@POST
	@Path("/mapa/hospedes")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response mapaHospedes(@RequestBody MapaHospedagemRequest model)
	{
		MapaHospedes retorno = service.buildMapaHospedes(model.data);
		return Response.status(OK).entity(retorno).build();
	}

	@POST
	@Path("/mapa/cidades")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response mapaCidades(@RequestBody MapaHospedagemRequest model)
	{
		MapaCidades retorno = service.buildMapaCidades(model.data);
		return Response.status(OK).entity(retorno).build();
	}

	@POST
	@Path("/mapa/quadro")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response mapaQuadro(@RequestBody MapaHospedagemRequest model)
	{
		MapaQuadro retorno = service.buildMapaQuadro(model.data);
		return Response.status(OK).entity(retorno).build();
	}

	@POST
	@Path("/planilha_geral")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response planilhaGeral(@RequestBody PeriodoRequest model)
	{
		ItaValidator<PeriodoRequest> v = new ItaValidator<PeriodoRequest>(model).validate(validator);
		
		if (model.dataIni == null) {
			v.addError("dataIni", "Data Inicial deve ser preenchida");
		}
		if (model.dataIni == null) {
			v.addError("dataFim", "Data Final deve ser preenchida");
		}
		
		if (!v.hasErrors() ) {
			return Response.status(BAD_REQUEST).entity(v.getErrors()).build();
		}

		try {
			RelatorioAtendimentos retorno = relatorioService.buildNovaPlanilha(model.dataIni, model.dataFim);
			return Response.status(OK).entity(retorno).build();
		} catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}

	}

	//https://grokonez.com/spring-framework/spring-boot/excel-file-download-from-springboot-restapi-apache-poi-mysql
	//@GetMapping(value = "/planilha_geral_arquivo")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	@POST
	@Path("/planilha_geral_arquivo")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response planilhaGeralExcel(@RequestBody PeriodoRequest model) throws IOException {
		ItaValidator<PeriodoRequest> v = new ItaValidator<PeriodoRequest>(model).validate(validator);
		
		if (model.dataIni == null) {
			v.addError("dataIni", "Data Inicial deve ser preenchida");
		}
		if (model.dataIni == null) {
			v.addError("dataFim", "Data Final deve ser preenchida");
		}
		
		if (!v.hasErrors() ) {
			return Response.status(BAD_REQUEST).entity(v.getErrors()).build();
		}
		
		RelatorioAtendimentos retorno = null;
		try {
			retorno = relatorioService.buildNovaPlanilha(model.dataIni, model.dataFim);
		} catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
		
		ByteArrayInputStream is = PlanilhaGeralService.toExcel(retorno);

		/*
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				IOUtils.copy(is, os);
				IOUtils.closeQuietly(is);
				IOUtils.closeQuietly(os);
			}
		};
		*/

		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream out) throws IOException, WebApplicationException {
				byte[] buffer = new byte[8192];
				int bytesRead;
				while ((bytesRead = is.read(buffer)) != -1) {
					out.write(buffer, 0, bytesRead);
				}
			}
		};


		return Response.status(OK)
				.type(MediaType.APPLICATION_OCTET_STREAM)
				.entity(stream)
				.header("Content-Disposition", "attachment; filename=planilha.xlsx")
				.header("Cache-Control", "no-cache, no-store, must-revalidate")
				.header("Pragma", "no-cache")
				.header("Expires", "0")
				.build();
				/*
				.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				//.contentLength(file.length())
				.headers(headers)
				.body(new InputStreamResource(in));

				 */
	}

	@POST
	@Path("/leitos_ocupados")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response leitosOcupados(@RequestBody HospedagemPeriodoRequest model)
	{

		try {
			List<OcupacaoLeito> retorno = service.getLeitosOcupadosNoPeriodo(model.hospedagemId, model.dataIni, model.dataFim);
			return Response.status(OK).entity(retorno).build();
		} catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	}

	@POST
	@Path("/mapa/alterar_hospede")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response alterarHospede(@RequestBody AlteracaoHospedeRequest model)
	{
		try {
			service.alterarTipoHospede(model.hospedeId, model.tipoHospedeId);
			return Response.status(OK).entity(new ResponseReturn("ok")).build();
		} catch(ValidationException e) {
			return Response.status(BAD_REQUEST).entity(e.getRe()).build();
		}
	}

	@POST
	@Path("/mapa/encerramento")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response encerramento(@RequestBody OperacoesRequest model)
	{
		try {
			service.encerrarHospedagem(model.hospedagemId, model.data);
			return Response.status(OK).entity(new ResponseReturn("ok")).build();
		} catch(ValidationException e) {
			return Response.status(BAD_REQUEST).entity(e.getRe()).build();
		}
	}
	
	@POST
	@Path("/mapa/renovacao")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response renovacao(@RequestBody OperacoesRequest model)
	{
		try {
			service.renovarHospedagem(model.hospedagemId, model.data);
			return Response.status(OK).entity(new ResponseReturn("ok")).build();
		} catch(ValidationException e) {
			return Response.status(BAD_REQUEST).entity(e.getRe()).build();
		}
	}

	@POST
	@Path("/remover_hospede")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response removerHospede(@RequestBody RemoverHospedeRequest model)
	{
		try {
			service.removerHospede(model.hospedagemId, model.hospedeId);
			return Response.status(OK).entity(new ResponseReturn("ok")).build();
		} catch(ValidationException e) {
			return Response.status(BAD_REQUEST).entity(e.getRe()).build();
		}
	}

	@POST
	@Path("/mapa/baixar")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response baixar(@RequestBody BaixaRequest model)
	{
		try {
			service.baixarHospede(model.hospedeId, model.data);
			return Response.status(OK).entity(new ResponseReturn("ok")).build();
		} catch(ValidationException e) {
			return Response.status(BAD_REQUEST).entity(e.getRe()).build();
		}
	}

	@POST
	@Path("/mapa/transferir")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response transferir(@RequestBody TransferenciaRequest model)
	{
		try {
			service.transferirHospede(model.hospedeId, model.leitoId, model.data);
			return Response.status(OK).entity(new ResponseReturn("ok")).build();
		} catch(ValidationException e) {
			return Response.status(BAD_REQUEST).entity(e.getRe()).build();
		}
	}

	@POST
	@Path("/mapa/adicionar")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response adicionarHospede(@RequestBody AdicionarHospedeRequest model)
	{
		try {
			service.adicionarHospede(model.hospedagemId, model.pessoaId, model.tipoHospedeId, model.leitoId, model.data);
			return Response.status(OK).entity(new ResponseReturn("ok")).build();
		} catch(ValidationException e) {
			return Response.status(BAD_REQUEST).entity(e.getRe()).build();
		}
	}

	@POST
	@Path("/mapa/hospedagem_info")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response getHospedagemInfo(@RequestBody HospdeagemInfoRequest model)
	{
		HospedagemFullVO h = service.getHospedagemPorHospedeLeitoId(model.hospedagemId);
		return Response.status(OK).entity(h).build();
	}

	@DELETE
	@Path("{id}")
	//@PreAuthorize("hasAnyRole('ADMIN','ROOT')")
	public Response excluir(@PathParam("id") Long id) {
		try {
			service.excluirHospedagem(id);
			return Response.status(OK).entity(new ResponseReturn("sucesso")).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	 }

	private static class MapaHospedagemRequest{
		public LocalDate data;
	}

	private static class HospedagemPeriodoRequest{
		public Long hospedagemId;
		public LocalDate dataIni;
		public LocalDate dataFim;
	}
	
	private static class PeriodoRequest{
		public LocalDate dataIni;
		public LocalDate dataFim;
	}

	private static class BaixaRequest{
		public LocalDate data;
		public Long hospedeId;
	}
	
	private static class TransferenciaRequest{
		public LocalDate data;
		public Long hospedeId;
		public Long leitoId;
	}
	
	private static class AlteracaoHospedeRequest {
		public Long hospedeId;
		public Long tipoHospedeId;
	}
	
	private static class AdicionarHospedeRequest{
		public Long hospedagemId;
		public Long pessoaId;
		public Long tipoHospedeId;
		public LocalDate data;
		public Long leitoId;
	}

	private static class RemoverHospedeRequest{
		public Long hospedagemId;
		public Long hospedeId;
	}
	
	private static class OperacoesRequest{
		public LocalDate data;
		public Long hospedagemId;
	}

	private static class HospdeagemInfoRequest{
		public Long hospedagemId;
	}
}

