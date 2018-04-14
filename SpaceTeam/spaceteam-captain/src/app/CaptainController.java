package app;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

import buczaq.spaceteam.server.CaptainGameServer;
import buczaq.spaceteam.server.GameServerManager;
import buczaq.spaceteam.server.command.Command;
import buczaq.spaceteam.server.impl.GameState;

public class CaptainController {

	private CaptainWindow captainWindow;

	private CaptainGameServer gameServerStub;

	private GameState lastGameState;

	private int currentTeamScore;

	public void setCaptainWindow(CaptainWindow captainWindow) {
		this.captainWindow = captainWindow;
	}

	public void connectAndStart() {
		if (captainWindow != null) {
			try {
				Registry registry = LocateRegistry.getRegistry(GameServerManager.REGISTRY_PORT);
				gameServerStub = (CaptainGameServer) registry.lookup(GameServerManager.REGISTRY_STUB_NAME);

				if (gameServerStub.connectCaptain()) {
					System.out.println("Połączono");
				} else {
					System.err.println("Nie można ustanowić połączenia");
				}
			} catch (Exception e) {
				System.err.println("Nie można połączyć z załogą: " + e.getMessage());
				e.printStackTrace();
			}

			IntervalPooling intervalPooling = new IntervalPooling(this);
			intervalPooling.execute();

			captainWindow.setVisible(true);

		}
	}

	public void sendCommand(Command command) {
		if (lastGameState == GameState.COMMAND_PHASE) {
			try {
				lastGameState = gameServerStub.sendCommand(command);
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			if (lastGameState == GameState.EXECUTION_PHASE) {
				System.out.println("Wysłano rozkaz");

				captainWindow.setSendCommandBtnEnabled(false);
			} else {
				System.out.println("Nie wysłano rozkazu");
			}
		}
	}

	public void startGame() {
		try {

			lastGameState = gameServerStub.startGame();

			if (lastGameState == GameState.INITIALIZING) {
				System.out.println("Nie można rozpocząć gry");
			} else if (lastGameState == GameState.COMMAND_PHASE) {
				System.out.println("Gra rozpoczęta");
				captainWindow.updateCommands(gameServerStub.getAllCommands());

			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void poolFromServer() {

		try {
			GameState state = gameServerStub.getState();

			currentTeamScore = gameServerStub.getCurrentTeamScore();
			captainWindow.setCurrentTeamScore(currentTeamScore);

			if (state != lastGameState) {

				if (state == GameState.COMMAND_PHASE) {
					captainWindow.setSendCommandBtnEnabled(true);
				}

				this.lastGameState = state;
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void endGame() {
		try {

			this.lastGameState = gameServerStub.endGame();

			if (lastGameState == GameState.ENDED) {
				JOptionPane.showMessageDialog(null, "Koniec gry!");
			} else {
				JOptionPane.showMessageDialog(null, "Nie można zakończyć gry.");
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
