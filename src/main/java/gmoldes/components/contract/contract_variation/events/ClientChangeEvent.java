package gmoldes.components.contract.contract_variation.events;

import gmoldes.domain.client.dto.ClientDTOOk;
import javafx.event.Event;
import javafx.event.EventType;

import java.time.LocalDate;

public class ClientChangeEvent extends Event {

	public static final EventType<ClientChangeEvent> CLIENT_CHANGE_EVENT = new EventType<>("CLIENT_CHANGE_EVENT");
	private final LocalDate date;
	private final ClientDTOOk client;


	public ClientChangeEvent(ClientDTOOk client, LocalDate date) {
		super(CLIENT_CHANGE_EVENT);
		this.date = date;
		this.client = client;
	}

	public LocalDate getDate() {
		return date;
	}

	public ClientDTOOk getClient(){
		return client;
	}
}
