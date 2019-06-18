package gmoldes.components.contract_documentation_control.events;

import javafx.event.Event;
import javafx.event.EventType;

public class CellTableChangedEvent extends Event {

	public static final EventType<CellTableChangedEvent> CELL_TABLE_CHANGED_EVENT = new EventType<>("CELL_TABLE_CHANGED_EVENT");
	private final Boolean cellsEdited;


	public CellTableChangedEvent(Boolean cellsEdited) {
		super(CELL_TABLE_CHANGED_EVENT);
		this.cellsEdited = cellsEdited;
	}

	public Boolean getCellsEdited() {
		return cellsEdited;
	}
}
