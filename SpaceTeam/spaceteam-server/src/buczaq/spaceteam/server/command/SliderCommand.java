package buczaq.spaceteam.server.command;

import buczaq.spaceteam.bean.ControlPanel;
import buczaq.spaceteam.server.CrewType;

public class SliderCommand extends Command {

	private int targetSliderValue;
	
	public SliderCommand(CrewType targetCrew, String sliderDeviceName, int targetSliderValue) {
		
		super(targetCrew, String.format("Zmień %s na %d", sliderDeviceName, targetSliderValue));
	
		this.targetSliderValue = targetSliderValue;
	}

	@Override
	public boolean wasExecuted(CrewType crew, ControlPanel controlPanel) {
		if(!isTargetCrew(crew)) {
			return false;
		}
		
		return controlPanel.getSliderDeviceValue() == targetSliderValue;
	}

}
