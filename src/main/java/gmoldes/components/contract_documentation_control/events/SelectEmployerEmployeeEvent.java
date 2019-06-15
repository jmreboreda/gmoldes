package gmoldes.components.contract_documentation_control.events;

import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.person.dto.PersonDTO;
import javafx.event.Event;
import javafx.event.EventType;

public class SelectEmployerEmployeeEvent extends Event {

	public static final EventType<SelectEmployerEmployeeEvent> SELECT_EMPLOYER_EMPLOYEE_EVENT = new EventType<>("SELECT_EMPLOYER_EMPLOYEE_EVENT");
	private final ClientDTO newClientEmployerSelected;
	private final PersonDTO newEmployeeSelected;


	public SelectEmployerEmployeeEvent(ClientDTO newClientEmployerSelected, PersonDTO newEmployeeSelected) {
		super(SELECT_EMPLOYER_EMPLOYEE_EVENT);
		this.newClientEmployerSelected = newClientEmployerSelected;
		this.newEmployeeSelected = newEmployeeSelected;

	}

	public ClientDTO getSelectedClientEmployer() {
		return newClientEmployerSelected;
	}

	public PersonDTO getNewEmployeeSelected() {
		return newEmployeeSelected;
	}
}
