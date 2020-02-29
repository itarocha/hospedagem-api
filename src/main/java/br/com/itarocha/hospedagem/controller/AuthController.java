package br.com.itarocha.hospedagem.controller;

import java.net.URI;
import java.util.Collections;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import br.com.itarocha.hospedagem.model.Role;
import br.com.itarocha.hospedagem.model.RoleName;
import br.com.itarocha.hospedagem.model.User;
//import br.com.itarocha.hospedagem.payload.ApiResponse;
//import br.com.itarocha.hospedagem.payload.JwtAuthenticationResponse;
import br.com.itarocha.hospedagem.payload.LoginRequest;
//import br.com.itarocha.hospedagem.payload.SignUpRequest;
import br.com.itarocha.hospedagem.repository.RoleRepository;
import br.com.itarocha.hospedagem.repository.UserRepository;
//import br.com.itarocha.hospedagem.security.JwtTokenProvider;
//import br.com.itarocha.hospedagem.service.EmailService;

import static javax.ws.rs.core.Response.Status.OK;


@Path("/api/app/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "auth")
public class AuthController {

    //@Inject
    //AuthenticationManager authenticationManager;

    @Inject
    UserRepository userRepository;

    @Inject
    RoleRepository roleRepository;

    //@Inject
    //PasswordEncoder passwordEncoder;

    //@Inject
    //JwtTokenProvider tokenProvider;

    //@Inject
    //JsonWebToken jwt;
    
    //@Inject
    //EmailService emailService;

    @GET
    @Path("/hello")
    public Response hello() {
        return Response.status(OK).entity(new Hello("Hello Mondo")).build();
    }

    @POST
    @Path("/login")
    public Response authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        /*
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return Response.status(OK).entity(new JwtAuthenticationResponse(jwt)).build();
         */
        return null;
    }

    /*
    @POST
    @Path("/assinar")
    //@PreAuthorize("hasAnyRole('ROOT')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	System.out.println(signUpRequest);
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
        	return new ResponseEntity(new ApiResponse(false, "Nome de usuário já cadastrado!"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email já está em uso!"), HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Usuário registrado com sucesso!"));
    }
    */

    private class Hello {
    	private String mensagem;
    	
    	public Hello(String mensagem) {
    		this.mensagem = mensagem;
    	}

		public String getMensagem() {
			return mensagem;
		}

		public void setMensagem(String mensagem) {
			this.mensagem = mensagem;
		}
    }
}