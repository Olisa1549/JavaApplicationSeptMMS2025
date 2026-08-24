
package chapter12;

import javax.swing.SwingUtilities;

public class DrawTest {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                DrawFrame frame =
                    new DrawFrame();

                frame.setVisible(true);
            }
        });
    }
}

