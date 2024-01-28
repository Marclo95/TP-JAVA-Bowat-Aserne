package TP2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.io.Serializable;

public class GameOverWindow extends JFrame implements KeyListener, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public GameOverWindow() {
        super("Game Over");
        setSize(200, 100);
        setLocationRelativeTo(null);
        addKeyListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("Game Over", 50, 50);
    }

    public static void main(String[] args) {
        new GameOverWindow();
    }
}

