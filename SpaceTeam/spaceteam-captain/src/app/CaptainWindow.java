package app;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import buczaq.spaceteam.server.command.Command;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

public class CaptainWindow extends JFrame {

	private CaptainController captainController;

	private DefaultListModel<Command> listModel;

	private JList<Command> listCommands;

	private JButton btnSendCommand;

	private JLabel lblTeamScoreValue;

	public CaptainWindow() {
		setSize(500,500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		listModel = new DefaultListModel<>();

		listCommands = new JList(listModel);

		listCommands.setCellRenderer(new ListCellRenderer<Command>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Command> list, Command value, int index,
					boolean isSelected, boolean cellHasFocus) {

				JLabel label = new JLabel(value.getCommandMessage());

				if (isSelected) {
					label.setBorder(BorderFactory.createLineBorder(Color.black));
				}

				return label;
			}
		});

		getContentPane().add(listCommands, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("--- PANEL KAPITANA - WYBIERZ ROZKAZ  Z LISTY ---");
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		btnSendCommand = new JButton("Wyślij rozkaz");
		panel_1.add(btnSendCommand);

		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		JButton btnStartGame = new JButton("Rozpocznij");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captainController.startGame();
			}
		});
		panel_2.add(btnStartGame);

		JButton btnNewButton = new JButton("Zakończ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captainController.endGame();
			}
		});
		panel_2.add(btnNewButton);
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblTeamScore = new JLabel("Wynik");
		lblTeamScore.setBounds(12, 65, 68, 16);
		panel_3.add(lblTeamScore);
		
		lblTeamScoreValue = new JLabel("0");
		lblTeamScoreValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeamScoreValue.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblTeamScoreValue.setBounds(12, 94, 73, 94);
		panel_3.add(lblTeamScoreValue);

		btnSendCommand.addActionListener(ev -> {
			if (listCommands.getSelectedValue() != null) {
				captainController.sendCommand(listCommands.getSelectedValue());
			}
		});
	}

	public void updateCommands(List<Command> commands) {

		listModel.clear();

		commands.forEach(command -> listModel.addElement(command));

	}

	public void setCaptainController(CaptainController captainController) {
		this.captainController = captainController;
	}

	public void setSendCommandBtnEnabled(boolean b) {
		this.btnSendCommand.setEnabled(b);
	}

	public void setCurrentTeamScore(int currentTeamScore) {
		this.lblTeamScoreValue.setText(Integer.toString(currentTeamScore));
	}

}
