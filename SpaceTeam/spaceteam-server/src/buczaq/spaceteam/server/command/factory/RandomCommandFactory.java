package buczaq.spaceteam.server.command.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import buczaq.spaceteam.bean.ControlPanel;
import buczaq.spaceteam.server.CrewType;
import buczaq.spaceteam.server.command.Command;
import buczaq.spaceteam.server.command.SliderCommand;
import buczaq.spaceteam.server.command.TextCommand;
import buczaq.spaceteam.server.command.ToggleCommand;

public class RandomCommandFactory {
	
	private RandomCommandFactory() {};
	
	private static RandomCommandFactory instance = null;
	
	public static RandomCommandFactory getInstance() {
		return instance == null ? instance = new RandomCommandFactory() : instance;
	}

	private static final int NUM_SLIDER_COMMANDS = 5;

	private static final int NUM_TEXT_COMMANDS = 3;

	private Random rand = new Random();

	public List<Command> generate(ControlPanel controlPanel, CrewType crewType) {

		List<Command> randomCommands = new ArrayList<>();

		// Generate toggle commands
		if (controlPanel.getToggleDeviceName() != null && !controlPanel.getToggleDeviceName().isEmpty()) {
			String toggleButtonName = controlPanel.getToggleDeviceBtnLabel();
			randomCommands.add(new ToggleCommand(crewType, toggleButtonName, true));
			randomCommands.add(new ToggleCommand(crewType, toggleButtonName, false));
		}

		// Generate slider commands
		if (controlPanel.getSliderDeviceName() != null && !controlPanel.getSliderDeviceName().isEmpty()) {
			String sliderDeviceName = controlPanel.getSliderDeviceName();
			int sliderMin = controlPanel.getSliderDeviceMin();
			int sliderMax = controlPanel.getSliderDeviceMax();

			for (int i = 0; i < NUM_SLIDER_COMMANDS; i++) {
				int targetSliderValue = rand.nextInt(sliderMax - sliderMin + 1) + sliderMin;

				randomCommands.add(new SliderCommand(crewType, sliderDeviceName, targetSliderValue));
			}
		}

		// Generate text commands
		if (controlPanel.getTextDeviceName() != null && !controlPanel.getTextDeviceName().isEmpty()) {
			String textDeviceName = controlPanel.getTextDeviceName();

			for (int i = 0; i < NUM_TEXT_COMMANDS; i++) {
				int randomPossibleTextIndex = rand.nextInt(controlPanel.getPossibleTextCommands().length);

				randomCommands.add(new TextCommand(crewType, textDeviceName,
						controlPanel.getPossibleTextCommands()[randomPossibleTextIndex]));
			}
		}

		return randomCommands;
	}
}
