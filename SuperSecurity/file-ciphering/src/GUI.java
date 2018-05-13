import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    //private JTextField textField1;
    //private JButton zaszyfrujButton;
    //private JButton odszyfrujButton;
    private Encryptor encryptor;

    public GUI() {
        super("szyfrator");
        setSize(250, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        JPanel p = new JPanel(new GridLayout(4, 1));
        JTextField textField1 = new JTextField();
        p.add(textField1);
        JButton zaszyfrujButton = new JButton();
        zaszyfrujButton.setText("zaszyfruj");
        p.add(zaszyfrujButton);
        JButton odszyfrujButton = new JButton();
        odszyfrujButton.setText("odszyfruj");
        p.add(odszyfrujButton);
        setContentPane(p);
        try {
            encryptor = new Encryptor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        zaszyfrujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    encryptor.writeEncrypted(textField1.getText());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });

        odszyfrujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    encryptor.writeDecrypted(textField1.getText());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args)
    {
        GUI gui = new GUI();
        gui.setVisible(true);
    }
};