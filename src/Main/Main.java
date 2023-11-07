package Main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game spaceInvaders = new Game();
        spaceInvaders.setBounds(0, 0, 960, 540);
        frame.setSize(960, 540);
        frame.add(spaceInvaders);
        frame.addKeyListener(spaceInvaders);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
