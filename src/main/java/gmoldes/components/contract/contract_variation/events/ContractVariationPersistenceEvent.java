package gmoldes.components.contract.contract_variation.events;

import javafx.event.Event;
import javafx.event.EventType;

public class ContractVariationPersistenceEvent extends Event {

	public static final EventType<ContractVariationPersistenceEvent> PERSIST_CONTRACT_EXTINCTION_EVENT_EVENT_TYPE = new EventType<>("PERSIST_CONTRACT_EXTINCTION_EVENT_EVENT_TYPE");
	private final Boolean persistenceIsOk;
	private final String persistenceMessage;


	public ContractVariationPersistenceEvent(Boolean persistenceIsOk, String persistenceMessage) {
		super(PERSIST_CONTRACT_EXTINCTION_EVENT_EVENT_TYPE);
		this.persistenceIsOk = persistenceIsOk;
		this.persistenceMessage = persistenceMessage;
	}

	public Boolean getPersistenceIsOk() {
		return persistenceIsOk;
	}

	public String getPersistenceMessage() {
		return persistenceMessage;
	}
}
