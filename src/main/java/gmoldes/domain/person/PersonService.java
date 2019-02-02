package gmoldes.domain.person;

import gmoldes.domain.person.controllers.PersonController;
import gmoldes.domain.person.dto.PersonDTO;

public class PersonService {

    private PersonService() {
    }

    public static class PersonServiceFactory {

        private static PersonService personService;

        public static PersonService getInstance() {
            if(personService == null) {
                personService = new PersonService();
            }
            return personService;
        }
    }

    private PersonController personController = new PersonController();

    public PersonDTO findPersonById(Integer clientId){

        return personController.findPersonById(clientId);
    }
}
