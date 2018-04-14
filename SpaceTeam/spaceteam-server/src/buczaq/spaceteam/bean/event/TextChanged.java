package buczaq.spaceteam.bean.event;

import java.util.EventObject;

public class TextChanged extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String newText;
	
	public TextChanged(Object source, String newText) {
		super(source);
		
		this.newText = newText;
	}
	
	public String getNewText() {
		return newText;
	}

}
