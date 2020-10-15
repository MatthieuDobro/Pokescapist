package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Inventory {

    Rectangle rec1 = new Rectangle();
    Rectangle rec2 = new Rectangle();
    Rectangle rec3 = new Rectangle();
    final int size = 3;
    String[] inventory = new String[]{"", "", ""};
    int actual = 0;
    Group group;
    ImageView img1;
    ImageView img2;
    ImageView img3;

    long start;
    long end;
    boolean started = false;

    Image blank = new Image("file:../../images/blank.png");

    Player joueur;

    public Inventory(Group g) {
        group = g;
        rec1.setLayoutY(5);
        rec1.setLayoutX(5);
        rec1.setWidth(50);
        rec1.setHeight(50);
        rec1.setStroke(Color.WHITE);
        rec1.setFill(Color.TRANSPARENT);
        img1 = new ImageView();
        Affichage.setSize(img1,50, 50);
        Affichage.setPos(img1, 5, 5);

        rec2.setLayoutX(60);
        rec2.setLayoutY(5);
        rec2.setWidth(50);
        rec2.setHeight(50);
        rec2.setStroke(Color.WHITE);
        rec2.setFill(Color.TRANSPARENT);
        img2 = new ImageView();
        Affichage.setSize(img2,50, 50);
        Affichage.setPos(img2, 60, 5);

        rec3.setLayoutY(5);
        rec3.setStroke(Color.WHITE);
        rec3.setFill(Color.TRANSPARENT);
        rec3.setLayoutX(115);
        rec3.setWidth(50);
        rec3.setHeight(50);
        img3 = new ImageView();
        Affichage.setSize(img3,50, 50);
        Affichage.setPos(img3, 115, 5);

        group.getChildren().addAll(rec1, rec2, rec3, img1, img2, img3);
    }

    // Permet d'ajouter un objet dans l'inventaire
    public boolean addItem(String item){
        Image image = new Image("file:../../images/blank.png");
        switch(item){
            case "key":
                image = new Image("file:../../images/key.png");
                break;
            case "invisible":
                image = new Image("file:../../images/invisible.png");
                break;
        }
        if (actual < size){
            int test = 0;
            for (int i = 0; i < size; i++){
                if (inventory[i].equals("")){
                    inventory[i] = item;
                    test = i;
                    switch (test){
                        case 0:
                            img1.setImage(image);
                            break;
                        case 1:
                            img2.setImage(image);
                            break;
                        case 2:
                            img3.setImage(image);
                            break;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    // Permet de lancer le chrono d'invisibilité
    public void startHide(){
        started = true;
        start = System.currentTimeMillis();
    }

    // Permet de retirer et/ou utiliser un objet (sauf la clé)
    public boolean removeItem(int index){
        if (!inventory[index].equals("")){
            if (!inventory[index].equals("key")){
                String i = inventory[index];
                inventory[index] = "";
                switch (index){
                    case 0:
                        img1.setImage(blank);
                        break;
                    case 1:
                        img2.setImage(blank);
                        break;
                    case 2:
                        img3.setImage(blank);
                        break;
                }
                if (i.equals("invisible")){
                    joueur.isVisible = false;
                    joueur.carton.setVisible(true);
                    startHide();
                }
            }
        }
        return false;
    }
}
