package sample;

import javafx.scene.Group;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;



public class Level {


    private ArrayList<Entity> EntityarrayList;
    private ArrayList<Map> mapArrayList;
    private ArrayList<String> urlArray;
    public static Map currentmap;
    private Group group;
    private int levelId;
    private String filename;
    private String repository;

   public Level(Group group,int levelId,String filename,String repository) {
       this.repository=repository;
       this.filename = filename;
        this.levelId=levelId;
        EntityarrayList=new ArrayList<Entity>();
        mapArrayList=new ArrayList<Map>();
        this.group = group;
        urlArray=new ArrayList<String>();
    }


    /**
     * paccourt le fichier entré dans le constructeur et associe les variable correspondant a chaque level
     */
    public void setLevel()  {
        File file = new File(filename);
        if (file.exists()) {
            try {
                Scanner in = new Scanner(new File(filename));
                int id = 0, floor = 0;
                int[][] map = new int[15][26];
                String [] url;
                final String SEPARATEUR = " ";
                String tmp;
                in.useDelimiter("\\s");
                while (in.hasNextLine()) {
                    switch (in.next()) {
                        case "url":
                            url=in.nextLine().split(SEPARATEUR);
                            for(int i=0;i<url.length-1;i++) {
                                urlArray.add(url[i+1]);
                            }
                        break;

                        case "id":
                            id = in.nextInt();
                        break;

                        case "floor":
                            floor = in.nextInt();
                        break;

                        case "map":
                            map=new int[in.nextInt()][in.nextInt()];
                            in.nextLine();
                            for (int i=0;i<map.length;i++){

                                for (int j=0;j<map[i].length;j++){
                                    map[i][j]=in.nextInt();
                                }
                                if (in.hasNextLine())
                                    in.nextLine();
                            }
                            mapArrayList.add(new Map(id,floor,map));
                            break;
                        }
                    }
                in.close();
                initMap();
            }
            catch(Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
    public void initMap(){
        for (Map map:mapArrayList) {
            if(map.getFloor()==0&&map.getId()==0)
                currentmap = map;
        }
    }

    /**
     * parcourt la matrice qui contient les éléments de la carte et crée les entitées associé puis les ajoutes au groupe affiché.
     * @param
     */
    public void drawMap(int a, int b) {
        int [][] map=currentmap.getMap();
        String url=setUrl();
        for (int i=a; i < map.length; i++) { //i rows
            for ( int j=b; j < map[i].length; j++) {
                if (map[i][j] != -1) {
                    if (map[i][j] == 0) {
                        EntityarrayList.add(new Entity(j * 50, i * 50, "file:../../images/void.png"));
                        EntityarrayList.add(new Entity(j * 50, i * 50, repository+"echelle.png"));
                    } else {
                        EntityarrayList.add(new Entity(j * 50, i * 50, url));
                        if (map[i][j] != 1) {
                            if(map[i][j]<urlArray.size())
                            EntityarrayList.add(new Entity(j * 50, i * 50,repository+urlArray.get(map[i][j])));
                        }
                    }
                }
                else {
                    EntityarrayList.add(new Entity(j * 50 , i * 50 , "file:../../images/void.png"));
                }

            }
        }
    }

    /**
     * charge l'url correspond à l'étage où l'on se situe
     * @return
     */
    public String setUrl(){
        String url="file:../../images/sol1.png";
        switch (currentmap.getFloor()){
            case 0:
                url=repository+urlArray.get(1);
                break;
            case 1:
                url=repository+urlArray.get(urlArray.size()-2);
                break;
            case -1:
                url=repository+urlArray.get(urlArray.size()-1);
                break;
        }
        return url;
    }

    /**
     * mets a jours les entitées de l'array liste en changeant les positions et l'image pour celles correspondantes
     */
    public void updatemap(){
        String url=setUrl();
        int [][] map=currentmap.getMap();
        int ret=0;
        for (int i = 0; i < map.length; i++) { //i rows
            for (int j = 0; j < map[i].length; j++) {
                if(ret<EntityarrayList.size()) {
                    if (map[i][j] != -1) {
                        if (map[i][j] == 0) {
                            EntityarrayList.get(ret).update(i * 50, j * 50, "file:../../images/void.png");
                            ret++;
                            EntityarrayList.get(ret).update(i * 50, j * 50, repository + "echelle.png");
                        } else {
                            if (map[i][j] == 1)
                                EntityarrayList.get(ret).update(i * 50, j * 50, url);
                            else {
                                EntityarrayList.get(ret).update(i * 50, j * 50, url);
                                ret++;
                            }
                            if (map[i][j] != 1) {

                                if (map[i][j] < urlArray.size())
                                    EntityarrayList.get(ret).update(i * 50, j * 50, repository + urlArray.get(map[i][j]));
                            }
                        }
                    } else {
                        EntityarrayList.get(ret).update(i * 50, j * 50, "file:../../images/void.png");
                    }
                    ret++;

                }


            }
        }
        for(int i=ret;i<EntityarrayList.size()-1;i++){
            EntityarrayList.get(i).update(0 ,0, "file:../../images/void.png");
        }

    }
    /**
     * changer de carte pour aller à droite
     */
    public boolean goUp() {
        return changeMap(currentmap.getFloor()+1,currentmap.getId());
    }
    /**
     * changer de carte pour aller à gauche
     */
    public boolean goDown() {
       return changeMap(currentmap.getFloor()-1,currentmap.getId());
    }
    /**
     * changer de carte pour aller à droite
     */
    public boolean goRight() {
        return changeMap(currentmap.getFloor(),currentmap.getId()+1);
    }
    /**
     * changer de carte pour aller à gauche
     */
    public boolean goLeft() {
       return changeMap(currentmap.getFloor(),currentmap.getId()-1);
    }
    /**
     * parcourt l'arrayList qui contient toutes les entitées et ajoute les images au groupe du jeu
     *
     */
    public void addToGroup(){
        for (Entity entity:EntityarrayList) {
            group.getChildren().addAll(entity.getImage());

        }

    }


    /**
     *
     * @param newFloor the floor that will be looked for
     * @param newId the id that will be looked for
     *  si une carte avec l'étage et l'id qui sont entré alors la map actuelle change. Sinon il se passe rien car la map n'existe pas
     */
    public boolean changeMap(int newFloor,int newId){
        boolean flag=false;
        for (Map map:mapArrayList) {

            if(map.getFloor()==newFloor&&map.getId()==newId) {
                currentmap = map;
                updatemap();
                flag=true;
            }
        }
        return flag;
    }
    //getters and setters

    public void setEntityarrayList(ArrayList<Entity> entityarrayList) {
        EntityarrayList = entityarrayList;
    }
    public int getLevelId(){
        return levelId;
    }
}
