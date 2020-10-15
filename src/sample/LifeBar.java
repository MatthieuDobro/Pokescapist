package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LifeBar {
    Group group;
    ImageView coeur1= new ImageView(new Image("file:../../images/heart.png"));
    ImageView coeur2= new ImageView(new Image("file:../../images/heart.png"));
    ImageView coeur3= new ImageView(new Image("file:../../images/heart.png"));
    ImageView extra= new ImageView(new Image("file:../../images/heart.png"));


    public LifeBar(Group g) {
        group = g;
        extra.setVisible(false);
        Affichage.setSize(coeur1, 32, 32);
        Affichage.setSize(coeur2, 32, 32);
        Affichage.setSize(coeur3, 32, 32);
        Affichage.setSize(extra, 32, 32);
        Affichage.setPos(coeur1, 1190, 15);
        Affichage.setPos(coeur2, 1150, 15);
        Affichage.setPos(coeur3, 1110, 15);
        Affichage.setPos(extra, 1070, 15);
        group.getChildren().addAll(coeur1, coeur2, coeur3, extra);
    }
}
