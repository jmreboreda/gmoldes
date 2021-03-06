package gmoldes.components.contract_variation.events;

import javafx.event.Event;
import javafx.event.EventType;

public class CompatibleVariationEvent extends Event {

	public static final EventType<CompatibleVariationEvent> COMPATIBLE_VARIATION_EVENT = new EventType<>("COMPATIBLE_VARIATION_EVENT");
	private final Boolean isContractExtinctionEvent;
	private final Boolean isContractExtensionEvent;
	private final Boolean isWeeklyWorkDurationVariationEvent;
	private final Boolean isContractConversionEvent;
	private final String errorContractVariationMessage;


	public CompatibleVariationEvent(
			Boolean isContractExtinctionEvent,
			Boolean isContractExtensionEvent,
			Boolean isWeeklyWorkDurationVariationEvent,
			Boolean isContractConversionEvent,
			String errorContractVariationMessage
	) {
		super(COMPATIBLE_VARIATION_EVENT);
		this.isContractExtinctionEvent = isContractExtinctionEvent;
		this.isContractExtensionEvent = isContractExtensionEvent;
		this.isWeeklyWorkDurationVariationEvent = isWeeklyWorkDurationVariationEvent;
		this.isContractConversionEvent = isContractConversionEvent;
		this.errorContractVariationMessage = errorContractVariationMessage;
	}

	public Boolean getContractExtinctionEvent() {
		return isContractExtinctionEvent;
	}

	public Boolean getContractExtensionEvent() {
		return isContractExtensionEvent;
	}

	public Boolean getWeeklyWorkDurationVariationEvent() {
		return isWeeklyWorkDurationVariationEvent;
	}

	public Boolean getContractConversionEvent() {
		return isContractConversionEvent;
	}

	public String getErrorContractVariationMessage() {
		return errorContractVariationMessage;
	}
}
