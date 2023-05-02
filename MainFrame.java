import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener {
    JButton button;
    JLabel label;
    MainFrame() {
        label = new JLabel("Wynik dekompresji.");
        label.setForeground(Color.white);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        button = new JButton("Wybierz plik.");
        button.addActionListener(this);
        button.setFocusable(false);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBackground(Color.darkGray);
        mainPanel.add(button);
        mainPanel.add(label);

        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        //this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getName();
                if (fileName.endsWith(".huff")) {
                    try {
                        new Decoder(new File(fileChooser.getSelectedFile().getAbsolutePath()));
                        label.setText("Dekompresja zakończona sukcesem");
                    } catch (IOException err) {
                        label.setText("Dekompresja zakończona niepowodzeniem. Sprawdź, czy plik nie został uszkodzony");
                    }
                } else {
                    label.setText("Niepoprawne rozszerzenie pliku.");
                }
            }
        }
    }
}
