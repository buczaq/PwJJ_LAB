package game.factory;

import buczaq.spaceteam.bean.ControlPanel;
import buczaq.spaceteam.server.CrewType;

public class ControlPanelFactory {
	
	private ControlPanelFactory() {};
	
	private static ControlPanelFactory instance = null;
	
	public static ControlPanelFactory getInstance() {
		return instance == null ? instance = new ControlPanelFactory() : instance;
	}
	
	ControlPanel create(CrewType crewType) {
		
		ControlPanel controlPanel = new ControlPanel();
		
		if(crewType == CrewType.EngineCrew) {
			customizeForEngineCrew(controlPanel);
		} else if(crewType == CrewType.SteeringCrew) {
			customizeForSteeringCrew(controlPanel);
		} else if(crewType == CrewType.ArtilleryCrew) {
			customizeForArtilleryCrew(controlPanel);
		}
		
		return controlPanel;
		
	}

	private void customizeForEngineCrew(ControlPanel controlPanel) {
		controlPanel.setSliderDeviceMax(100);
		controlPanel.setSliderDeviceMin(10);
		controlPanel.setToggleDeviceBtnLabel("Turbo");
		controlPanel.setToggleDeviceName("Dodatkowe");
		controlPanel.setTextDeviceName("");
		controlPanel.setSliderDeviceName("Chłodzenie silnika");
		controlPanel.setRoomName("Silnik");
	}


	private void customizeForSteeringCrew(ControlPanel controlPanel) {
		controlPanel.setTextDeviceName("Kierunek");
		controlPanel.setPossibleTextCommands(new String[] {"E", "W", "N", "S"});
		controlPanel.setSliderDeviceName("");
		controlPanel.setRoomName("Sterownia");
	}

	private void customizeForArtilleryCrew(ControlPanel controlPanel) {
		controlPanel.setSliderDeviceMax(150);
		controlPanel.setSliderDeviceMin(100);
		controlPanel.setSliderDeviceName("Tarcza");
		controlPanel.setRoomName("Działa");
	}
	
}
