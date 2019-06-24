package gmoldes.components.contract_variation.events;

import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import javafx.event.Event;
import javafx.event.EventType;

import java.util.Set;

public class WorkScheduleEvent extends Event {

	public static final EventType<WorkScheduleEvent> WORK_SCHEDULE_EVENT = new EventType<>("WORK_SCHEDULE_EVENT");
	private final Set<WorkDaySchedule> schedule;


	public WorkScheduleEvent(Set<WorkDaySchedule> schedule) {
		super(WORK_SCHEDULE_EVENT);
		this.schedule = schedule;
	}

	public Set<WorkDaySchedule> getSchedule() {
		return schedule;
	}
}
