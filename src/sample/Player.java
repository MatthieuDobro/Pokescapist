package sample;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class Player {
    private int nblives;
    private static double vMarche = 2;
    private int flag=0;
    boolean isVisible;

    boolean alive = true;

    ImageView carton = new ImageView(new Image("file:../../images/carton.png"));

    ImageView avatar;
    Inventory inventory;
    LifeBar lifeBar;
    MediaPlayer m = new MediaPlayer(new Media(this.getClass().getResource("level2.mp3").toString()));

    int basID = 0;
    int hautID = 0;
    int gaucheID = 0;
    int droiteID = 0;

    Timer tb;
    Timer th;
    Timer td;
    Timer tg;

    boolean runningb = false;
    boolean runningg = false;
    boolean runningh = false;
    boolean runningd = false;

    boolean droite = false;
    boolean gauche  = false;
    boolean haut = false;
    boolean bas = false;

    public Player(int nblives, LifeBar lifeBar, Inventory inv, Image avatar) {
        Affichage.setSize(carton, 50, 50);
        carton.setVisible(false);
        isVisible = true;
        inventory = inv;
        this.nblives = nblives;
        this.lifeBar = lifeBar;
        this.avatar = new ImageView(avatar);
        Affichage.setSize(this.avatar, 50, 50);
        Affichage.setPos(this.avatar, 500, 350);

        m.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                m.seek(Duration.ZERO);
            }
        });
    }

    // Affichage de la barre de vie
    public void showLives(){
        if (getNblives() == 4){
            lifeBar.extra.setVisible(true);
            lifeBar.coeur3.setVisible(true);
            lifeBar.coeur2.setVisible(true);
            lifeBar.coeur1.setVisible(true);
        }
        else if (getNblives() == 3){
            lifeBar.extra.setVisible(false);
            lifeBar.coeur3.setVisible(true);
            lifeBar.coeur2.setVisible(true);
            lifeBar.coeur1.setVisible(true);
        }
        else if (getNblives() == 2){
            lifeBar.extra.setVisible(false);
            lifeBar.coeur3.setVisible(false);
            lifeBar.coeur2.setVisible(true);
            lifeBar.coeur1.setVisible(true);
        }
        else if (getNblives() == 1){
            lifeBar.extra.setVisible(false);
            lifeBar.coeur3.setVisible(false);
            lifeBar.coeur1.setVisible(true);
            lifeBar.coeur2.setVisible(false);
        }
        else{
            lifeBar.extra.setVisible(false);
            lifeBar.coeur3.setVisible(false);
            lifeBar.coeur2.setVisible(false);
            lifeBar.coeur1.setVisible(false);

        }
    }

    // Fonction permettant de faire bouger le personnage
    public void move(KeyCode key){
        switch(key){
            case LEFT :
                gauche = true;
                if (!runningh && !runningb){
                    if (gaucheID == 0){
                        gaucheID = 1;
                        if (!runningg){
                            tg = new Timer();
                            tg.scheduleAtFixedRate(new TimerTask(){
                                @Override
                                public void run(){
                                    runningg = true;
                                    switchAvatar("gauche");
                                }
                            },0,300);
                        }
                    }
                }
                break;
            case RIGHT :
                droite = true;
                if (!runningh && !runningb){
                    if (droiteID == 0){
                        droiteID = 1;
                        if (!runningd){
                            td = new Timer();
                            td.scheduleAtFixedRate(new TimerTask(){
                                @Override
                                public void run(){
                                    runningd = true;
                                    switchAvatar("droite");
                                }
                            },0,300);
                        }
                    }
                }
                break;
            case UP:
                haut = true;
                if (hautID == 0){
                    hautID = 1;
                    if (!runningh){
                        th = new Timer();
                        th.scheduleAtFixedRate(new TimerTask(){
                            @Override
                            public void run(){
                                runningh = true;
                                switchAvatar("haut");
                            }
                        },0,300);
                    }
                }
                break;
            case DOWN:
                bas = true;
                if (basID == 0){
                    basID = 1;
                }
                if (!runningb){
                    tb = new Timer();
                    tb.scheduleAtFixedRate(new TimerTask(){
                        @Override
                        public void run(){
                            runningb = true;
                            switchAvatar("bas");
                        }
                    },0,300);
                }

                break;
            case DIGIT1:
                inventory.removeItem(0);
                break;
            case DIGIT2:
                inventory.removeItem(1);
                break;
            case DIGIT3:
                inventory.removeItem(2);
                break;
            case ESCAPE:
                System.exit(0);
                break;

            default:
                break;

        }

    }

    // Fonction permettant de s'arreter quand on relache une touche
    public void stopMove(KeyCode key){
        switch(key){
            case LEFT :
                gauche = false;
                if (runningg){
                    tg.cancel();
                    stopAvatar("gauche");
                }
                runningg = false;
                break;
            case RIGHT :
                droite = false;
                if (runningd){
                    td.cancel();
                    stopAvatar("droite");
                }

                runningd = false;

                break;
            case UP:
                haut = false;
                runningh = false;
                th.cancel();
                stopAvatar("haut");
                break;
            case DOWN:
                bas = false;
                runningb = false;
                tb.cancel();
                stopAvatar("bas");
                break;

            default:
                break;

        }
    }

    // Vérifications des postions pour les collisions
    public void checkPosition(){

        if (avatar.getLayoutY() + avatar.getFitHeight() >= 750) {
            if (Main.game.currentLevel.goDown())
                avatar.setLayoutY(50);
        }

        if (avatar.getLayoutY() <= 0){
            if(Main.game.currentLevel.goUp())
                avatar.setLayoutY(700);

        }
        if (avatar.getLayoutX() <= 0){
            if (Main.game.currentLevel.goLeft())
                avatar.setLayoutX(1150);

        }
        if (avatar.getLayoutX() + avatar.getFitWidth() > 1250){
            if (Main.game.currentLevel.goRight())
                avatar.setLayoutX(50);
        }

        if (droite ) {
            int[][] t =Main.game.currentLevel.currentmap.getMap();
            int x = (int)avatar.getLayoutX()/50;
            int y = (int)avatar.getLayoutY()/50;
            if (t[y][x+1] == 1 && t[y+1][x+1] == 1 || (t[y][x+1] >= 5 && t[y+1][x+1] >= 5 && t[y][x+1] <= 16 && t[y+1][x+1] <= 16))
                avatar.setLayoutX(avatar.getLayoutX()+vMarche);
        }

        if (gauche ) {
            int[][] t =Main.game.currentLevel.currentmap.getMap();
            int x = (int)avatar.getLayoutX()/50;
            int y = (int)avatar.getLayoutY()/50;
            if (t[y][x] == 1 && t[y+1][x] == 1 || (t[y][x] >= 5 && t[y+1][x] >= 5 && t[y][x] <= 16 && t[y+1][x] <= 16))
                avatar.setLayoutX(avatar.getLayoutX()-vMarche);

        }
        if (bas){
            int[][] t =Main.game.currentLevel.currentmap.getMap();
            int x = (int)avatar.getLayoutX()/50;
            int y = (int)avatar.getLayoutY()/50;
            if (t[y+1][x] == 1 && t[y+1][x+1] == 1 || (t[y+1][x] >= 5 && t[y+1][x+1] >= 5 && t[y+1][x+1] <= 16 && t[y+1][x] <= 16))
                avatar.setLayoutY(avatar.getLayoutY()+vMarche);

        }
        if (haut){
            int[][] t =Main.game.currentLevel.currentmap.getMap();
            int x = (int)avatar.getLayoutX()/50;
            int y = (int)avatar.getLayoutY()/50;
            if ((t[y][x] == 1 && t[y][x+1] == 1 || (t[y][x+1] >= 5 && t[y][x] >= 5 && t[y][x+1] <= 16 && t[y][x] <= 16)))
                avatar.setLayoutY(avatar.getLayoutY()-vMarche);
        }


    }

    // Arrêt de l'animation de l'avatar
    public void stopAvatar(String direction){
        switch(direction){
            case "bas":
                basID = 0;
                avatar.setImage(new Image("file:../../images/player/pikachub2.png"));
                tb.cancel();
                break;
            case "haut":
                hautID = 0;
                avatar.setImage(new Image("file:../../images/player/pikachuh2.png"));
                th.cancel();
                break;
            case "gauche":
                gaucheID = 0;
                avatar.setImage(new Image("file:../../images/player/pikachug2.png"));
                tg.cancel();
                break;
            case "droite":
                droiteID = 0;
                avatar.setImage(new Image("file:../../images/player/pikachud2.png"));
                td.cancel();
                break;
        }
    }

    // Animation de l'avatar
    public void switchAvatar(String direction){

        switch(direction){
            case "bas":
                if (basID == 1){
                    avatar.setImage(new Image("file:../../images/player/pikachub3.png"));
                    basID = 2;
                }
                else if (basID == 2){
                    avatar.setImage(new Image("file:../../images/player/pikachub1.png"));
                    basID = 1;
                }
                break;
            case "haut":
                if (hautID == 1){
                    avatar.setImage(new Image("file:../../images/player/pikachuh1.png"));
                    hautID = 2;
                }
                else {
                    avatar.setImage(new Image("file:../../images/player/pikachuh3.png"));
                    hautID = 1;
                }
                break;
            case "gauche":
                if (gaucheID == 1){
                    avatar.setImage(new Image("file:../../images/player/pikachug1.png"));
                    gaucheID = 2;
                }
                else {
                    avatar.setImage(new Image("file:../../images/player/pikachug3.png"));
                    gaucheID = 1;
                }
                break;
            case "droite":
                if (droiteID == 1){
                    avatar.setImage(new Image("file:../../images/player/pikachud1.png"));
                    droiteID = 2;
                }
                else {
                    avatar.setImage(new Image("file:../../images/player/pikachud3.png"));
                    droiteID = 1;
                }
                break;
        }

    }


    public int getNblives() {
        return nblives;
    }

    public void removeLife(int nb){
        nblives -= nb;
    }
    public void addLife(int nb){
        nblives += nb;
    }

    public boolean haveKey(){
        for (String t:inventory.inventory){
            if (t.equals("key"))
                return true;
        }
        return false;
    }

    public void endLevel(Group group){

        if (Main.game.currentLevel.getLevelId() == 0 && Level.currentmap.getFloor() == 0 && Level.currentmap.getId() == 3 && haveKey() && avatar.getLayoutX()+50 >=1050 && avatar.getLayoutY() <= 175){
            Main.game.changeLevel();
            Main.mp.stop();
            m.play();
            avatar.setLayoutY(350);
            avatar.setLayoutX(250);
            vMarche=1;
        }
        if(Main.game.currentLevel.getLevelId() == 1 && Level.currentmap.getFloor() == 1 && Level.currentmap.getId()==0 ){

            int[][] t =Main.game.currentLevel.currentmap.getMap();
            int x = (int)avatar.getLayoutX()/50;
            int y = (int)avatar.getLayoutY()/50;
            if (t[y][x+1] >= 5 && t[y][x] >= 5 && t[y][x+1] <= 16 && t[y][x] <= 16){
                if(flag==0) {
                    ImageView killed = new ImageView(new Image("file:../../images/win.png"));
                    Affichage.setSize(killed, 750, 1250);
                    killed.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        System.exit(0);
                    });
                    group.getChildren().add(killed);
                    flag++;
                }
            }
        }
    }
    public void isOnKey(){
        if (Level.currentmap.getId() == 3 && Level.currentmap.getFloor() == -1 && avatar.getLayoutX()+50 >=1050 && avatar.getLayoutY() <= 175){
            inventory.addItem("key");
            Main.itemList.get(1).available = false;
        }
    }

    public void isOnGift(){
        if (Level.currentmap.getId() == 2 && Level.currentmap.getFloor() == 1 && avatar.getLayoutX()+50 >=1050 && avatar.getLayoutY() <= 175){
            inventory.addItem("invisible");
            Main.itemList.get(0).available = false;
        }
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
