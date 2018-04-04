package gmoldes.domain.person.controllers;

import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.manager.PersonManager;

import java.util.List;

public class PersonController {

    private PersonManager personManager = new PersonManager();

    public PersonDTO findPersonById(Integer id){

        return personManager.findPersonById(id);
    }

    public List<PersonDTO> findAllPersonsByNamePatternInAlphabeticalOrder(String pattern){
        return personManager.findAllPersonsByNamePatternInAlphabeticalOrder(pattern);
    }
}