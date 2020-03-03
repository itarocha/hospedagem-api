package br.com.itarocha.hospedagem.controller;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonString;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
@Path("/api/teste")
@Produces(MediaType.APPLICATION_JSON )
public class TestesController {

	@Inject
    JsonWebToken jwt;

	@Inject 
    @Claim(standard = Claims.given_name ) 
    Optional<JsonString> givenName; 
	
	
    @GET
    @Path("/publico")
    public String publico(){
        return "{\"mensagem\":\"publico\"}";
    }

    @GET
    @Path("/protegido")
    @RolesAllowed({"user","admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public String protegido(@Context SecurityContext ctx){
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        
        String email = jwt.getClaim("email");
        
        boolean hasJWT = jwt.getClaimNames() != null;
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s, hasJWT: %s", name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT);
        System.out.println(helloReply);
        System.out.println("Email: "+email);
        
    	return helloReply;
    }

}
/*

{
"jti": "ed72bf8e-e904-41d8-a84b-c32175c67f5a",
"exp": 1583194954,
"nbf": 0,
"iat": 1583194654,
"iss": "http://localhost:8200/auth/realms/quarkus-quickstart",
"aud": "account",
"sub": "a19b2afc-e96e-4939-82bf-aa4b589de136",
"typ": "Bearer",
"azp": "quarkus-front",
"auth_time": 0,
"session_state": "07f77937-b017-49d7-8707-7b44a71608dc",
"acr": "1",
"allowed-origins": [
  "http://localhost:8080"
],
"realm_access": {
  "roles": [
    "offline_access",
    "uma_authorization",
    "user"
  ]
},
"resource_access": {
  "account": {
    "roles": [
      "manage-account",
      "manage-account-links",
      "view-profile"
    ]
  }
},
"scope": "email profile",
"email_verified": false,
"name": "Theo Tester",
"groups": [
  "offline_access",
  "uma_authorization",
  "user"
],
"preferred_username": "test",
"given_name": "Theo",
"family_name": "Tester",
"email": "tester@localhost"
}
*/