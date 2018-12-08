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
    public void a_getPerson_void_personList() {
        webClient.get().uri("/persons").exchange().expectStatus().isOk()
                .expectBody().json("[{\"id\":\"1\",\"name\":\"Pham Hong Nhat\",\"age\":20},{\"id\":\"2\",\"name\":\"Nguyen Danh\",\"age\":22}]");
    }

    @Test
    public void b_createPerson_newPerson_newListAfterInsert() {
        Person person = new Person("3","Nguyen Hoai Danh",22);
        webClient.post().uri("/persons").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(person),Person.class).exchange().expectStatus().isOk().expectBody()
                .json("[{\"id\":\"1\",\"name\":\"Pham Hong Nhat\",\"age\":20},{\"id\":\"2\",\"name\":\"Nguyen Danh\",\"age\":22},{\"id\":\"3\",\"name\":\"Nguyen Hoai Danh\",\"age\":22}]");
    }

    @Test
    public void b_createPerson_personAlreadyInList_emptyList() {
        Person person = new Person("2","Nguyen Danh",22);
        webClient.post().uri("/persons").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(person),Person.class).exchange().expectStatus().isOk().expectBody()
                .json("[]");
    }

    @Test
    public void c_updatePerson_personUpdateData_listAfterUpdate() {
        Person person = new Person("2","Nguyen Hong Nhat",25);
        webClient.put().uri("/persons/{id}", person.getId())
                .body(Mono.just(person),Person.class).exchange().expectStatus().isOk().expectBody()
                .json("[{\"id\":\"1\",\"name\":\"Pham Hong Nhat\",\"age\":20},{\"id\":\"2\",\"name\":\"Nguyen Hong Nhat\",\"age\":25},{\"id\":\"3\",\"name\":\"Nguyen Hoai Danh\",\"age\":22}]");
    }

    @Test
    public void c_updatePerson_invalidPersonUpdateData_emptyList() {
        Person person = new Person("4","Nguyen Hong Nhat",25);
        webClient.put().uri("/persons/{id}", person.getId())
                .body(Mono.just(person),Person.class).exchange().expectStatus().isOk().expectBody()
                .json("[]");
    }

    @Test
    public void d_delete_invalidPersonId_emptyList() {
        webClient.delete().uri("/persons/{id}", "4").exchange().expectStatus().isOk().expectBody()
                .json("[]");
    }

    @Test
    public void d_delete_personId_listAfterDelete() {
        webClient.delete().uri("/persons/{id}", "1").exchange().expectStatus().isOk().expectBody()
                .json("[{\"id\":\"2\",\"name\":\"Nguyen Hong Nhat\",\"age\":25},{\"id\":\"3\",\"name\":\"Nguyen Hoai Danh\",\"age\":22}]");
    }

}
