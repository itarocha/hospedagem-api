package br.com.itarocha;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.DestinacaoHospedagem;
import br.com.itarocha.hospedagem.repository.DestinacaoHospedagemRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@Transactional
public class DestinacaoHospedagemRepositoryTest {

    @Inject DestinacaoHospedagemRepository repository;

    //@Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

    @Test
    @Order(1)
    @Timeout(value = 1, unit = MINUTES)
    public void testInsert(){
        List<String> toInsert = Arrays.asList("NOVA YORK", "ALBUQUERQUE", "CAXIAS", "UBERLÂNDIA", "UBERABA");
        List<SelectValueVO> lstSelect = new ArrayList<>();
        for (String s : toInsert){
            DestinacaoHospedagem model = new DestinacaoHospedagem();
            model.setDescricao(s);
            DestinacaoHospedagem saved = repository.save(model);
            lstSelect.add(new SelectValueVO(saved.getId(), saved.getDescricao()));
        }

        int qtd = repository.getAll().size();
        Assertions.assertEquals(5 , qtd);

        List<DestinacaoHospedagem> encontrados = repository.findByFieldNameAndValue("descricao", "ber", "descricao");
        Assertions.assertEquals(2, encontrados.size());
        for(DestinacaoHospedagem d : encontrados){
            System.out.println(d.getDescricao());
        }
        List<SelectValueVO> lst = repository.getListSelect();

        lst.forEach(o -> System.out.println(o));

        repository.count();

        List<DestinacaoHospedagem> lstNative = repository.getAllNative();
        lstNative.forEach(model -> System.out.println(model.getId() + " - " + model.getDescricao()));

        Assertions.assertEquals(true, lst.containsAll(lstSelect));
    }

    /*
    @Test
    @Order(2)
    public void testeFind(){
        System.out.println("TESTE 2");
        List<DestinacaoHospedagem> lstNative = repository.getAllNative();
        lstNative.forEach(model -> System.out.println(model.getId() + " - " + model.getDescricao()));

        System.out.println("TESTE 2.A");
        List<DestinacaoHospedagem> encontrados = repository.findByFieldNameAndValue("descricao", "ber", "descricao");
        Assertions.assertEquals(2, encontrados.size());
        for(DestinacaoHospedagem d : encontrados){
            System.out.println(d.getDescricao());
        }
    }
*/

    @Test
    @Order(3)
    public void testUpdate(){
        Optional<DestinacaoHospedagem> opt = repository.findById(1L);
        opt.ifPresent( model -> {
            model.setDescricao("ALTERADO");
            DestinacaoHospedagem retorno = repository.save(model);
            Assertions.assertEquals("ALTERADO", retorno.getDescricao());
        });
    }

    @Test
    @Order(4)
    public void testDelete(){
        Optional<DestinacaoHospedagem> opt = repository.findById(1L);
        opt.ifPresent( model -> {
            repository.delete(model);
            Optional<DestinacaoHospedagem> retorno = repository.findById(1L);
            Assertions.assertEquals(Optional.empty(), retorno);
        });
    }
}

/*

# Run all the unit test classes.
$ mvn test

# Run a single test class.
$ mvn -Dtest=ExampleResourceTest test

# Run multiple test classes.
$ mvn -Dtest=TestApp1,TestApp2 test

# Run a single test method from a test class.
$ mvn -Dtest=TestApp1#methodname test

# Run all test methods that match pattern 'testHello*' from a test class.
$ mvn -Dtest=TestApp1#testHello* test

# Run all test methods match pattern 'testHello*' and 'testMagic*' from a test class.
$ mvn -Dtest=TestApp1#testHello*+testMagic* test

 */