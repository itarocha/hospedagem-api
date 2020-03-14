package br.com.itarocha.hospedagem.controller;

import br.com.itarocha.hospedagem.dto.ResponseReturn;
import br.com.itarocha.hospedagem.dto.TipoServicoDTO;
import br.com.itarocha.hospedagem.model.TipoServico;
import br.com.itarocha.hospedagem.service.TipoServicoService;
import br.com.itarocha.hospedagem.validation.ItaValidator;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

@RequestScoped
@Path("/api/app/tipo_servico")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "config")
@Transactional
public class TipoServicoController {

	@Inject
	TipoServicoService service;

	@Inject	Validator validator;

	@GET
	@APIResponse(responseCode="200", description="Sucesso")
	@RolesAllowed({"USER", "ADMIN", "ROOT"})
	@Operation(summary = "Listar", description = "Retorna todos os Tipos de Serviço cadastrados")
	public List<TipoServico> listar() {
		return service.findAll();
	}

	@GET
	@Path("/{id}")
	@RolesAllowed({"USER", "ADMIN", "ROOT"})
	@APIResponse(responseCode="200", description="Sucesso")
	@APIResponse(responseCode="404", description="Caso a chave não seja localizada")
	@Operation(summary = "Buscar por id", description = "Efetua busca de Tipos de Serviços baseado na chave definida no parâmetro \"id\"")
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
	@RolesAllowed({"ADMIN", "ROOT"})
	@APIResponse(responseCode="200", description="Sucesso")
	@APIResponse(responseCode="400", description="Caso as validações não passem")
	@Operation(summary = "Gravar", description = "Grava Tipos de Serviços")
	public Response gravar(@RequestBody TipoServicoDTO model) {

		ItaValidator<TipoServicoDTO> v = new ItaValidator<TipoServicoDTO>(model).validate(validator);
		if (v.hasErrors() ) {
			return Response.status(Response.Status.BAD_REQUEST).entity(v.getErrors()).build();
		}

		try {
			TipoServico saved = null;
			saved = service.create(model);
			return Response.ok().entity(saved).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed({"ADMIN", "ROOT"})
	@APIResponse(responseCode="200", description="Ok")
	@APIResponse(responseCode="404", description="Caso a chave não seja localizada")
	@APIResponse(responseCode="500", description="Ocorre quando não foi possível excluir")
	@Operation(summary = "Excluir por id", description = "Remove o Tipo de Serviço identificado pelo parâmetro \"id\"")
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
