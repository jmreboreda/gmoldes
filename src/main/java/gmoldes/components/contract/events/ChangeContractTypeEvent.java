package gmoldes.components.contract.events;

import gmoldes.domain.contract.dto.ContractTypeDTO;
import javafx.event.Event;
import javafx.event.EventType;

import java.time.Duration;

public class ChangeContractTypeEvent extends Event {

	public static final EventType<ChangeContractTypeEvent> CHANGE_CONTRACT_TYPE_EVENT = new EventType<>("CHANGE_CONTRACT_TYPE_EVENT");
	private final ContractTypeDTO contractType;


	public ChangeContractTypeEvent(ContractTypeDTO contractType) {
		super(CHANGE_CONTRACT_TYPE_EVENT);
		this.contractType = contractType;
	}

	public ContractTypeDTO getContractType() {
		return contractType;
	}
}