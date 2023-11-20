package sg.edu.nus.iss.d13rev.controllers;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.d13rev.models.PersonForm;
import sg.edu.nus.iss.d13rev.models.Person;
import sg.edu.nus.iss.d13rev.services.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PersonController {
    private static List<Person> personList = new ArrayList<Person>();

    // static {
    //     persons.add(new Person("Bill", "Gates"));
    //     persons.add(new Person("Steve", "Jobs"));
    // }

    @Autowired
    PersonService perSvc;

    // Inject via application.properties
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {

        personList = perSvc.getPersons();
        model.addAttribute("persons", personList);

        return "personList";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {

        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);

        return "addPerson";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
    public String savePerson(Model model, //
            @ModelAttribute("personForm") PersonForm personForm) {

        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();

        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            Person newPerson = new Person(firstName, lastName);
            perSvc.addPerson(newPerson);
            //personList.add(newPerson);

            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addPerson";
    }

    @RequestMapping(value="/personToEdit", method=RequestMethod.POST)
    public String personToEdit(@ModelAttribute(value="per") Person p, Model model) {
        model.addAttribute("per", p);
        return "editPerson";
    }

    @RequestMapping(value="personEdit", method = RequestMethod.POST)
    public String personEdit(@ModelAttribute(value="per") Person p, Model model) {
        perSvc.updatePerson(p);
        return "redirect:/personList";
    }

    @RequestMapping(value="personDelete", method = RequestMethod.POST)
    public String personDelete(@ModelAttribute(value="per") Person p, Model model) {
        perSvc.removePerson(p);
        return "redirect:/personList";
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @ResponseBody
    @RequestMapping(path = "/log")
    public String home() {

        LOGGER.trace("This is TRACE");
        LOGGER.debug("This is DEBUG");
        LOGGER.info("This is INFO");
        LOGGER.warn("This is WARN");
        LOGGER.error("This is ERROR");

        return "Hi, show loggings in the console or file!";
    }
}
