package team.engine;

import javax.swing.JFrame;

import buczaq.spaceteam.bean.ControlPanel;
import game.GameController;
import team.CrewWindow;

public class CrewWindowImpl extends JFrame implements CrewWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameController gameController;

	public CrewWindowImpl() {
		this.setSize(500, 500);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	@Override
	public void setControlPanel(ControlPanel controlPanel) {
		controlPanel.setBounds(12, 13, 599, 405);
		getContentPane().add(controlPanel);
		
		
		registerControlPanelEventHandlers(controlPanel);
	}

	private void registerControlPanelEventHandlers(ControlPanel controlPanel) {
		
		controlPanel.addCommandExecutedButtonPressedListener(ev -> {
			gameController.onCommandExecutedButtonPressed();
		});
		
	}
}
