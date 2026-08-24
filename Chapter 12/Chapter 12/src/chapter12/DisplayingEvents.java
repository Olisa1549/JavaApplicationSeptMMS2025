
package chapter12;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class DisplayingEvents extends JFrame
        implements ActionListener, ItemListener, ListSelectionListener,
                   MouseListener, MouseMotionListener, KeyListener {

    private JTextArea eventArea;
    private JButton button;
    private JCheckBox checkBox;
    private JList<String> list;
    private JTextField textField;
    private JPanel mousePanel;

    public DisplayingEvents() {
        setTitle("Displaying Events");
        setLayout(new BorderLayout(10, 10));

        // Text area used to display events
        eventArea = new JTextArea(15, 50);
        eventArea.setEditable(false);
        add(new JScrollPane(eventArea), BorderLayout.CENTER);

        // Top controls
        JPanel topPanel = new JPanel();

        button = new JButton("Click Me");
        button.addActionListener(this);
        topPanel.add(button);

        checkBox = new JCheckBox("Check Me");
        checkBox.addItemListener(this);
        topPanel.add(checkBox);

        textField = new JTextField(12);
        textField.addKeyListener(this);
        topPanel.add(textField);

        add(topPanel, BorderLayout.NORTH);

        // List for ListSelectionEvents
        String[] items = {
            "Item 1", "Item 2", "Item 3", "Item 4"
        };

        list = new JList<>(items);
        list.setVisibleRowCount(4);
        list.addListSelectionListener(this);

        JPanel listPanel = new JPanel();
        listPanel.add(new JLabel("Select an item:"));
        listPanel.add(new JScrollPane(list));

        add(listPanel, BorderLayout.WEST);

        // Panel for mouse and mouse-motion events
        mousePanel = new JPanel();
        mousePanel.setBackground(Color.LIGHT_GRAY);
        mousePanel.setPreferredSize(new Dimension(250, 150));

        mousePanel.addMouseListener(this);
        mousePanel.addMouseMotionListener(this);

        mousePanel.add(new JLabel("Mouse Area"));
        add(mousePanel, BorderLayout.SOUTH);

        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // --------------------------------------------------
    // ActionListener
    // --------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent event) {
        displayEvent("ActionEvent:\n" + event.toString());
    }

    // --------------------------------------------------
    // ItemListener
    // --------------------------------------------------

    @Override
    public void itemStateChanged(ItemEvent event) {
        displayEvent("ItemEvent:\n" + event.toString());
    }

    // --------------------------------------------------
    // ListSelectionListener
    // --------------------------------------------------

    @Override
    public void valueChanged(ListSelectionEvent event) {
        displayEvent("ListSelectionEvent:\n" + event.toString());
    }

    // --------------------------------------------------
    // MouseListener
    // --------------------------------------------------

    @Override
    public void mouseClicked(MouseEvent event) {
        displayEvent("MouseClicked:\n" + event.toString());
    }

    @Override
    public void mousePressed(MouseEvent event) {
        displayEvent("MousePressed:\n" + event.toString());
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        displayEvent("MouseReleased:\n" + event.toString());
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        displayEvent("MouseEntered:\n" + event.toString());
    }

    @Override
    public void mouseExited(MouseEvent event) {
        displayEvent("MouseExited:\n" + event.toString());
    }

    // --------------------------------------------------
    // MouseMotionListener
    // --------------------------------------------------

    @Override
    public void mouseDragged(MouseEvent event) {
        displayEvent("MouseDragged:\n" + event.toString());
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        displayEvent("MouseMoved:\n" + event.toString());
    }

    // --------------------------------------------------
    // KeyListener
    // --------------------------------------------------

    @Override
    public void keyPressed(KeyEvent event) {
        displayEvent("KeyPressed:\n" + event.toString());
    }

    @Override
    public void keyReleased(KeyEvent event) {
        displayEvent("KeyReleased:\n" + event.toString());
    }

    @Override
    public void keyTyped(KeyEvent event) {
        displayEvent("KeyTyped:\n" + event.toString());
    }

    // --------------------------------------------------
    // Display the event
    // --------------------------------------------------

    private void displayEvent(String message) {
        eventArea.append(message + "\n\n");
        eventArea.setCaretPosition(eventArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        new DisplayingEvents();
    }
}

