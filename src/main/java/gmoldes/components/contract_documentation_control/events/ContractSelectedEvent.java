package gmoldes.components.contract_documentation_control.events;

import javafx.event.Event;
import javafx.event.EventType;

public class ContractSelectedEvent extends Event {

	public static final EventType<ContractSelectedEvent> CONTRACT_SELECTED_EVENT = new EventType<>("CONTRACT_SELECTED_EVENT");
	private final Integer selectedContractNumber;


	public ContractSelectedEvent(Integer selectedContract) {
		super(CONTRACT_SELECTED_EVENT);
		this.selectedContractNumber = selectedContract;
	}

	public Integer getSelectedContractNumber() {
		return selectedContractNumber;
	}
}
