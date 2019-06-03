package gmoldes.domain.person.controllers;

import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.PersonManager;

import java.util.List;

public class PersonController {

    private PersonManager personManager = new PersonManager();

    public Integer createPerson(PersonDTO personDTO){
        return personManager.createPerson(personDTO);
    }

    public Integer updatePerson(PersonDTO personDTO){
        return personManager.updatePerson(personDTO);
    }

    public List<PersonDTO> findAllPersonInAlphabeticalOrder(){
        return personManager.findAllPersonInAlphabeticalOrder();
    }

    public PersonDTO findPersonById(Integer id){
        return personManager.findPersonById(id);
    }

    public List<PersonDTO> findAllPersonsByNamePatternInAlphabeticalOrder(String pattern){
        return personManager.findAllPersonsByNamePatternInAlphabeticalOrder(pattern);
    }

    public List<PersonDTO> findPersonByNieNif(String nieNif, Integer personId){

        return personManager.findPersonByNieNif(nieNif, personId);
    }

    public List<PersonDTO> findPersonByNass(String nieNif, Integer personId){

        return personManager.findPersonByNass(nieNif, personId);
    }
}
