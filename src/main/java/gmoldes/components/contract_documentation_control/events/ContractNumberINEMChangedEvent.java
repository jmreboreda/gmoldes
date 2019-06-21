package gmoldes.components.contract_documentation_control.events;

import javafx.event.Event;
import javafx.event.EventType;

public class ContractNumberINEMChangedEvent extends Event {

	public static final EventType<ContractNumberINEMChangedEvent> CONTRACT_NUMBER_INEM_CHANGED_EVENT = new EventType<>("CONTRACT_NUMBER_INEM_CHANGED_EVENT");
	private final Boolean textFieldContractNumberINEMChanged;


	public ContractNumberINEMChangedEvent(Boolean textFieldContractNumberINEMChanged) {
		super(CONTRACT_NUMBER_INEM_CHANGED_EVENT);
		this.textFieldContractNumberINEMChanged = textFieldContractNumberINEMChanged;
	}

	public Boolean getTextFieldContractNumberINEMChanged() {
		return textFieldContractNumberINEMChanged;
	}
}
