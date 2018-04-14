package buczaq.spaceteam.bean.event;

import java.util.EventObject;

public class ButtonToggled extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean newState;
	
	public ButtonToggled(Object source, boolean newState) {
		super(source);
		
		this.newState = newState;
	}
	
	public boolean getNewState() {
		return newState;
	}
	
}
