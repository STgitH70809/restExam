package personService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class personServiceController {
    private static Map<String, Person> personRepo = new HashMap<>();
    static {
        Person Nhat = new Person();
        Nhat.setId("1");
        Nhat.setName("Pham Hong Nhat");
        Nhat.setAge(20);
        personRepo.put(Nhat.getId(), Nhat);

        Person Danh = new Person();
        Danh.setId("2");
        Danh.setName("Nguyen Danh");
        Danh.setAge(22);
        personRepo.put(Danh.getId(), Danh);
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.DELETE)
    public Collection<Person> delete(@PathVariable("id") String id) {
        personRepo.remove(id);
        return personRepo.values();
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.PUT)
    public Collection<Person> updatePerson(@PathVariable("id") String id, @RequestBody Person person) {
        personRepo.remove(id);
        person.setId(id);
        personRepo.put(id, person);
        return personRepo.values();
    }

    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    public Collection<Person> createPerson(@RequestBody Person person) {
        personRepo.put(person.getId(), person);
        return personRepo.values();
    }

    @RequestMapping(value = "/persons")
    public Collection<Person> getPerson() {
        return personRepo.values();
    }
}
