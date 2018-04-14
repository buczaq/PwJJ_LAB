package buczaq.spaceteam.bean.event;

import java.util.EventObject;

public class CommandExecutedButtonPressed extends EventObject {

	private static final long serialVersionUID = 1L;

	public CommandExecutedButtonPressed(Object source) {
		super(source);
	}

}
