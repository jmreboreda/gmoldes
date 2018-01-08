package gmoldes.components.contract.new_contract.events;

import javafx.event.Event;
import javafx.event.EventType;

public class SearchEmployeesEvent extends Event {

	public static final EventType<SearchEmployeesEvent> SEARCH_EMPLOYERS_EVENT = new EventType<>("SEARCH_EMPLOYEES_EVENT");
	private final String pattern;
	private final String employeesNameSelectedItem;

	public SearchEmployeesEvent(String pattern, String employersNameSelectedItem) {
		super(SEARCH_EMPLOYERS_EVENT);
		this.pattern = pattern;
		this.employeesNameSelectedItem = employersNameSelectedItem;
	}

	public String getPattern() {
		return pattern;
	}
	public String getEmployeesNameSelectedItem() {
		return employeesNameSelectedItem;
	}

}
