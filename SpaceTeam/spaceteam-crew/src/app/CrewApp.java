package app;

import buczaq.spaceteam.server.CrewType;
import game.GameController;
import game.factory.GameControllerFactory;

public class CrewApp {

	private static final CrewType CREW_TYPE = CrewType.EngineCrew;

	public static void main(String[] args) {

		CrewApp app = new CrewApp();
		
		SelectCrewTypeFrame selectCrewTypeFrame = new SelectCrewTypeFrame();
		selectCrewTypeFrame.setApp(app);
		selectCrewTypeFrame.setVisible(true);

	}

	public void enterGame(CrewType crewType) {

		GameController gameController = GameControllerFactory.getInstance().create(crewType);

		gameController.connectAndStart();

	}

}
