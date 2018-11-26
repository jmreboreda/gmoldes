package gmoldes.components.contract.contract_variation.events;

import javafx.event.Event;
import javafx.event.EventType;

import java.time.LocalDate;

public class DateChangeEvent extends Event {

	public static final EventType<DateChangeEvent> DATE_CHANGE_EVENT = new EventType<>("DATE_CHANGE_EVENT");
	private final LocalDate date;


	public DateChangeEvent(LocalDate date) {
		super(DATE_CHANGE_EVENT);
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}
}
