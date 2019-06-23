package gmoldes.components.contract.consultation_contract.events;

import javafx.event.Event;
import javafx.event.EventType;

import java.time.LocalDate;

public class ContractDataTableSelectedRowEvent extends Event {

	public static final EventType<ContractDataTableSelectedRowEvent> CONTRACT_DATA_TABLE_SELECTED_ROW_EVENT = new EventType<>("CONTRACT_DATA_TABLE_SELECTED_ROW_EVENT");
	private final Integer variationTypeCode;
	private final LocalDate startDate;


	public ContractDataTableSelectedRowEvent(Integer variationTypeCode, LocalDate startDate) {
		super(CONTRACT_DATA_TABLE_SELECTED_ROW_EVENT);
		this.variationTypeCode = variationTypeCode;
		this.startDate = startDate;
	}

	public Integer getVariationTypeCode() {
		return variationTypeCode;
	}

	public LocalDate getStartDate() {
		return startDate;
	}
}
