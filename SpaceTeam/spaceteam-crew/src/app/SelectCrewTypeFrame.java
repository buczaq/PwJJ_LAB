package app;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import buczaq.spaceteam.server.CrewType;

public class SelectCrewTypeFrame extends JFrame {
	private JComboBox<CrewType> cbCrew;
	private CrewApp app;

	public SelectCrewTypeFrame() {
		setSize(452,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		cbCrew = new JComboBox<>();
		cbCrew.setBounds(50, 98, 215, 28);
		panel.add(cbCrew);
		
		for(CrewType crewType : CrewType.values()) {
			cbCrew.addItem(crewType);
		}
		
		JLabel lblSelectYourCrew = new JLabel("Wybierz funkcję:");
		lblSelectYourCrew.setBounds(70, 43, 307, 44);
		panel.add(lblSelectYourCrew);
		
		JButton btnEnterGame = new JButton("Wejdź do gry");
		btnEnterGame.setBounds(278, 100, 99, 25);
		panel.add(btnEnterGame);

		btnEnterGame.addActionListener(ev -> {
			CrewType crewType = (CrewType) cbCrew.getSelectedItem();
			
			app.enterGame(crewType);
		
			this.dispose();
		});
	}
	
	public void setApp(CrewApp app) { 
		this.app = app;
	}
}
