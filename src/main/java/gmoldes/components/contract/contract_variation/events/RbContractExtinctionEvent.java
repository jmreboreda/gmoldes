package gmoldes.components.contract.contract_variation.events;

import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import javafx.event.Event;
import javafx.event.EventType;

import java.time.LocalDate;
import java.util.List;

public class RbContractExtinctionEvent extends Event {

	public static final EventType<RbContractExtinctionEvent> RB_CONTRACT_EXTINCTION_EVENT_EVENT_TYPE = new EventType<>("RB_CONTRACT_EXTINCTION_EVENT_EVENT_TYPE");
	private final List<TypesContractVariationsDTO> contractTypeDTOList;


	public RbContractExtinctionEvent(List<TypesContractVariationsDTO> contractTypeDTOList) {
		super(RB_CONTRACT_EXTINCTION_EVENT_EVENT_TYPE);
		this.contractTypeDTOList = contractTypeDTOList;
	}

	public List<TypesContractVariationsDTO> getContractTypeDTOList() {
		return contractTypeDTOList;
	}
}
