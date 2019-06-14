package gmoldes.components.contract_documentation_control.events;

import gmoldes.domain.client.dto.ClientDTO;
import javafx.event.Event;
import javafx.event.EventType;

public class SelectClientEmployerEvent extends Event {

	public static final EventType<SelectClientEmployerEvent> SELECT_CLIENT_EMPLOYER_EVENT = new EventType<>("SELECT_CLIENT_EMPLOYER_EVENT");
	private final ClientDTO newClientEmployerSelected;


	public SelectClientEmployerEvent(ClientDTO newClientEmployerSelected) {
		super(SELECT_CLIENT_EMPLOYER_EVENT);
		this.newClientEmployerSelected = newClientEmployerSelected;
	}

	public ClientDTO getSelectedClientEmployer() {
		return newClientEmployerSelected;
	}
}
