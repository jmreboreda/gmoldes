package gmoldes.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class ViewLoader {

	private static final Logger logger = Logger.getLogger(ViewLoader.class.getSimpleName());

	public static Parent load(Object object, String fxml) {
		final URL resource = ViewLoader.class.getResource(fxml);
		final FXMLLoader loader = new FXMLLoader(resource);
		loader.setController(object);
		loader.setRoot(object);
		try {
			logger.info("Loading " + fxml +" ...");
			return loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
