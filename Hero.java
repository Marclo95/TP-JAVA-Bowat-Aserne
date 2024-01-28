package TP2D;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.time.DateTimeException;
import java.util.Date;

public final class Hero extends DynamicThings{

    private static volatile Hero instance = null;
    private Orientation orientation=Orientation.RIGHT;
    private boolean isWalking = false;
    private int health; // Declare the 'health' variable here

    private Hero() {
        super(120,120, 48,52);
        try{this.setImage(ImageIO.read(new File("img/heroTileSheet.png")));}
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void giveLife(int lives) {
        health += lives;
        if (health > 100) {
            health = 100;
        }
    }

    public final static Hero getInstance() {
        if (Hero.instance == null) {
            synchronized(Hero.class) {
                if (Hero.instance == null) {
                    Hero.instance = new Hero();
                }
            }
        }
        return Hero.instance;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void moveIfPossible(double dx, double dy, Dungeon dungeon) {
        boolean movePossible = true;
        this.getHitBox().move(dx, dy);
        for (Things things : dungeon.getRenderList()) {
            if (things instanceof SolidThings) {
                if (((SolidThings) things).getHitBox().intersect(this.getHitBox())) {
                    movePossible = false;
                    // Affiche la fenêtre Game Over
                    new GameOverWindow().show();
                    break;
                }
            }
        }

        if (movePossible){
            this.x=x+dx;
            this.y=y+dy;
        }
        else{
            this.getHitBox().move(-dx,-dy);
        }
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    @Override
    public void draw(Graphics g){
        int attitude = orientation.getI();
        int index = (int) ((System.currentTimeMillis()/125)%10);
        index=isWalking?index:0;
        g.drawImage(image,(int)x,(int)y,(int)x+48,(int) y+ 52,index*96,100*attitude,(index+1)*96,100*(attitude+1),null,null);
        if (health > 0) {
            int width = health * 10 / 100;
            g.setColor(Color.RED);
            g.fillRect(0, 0, width, 20);
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, 100, 20);
        }
    }
}

