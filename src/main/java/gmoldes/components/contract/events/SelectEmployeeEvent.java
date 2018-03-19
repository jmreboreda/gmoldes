package gmoldes.components.contract.events;

import gmoldes.domain.dto.ClientDTO;
import gmoldes.domain.dto.PersonDTO;
import javafx.event.Event;
import javafx.event.EventType;

public class SelectEmployeeEvent extends Event {

	public static final EventType<SelectEmployeeEvent> SELECT_EMPLOYEE_EVENT = new EventType<>("SELECT_EMPLOYEE_EVENT");
	private final PersonDTO newEmployeeSelected;


	public SelectEmployeeEvent(PersonDTO newEmployeeSelected) {
		super(SELECT_EMPLOYEE_EVENT);
		this.newEmployeeSelected = newEmployeeSelected;
	}

	public PersonDTO getSelectedEmployee() {
		return newEmployeeSelected;
	}
}
