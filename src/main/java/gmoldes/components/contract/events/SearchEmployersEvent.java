package gmoldes.components.contract.events;

import javafx.event.Event;
import javafx.event.EventType;

public class SearchEmployersEvent extends Event {

	public static final EventType<SearchEmployersEvent> SEARCH_EMPLOYERS_EVENT = new EventType<>("CHANGE_SCHEDULE_DURATION_EVENT");
	private final String pattern;
	private final String employersNameSelectedItem;

	public SearchEmployersEvent(String pattern, String employersNameSelectedItem) {
		super(SEARCH_EMPLOYERS_EVENT);
		this.pattern = pattern;
		this.employersNameSelectedItem = employersNameSelectedItem;
	}

	public String getPattern() {
		return pattern;
	}
	public String getEmployersNameSelectedItem() {
		return employersNameSelectedItem;
	}

}
