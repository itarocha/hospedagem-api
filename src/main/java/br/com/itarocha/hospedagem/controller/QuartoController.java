package br.com.itarocha.hospedagem.controller;

import java.util.List;
import java.util.Optional;

import br.com.itarocha.hospedagem.dto.*;
import br.com.itarocha.hospedagem.validation.ItaValidator;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.itarocha.hospedagem.model.Leito;
import br.com.itarocha.hospedagem.model.Quarto;
import br.com.itarocha.hospedagem.service.DestinacaoHospedagemService;
import br.com.itarocha.hospedagem.service.EntidadeService;
import br.com.itarocha.hospedagem.service.QuartoService;
import br.com.itarocha.hospedagem.service.SituacaoLeitoService;
import br.com.itarocha.hospedagem.service.TipoHospedeService;
import br.com.itarocha.hospedagem.service.TipoLeitoService;
import br.com.itarocha.hospedagem.service.TipoServicoService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.*;

@RequestScoped
@Path("/api/app/quarto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "config")
@Transactional
public class QuartoController {

	@Inject QuartoService service;

	@Inject	TipoLeitoService tls;

	@Inject DestinacaoHospedagemService dhs;
	
	@Inject SituacaoLeitoService sls;
	
	@Inject TipoHospedeService ths;
	
	@Inject TipoServicoService tss;
	
	@Inject EntidadeService etds;

	@Inject Validator validator;

	@GET
	@APIResponse(responseCode="200", description="Sucesso")
	@RolesAllowed({"USER", "ADMIN", "ROOT"})
	public Response listar() {
		List<Quarto> lst = service.findAll();


		return Response.status(OK).entity(lst).build();
	}

	@GET
	@Path("/{id}")
	@RolesAllowed({"USER", "ADMIN", "ROOT"})
	@APIResponse(responseCode="200", description="Sucesso")
	@APIResponse(responseCode="404", description="Caso a chave não seja localizada")
	//@Operation(summary = "Buscar por id", description = "Efetua busca de Destinação de Hospedagem baseado na chave definida no parâmetro \"id\"")
	@Schema(type = SchemaType.OBJECT)
	public Response find(@PathParam("id") Long id) {
		try {
			return service.find(id)
					.map(model -> Response.status(OK)
							.entity(model)
							.build())
					.orElse(Response.status(NOT_FOUND)
							.entity(new ResponseReturn("id não localizado"))
							.build());
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	}

	@GET
	@Path("/leito/{id}")
	@RolesAllowed({"USER", "ADMIN", "ROOT"})
	public Response getLeitoById(@PathParam("id") Long id) {
		try {
			Optional<Leito> optLeito = service.findLeito(id);
			if (optLeito.isPresent()) {
				Leito model = optLeito.get();
				EditLeitoVO leito = new EditLeitoVO();
				leito.setId(model.getId());
				leito.setNumero(model.getNumero());
				leito.setQuartoId(model.getQuarto().getId());
				leito.setQuartoNumero(model.getQuarto().getNumero());
				leito.setTipoLeito(model.getTipoLeito().getId());
				leito.setSituacao(model.getSituacao().getId());
				
				return Response.status(OK).entity(leito).build();
			} else {
				return Response.status(NOT_FOUND)
						.entity(new ResponseReturn("Leito não existe"))
						.build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/por_destinacao_hospedagem/{id}")
	@RolesAllowed({"USER", "ADMIN", "ROOT"})
	public Response listarByDestinacaoHospedagem(@PathParam("id") Long id) {
		List<Quarto> lista = service.findAllByDestinacaoHospedagem(id);
		return Response.status(OK).entity(lista).build();
	}

	@GET
	@Path("/{id}/leitos")
	@RolesAllowed({"USER", "ADMIN", "ROOT"})
	public Response listarLeitosByQuarto(@PathParam("id") Long id) {
		List<Leito> lista = service.findLeitosByQuarto(id);
		return Response.status(OK).entity(lista).build();
	}

	@GET
	@Path("/leitos_disponiveis")
	@RolesAllowed({"USER", "ADMIN", "ROOT"})
	public Response listarLeitosDisponiveis() {
		List<Leito> lista = service.findLeitosDisponiveis();
		return Response.status(OK).entity(lista).build();
	}

	@POST
	@RolesAllowed({"ADMIN", "ROOT"})
	public Response gravar(@RequestBody NovoQuartoVO model) throws Exception {
		ItaValidator<NovoQuartoVO> v = new ItaValidator<NovoQuartoVO>(model).validate(validator);
		if (service.existeOutroQuartoComEsseNumero(model.getNumero())) {
			v.addError("numero", "Existe outro Quarto com esse número");
		}
		
		if (v.hasErrors() ) {
			return Response.status(BAD_REQUEST).entity(v.getErrors()).build();
		}
	
		// TODO tratar exceção
		Quarto saved = null;
		saved = service.create(model);
	    return Response.status(OK).entity(saved).build();
	}

	@POST
	@Path("/alterar")
	@RolesAllowed({"ADMIN", "ROOT"})
	public Response gravarAlteracao(@RequestBody EditQuartoVO model) {
		ItaValidator<EditQuartoVO> v = new ItaValidator<EditQuartoVO>(model).validate(validator);
		try {
			if (model.getId() != null) {
				if (service.existeOutroQuartoComEsseNumero(model.getId(), model.getNumero())) {
					v.addError("numero", "Existe outro Quarto com esse número");
				}
			}
			
			if (v.hasErrors() ) {
				return Response.status(BAD_REQUEST).entity(v.getErrors()).build();
			}
		
			Quarto saved = null;
			saved = service.update(model);
		    return Response.status(OK).entity(saved).build();
		} catch (Exception e) {
			return Response.status(INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	}

	@POST
	@Path("/leito")
	@RolesAllowed({"ADMIN", "ROOT"})
	public Response gravarLeito(@RequestBody EditLeitoVO model) {
		ItaValidator<EditLeitoVO> v = new ItaValidator<EditLeitoVO>(model).validate(validator);
		
		try {
			if (model.getId() == null) {
				if (service.existeOutroLeitoComEsseNumero(model.getQuartoId(), model.getNumero())) {
					v.addError("numero", "Existe outro Leito com esse número");
				}
			} else {
				if (service.existeOutroLeitoComEsseNumero(model.getId(), model.getQuartoId(), model.getNumero())) {
					v.addError("numero", "Existe outro Leito com esse número");
				}
			}
			if (v.hasErrors() ) {
				return Response.status(INTERNAL_SERVER_ERROR).entity(v.getErrors()).build();
			}

			Leito saved = null;
			saved = service.saveLeito(model);
			return Response.status(OK).entity(saved).build();
		} catch (Exception e) {
			return Response.status(INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed({"ADMIN", "ROOT"})
	public Response excluir(@PathParam("id") Long id) {

		try {
			if (!service.remove(id)){
				return Response.status(Response.Status.NOT_FOUND).entity(new ResponseReturn("id não localizado")).build();
			}
			return Response.status(OK).entity(new ResponseReturn("sucesso")).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	 }

	@DELETE
	@Path("/leito/{id}")
	@RolesAllowed({"ADMIN", "ROOT"})
	public Response excluirLeito(@PathParam("id") Long id) {
		try {
			if (!service.removeLeito(id)){
				return Response.status(Response.Status.NOT_FOUND).entity(new ResponseReturn("id não localizado")).build();
			}
			return Response.status(OK).entity(new ResponseReturn("sucesso")).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	 }

	@GET
	@Path("/listas")
	@RolesAllowed({"USER", "ADMIN", "ROOT"})
	public Response listas() {
		ListasVO retorno = new ListasVO();
		try {
			retorno.setListaTipoLeito(tls.listSelect());
			retorno.setListaDestinacaoHospedagem(dhs.listSelect());
			retorno.setListaSituacaoLeito(sls.listSelect());
			retorno.setListaTipoHospede(ths.listSelect());
			retorno.setListaTipoServico(tss.listSelect());
			retorno.setListaEntidade(etds.listSelect());
		} catch (Exception e){
			e.printStackTrace();
		}
		return Response.status(OK).entity(retorno).build();
	}

}
