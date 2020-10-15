package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Item {
    ImageView image;
    String name;
    boolean available = true;
    int floor;
    int mapId;

    Group group;

    Player j;

    public Item(Image image, String name, int floor, int mapId, Group g, Player p) {
        this.image = new ImageView(image);
        group = g;
        j = p;
        this.name = name;
        this.floor = floor;
        this.mapId = mapId;
        switch (floor){
            case 1: case -1:
                Affichage.setPos(this.image, 1050, 150);
                Affichage.setSize(this.image, 50, 50);
                break;
            case 0 :
                if (name.equals("echelle1")){
                    Affichage.setPos(this.image, 1050, 150);
                    Affichage.setSize(this.image, 50, 50);
                }

                else if (name.equals("echelle2")){
                    Affichage.setPos(this.image, 1050, 100);
                    Affichage.setSize(this.image, 50, 50);
                }
                else if (name.equals("echelle3")){
                    Affichage.setPos(this.image, 1050, 50);
                    Affichage.setSize(this.image, 50, 50);
                }

        }
        group.getChildren().add(this.image);
    }

    public boolean checkVisible(){
        return (Level.currentmap.getId() == mapId && Level.currentmap.getFloor() == floor);
    }

}
