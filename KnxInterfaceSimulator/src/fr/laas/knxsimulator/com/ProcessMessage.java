package fr.laas.knxsimulator.com;

import fr.laas.knxsimulator.view.Window;
import fr.laas.knxsimulator.view.Window.ButtonListener;

/**
 * Allows to process messages when generated by the view
 * 
 * @author Guillaume Garzone
 * 
 */
public class ProcessMessage {

	private Window view;
	private MessagesBuffer outBuffer;

	public MessagesBuffer getOutBuffer() {
		return outBuffer;
	}

	public void setOutBuffer(MessagesBuffer outBuffer) {
		this.outBuffer = outBuffer;
	}

	/**
	 * Constructor
	 * 
	 * @param window
	 * @param buffer
	 */
	public ProcessMessage(Window window, MessagesBuffer buffer) {
		this.view = window;
		this.setOutBuffer(buffer);
		view.addButtonListener(new ButtonListener() {

			public void writeCalled(String sourceAddress, String groupAddress,
					String value) {
				processWrite(sourceAddress, groupAddress, value);
			}

			public void readCalled(String address) {
				processRead(address);
			}
		});
	}

	/**
	 * Generates the correct message and adds it to the output buffer
	 * 
	 * @param groupAddress
	 * @param value
	 * @param value2
	 */
	private void processWrite(String sourceAddress, String groupAddress,
			String value) {
		if (!groupAddress.equals("") && !value.equals("")
				&& sourceAddress.equals("")) {
			
			this.outBuffer.addMessageToFifo("write from x.x.x to "
					+ groupAddress + ": " + value);
			
		} else if (!groupAddress.equals("") && !value.equals("")
				&& !sourceAddress.equals("")) {
			
			this.outBuffer.addMessageToFifo("write from " + sourceAddress
					+ " to " + groupAddress + ": " + value);
		}
	}

	/**
	 * Generates the correct message and adds it to the output buffer
	 * 
	 * @param address
	 */
	private void processRead(String address) {
		if (!address.equals("")) {
			this.outBuffer.addMessageToFifo("read from x.x.x to " + address);
		}
	}

}
