package gmoldes.components.person_management.events;

import javafx.event.Event;
import javafx.event.EventType;

public class PersonSurNamesPatternChangedEvent extends Event {

    public static final EventType<PersonSurNamesPatternChangedEvent> PERSON_SUR_NAMES_CHANGED_EVENT = new EventType<>("PERSON_SUR_NAMES_CHANGED_EVENT");
    private final String pattern;

    public PersonSurNamesPatternChangedEvent(String pattern) {
        super(PERSON_SUR_NAMES_CHANGED_EVENT);
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
