package hospital;

import hospital.gui.LoginFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class GUIApplication {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            LoginFrame login
                    = new LoginFrame();

            login.setVisible(true);
        });
    }
}
