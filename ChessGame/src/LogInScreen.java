import javax.naming.ContextNotEmptyException;
import javax.naming.NotContextException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LogInScreen extends JFrame {

    public static final int DEFAULT_WIDTH = 550;
    public static final int DEFAULT_HEIGHT = 550;
    private JFrame jFrame;
    private ImageIcon iconBackground;
    private JLabel lblBackground;
    private ImageIcon iconMenu;
    private JLabel lblMenu;
    public JTextField txtName1;
    public JTextField txtName2;
    private JLabel lblName1;
    private JLabel lblName2;
    private JLabel lblExpression;
    private JLabel addUserIcon;
    private ImageIcon addUserIconn;

    public LogInScreen() {
        Background();
        Frame();
        Button(lblBackground);
        fieldTextName(lblBackground);
        addUserIcon();
        lblPlayerName();
    }

    public void Background() {
        iconMenu = new ImageIcon(this.getClass().getResource("/images/chess_menu_icon3.png"));
        lblMenu = new JLabel(iconMenu);
        lblMenu.setBounds(187, 0, 128, 128);

        iconBackground = new ImageIcon(this.getClass().getResource("/images/gradient.png"));
        lblBackground = new JLabel(iconBackground);
        lblBackground.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        lblBackground.add(lblMenu);

    }

    public void Frame() {
        jFrame = new JFrame("Chess");
        jFrame.add(lblBackground);
        jFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null); // to open the window on the middle of screen
        ImageIcon gameIcon = new ImageIcon(this.getClass().getResource("/images/chess_menu_icon.png"));
        jFrame.setIconImage(gameIcon.getImage());
        jFrame.setVisible(true);
        jFrame.setResizable(false);
    }

    public void Button(JLabel jlabel) {
        JButton btnStart;
        lblExpression = new JLabel();
        lblBackground.add(lblExpression);
        lblExpression.setBounds(160, 400, 550, 25);
        btnStart = new JButton("Start Game");
        btnStart.setBounds(200, 350, 100, 30);
        jlabel.add(btnStart);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Main.player0.setName(txtName1.getText().trim().toUpperCase());
                    Main.player1.setName(txtName2.getText().trim().toUpperCase());
                    if (txtName1.getText().trim().isEmpty() || txtName2.getText().trim().isEmpty())
                        throw new IllegalArgumentException("Please enter a name to the blanks.");
                    if (txtName1.getText().trim().equalsIgnoreCase(txtName2.getText().trim()))
                        throw new IllegalArgumentException("Please enter a different name than other player.");
                    lblExpression.setText("Players have added successfully.");
                    jFrame.setVisible(false);
                    Gui gui = new Gui();

                } catch (IllegalArgumentException exception) {
                    lblExpression.setText(exception.getMessage());
                }
            }
        });
    }

    public void fieldTextName(JLabel lbl) {
        txtName1 = new javax.swing.JTextField();
        txtName2 = new javax.swing.JTextField();
        lbl.add(txtName1);
        txtName1.setBounds(200, 200, 100, 25);
        txtName1.setOpaque(false);
        txtName2.setOpaque(false);


        lbl.add(txtName2);
        txtName2.setBounds(200, 275, 100, 25);


    }

    public void lblPlayerName() {
        lblName1 = new JLabel("Player 1:");
        lblName2 = new JLabel("Player 2:");

        lblBackground.add(lblName1);
        lblBackground.add(lblName2);
        lblName1.setBounds(200, 180, 50, 25);
        lblName2.setBounds(200, 255, 50, 25);

    }

    public void addUserIcon() {
        addUserIconn = new ImageIcon(this.getClass().getResource("/images/add-user.png"));
        addUserIcon = new JLabel(addUserIconn);
        lblBackground.add(addUserIcon);
    }

    public static void main(String[] args) {
        LogInScreen logInScreen = new LogInScreen();
    }
}