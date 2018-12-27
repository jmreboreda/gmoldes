package gmoldes.components.contract.contract_variation.events;

import javafx.event.Event;
import javafx.event.EventType;

public class VariationTypeEvent extends Event {

	public static final EventType<VariationTypeEvent> VARIATION_TYPE_EVENT = new EventType<>("VARIATION_TYPE_EVENT");
	private final Boolean isContractVariationExtinction;
	private final Boolean isContractVariationExtension;
	private final Boolean isContractVariationConversion;


	public VariationTypeEvent(Boolean isContractVariationExtinction, Boolean isContractVariationExtension, Boolean isContractVariationConversion) {
		super(VARIATION_TYPE_EVENT);
		this.isContractVariationExtinction = isContractVariationExtinction;
		this.isContractVariationExtension = isContractVariationExtension;
		this.isContractVariationConversion = isContractVariationConversion;
	}

	public Boolean getContractVariationExtinction() {
		return isContractVariationExtinction;
	}

	public Boolean getContractVariationExtension() {
		return isContractVariationExtension;
	}

	public Boolean getContractVariationConversion() {
		return isContractVariationConversion;
	}
}
