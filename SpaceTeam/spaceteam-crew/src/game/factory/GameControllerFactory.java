package game.factory;

import buczaq.spaceteam.server.CrewType;
import game.GameController;
import team.engine.CrewWindowImpl;

public class GameControllerFactory {

	private GameControllerFactory() {};
	
	private static GameControllerFactory instance = null;
	
	public static GameControllerFactory getInstance() {
		return instance == null ? instance = new GameControllerFactory() : instance;
	}
	
	public GameController create(CrewType crewType) {
		
		ControlPanelFactory controlPanelFactory = ControlPanelFactory.getInstance();

		GameController gameController = new GameController(crewType, new CrewWindowImpl());
		
		gameController.setControlPanel(controlPanelFactory.create(crewType));

		return gameController;
	}

}
