package gmoldes.components.contract.contract_variation.events;

import gmoldes.components.contract.contract_variation.forms.ContractVariationDataSubfolder;
import javafx.event.Event;
import javafx.event.EventType;

public class MessageContractVariationEvent extends Event {

	public static final EventType<MessageContractVariationEvent> MESSAGE_CONTRACT_VARIATION_EVENT = new EventType<>("MESSAGE_CONTRACT_VARIATION_EVENT");

	private final String messageText;
	private final ContractVariationDataSubfolder contractVariationDataSubfolder;


	public MessageContractVariationEvent(

			String messageText,
			ContractVariationDataSubfolder contractVariationDataSubfolder
	) {
		super(MESSAGE_CONTRACT_VARIATION_EVENT);
		this.messageText = messageText;
		this.contractVariationDataSubfolder = contractVariationDataSubfolder;
	}

	public String getMessageText() {
		return messageText;
	}

	public ContractVariationDataSubfolder getContractVariationDataSubfolder() {
		return contractVariationDataSubfolder;
	}
}
