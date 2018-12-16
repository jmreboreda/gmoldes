package gmoldes.components.contract.events;

import gmoldes.domain.client.dto.ClientDTOOk;
import javafx.event.Event;
import javafx.event.EventType;

public class SelectEmployerEvent extends Event {

	public static final EventType<SelectEmployerEvent> SELECT_EMPLOYER_EVENT = new EventType<>("SELECT_EMPLOYER_EVENT");
	private final ClientDTOOk newClientEmployerSelected;


	public SelectEmployerEvent(ClientDTOOk newClientEmployerSelected) {
		super(SELECT_EMPLOYER_EVENT);
		this.newClientEmployerSelected = newClientEmployerSelected;
	}

	public ClientDTOOk getSelectedEmployer() {
		return newClientEmployerSelected;
	}
}
