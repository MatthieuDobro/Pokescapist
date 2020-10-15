package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class Guard {
    int id;
    int floor;
    int idMap;
    ImageView skin;

    MediaPlayer mediaPlayer = new MediaPlayer(new Media(this.getClass().getResource("oof.mp3").toString()));
    int startX;
    int startY;
    Group group;
    boolean bas = false;
    boolean gauche = false;
    boolean droite = false;
    boolean haut = false;
    int vMarche = 3;
    Timer g = new Timer();
    Timer d = new Timer();
    Timer h = new Timer();
    Timer b = new Timer();
    int i = 0;

    public Guard(int id, int floor, int idMap, int x, int y, String start, Image skin, Group g) {
        group = g;
        startX = x;
        startY = y;
        this.skin = new ImageView(skin);
        switch (start){
            case "bas":
                bas = true;
                break;
            case "haut":
                haut = true;
                break;
            case "gauche":
                gauche = true;
                break;
            case "droite":
                droite = true;
                break;
        }
        this.id = id;
        this.floor = floor;
        this.idMap = idMap;

        Affichage.setSize(this.skin, 50, 50);
        Affichage.setPos(this.skin, this.startX, this.startY);
        group.getChildren().add(this.skin);
    }

    // Permet d'animer le garde
    public void switchAvatar(String direction){
        switch(direction) {
            case "bas":
                if (i % 2 == 1) {
                    skin.setImage(new Image("file:../../images/guards/b1.png"));
                    i++;
                } else if (i % 2 == 0) {
                    skin.setImage(new Image("file:../../images/guards/b2.png"));
                    i++;
                }
                break;
            case "gauche":
                if (i % 2 == 1) {
                    skin.setImage(new Image("file:../../images/guards/g1.png"));
                    i++;
                } else if (i % 2 == 0) {
                    skin.setImage(new Image("file:../../images/guards/g2.png"));
                    i++;
                }
                break;
            case "droite":
                if (i % 2 == 1) {
                    skin.setImage(new Image("file:../../images/guards/d1.png"));
                    i++;
                } else if (i % 2 == 0) {
                    skin.setImage(new Image("file:../../images/guards/d2.png"));
                    i++;
                }
                break;
            case "haut":
                if (i % 2 == 1) {
                    skin.setImage(new Image("file:../../images/guards/h1.png"));
                    i++;
                } else if (i % 2 == 0) {
                    skin.setImage(new Image("file:../../images/guards/h2.png"));
                    i++;
                }
                break;
        }
    }

    // Permet de garder le garde en mouvement
    public void keepMoving(){
        if (droite) {
            int[][] t = Level.currentmap.getMap();
            int x = (int)skin.getLayoutX()/50;
            int y = (int)skin.getLayoutY()/50;
            if (t[y][x+1] == 1 && t[y+1][x+1] == 1 || (t[y][x+1] >= 5 && t[y+1][x+1] >= 5 && t[y][x+1] <= 16 && t[y+1][x+1] <= 16))
                skin.setLayoutX(skin.getLayoutX()+vMarche);
            else{
                droite = false;
                d.cancel();
                gauche = true;
                g = new Timer();
                g.scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run(){
                        switchAvatar("gauche");
                    }
                },0,200);
            }
        }

        if (gauche ) {
            int[][] t = Level.currentmap.getMap();
            int x = (int)skin.getLayoutX()/50;
            int y = (int)skin.getLayoutY()/50;
            if (t[y][x] == 1 && t[y+1][x] == 1 || (t[y][x] >= 5 && t[y+1][x] >= 5 && t[y][x] <= 16 && t[y+1][x] <= 16))
                skin.setLayoutX(skin.getLayoutX()-vMarche);
            else{
                gauche = false;
                g.cancel();
                droite = true;
                d = new Timer();
                d.scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run(){
                        switchAvatar("droite");
                    }
                },0,200);
            }

        }
        if (bas){
            int[][] t = Level.currentmap.getMap();
            int x = (int)skin.getLayoutX()/50;
            int y = (int)skin.getLayoutY()/50;
            if (t[y+1][x] == 1 && t[y+1][x+1] == 1 || (t[y+1][x] >= 5 && t[y+1][x+1] >= 5 && t[y+1][x+1] <= 16 && t[y+1][x] <= 16))
                skin.setLayoutY(skin.getLayoutY()+vMarche);
            else{
                bas = false;
                b.cancel();
                haut = true;
                h = new Timer();
                h.scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run(){
                        switchAvatar("haut");
                    }
                },0,200);
            }

        }
        if (haut){
            int[][] t = Level.currentmap.getMap();
            int x = (int)skin.getLayoutX()/50;
            int y = (int)skin.getLayoutY()/50;
            if (t[y][x] == 1 && t[y][x+1] == 1 || (t[y][x+1] >= 5 && t[y][x] >= 5 && t[y][x+1] <= 16 && t[y][x] <= 16))
                skin.setLayoutY(skin.getLayoutY()-vMarche);
            else{
                haut = false;
                h.cancel();
                bas = true;
                b = new Timer();
                b.scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run(){
                        switchAvatar("bas");
                    }
                },0,200);
            }
        }
    }

    // Permet de savoir si le joueur est attrapé
    public boolean catchPlayer(Player p){
        boolean caught = false;
        if (skin.isVisible()){
            if (droite){
                caught = (p.avatar.getLayoutX() < skin.getLayoutX() + 200 && p.avatar.getLayoutX() > skin.getLayoutX() && p.avatar.getLayoutY() +50 > skin.getLayoutY() && p.avatar.getLayoutY() < skin.getLayoutY()+50);
            }
            if (gauche){
                caught = (p.avatar.getLayoutX()+50 > skin.getLayoutX() - 150 && p.avatar.getLayoutX() < skin.getLayoutX() && p.avatar.getLayoutY() +50 > skin.getLayoutY() && p.avatar.getLayoutY() < skin.getLayoutY()+50);

            }
            if (haut){
                caught = (p.avatar.getLayoutY() < skin.getLayoutY() && p.avatar.getLayoutY()+50 > skin.getLayoutY()-100 && p.avatar.getLayoutX()+50 > skin.getLayoutX() && p.avatar.getLayoutX() < skin.getLayoutX()+50);
            }
            if (bas){
                caught = (p.avatar.getLayoutY() > skin.getLayoutY() && p.avatar.getLayoutY() < skin.getLayoutY()+150 && p.avatar.getLayoutX()+50 > skin.getLayoutX() && p.avatar.getLayoutX() < skin.getLayoutX()+50);
            }
            if (caught){
                p.removeLife(1);
                if (p.getNblives() == 0){
                    ImageView killed = new ImageView(new Image("file:../../images/killed.png"));
                    Affichage.setSize(killed, 750, 1250);
                    group.getChildren().add(killed);
                    p.alive = false;
                    mediaPlayer.play();

                }
                else{
                    Main.game.currentLevel.changeMap(0, 0);
                    Affichage.setPos(p.avatar, 500, 350);
                }

            }

        }
        return caught;
    }
}
