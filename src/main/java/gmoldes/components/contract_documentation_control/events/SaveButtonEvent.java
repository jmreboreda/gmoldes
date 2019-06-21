package gmoldes.components.contract_documentation_control.events;

import gmoldes.domain.contract_documentation_control.ContractDocumentationControlDataDTO;
import javafx.event.Event;
import javafx.event.EventType;

public class SaveButtonEvent extends Event {

	public static final EventType<SaveButtonEvent> SAVE_BUTTON_EVENT = new EventType<>("SAVE_BUTTON_EVENT");
	private final ContractDocumentationControlDataDTO contractDocumentationControlDataDTO;


	public SaveButtonEvent(ContractDocumentationControlDataDTO contractDocumentationControlDataDTO) {
		super(SAVE_BUTTON_EVENT);
		this.contractDocumentationControlDataDTO = contractDocumentationControlDataDTO;
	}

	public ContractDocumentationControlDataDTO getContractDocumentationControlDataDTO() {
		return contractDocumentationControlDataDTO;
	}
}
