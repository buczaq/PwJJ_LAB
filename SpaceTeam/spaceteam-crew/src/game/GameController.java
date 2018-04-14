package game;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import buczaq.spaceteam.bean.ControlPanel;
import buczaq.spaceteam.server.CrewType;
import buczaq.spaceteam.server.CrewGameServer;
import buczaq.spaceteam.server.GameServerManager;
import buczaq.spaceteam.server.command.Command;
import buczaq.spaceteam.server.command.factory.RandomCommandFactory;
import buczaq.spaceteam.server.impl.GameState;
import team.CrewWindow;

public class GameController {

	private CrewType crewType;

	private CrewWindow crewWindow;

	private ControlPanel controlPanel;

	private GameState lastGameState;

	private CrewGameServer gameServerStub;

	private Command currentCommand;

	public GameController(CrewType crewType, CrewWindow crewWindow) {
		this.crewType = crewType;
		this.crewWindow = crewWindow;

		this.crewWindow.setGameController(this);
	}

	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;

		crewWindow.setControlPanel(controlPanel);
	}

	public void onCommandExecutedButtonPressed() {

		System.out.println("Wciśnięto przycisk");

		boolean executedCorrectly = currentCommand.wasExecuted(crewType, controlPanel);

		try {
			if (executedCorrectly) {
				controlPanel.setLastCommandExecutedCorrectly(true);
				
				updateGameState(gameServerStub.onExecutedCorrectly());

			} else {
				controlPanel.setLastCommandExecutedCorrectly(false);
				
				updateGameState(gameServerStub.onExecutedIncorrectly());
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void connectAndStart() {
		if (controlPanel != null && crewWindow != null) {
			try {
				Registry registry = LocateRegistry.getRegistry(GameServerManager.REGISTRY_PORT);
				gameServerStub = (CrewGameServer) registry.lookup(GameServerManager.REGISTRY_STUB_NAME);

				List<Command> generatedCommands = RandomCommandFactory.getInstance().generate(controlPanel, crewType);

				if (gameServerStub.connectCrew(crewType, generatedCommands)) {
					System.out.println("Połączono");

					startPooling();

				} else {
					System.err.println("Nie można ustanowić połączenia");
				}
			} catch (Exception e) {
				System.err.println("Couldn't connect the crew: " + e.getMessage());
				e.printStackTrace();
			}

			crewWindow.setVisible(true);
		}

	}

	private void startPooling() {

		GameController gameController = this;

		new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {

				while (true) {
					Thread.sleep(1000);

					gameController.poolFromServer();
				}

			}

		}.execute();
	}

	private void poolFromServer() {

		try {
			GameState state = gameServerStub.getState();

			updateGameState(state);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	private void updateGameState(GameState gameState) {
		try {

			if (gameState != lastGameState) {
				// game state changed

				if (gameState == GameState.INITIALIZING) {
					// Disable send command button
					controlPanel.setSendCommandButtonEnabled(false);
				} else if(gameState == GameState.COMMAND_PHASE) {
					controlPanel.setSendCommandButtonEnabled(false);
					controlPanel.setCurrentCommand("Czekaj na rozkazy");
				} else	if (gameState == GameState.EXECUTION_PHASE) {
				
					// Enable send command button
					controlPanel.setSendCommandButtonEnabled(true);

					// get latest command
					currentCommand = gameServerStub.getCurrentCommand();
					controlPanel.setCurrentCommand(currentCommand.getCommandMessage());

				} else if(gameState == GameState.ENDED) {

					int finalTeamScore = gameServerStub.getCurrentTeamScore();

					JOptionPane.showMessageDialog(null, "Koniec gry, wynik: " + finalTeamScore);
					controlPanel.setSendCommandButtonEnabled(false);
				}

				this.lastGameState = gameState;
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

}
