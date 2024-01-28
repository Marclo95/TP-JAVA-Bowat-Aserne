package TP2D;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainInterface extends JFrame implements KeyListener {
// Crée un gestionnaire de tuiles pour charger et gérer les images des tuiles
    TileManager tileManager = new TileManager(48, 48, "./img/tileSet.png");
    // Crée un donjon en chargeant les données du niveau depuis un fichier texte
    Dungeon dungeon = new Dungeon("./gameData/level1.txt", tileManager);
    // Crée le héros, le personnage contrôlé par le joueur
    Hero hero = Hero.getInstance();
    // Crée un panneau de rendu pour afficher le donjon et le héros
    GameRender panel = new GameRender(dungeon, hero);


// Initialise la fenêtre principale de l'application
    public MainInterface() throws HeadlessException {
        super();

        // Définit la fermeture de la fenêtre pour quitter le programme
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Ajoute le panneau de rendu à la fenêtre
        getContentPane().add(panel);

        // Rend la fenêtre visible
        setVisible(true);

        // Ajuste la taille de la fenêtre en fonction des dimensions du donjon
        setSize(new Dimension(dungeon.getWidth() * tileManager.getWidth(), dungeon.getHeight() * tileManager.getHeigth()));

        // Ajoute un gestionnaire de touches pour gérer les événements clavier
        addKeyListener(this);

        // Crée et démarre un minuteur d'animation pour mettre à jour l'état du jeu à un rythme régulier
        ActionListener animationTimer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                // Définit une vitesse de déplacement fixe pour le héros
                final int speed = 6;

                // Vérifie si le héros est en train de marcher
                if (hero.isWalking()) {
                    // Détermine l'orientation du héros en fonction de l'entrée au clavier
                    switch (hero.getOrientation()) {
                        case LEFT: hero.moveIfPossible(-speed, 0, dungeon); // Move left
                            break;
                        case RIGHT: hero.moveIfPossible(speed, 0, dungeon); // Move right
                            break;
                        case UP: hero.moveIfPossible(0, -speed, dungeon); // Move up
                            break;
                        case DOWN: hero.moveIfPossible(0, speed, dungeon); // Move down
                            break;
                    }
                }
            }
        };
        Timer timer = new Timer(50, animationTimer);
        timer.start();

        // Affiche une fenêtre de démarrage avec un bouton "Démarrer"
        JFrame startWindow = new JFrame("Démarrer");
        startWindow.setSize(200, 100);
        startWindow.setLocationRelativeTo(null);
        startWindow.setAlwaysOnTop(true);

        JButton startButton = new JButton("Démarrer");
        startWindow.getContentPane().add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
                startWindow.dispose(); // Ferme la fenêtre de démarrage
            }
        });

        startWindow.setVisible(true); // Affiche la fenêtre de démarrage
    }
// Main() pour lancer l'application
    public static void main(String[] args) {
        MainInterface mainInterface = new MainInterface();
    }
// Ignore les touches qui ne produisent pas de caractères
    @Override
    public void keyTyped(KeyEvent e) {
    }
// Gère les touches enfoncées
    @Override
    public void keyPressed(KeyEvent e) {
        // Détermine la direction du mouvement du héros en fonction de la touche enfoncée
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                hero.setOrientation(Orientation.LEFT);
                hero.setWalking(true);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setOrientation(Orientation.RIGHT);
                hero.setWalking(true);
                break;
            case KeyEvent.VK_UP:
                hero.setOrientation(Orientation.UP);
                hero.setWalking(true);
                break;
            case KeyEvent.VK_DOWN:
                hero.setOrientation(Orientation.DOWN);
                hero.setWalking(true);
                break;
        }
        // Met à jour les graphiques
        this.repaint();
    }
    // Désactive le mouvement du héros
    @Override
    public void keyReleased(KeyEvent e) {
        hero.setWalking(false);
    }
}

