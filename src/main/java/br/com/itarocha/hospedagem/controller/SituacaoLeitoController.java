package br.com.itarocha.hospedagem.controller;

import java.util.List;

import br.com.itarocha.hospedagem.dto.ResponseReturn;
import br.com.itarocha.hospedagem.dto.SituacaoLeitoDTO;
import br.com.itarocha.hospedagem.validation.ItaValidator;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.itarocha.hospedagem.model.SituacaoLeito;
import br.com.itarocha.hospedagem.service.SituacaoLeitoService;

import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/api/app/situacao_leito")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "config")
public class SituacaoLeitoController {

	@Inject SituacaoLeitoService service;

	@Inject	Validator validator;

	@GET
	@APIResponse(responseCode="200", description="Sucesso")
	// @PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	@Operation(summary = "Listar", description = "Retorna todas as Situações de Leito cadastradas")
	public Response listar() {
		List<SituacaoLeito> lista = service.findAll();
		return Response.status(OK).entity(lista).build();
	}

	@GET
	@Path("/{id}")
	@APIResponse(responseCode="200", description="Sucesso")
	@APIResponse(responseCode="404", description="Caso a chave não seja localizada")
	@Operation(summary = "Buscar por id", description = "Efetua busca de Situação de Leito baseado na chave definida no parâmetro \"id\"")
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

	// @PreAuthorize("hasAnyRole('ADMIN','ROOT')")
	@POST
	@APIResponse(responseCode="200", description="Sucesso")
	@APIResponse(responseCode="400", description="Caso as validações não passem")
	@Operation(summary = "Gravar", description = "Grava Situação de Leito")
	public Response gravar(@RequestBody SituacaoLeitoDTO model) {
		ItaValidator<SituacaoLeitoDTO> v = new ItaValidator<SituacaoLeitoDTO>(model).validate(validator);
		if (v.hasErrors() ) {
			return Response.status(Response.Status.BAD_REQUEST).entity(v.getErrors()).build();
		}
		try {
			SituacaoLeito saved = null;
			saved = service.create(model);
			return Response.ok().entity(saved).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	//@PreAuthorize("hasAnyRole('ADMIN','ROOT')")
	@DELETE
	@Path("/{id}")
	@APIResponse(responseCode="200", description="Ok")
	@APIResponse(responseCode="404", description="Caso a chave não seja localizada")
	@APIResponse(responseCode="500", description="Ocorre quando não foi possível excluir")
	@Operation(summary = "Excluir por id", description = "Remove a Situação de Leito identificado pelo parâmetro \"id\"")
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

}
