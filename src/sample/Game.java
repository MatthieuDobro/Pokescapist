package sample;

import java.util.ArrayList;

public class Game {
    private ArrayList<Entity> entityArrayList;
    private ArrayList<Level> levelArrayList;
    static Level currentLevel;

    public Game(ArrayList<Level> levelArrayList) {
        this.levelArrayList=levelArrayList;
        this.entityArrayList=new ArrayList<Entity>();
        setGame();
    }

    /**
     * passe au niveau superieur s'il y en a un
     * si la condition requise pour changer de niveau est valable alors cette fonction sera appellée
     */
    public void changeLevel(){
        for (Level level: levelArrayList) {
            if (level.getLevelId()==currentLevel.getLevelId()+1){
                currentLevel=levelArrayList.get(level.getLevelId());
                currentLevel.setLevel();
                currentLevel.updatemap();
            }

        }
;
    }

    /**
     * ajoute aux differents levels l'array liste d'entity de la classe game
     * charge la première le premier level ainsi que la carte du début
     */
    public void setGame(){
        for (int i=0;i<this.levelArrayList.size();i++){
            this.levelArrayList.get(i).setEntityarrayList(entityArrayList);
            if(i==0){
                currentLevel=levelArrayList.get(0);
                currentLevel.setLevel();
                currentLevel.drawMap(0,0);
                currentLevel.addToGroup();
            }
        }
    }

}
