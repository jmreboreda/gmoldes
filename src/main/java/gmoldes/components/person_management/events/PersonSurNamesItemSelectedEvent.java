package gmoldes.components.person_management.events;

import gmoldes.domain.person.dto.PersonDTO;
import javafx.event.Event;
import javafx.event.EventType;

public class PersonSurNamesItemSelectedEvent extends Event {

    public static final EventType<PersonSurNamesItemSelectedEvent> PERSON_SUR_NAMES_ITEM_SELECTED_EVENT = new EventType<>("PERSON_SUR_NAMES_ITEM_SELECTED_EVENT");
    private final PersonDTO personDTO;

    public PersonSurNamesItemSelectedEvent(PersonDTO personDTO) {
        super(PERSON_SUR_NAMES_ITEM_SELECTED_EVENT);
        this.personDTO = personDTO;

    }

    public PersonDTO getPersonDTO() {
        return personDTO;
    }
}
