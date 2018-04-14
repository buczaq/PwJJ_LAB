package buczaq.spaceteam.server.command;

import buczaq.spaceteam.bean.ControlPanel;
import buczaq.spaceteam.server.CrewType;

public class ToggleCommand extends Command{

	private boolean targetState;
	
	public ToggleCommand(CrewType targetCrew, String toggleButtonName, boolean targetState) {

		super(targetCrew, String.format("Zmień %s %s na", toggleButtonName, targetState ? "włączone" : "wyłączone"));

		this.targetState = targetState;
	}

	@Override
	public boolean wasExecuted(CrewType crew, ControlPanel controlPanel) {
		if(!isTargetCrew(crew)) {
			return false;
		}
		
		return controlPanel.getToggleDeviceValue() == targetState;
	}

}
