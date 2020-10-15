package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {
    ImageView chocapic = new ImageView(new Image("file:../../images/safe.png"));
    boolean found = false;

    public Controller(Group g, Player j){
        Affichage.setSize(chocapic, 50, 50);
        Affichage.setPos(chocapic, 0, 700);
        chocapic.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            j.addLife(1);
            found = true;
        });
        chocapic.setVisible(false);
        g.getChildren().add(chocapic);
    }

    public void easter(Player j){
        if (found) {
            chocapic.setVisible(false);
        }
        else{
            if (Level.currentmap.getFloor() == 1 && Level.currentmap.getId() == 1){
                chocapic.setVisible(true);
            }
            else chocapic.setVisible(false);
        }
    }
}
