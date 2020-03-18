package br.com.itarocha.hospedagem.controller;

import java.util.List;

import br.com.itarocha.hospedagem.dto.EncaminhadorDTO;
import br.com.itarocha.hospedagem.dto.ResponseReturn;
import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.validation.ItaValidator;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import br.com.itarocha.hospedagem.model.Encaminhador;
import br.com.itarocha.hospedagem.service.EncaminhadorService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.transaction.Transactional.TxType.*;
import static javax.ws.rs.core.Response.Status.*;

@ApplicationScoped
@Path("/api/app/encaminhadores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "config")
public class EncaminhadoresController {

	@Inject EncaminhadorService service;

    @Inject Validator validator;

	@GET
	@Path("/por_encaminhador/{id}")
	@RolesAllowed({"USER","ADMIN", "ROOT"})
	@Transactional(NOT_SUPPORTED)
	public Response listar(@PathParam("id") Long entidadeId) {
		List<Encaminhador> lista = service.findAll(entidadeId);
		return Response.status(OK).entity(lista).build();
	}

	@GET
	@Path("/{id}")
	@RolesAllowed({"USER","ADMIN", "ROOT"})
	@Transactional(NOT_SUPPORTED)
	public Response getById(@PathParam("id") Long id) {
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

	@POST
	@RolesAllowed({"USER","ADMIN", "ROOT"})
	@Transactional(REQUIRED)
	public Response gravar(@RequestBody EncaminhadorDTO model) {
		ItaValidator<EncaminhadorDTO> v = new ItaValidator<EncaminhadorDTO>(model).validate(validator);
		if (v.hasErrors() ) {
			return Response.status(BAD_REQUEST).entity(v.getErrors()).build();
		}
		try {
			Encaminhador saved = null;
			saved = service.create(model);
			return Response.status(OK).entity(saved).build();
		} catch (Exception e) {
			return Response.status(INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	}

	@DELETE
    @Path("/{id}")
	@RolesAllowed({"USER","ADMIN", "ROOT"})
	@Transactional(REQUIRED)
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
	
	@GET
	@Path("/lista/{id}")
	@RolesAllowed({"USER","ADMIN", "ROOT"})
	@Transactional(NOT_SUPPORTED)
	public Response getListaEncaminhadores(@PathParam("id") Long entidadeId) {
		List<SelectValueVO> lista = service.listSelect(entidadeId);
		return Response.status(OK).entity(lista).build();
	}
}
