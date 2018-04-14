package buczaq.spaceteam.bean.event;

import java.util.EventListener;

public interface ButtonToggledListener extends EventListener {
	public abstract void buttonToggled(ButtonToggled event);
}
