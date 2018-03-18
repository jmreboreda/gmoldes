package gmoldes.components.contract.events;

import javafx.event.Event;
import javafx.event.EventType;

public class SearchEmployeesEvent extends Event {

	public static final EventType<SearchEmployeesEvent> SEARCH_EMPLOYERS_EVENT = new EventType<>("SEARCH_EMPLOYEES_EVENT");
	private final String pattern;

	public SearchEmployeesEvent(String pattern) {
		super(SEARCH_EMPLOYERS_EVENT);
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}
}
