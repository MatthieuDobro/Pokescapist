package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entity {
    private int posX;
    private int posY;
    private String url;
    private ImageView image;

    public Entity(int posX, int postY, String url) {
        this.posX = posX;
        this.posY = postY;
        image = new ImageView(new Image(url));
        Affichage.setSize(image,50,50);
        Affichage.setPos(image,posX,postY);

    }


    /**
     * change l'image ainsi que les positions x,y de l'entity
     * @param posX nouvelle position x
     * @param posY nouvelle position y
     * @param url chemin de la nouvelle image
     */
    public void update(int posX,int posY,String url){
        image.setImage(setImg(url));
        image.setLayoutY(posX);
        image.setLayoutX(posY);
    }

    /**
     * change l'image d'une entité sans en changer les positions x,y
     * @param url chemin de la nouvel image
     */
    public void reset(String url){
        setImg(url);
        image.setImage(setImg(url));
    }


    //getters and setters
    public void setPos(int posX,int posY) {
        this.posX=posX;this.posY=posY;
        //Affichage.setSize(image,posX,posY);
        Affichage.setPos(image,posX,posY);;
    }
    public Image setImg(String url) {
        this.url = url;
        return new Image(url);
    }
    public int getPosX() {
        return posX;
    }

    public int getPostY() {
        return posY;
    }

    public String getUrl() {
        return url;
    }



    public ImageView getImage() {
        return image;
    }
}
