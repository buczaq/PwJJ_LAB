package buczaq.spaceteam.bean.event;

import java.util.EventListener;

public interface CommandExecutedButtonPressedListener extends EventListener{
	public abstract void commandExecutedButtonPressed(CommandExecutedButtonPressed ev);
}
