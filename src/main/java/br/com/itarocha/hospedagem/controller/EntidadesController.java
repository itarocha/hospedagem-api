package br.com.itarocha.hospedagem.controller;

import br.com.itarocha.hospedagem.dto.ResponseReturn;
import br.com.itarocha.hospedagem.exception.ValidationException;
import br.com.itarocha.hospedagem.model.Encaminhador;
import br.com.itarocha.hospedagem.model.Entidade;
import br.com.itarocha.hospedagem.service.EntidadeService;
import br.com.itarocha.hospedagem.validation.ItaValidator;
import br.com.itarocha.hospedagem.validators.Validadores;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

@Transactional
@Path("/api/app/entidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "cadastros")
public class EntidadesController {

	@Inject EntidadeService service;

    @Inject Validator validator;

	@Path("{id}")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
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

	@GET
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response listar() {
		List<Entidade> lista = service.findAll();
        return Response.status(OK).entity(lista).build();
	}

	@GET
	@Path("/consultar/{texto}")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response consultar(@PathParam("texto") String texto) {
		List<Entidade> lista = service.consultar(texto);
        return Response.status(OK).entity(lista).build();
	}

	@POST
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
	public Response gravar(@RequestBody Entidade model) {
		
		if (model.getCnpj() != null) {
			model.setCnpj(model.getCnpj().replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\/", ""));
		}
		if (model.getEndereco() != null && model.getEndereco().getCep() != null) {
			model.getEndereco().setCep((model.getEndereco().getCep().replaceAll("\\-", "")));
		}
		
		ItaValidator<Entidade> v = new ItaValidator<Entidade>(model).validate(validator);
		
		if (model.getCnpj() != null && model.getCnpj() != "") {
			if (!Validadores.isValidCNPJ(model.getCnpj())) {
				v.addError("cnpj", "CNPJ inválido");
			}
		}
		
		if (!v.hasErrors() ) {
			return Response.status(BAD_REQUEST).entity(v.getErrors()).build();
		}
		
		try {
			Entidade saved = null;
			saved = service.create(model);
		    return Response.status(OK).entity(saved).build();
		} catch (ValidationException e) {
		    return Response.status(BAD_REQUEST).entity(e.getRe()).build();
		} catch (Exception e) {
			return Response.status(INTERNAL_SERVER_ERROR).entity(new ResponseReturn(e.getMessage())).build();
		}
	}

	@DELETE
	@Path("{id}")
	//@PreAuthorize("hasAnyRole('USER','ADMIN','ROOT')")
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
