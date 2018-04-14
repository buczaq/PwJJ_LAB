package buczaq.spaceteam.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import buczaq.spaceteam.server.CrewType;
import buczaq.spaceteam.server.GameServerManager;

import javax.swing.JLabel;
import java.awt.Font;

public class ServerWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private GameServerManager gameServerManager;

	private JButton btnStartServer;

	private JButton btnStopServer;

	private JLabel lblSteeringCrew;

	private JLabel lblEngineCrew;

	private JLabel lblArtilleryCrew;

	private JLabel lblTheCaptain;
	private JLabel lblConnectedPlayers;

	public static void main(String args[]) {
		ServerWindow serverWindow = new ServerWindow("Game Server");
		serverWindow.setGameServerManager(new GameServerManager(serverWindow));

		serverWindow.setVisible(true);
	}

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public ServerWindow(String title) throws HeadlessException {
		super(title);

		setSize(400, 640);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		btnStartServer = new JButton("Włącz serwer");
		btnStartServer.setBounds(12, 5, 177, 25);
		panel.add(btnStartServer);

		btnStopServer = new JButton("Wyłącz serwer");
		btnStopServer.setBounds(194, 5, 176, 25);
		panel.add(btnStopServer);

		lblSteeringCrew = new JLabel("Sterownia");
		lblSteeringCrew.setOpaque(true);
		lblSteeringCrew.setBackground(Color.RED);
		lblSteeringCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSteeringCrew.setBounds(135, 124, 111, 35);
		panel.add(lblSteeringCrew);

		lblEngineCrew = new JLabel("Silnik");
		lblEngineCrew.setOpaque(true);
		lblEngineCrew.setBackground(Color.RED);
		lblEngineCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEngineCrew.setBounds(135, 224, 111, 35);
		panel.add(lblEngineCrew);

		lblArtilleryCrew = new JLabel("Działa");
		lblArtilleryCrew.setOpaque(true);
		lblArtilleryCrew.setBackground(Color.RED);
		lblArtilleryCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblArtilleryCrew.setBounds(135, 324, 111, 35);
		panel.add(lblArtilleryCrew);

		lblTheCaptain = new JLabel("Kapitan");
		lblTheCaptain.setOpaque(true);
		lblTheCaptain.setBackground(Color.RED);
		lblTheCaptain.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTheCaptain.setBounds(135, 424, 111, 35);
		panel.add(lblTheCaptain);

		lblConnectedPlayers = new JLabel("Połączeni gracze (czerwony = niepołączony):");
		lblConnectedPlayers.setBounds(12, 59, 350, 16);
		panel.add(lblConnectedPlayers);

		registerButtonHandlers();
		registerWindowClosingHandler();
	}

	private void registerButtonHandlers() {
		btnStartServer.addActionListener((ev) -> {
			try {
				gameServerManager.startServer();
			} catch (RemoteException | AlreadyBoundException e) {
				System.err.println("Nie udało się uruchomić serwera: " + e.getMessage());
			}
		});

		btnStopServer.addActionListener((ev) -> {
			try {
				gameServerManager.stopServer();
			} catch (RemoteException | NotBoundException e) {
				System.err.println("Nie udało się wyłączyć„ serwera: " + e.getMessage());
			}
		});

	}

	public void setGameServerManager(GameServerManager gameServerManager) {
		this.gameServerManager = gameServerManager;
	}

	private void registerWindowClosingHandler() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				if (gameServerManager != null) {
					try {
						gameServerManager.stopServer();
					} catch (RemoteException | NotBoundException e1) {
					}
				}

				System.exit(0);
			}
		});
	}

	public void addCrewMemberToPlayerList(CrewType crewType) {
		if (crewType == CrewType.EngineCrew) {
			lblEngineCrew.setBackground(Color.GREEN);
		} else if (crewType == CrewType.SteeringCrew) {
			lblSteeringCrew.setBackground(Color.GREEN);
		} else if (crewType == CrewType.ArtilleryCrew) {
			lblArtilleryCrew.setBackground(Color.GREEN);
		}
	}

	public void addCaptainConnected() {
		lblTheCaptain.setBackground(Color.GREEN);
	}
}
