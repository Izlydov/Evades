package objects;

import java.util.ArrayList;

public class Area {
    public int id;
    public String name;
    public int numOfBalls;
    public int numOfSlowings;
    public int xpPerPellet;
    public float ballSpeed;
    public float ballRadius;
    public int width;
    public int height;
    public int safeZone;
    public int winZone;
    public boolean isWinArea;
    public int backZone;
    public ArrayList<Ball> balls = new ArrayList<>();
    public ArrayList<Pellet> pellets = new ArrayList<>();
    public Area() {
        xpPerPellet = 30;
    }
    public Area(int numOfBalls, float radius, float speed){
        this.numOfBalls = numOfBalls;
        this.ballRadius = radius;
        this.ballSpeed = speed;
    }
}
