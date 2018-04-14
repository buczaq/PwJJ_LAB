package buczaq.spaceteam.bean;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import buczaq.spaceteam.bean.event.TextChangedListener;
import buczaq.spaceteam.server.CrewType;
import buczaq.spaceteam.bean.event.ButtonToggledListener;
import buczaq.spaceteam.bean.event.CommandExecutedButtonPressed;
import buczaq.spaceteam.bean.event.CommandExecutedButtonPressedListener;
import buczaq.spaceteam.bean.event.SliderChanged;
import buczaq.spaceteam.bean.event.SliderChangedListener;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;

public class ControlPanel extends JPanel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Bean properties

	private String sliderDeviceName;

	private Integer sliderDeviceMin;

	private Integer sliderDeviceMax;

	private String toggleDeviceName;

	private String toggleDeviceBtnLabel;

	private String textDeviceName;

	private String currentCommand;

	private CrewType crewType;
	
	private String roomName;

	private String[] possibleTextCommands;

	private Boolean sendCommandButtonEnabled = true;

	// Event listeners

	private List<TextChangedListener> textChangedListeners = new ArrayList<>();

	private List<ButtonToggledListener> buttonToggledListeners = new ArrayList<>();

	private List<SliderChangedListener> sliderChangedListeners = new ArrayList<>();

	private List<CommandExecutedButtonPressedListener> commandExecutedButtonPressedListeners = new ArrayList<>();

	private JButton buttons[];
	private JTextField tfTextDevice;

	private JPanel panelSliderDevice;

	private JSlider sliderSliderDevice;

	private JLabel lblSliderDeviceValue;

	private JLabel lblTitle;

	private JPanel panelToggleDevice;

	private JToggleButton tglbtnToggleDevice;

	private JPanel panelTextDevice;

	private JButton btnActionDone;
	private JLabel lblCommanderSays;
	private JPanel panelCommand;
	private JLabel lblCurrentCommand;
	private JLabel label;
	private JPanel panel_3;
	private JLabel lblYoureInThe;

	public ControlPanel() {
		setLayout(null);

		panelSliderDevice = new JPanel();
		panelSliderDevice.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Slider Device",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSliderDevice.setBounds(12, 125, 426, 66);
		add(panelSliderDevice);

		sliderSliderDevice = new JSlider();
		panelSliderDevice.add(sliderSliderDevice);
		
		sliderSliderDevice.addChangeListener(change -> {
			lblSliderDeviceValue.setText(Integer.toString(sliderSliderDevice.getValue()));
		});

		lblSliderDeviceValue = new JLabel("New label");
		panelSliderDevice.add(lblSliderDeviceValue);

		panelToggleDevice = new JPanel();
		panelToggleDevice
				.setBorder(new TitledBorder(null, "Toggle Device", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelToggleDevice.setBounds(12, 204, 426, 58);
		add(panelToggleDevice);

		tglbtnToggleDevice = new JToggleButton("New toggle button");
		panelToggleDevice.add(tglbtnToggleDevice);

		panelTextDevice = new JPanel();
		panelTextDevice
				.setBorder(new TitledBorder(null, "Text Device", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTextDevice.setBounds(12, 275, 426, 74);
		add(panelTextDevice);

		tfTextDevice = new JTextField();
		panelTextDevice.add(tfTextDevice);
		tfTextDevice.setColumns(10);

		btnActionDone = new JButton("Komenda wykonana");
		btnActionDone.setBounds(12, 362, 426, 25);
		add(btnActionDone);

		panelCommand = new JPanel();
		panelCommand.setBackground(Color.ORANGE);
		panelCommand.setBounds(12, 13, 438, 39);
		add(panelCommand);

		lblCommanderSays = new JLabel("Rozkaz kapitana: \"");
		panelCommand.add(lblCommanderSays);

		lblCurrentCommand = new JLabel("");
		panelCommand.add(lblCurrentCommand);

		label = new JLabel("\"");
		panelCommand.add(label);

		panel_3 = new JPanel();
		panel_3.setBounds(12, 60, 426, 39);
		add(panel_3);

		lblTitle = new JLabel("Silnik");
		panel_3.add(lblTitle);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));

		registerUIListeners();
	}

	private void registerUIListeners() {
		sliderSliderDevice.addChangeListener(ev -> {
			sliderChangedListeners
					.forEach(l -> l.sliderChanged(new SliderChanged(this, ((JSlider) ev.getSource()).getValue())));
		});

		btnActionDone.addActionListener(ev -> {
			commandExecutedButtonPressedListeners
					.forEach(l -> l.commandExecutedButtonPressed(new CommandExecutedButtonPressed(this)));
		});
	}

	@Override
	public void paint(Graphics g) {

		// Slider device
		if (sliderDeviceName == null || sliderDeviceName.isEmpty()) {
			panelSliderDevice.setEnabled(false);
			sliderSliderDevice.setEnabled(false);
			lblSliderDeviceValue.setEnabled(false);
			setTitledBorder(panelSliderDevice, "Wyłączone");

		} else {
			setTitledBorder(panelSliderDevice, sliderDeviceName);

			if (sliderDeviceMin != null && sliderDeviceMax != null) {
				sliderSliderDevice.setMinimum(sliderDeviceMin);
				sliderSliderDevice.setMaximum(sliderDeviceMax);
			}
		}

		// Toggle device
		if (toggleDeviceName == null || toggleDeviceName.isEmpty() || toggleDeviceBtnLabel == null
				|| toggleDeviceBtnLabel.isEmpty()) {
			panelToggleDevice.setEnabled(false);
			setTitledBorder(panelToggleDevice, "Wyłączone");
			tglbtnToggleDevice.setEnabled(false);

		} else {
			setTitledBorder(panelToggleDevice, toggleDeviceName);
			tglbtnToggleDevice.setText(toggleDeviceBtnLabel);
		}

		// Text device
		if (textDeviceName == null || textDeviceName.isEmpty()) {
			panelTextDevice.setEnabled(false);
			setTitledBorder(panelTextDevice, "Wyłączone");
			tfTextDevice.setEnabled(false);

		} else {
			setTitledBorder(panelTextDevice, textDeviceName);
		}

		if(roomName != null && !roomName.isEmpty()) {
			lblTitle.setText(roomName);
		}

		btnActionDone.setEnabled(sendCommandButtonEnabled);

		super.paint(g);
	}

	private void setTitledBorder(JPanel panel, String title) {
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), title, TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
	}

	public synchronized void addButtonPressedListener(TextChangedListener l) {
		textChangedListeners.add(l);
	}

	public synchronized void removeButtonPressedListener(TextChangedListener l) {
		textChangedListeners.remove(l);
	}

	public synchronized void addButtonToggledListener(ButtonToggledListener l) {
		buttonToggledListeners.add(l);
	}

	public synchronized void removeButtonToggledListener(ButtonToggledListener l) {
		buttonToggledListeners.remove(l);
	}

	public synchronized void addSliderChangedListener(SliderChangedListener l) {
		sliderChangedListeners.add(l);
	}

	public synchronized void removeSliderChangedListener(SliderChangedListener l) {
		sliderChangedListeners.remove(l);
	}

	public synchronized void addCommandExecutedButtonPressedListener(CommandExecutedButtonPressedListener l) {
		commandExecutedButtonPressedListeners.add(l);
	}

	public synchronized void removeCommandExecutedButtonPressedListener(CommandExecutedButtonPressedListener l) {
		commandExecutedButtonPressedListeners.remove(l);
	}

	public String getToggleDeviceName() {
		return toggleDeviceName;
	}

	public void setToggleDeviceName(String toggleDeviceName) {
		this.toggleDeviceName = toggleDeviceName;
	}

	public String getToggleDeviceBtnLabel() {
		return toggleDeviceBtnLabel;
	}

	public void setToggleDeviceBtnLabel(String toggleDeviceBtnLabel) {
		this.toggleDeviceBtnLabel = toggleDeviceBtnLabel;
	}

	public String getSliderDeviceName() {
		return sliderDeviceName;
	}

	public void setSliderDeviceName(String sliderDeviceName) {
		this.sliderDeviceName = sliderDeviceName;
	}

	public Integer getSliderDeviceMin() {
		return sliderDeviceMin;
	}

	public void setSliderDeviceMin(Integer sliderDeviceMin) {
		this.sliderDeviceMin = sliderDeviceMin;
	}

	public Integer getSliderDeviceMax() {
		return sliderDeviceMax;
	}

	public void setSliderDeviceMax(Integer sliderDeviceMax) {
		this.sliderDeviceMax = sliderDeviceMax;
	}

	public String getTextDeviceName() {
		return textDeviceName;
	}

	public void setTextDeviceName(String textDeviceName) {
		this.textDeviceName = textDeviceName;
	}

	public void setCurrentCommand(String currentCommand) {
		this.currentCommand = currentCommand;
		lblCurrentCommand.setText(currentCommand);
	}

	public CrewType getCrewType() {
		return crewType;
	}

	public void setCrewType(CrewType crewType) {
		this.crewType = crewType;
	}

	public String[] getPossibleTextCommands() {
		return possibleTextCommands;
	}

	public void setPossibleTextCommands(String[] possibleTextCommands) {
		this.possibleTextCommands = possibleTextCommands;
	}

	public void setSendCommandButtonEnabled(Boolean sendCommandButtonEnabled) {
		this.sendCommandButtonEnabled = sendCommandButtonEnabled;
		
		this.btnActionDone.setEnabled(sendCommandButtonEnabled);
	}

	public int getSliderDeviceValue() {
		return this.sliderSliderDevice.getValue();
	}

	public String getTextDeviceValue() {
		return this.tfTextDevice.getText();
	}

	public boolean getToggleDeviceValue() {
		return this.tglbtnToggleDevice.isSelected();
	}

	public void setLastCommandExecutedCorrectly(boolean b) {
		panelCommand.setBackground(b ? Color.GREEN : Color.RED);
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

}
