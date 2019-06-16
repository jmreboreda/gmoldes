package gmoldes.components.contract_documentation_control.events;

import javafx.event.Event;
import javafx.event.EventType;

import java.time.LocalDate;

public class ContractVariationSelectedEvent extends Event {

	public static final EventType<ContractVariationSelectedEvent> CONTRACT_VARIATION_SELECTED_EVENT = new EventType<>("CONTRACT_VARIATION_SELECTED_EVENT");
	private final Integer contractNumber;
	private final Integer variationType;
	private final LocalDate startDate;



	public ContractVariationSelectedEvent(Integer contractNumber, Integer variationType, LocalDate startDate) {
		super(CONTRACT_VARIATION_SELECTED_EVENT);
		this.contractNumber = contractNumber;
		this.variationType = variationType;
		this.startDate = startDate;

	}

	public Integer getContractNumber() {
		return contractNumber;
	}

	public Integer getVariationType() {
		return variationType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}
}
