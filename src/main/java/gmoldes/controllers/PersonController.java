package gmoldes.controllers;

import gmoldes.domain.dto.PersonDTO;
import gmoldes.domain.dto.TimeRecordClientDTO;
import gmoldes.manager.ClientManager;
import gmoldes.manager.PersonManager;

import java.util.List;

public class PersonController {

    private PersonManager personManager = new PersonManager();

    public PersonDTO findPersonById(Integer id){

        return personManager.findPersonById(id);
    }
}
