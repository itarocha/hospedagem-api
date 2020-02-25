package br.com.itarocha.hospedagem;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
    tags = {
            @Tag(name="config", description="Operações de configurações"),
            @Tag(name="hospedagem", description="Operations relativas a hospedagens")
    },
    info = @Info(
        title = "Betesda Api",
        description = "Endpoints fornecidas pela api do sistema Betesda",
        version = "0.0.1",
        contact = @Contact(
            name = "Itamar Rocha",
            email = "itarocha@gmail.com"
        )
    )
)
public class BetesdaApplication extends Application {

}

