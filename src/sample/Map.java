package sample;


import java.util.Arrays;

public class Map {
    private int id;
    private int floor;
    private int[][] map;



    public Map(int id, int floor, int[][] map) {
        this.id = id;
        this.floor = floor;
        this.map = map;
    }
    // getters and setters
    public int getId() {
        return id;
    }

    public int getFloor() {
        return floor;
    }

    public int[][] getMap() {
        return map;
    }
}
