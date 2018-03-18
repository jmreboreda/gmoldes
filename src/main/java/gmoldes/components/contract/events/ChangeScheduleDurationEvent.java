package gmoldes.components.contract.events;

import javafx.event.Event;
import javafx.event.EventType;

import java.time.Duration;

public class ChangeScheduleDurationEvent extends Event {

	public static final EventType<ChangeScheduleDurationEvent> CHANGE_SCHEDULE_DURATION_EVENT = new EventType<>("CHANGE_SCHEDULE_DURATION_EVENT");
	private final Duration contractScheduleTotalHoursDuration;


	public ChangeScheduleDurationEvent(Duration contractScheduleTotalHoursDuration) {
		super(CHANGE_SCHEDULE_DURATION_EVENT);
		this.contractScheduleTotalHoursDuration = contractScheduleTotalHoursDuration;
	}

	public Duration getContractScheduleTotalHoursDuration() {
		return contractScheduleTotalHoursDuration;
	}
}
