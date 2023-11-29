package app.ui;

import app.ui.RoundedBorder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static app.Main.*;

public class Scene extends JFrame {
    public static JPanel panel;
    public static JLabel tabacoIcon;
    public static JLabel paperIcon;
    public static JLabel matchesIcon;
    public static JButton tabacoButton;
    public static JButton paperButton;
    public static JButton matchesButton;

    public Scene(String title) throws HeadlessException {
        super(title);
        init();
    }

    public void init(){
        panel = new JPanel();
        tabacoButton = new JButton("Табачник");
        paperButton = new JButton("Бумажик");
        matchesButton = new JButton("Спичник");

        try {
            tabacoIcon = new JLabel(new ImageIcon(ImageIO.read(new File("D:\\IdeaProjects\\Parallel\\Parallel\\src\\app\\res\\tabaco\\icons8-tobacco-pouch-50.png"))));
            paperIcon = new JLabel(new ImageIcon(ImageIO.read(new File("D:\\IdeaProjects\\Parallel\\Parallel\\src\\app\\res\\paper\\icons8-paper-50.png"))));
            matchesIcon = new JLabel(new ImageIcon(ImageIO.read(new File("D:\\IdeaProjects\\Parallel\\Parallel\\src\\app\\res\\matches\\icons8-matches-50.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setScene(){
        setFrame();
        setPanel();
        setIcons();
        setButtons();
    }

    public static void updateIcons(){
        tabacoIcon.setVisible(tobacco);
        matchesIcon.setVisible(matches);
        paperIcon.setVisible(paper);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private void setFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setBackground(Color.white);
        this.add(panel);
    }

    private void setPanel() {
        panel.setLayout(null);

        panel.add(tabacoIcon);
        panel.add(paperIcon);
        panel.add(matchesIcon);

        panel.add(tabacoButton);
        panel.add(paperButton);
        panel.add(matchesButton);
    }

    private void setIcons() {
        tabacoIcon.setBounds(165, 100, 50, 50);
        paperIcon.setBounds(200, 160, 50, 50);
        matchesIcon.setBounds(145, 160, 50, 50);
    }

    private void setButtons() {
        int size = 100;
        tabacoButton.setBounds(145, 250, size, size);
        paperButton.setBounds(20, 20, size, size);
        matchesButton.setBounds(270, 20, size, size);

        tabacoButton.setBackground(Color.white);
        paperButton.setBackground(Color.white);
        matchesButton.setBackground(Color.white);

        tabacoButton.setBorder(new RoundedBorder(20));
        paperButton.setBorder(new RoundedBorder(20));
        matchesButton.setBorder(new RoundedBorder(20));
    }
}
