package br.com.itarocha.hospedagem.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/teste")
public class TestesController {

    @GET
    @Path("/publico")
    @Produces(MediaType.TEXT_PLAIN)
    public String publico(){
        return "publico";
    }

    @GET
    @Path("/protegido")
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String protegido(){
        return "protegido";
    }

}
