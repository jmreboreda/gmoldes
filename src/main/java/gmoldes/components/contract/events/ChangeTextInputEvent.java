package gmoldes.components.contract.events;

import javafx.event.Event;
import javafx.event.EventType;

import java.time.Duration;

public class ChangeTextInputEvent extends Event {

	public static final EventType<ChangeTextInputEvent> CHANGE_TEXT_INPUT_EVENT = new EventType<>("CHANGE_TEXT_INPUT_EVENT");
	private final String inputText;


	public ChangeTextInputEvent(String inputText) {
		super(CHANGE_TEXT_INPUT_EVENT);
		this.inputText = inputText;
	}

	public String getInputText() {
		return inputText;
	}
}
