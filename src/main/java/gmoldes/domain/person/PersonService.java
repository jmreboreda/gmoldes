package gmoldes.domain.person;

import gmoldes.domain.person.controllers.PersonController;
import gmoldes.domain.person.dto.PersonDTO;

public class PersonService {

    public PersonService() {
    }

    private static PersonController personController = new PersonController();


    public static PersonDTO findPersonById(Integer clientId){

        return personController.findPersonById(clientId);
    }
}
