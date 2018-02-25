package gmoldes.components.contract.events;

import gmoldes.domain.dto.ClientDTO;
import javafx.event.Event;
import javafx.event.EventType;

public class SelectEmployerEvent extends Event {

	public static final EventType<SelectEmployerEvent> SELECT_EMPLOYER_EVENT = new EventType<>("SELECT_EMPLOYER_EVENT");
	private final ClientDTO newClientEmployerSelected;


	public SelectEmployerEvent(ClientDTO newClientEmployerSelected) {
		super(SELECT_EMPLOYER_EVENT);
		this.newClientEmployerSelected = newClientEmployerSelected;
	}

	public ClientDTO getSelectedEmployer() {
		return newClientEmployerSelected;
	}
}
