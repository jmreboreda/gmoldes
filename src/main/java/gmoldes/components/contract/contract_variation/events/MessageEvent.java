package gmoldes.components.contract.contract_variation.events;

import javafx.event.Event;
import javafx.event.EventType;

public class MessageEvent extends Event {

	public static final EventType<MessageEvent> MESSAGE_EVENT = new EventType<>("MESSAGE_EVENT");

	private final String messageText;


	public MessageEvent(

			String messageText
	) {
		super(MESSAGE_EVENT);
		this.messageText = messageText;
	}

	public String getMessageText() {
		return messageText;
	}
}
