package sg.edu.nus.iss.d13rev.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.d13rev.models.Person;

@Service
public class PersonService {
    private List<Person> persons = new ArrayList<Person>();

    // static {
    //     persons.add(new Person("Bill", "Gates"));
    //     persons.add(new Person("Steve", "Jobs"));
    // }

    public PersonService() {
        persons.add(new Person("Elon", "Musk"));
        persons.add(new Person("Mark", "Zul"));
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    public void addPerson(Person p) {
        persons.add(new Person(p.getFirstName(), p.getLastName()));
    }

    // Reference: https://codezup.com/4-ways-to-find-an-element-in-java-list-example/amp/
    public void updatePerson(Person p) {
        Person foundPerson = persons.stream().filter(o -> o.getId().equals(p.getId())).findAny().orElse(null);

        Person updatedPerson = new Person();
        updatedPerson.setId(p.getId());
        updatedPerson.setFirstName(p.getFirstName());
        updatedPerson.setLastName(p.getLastName());

        persons.remove(foundPerson);
        persons.add(new Person(updatedPerson.getId(), updatedPerson.getFirstName(), updatedPerson.getLastName()));
    }

    public void removePerson(Person p) {
        Person foundPerson = persons.stream().filter(o -> o.getId().equals(p.getId())).findAny().orElse(null);

        persons.remove(foundPerson);
    }
}
