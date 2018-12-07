package personService;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureWebTestClient
@SpringBootTest
@RunWith(SpringRunner.class)
public class personServiceControllerTest {
    WebTestClient webClient;
    @Before
    public void setUp(){
        webClient = WebTestClient.bindToController(new personServiceController()).build();
    }

    @Test
    public void getPerson_void_personList() {
        webClient.get().uri("/persons").exchange().expectStatus().isOk()
                .expectBody().json("[{\"id\":\"2\",\"name\":\"Nguyen Hong Nhat\",\"age\":25},{\"id\":\"3\",\"name\":\"Nguyen Hoai Danh\",\"age\":22}]");
    }

    @Test
    public void createPerson_newPerson_successfulMessage() {
        Person person = new Person("3","Nguyen Hoai Danh",22);
        webClient.post().uri("/persons").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(person),Person.class).exchange().expectStatus().isOk().expectBody().returnResult().equals("person is created successfully");
    }

    @Test
    public void e_updatePerson_personUpdateData_successfulMessage() {
        Person person = new Person("2","Nguyen Hong Nhat",25);
        webClient.put().uri("/persons/{id}", person.getId())
                .body(Mono.just(person),Person.class).exchange().expectStatus().isOk().expectBody().returnResult().equals("person is updated successfully");
    }

    @Test
    public void delete_personId_successfulMessage() {
        webClient.delete().uri("/persons/{id}", "1").exchange().expectBody()
                .returnResult().equals("person is deleted successfully");
    }

}
