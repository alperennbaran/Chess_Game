

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LogInScreen extends JFrame {
    private JPanel panelMain;
    private JTextField txtName;
    private JLabel lblName;
    private JButton btnClick;
    private JLabel lblExpression;

    DbHelper dbHelper = new DbHelper();
    FinalPanel panel = new FinalPanel();

    public String gettxtName() {
        return txtName.getText().trim();
    }

    public LogInScreen() {
        btnClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtName.getText().trim().isEmpty()) {
                    Main.player0.setName(txtName.getText().trim().toUpperCase());
                    lblExpression.setText(txtName.getText() + " isimli kullanıcı başarıyla eklendi");
                    setVisible(false);
                    Main.startGame();
                } else
                    lblExpression.setText("Lütfen isim giriniz.");
            }
        });
    }

    public static void main() {
        LogInScreen d = new LogInScreen();
        d.setContentPane(d.panelMain);
        d.setTitle("Chess");
        d.setSize(300, 400);
        d.setVisible(true);
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

