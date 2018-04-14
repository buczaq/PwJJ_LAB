package buczaq.spaceteam.server.command;

import buczaq.spaceteam.bean.ControlPanel;
import buczaq.spaceteam.server.CrewType;

public class TextCommand extends Command {

	private String targetText;

	public TextCommand(CrewType targetCrew, String textDeviceName, String targetText) {
		super(targetCrew, String.format("Ustaw %s na %s", targetText, textDeviceName));

		this.targetText = targetText;
	}

	@Override
	public boolean wasExecuted(CrewType crew, ControlPanel controlPanel) {
		if (isTargetCrew(crew) == false) {
			return false;
		}

		return controlPanel.getTextDeviceValue().equals(targetText);
	}

}
