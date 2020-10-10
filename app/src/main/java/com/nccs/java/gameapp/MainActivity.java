package com.nccs.java.gameapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.nccs.java.gameapp.engine.GameEngine;
import com.nccs.java.gameapp.enums.Direction;
import com.nccs.java.gameapp.enums.GameState;
import com.nccs.java.gameapp.views.SnakeView;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay=400;
    private float prevX,prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameEngine = new GameEngine();
        gameEngine.initGame();
        snakeView = (SnakeView) findViewById(R.id.snakev);
        snakeView.setOnTouchListener(this);
        startUpdateHandler();

    }

    private void startUpdateHandler() {
        handler.postDelayed(new Runnable() {
@Override
            public void run() {
                gameEngine.Update();
                if (gameEngine.getCurrentGameState() == GameState.Running) {

                    handler.postDelayed(this, updateDelay);

                }
                if(gameEngine.getCurrentGameState()==GameState.Lost){

                   OnGameLost();

                }
                snakeView.setSnakeViewMap(gameEngine.getMap());
                snakeView.invalidate();
            }
            },updateDelay);
    }
    private void OnGameLost(){
        Toast.makeText(this,"You lost",Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            prevX=event.getX();
            prevY=event.getY();
            break;
            case MotionEvent.ACTION_UP:
            float newX = event.getX();
            float newY = event.getY();

            if(Math.abs(newX-prevX)>Math.abs(newY - prevY)){

                if(newX > prevX){
                    gameEngine.UpdateDirection(Direction.East);
                }
                else{
                    gameEngine.UpdateDirection(Direction.West);
                }
            }else{
                if (newY > prevY){
                    gameEngine.UpdateDirection(Direction.South);
                }
                else{
                    gameEngine.UpdateDirection(Direction.North);
                }
            }

        }
        return true;
    }
}

