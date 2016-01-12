/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.io.PrintStream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * @author heifara
 *
 */
@SuppressWarnings("restriction")
public class Console extends BorderPane implements com.maohi.software.maohifx.common.Console {

	private final FXMLLoader loader;

	private ObjectProperty<PrintStream> printStream;
	private ObjectProperty<ConsoleRedirectOutputStream> consoleRedirectOutputStream;

	@FXML
	private Button resetButton;

	@FXML
	private TextArea textArea;

	public Console() {
		try {
			this.loader = new FXMLLoader();
			this.loader.setLocation(this.getClass().getResource("Console.fxml"));
			this.loader.setRoot(this);
			this.loader.setController(this);

			this.loader.load();
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
	}

	public ObjectProperty<ConsoleRedirectOutputStream> consoleRedirectOutputStreamProperty() {
		if (this.consoleRedirectOutputStream == null) {
			this.consoleRedirectOutputStream = new SimpleObjectProperty<>();
			this.consoleRedirectOutputStream.addListener(new ChangeListener<ConsoleRedirectOutputStream>() {

				@Override
				public void changed(final ObservableValue<? extends ConsoleRedirectOutputStream> aObservable, final ConsoleRedirectOutputStream aOldValue, final ConsoleRedirectOutputStream aNewValue) {
					final PrintStream iPrintStream = new PrintStream(aNewValue);
					Console.this.printStreamProperty().set(iPrintStream);
				}
			});
			this.consoleRedirectOutputStream.set(new ConsoleRedirectOutputStream(this.textArea));
		}
		return this.consoleRedirectOutputStream;
	}

	public ConsoleRedirectOutputStream getConsoleRedirectOutputStream() {
		return this.consoleRedirectOutputStreamProperty().get();
	}

	public PrintStream getPrintStream() {
		return this.printStreamProperty().get();
	}

	@Override
	public void log(final Object aObject) {
		if (aObject == null) {
			this.getPrintStream().println("null");
		} else if (aObject instanceof ScriptObjectMirror) {
			final ScriptObjectMirror iScriptObject = (ScriptObjectMirror) aObject;
			final ScriptObjectMirror iToStringMethod = (ScriptObjectMirror) iScriptObject.get("toString");
			if (iToStringMethod != null) {
				iToStringMethod.setMember("this", iScriptObject);
				final String iToString = (String) iToStringMethod.call(iScriptObject);
				if (iToString != null) {
					this.getPrintStream().println(iToString);
				}
			} else {
				this.getPrintStream().println("toString method missing");
			}
		} else {
			this.getPrintStream().println(aObject.toString());
		}
	}

	@Override
	public void log(final String aText) {
		this.getPrintStream().println(aText);
	}

	public ObjectProperty<PrintStream> printStreamProperty() {
		if (this.printStream == null) {
			this.printStream = new SimpleObjectProperty<>();
			this.printStream.set(new PrintStream(this.getConsoleRedirectOutputStream()));
		}
		return this.printStream;
	}

	@FXML
	public void resetEvent(final ActionEvent aEvent) {
		this.getConsoleRedirectOutputStream().clear();
	}

	public void setPrintStream(final PrintStream aPrintStream) {
		this.printStreamProperty().set(aPrintStream);
	}

}
