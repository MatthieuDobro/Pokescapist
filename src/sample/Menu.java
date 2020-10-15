package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class Menu {
    static ImageView jouer;
    static ImageView options;
    static ImageView quitter;
    static ImageView mute;
    static ImageView retour;
    static MediaPlayer mediaPlayer;

    public static void initMainScreen(MediaPlayer m){
        mediaPlayer = m;
        jouer = new ImageView(new Image("file:../../images/jouer.png"));
        Affichage.setSize(jouer, 44, 256);
        Affichage.setPos(jouer, 512, 540);

        options = new ImageView(new Image("file:../../images/options.png"));
        Affichage.setSize(options, 44, 256);

        Affichage.setPos(options, 512,576);

        quitter = new ImageView(new Image("file:../../images/quiiter.png"));
        Affichage.setSize(quitter, 44, 256);
        Affichage.setPos(quitter, 512, 612);

        retour = new ImageView(new Image("file:../../images/return.png"));
        Affichage.setSize(retour, 44, 256);
        Affichage.setPos(retour, 512, 612);
        retour.setVisible(false);

        retour.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            mute.setVisible(false);
            retour.setVisible(false);
            options.setVisible(true);
            jouer.setVisible(true);
            quitter.setVisible(true);
        });


        mute = new ImageView(new Image("file:../../images/unmute.png"));
        Affichage.setSize(mute, 75, 75);
        Affichage.setPos(mute, 600, 520);
        mute.setVisible(false);

        mute.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            if (mediaPlayer.isMute()){
                mediaPlayer.setMute(false);
                mute.setImage(new Image("file:../../images/unmute.png"));
            }
            else {
                mediaPlayer.setMute(true);
                mute.setImage(new Image("file:../../images/mute.png"));
            }
        });

        options.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            options.setVisible(false);
            jouer.setVisible(false);
            quitter.setVisible(false);
            retour.setVisible(true);
            mute.setVisible(true);
        });
    }
}
