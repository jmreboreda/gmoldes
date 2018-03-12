package gmoldes.components.contract.events;

import javafx.event.Event;
import javafx.event.EventType;

import java.time.Duration;

public class ChangeContractDataHoursWorkWeekEvent extends Event {

	public static final EventType<ChangeContractDataHoursWorkWeekEvent> CHANGE_CONTRACT_DATA_HOURS_WORK_WEEK_EVENT = new EventType<>("CHANGE_CONTRACT_DATA_HOURS_WORK_WEEK_EVENT");
	private final Duration contractDataHoursWorkWeek;


	public ChangeContractDataHoursWorkWeekEvent(Duration contractDataHoursWorkWeek) {
		super(CHANGE_CONTRACT_DATA_HOURS_WORK_WEEK_EVENT);
		this.contractDataHoursWorkWeek = contractDataHoursWorkWeek;
	}

	public Duration getContractDataHoursWorkWeek() {
		return contractDataHoursWorkWeek;
	}
}
