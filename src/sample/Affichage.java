package sample;

import javafx.scene.image.ImageView;


public class Affichage {

    // Comme son nom l'indique, il permet de configurer la taille d'une image
    public static void setSize(ImageView iv, double height, double width){
        iv.setFitHeight(height);
        iv.setFitWidth(width);
    }
    // Comme son nom l'indique, il permet de configurer la position d'une image

    public static void setPos(ImageView iv, double x, double y){
        iv.setLayoutX(x);
        iv.setLayoutY(y);
    }

}