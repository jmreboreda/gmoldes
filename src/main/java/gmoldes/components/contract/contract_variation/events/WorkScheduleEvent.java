package gmoldes.components.contract.contract_variation.events;

import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.domain.client.dto.ClientDTO;
import javafx.event.Event;
import javafx.event.EventType;

import java.time.LocalDate;
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
