package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    Player joueur;
    static ArrayList<Item> itemList = new ArrayList<Item>();
    static Game game;
    private MediaPlayer mediaPlayer = new MediaPlayer(new Media(this.getClass().getResource("generique.mp3").toString()));
    static MediaPlayer mp;

    ImageView background;

    @Override
    public void start(Stage primaryStage) {
        try {

            mp = new MediaPlayer(new Media(this.getClass().getResource("level1.mp3").toString()));
            mp.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mp.seek(Duration.ZERO);
                }
            });
            mp.setVolume(0.2);

            mediaPlayer.setVolume(0.2d);
            mediaPlayer.play();
            Random rand = new Random();
            int randomTips=rand.nextInt(4);
            ImageView tips = new ImageView(new Image("file:../../images/Tips/tips"+String.valueOf(randomTips)+".png"));

            tips.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
                tips.setVisible(false);
            });

            // mainScreenGroup est le group contenant le menu principal.
            Group mainScreenGroup = new Group();
            Scene mainScreenScene = new Scene(mainScreenGroup,1280,720);

            // gameGroup est le group qui, pour le moment, envoi sur l'écran pour jouer
            Group gameGroup = new Group();
            Scene gameScene = new Scene(gameGroup, 1250, 750);


            // Ici, on définit le background du mainscreen
            background = new ImageView(new Image("file:../../images/mainscreen.png"));

            // Ainsi que sa taille
            Affichage.setSize(background, 720, 1280);

            // Initialisation du menu principal
            Menu.initMainScreen(mediaPlayer);

            // Ajout des fonctionnalité onClick sur les boutons
            Menu.jouer.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
                mediaPlayer.stop();
                mp.play();
                primaryStage.setScene(gameScene);

            });


            Menu.quitter.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
                System.exit(0);

            });

            //Creation de la carte
            ArrayList<Level> levelArrayList=new ArrayList<Level>();
            levelArrayList.add(new Level(gameGroup,0,"levels/level1.txt","file:../../images/level1/"));
            levelArrayList.add(new Level(gameGroup,1,"levels/level2.txt","file:../../images/level2/"));
            game=new Game(levelArrayList);

            // Déclaration du joueur ici
            joueur = new Player(3, new LifeBar(gameGroup), new Inventory(gameGroup), new Image("file:../../images/player/pikachub2.png"));
            joueur.inventory.joueur = joueur;

            ArrayList<Guard> guardList= new ArrayList<Guard>();

            itemList.add(new Item(new Image("file:../../images/chest.png"), "gift", 1, 2, gameGroup, joueur));
            itemList.add(new Item(new Image("file:../../images/key.png"), "key", -1, 3, gameGroup, joueur));
            itemList.add(new Item(new Image("file:../../images/level1/echelle.png"), "echelle1", 0, 3, gameGroup, joueur));
            itemList.add(new Item(new Image("file:../../images/level1/echelle.png"), "echelle2", 0, 3, gameGroup, joueur));
            itemList.add(new Item(new Image("file:../../images/level1/echelle.png"), "echelle3", 0, 3, gameGroup, joueur));

            guardList.add(new Guard(0, 0, 1, 450, 150, "bas", new Image("file:../../images/guards/b1.png"), gameGroup));
            guardList.add(new Guard(2, 1, 1, 1020, 250,  "bas",new Image("file:../../images/guards/b1.png"), gameGroup));
            guardList.add(new Guard(3, 1, 2, 1020, 500,  "droite",new Image("file:../../images/guards/d1.png"), gameGroup));
            guardList.add(new Guard(4, 0, 2, 850, 200, "gauche", new Image("file:../../images/guards/g1.png"), gameGroup));
            guardList.add(new Guard(5, 0, 2, 1020, 500, "gauche", new Image("file:../../images/guards/g1.png"), gameGroup));
            guardList.add(new Guard(6, 0, 3, 450, 150, "bas", new Image("file:../../images/guards/b1.png"), gameGroup));
            guardList.add(new Guard(7, 0, 3, 650, 350, "bas", new Image("file:../../images/guards/b1.png"), gameGroup));
            guardList.add(new Guard(8, -1, 2, 950, 350, "bas", new Image("file:../../images/guards/b1.png"), gameGroup));
            guardList.add(new Guard(9, -1, 3, 850, 350, "bas", new Image("file:../../images/guards/b1.png"), gameGroup));
            guardList.add(new Guard(10, -1, 3, 720, 350, "bas", new Image("file:../../images/guards/b1.png"), gameGroup));

            Controller c = new Controller(gameGroup, joueur);
            AnimationTimer boucle = new AnimationTimer() {
                @Override
                public void handle(long arg0) {
                    Affichage.setPos(joueur.carton, joueur.avatar.getLayoutX(), joueur.avatar.getLayoutY());

                    for (Item item:itemList){
                        if (item.available)
                            item.image.setVisible(item.checkVisible());
                        else item.image.setVisible(false);
                    }
                    if (Game.currentLevel.getLevelId() == 0){
                        for (Guard guard:guardList){
                            guard.skin.setVisible(guard.idMap == Level.currentmap.getId() && guard.floor == Level.currentmap.getFloor());
                            guard.keepMoving();
                            if (joueur.isVisible)
                                guard.catchPlayer(joueur);
                        }
                    }
                    else{
                        for (Guard guard:guardList){
                            guard.skin.setVisible(false);
                        }
                    }

                    if (!joueur.alive){
                        joueur.avatar.setImage(new Image("file:../../images/broken_player.png"));
                    }
                    c.easter(joueur);
                    joueur.showLives();
                    joueur.checkPosition();
                    joueur.endLevel(gameGroup);
                    if(joueur.getFlag()==1){
                        gameGroup.getChildren().addAll(Menu.quitter);
                        joueur.setFlag(2);
                    }
                    if (joueur.inventory.started){
                        joueur.inventory.end = System.currentTimeMillis();
                        if ((joueur.inventory.end - joueur.inventory.start) / 1000 > 1){
                            joueur.inventory.started = false;
                            joueur.isVisible = true;
                            joueur.carton.setVisible(false);
                        }
                    }
                    if (itemList.get(1).available)
                        joueur.isOnKey();

                    if (itemList.get(0).available)
                        joueur.isOnGift();

                }
            };
            boucle.start();

            gameScene.setOnKeyPressed(e -> {
                if (joueur.alive)
                 joueur.move(e.getCode());
                if (e.getCode() == KeyCode.ESCAPE){
                    System.exit(0);
                }
            });

            gameScene.setOnKeyReleased(e -> {
                joueur.stopMove(e.getCode());
            });

            mainScreenGroup.getChildren().addAll(background, Menu.mute, Menu.jouer, Menu.options, Menu.quitter, Menu.retour);

            gameGroup.getChildren().addAll(joueur.avatar, tips, joueur.carton);

            primaryStage.setScene(mainScreenScene);
            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}