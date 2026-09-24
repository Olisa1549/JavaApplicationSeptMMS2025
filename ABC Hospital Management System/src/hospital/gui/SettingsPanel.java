package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.gui.theme.ThemePresets;

import javax.swing.*;
import java.awt.*;

/** Real application settings screen. */
public class SettingsPanel extends JPanel implements ThemeManager.ThemeChangeListener {
    private JLabel title, subtitle, activeTheme;
    private JPanel preview;
    private JComboBox<String> themeCombo;
    private boolean updating;

    public SettingsPanel() {
        setLayout(new BorderLayout(18,18));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        setOpaque(false);
        build();
        ThemeManager.addThemeChangeListener(this);
        applyTheme(ThemeManager.getCurrentTheme());
    }

    private void build() {
        JPanel head = new JPanel(); head.setOpaque(false); head.setLayout(new BoxLayout(head, BoxLayout.Y_AXIS));
        title = new JLabel("Settings"); title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        subtitle = new JLabel("Personalize the LifeSaver workspace and application appearance."); subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        head.add(title); head.add(Box.createVerticalStrut(5)); head.add(subtitle); add(head, BorderLayout.NORTH);

        JPanel body = new JPanel(new GridBagLayout()); body.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints(); g.gridx=0; g.gridy=0; g.weightx=1; g.fill=GridBagConstraints.HORIZONTAL; g.anchor=GridBagConstraints.NORTHWEST; g.insets=new Insets(0,0,14,0);

        JPanel themeCard = card();
        JPanel row = new JPanel(new BorderLayout(15,10)); row.setOpaque(false);
        JPanel labels = new JPanel(); labels.setOpaque(false); labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        JLabel h = new JLabel("Appearance & Theme"); h.setFont(new Font("Segoe UI", Font.BOLD, 17));
        activeTheme = new JLabel(); activeTheme.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labels.add(h); labels.add(Box.createVerticalStrut(5)); labels.add(activeTheme);
        themeCombo = new JComboBox<>(new String[]{"Ocean Blue","Emerald Care","Teal Horizon","Royal Violet","Midnight Slate"});
        themeCombo.setPreferredSize(new Dimension(220,38));
        themeCombo.addActionListener(e -> { if (!updating) ThemeManager.setThemeByName((String)themeCombo.getSelectedItem()); });
        row.add(labels, BorderLayout.CENTER); row.add(themeCombo, BorderLayout.EAST); themeCard.add(row, BorderLayout.NORTH);
        preview = new JPanel(new GridLayout(1,3,10,0)); preview.setBorder(BorderFactory.createEmptyBorder(18,0,0,0));
        themeCard.add(preview, BorderLayout.CENTER);
        g.weighty=0; body.add(themeCard,g);

        g.gridy=1;
        JPanel info = card();
        JLabel infoTitle = new JLabel("System Preferences"); infoTitle.setFont(new Font("Segoe UI", Font.BOLD,17));
        JTextArea text = new JTextArea("Theme changes are applied immediately across open LifeSaver screens.\n\nDatabase connection and security settings remain controlled by the application's configuration files.");
        text.setEditable(false); text.setOpaque(false); text.setLineWrap(true); text.setWrapStyleWord(true); text.setFont(new Font("Segoe UI",Font.PLAIN,13));
        info.add(infoTitle,BorderLayout.NORTH); info.add(text,BorderLayout.CENTER); body.add(info,g);
        add(body,BorderLayout.CENTER);
    }

    private JPanel card(){ JPanel p=new JPanel(new BorderLayout()); p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(210,215,220)),BorderFactory.createEmptyBorder(18,18,18,18))); return p; }

    @Override public void themeChanged(Theme newTheme){ applyTheme(newTheme); }
    private void applyTheme(Theme t){
        if(t==null) return;
        setBackground(t.getBackgroundColor()); title.setForeground(t.getTextColor()); subtitle.setForeground(t.getSecondaryTextColor()); activeTheme.setForeground(t.getSecondaryTextColor());
        if(themeCombo!=null){ updating=true; themeCombo.setSelectedItem(t.getName()); updating=false; }
        if(preview!=null){ preview.removeAll(); preview.add(swatch("Primary",t.getPrimaryColor())); preview.add(swatch("Accent",t.getAccentColor())); preview.add(swatch("Surface",t.getSurfaceColor())); preview.revalidate(); preview.repaint(); }
        repaint();
    }
    private JPanel swatch(String name, Color c){ JPanel p=new JPanel(new BorderLayout(5,5)); JLabel box=new JLabel(); box.setOpaque(true); box.setBackground(c); box.setPreferredSize(new Dimension(40,40)); JLabel l=new JLabel(name); l.setFont(new Font("Segoe UI",Font.PLAIN,11)); p.add(box,BorderLayout.WEST); p.add(l,BorderLayout.CENTER); return p; }
    public void dispose(){ ThemeManager.removeThemeChangeListener(this); }
}
