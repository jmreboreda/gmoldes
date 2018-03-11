package gmoldes.components.contract.events;

import javafx.event.Event;
import javafx.event.EventType;

public class SearchEmployersEvent extends Event {

	public static final EventType<SearchEmployersEvent> SEARCH_EMPLOYERS_EVENT = new EventType<>("CHANGE_SCHEDULE_DURATION_EVENT");
	private final String pattern;


	public SearchEmployersEvent(String pattern) {
		super(SEARCH_EMPLOYERS_EVENT);
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

}
