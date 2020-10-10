package com.nccs.java.gameapp.engine;

import com.nccs.java.gameapp.classes.Coordinate;
import com.nccs.java.gameapp.enums.Direction;
import com.nccs.java.gameapp.enums.GameState;
import com.nccs.java.gameapp.enums.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    public static final int GameWidth=25;
    public static final int GameHeight=25;

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snakes= new ArrayList<>();
    private List<Coordinate> food= new ArrayList<>();

    private Direction currentDirection=Direction.East;
    private GameState currentGameState=GameState.Running;

    private Random random = new Random();
    private boolean increaseTail= false;

    private Coordinate getSnakeHead(){
        return snakes.get(0);
    }


    public GameEngine() {

    }
    public void initGame(){
        AddSnakes();
        AddWalls();
        AddFood();
    }

    public void UpdateDirection(Direction newDirection){
        if(Math.abs(newDirection.ordinal() - currentDirection.ordinal())%2==1){
            currentDirection = newDirection;
        }
    }



    public void Update() {
        switch (currentDirection) {

            case North:
                UpdateSnake(0,-1);
                break;
            case East:
                UpdateSnake(1,0);
                break;
            case South:
                UpdateSnake(0,1);
                break;
            case West:
                UpdateSnake(-1,0);
                break;
        }
        for(Coordinate w :walls){
            if (snakes.get(0).equals(w)) {
                currentGameState = GameState.Lost;
            }
        }

        for(int i =1;i<snakes.size();i++){

            if(getSnakeHead().equals(snakes.get(i))){
                currentGameState=GameState.Lost;
                return;
            }
        }
        Coordinate removeFood = null;
        for(Coordinate f :food){
            if(getSnakeHead().equals(f)){
                removeFood=f;
                increaseTail=true;
            }
        }
        if( removeFood!= null){
            food.remove(removeFood);
            AddFood();
        }




    }

    public TileType[][] getMap(){
        TileType[][]map= new TileType[GameWidth][GameHeight];

       for(int x=0;x<GameWidth;x++){
           for (int y = 0; y<GameHeight;y++){
               map[x][y]=TileType.Nothing;
           }
       }
        for(Coordinate walls:walls){
            map[walls.getX()][walls.getY()]=TileType.Walls;
        }

       for(Coordinate s :snakes){
           map[s.getX()][s.getY()]=TileType.SnakeTail;

       }

       for(Coordinate a : food){
           map[a.getX()][a.getY()]=TileType.Food;
       }

       map[snakes.get(0).getX()][snakes.get(0).getY()]=TileType.SnakeHead;


        return map;
    }
    private void UpdateSnake(int x,int y){
        int newX = snakes.get(snakes.size()-1).getX();
        int newY = snakes.get(snakes.size()-1).getY();

        for(int i=snakes.size()-1; i>0; i--){
            snakes.get(i).setX(snakes.get(i-1).getX());
            snakes.get(i).setY(snakes.get(i-1).getY());
        }

        if(increaseTail){
            snakes.add(new Coordinate(newX,newY));
            increaseTail=false;
        }
        snakes.get(0).setX(snakes.get(0).getX() +x);
        snakes.get(0).setY(snakes.get(0).getY() +y);
    }

    private void AddSnakes(){
        snakes.clear();


        snakes.add(new Coordinate(8,8));
        snakes.add(new Coordinate(7,8));
        snakes.add(new Coordinate(6,8));
        snakes.add(new Coordinate(5,8));
        snakes.add(new Coordinate(4,8));
        snakes.add(new Coordinate(3,8));
        snakes.add(new Coordinate(2,8));
    }
    private void AddWalls(){
        for(int x=0;x<GameWidth;x++){
            walls.add(new Coordinate(x,0));
            walls.add(new Coordinate(x,GameHeight-1));

        }
        for(int y=0;y<GameHeight;y++){
            walls.add(new Coordinate(0,y));
            walls.add(new Coordinate(GameWidth-1,y));

        }
    }

    private void AddFood(){
        Coordinate coordinate= null;
        boolean added = false;

        while(!added){
            int x = 1 + random.nextInt(GameWidth - 2);
            int y = 1 + random.nextInt(GameHeight - 2);

            coordinate = new Coordinate(x,y);
            boolean collision= false;
            for(Coordinate s : snakes){
                if (s.equals(coordinate)){
                    collision = true;
                }
            }

            for(Coordinate a : food){
                if(a.equals(coordinate)){
                    collision = true;
                   // break;
                }
            }
            added = !collision;
        }
        food.add(coordinate);

    }
    public GameState getCurrentGameState()
    {
        return currentGameState;
    }
}
