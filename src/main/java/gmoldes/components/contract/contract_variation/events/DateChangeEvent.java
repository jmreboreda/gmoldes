package gmoldes.components.contract.contract_variation.events;

import gmoldes.domain.client.dto.ClientDTO;
import javafx.event.Event;
import javafx.event.EventType;

import java.time.LocalDate;

public class DateChangeEvent extends Event {

	public static final EventType<DateChangeEvent> DATE_CHANGE_EVENT = new EventType<>("DATE_CHANGE_EVENT");
	private final LocalDate date;
	private final ClientDTO client;


	public DateChangeEvent(ClientDTO client, LocalDate date) {
		super(DATE_CHANGE_EVENT);
		this.date = date;
		this.client = client;
	}

	public LocalDate getDate() {
		return date;
	}

	public ClientDTO getClient(){
		return client;
	}
}
