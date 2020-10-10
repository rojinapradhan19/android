package com.nccs.java.gameapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.nccs.java.gameapp.enums.TileType;



public class SnakeView extends View {
    private Paint paint = new Paint();
    private TileType snakeViewMap[][];

    public SnakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSnakeViewMap(TileType[][] map) {
        this.snakeViewMap = map;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    if (snakeViewMap!= null) {
            float tileSiteX = canvas.getWidth() / snakeViewMap.length;
            float tileSiteY = canvas.getHeight() / snakeViewMap.length;
            float circleSize = Math.min(tileSiteX, tileSiteY) / 2;

            for (int x = 0; x < snakeViewMap.length; x++) {
                for (int y = 0; y < snakeViewMap.length; y++) {
                    switch (snakeViewMap[x][y]) {
                        case Nothing:
                            paint.setColor(Color.WHITE);
                            break;
                        case Walls:
                            paint.setColor(Color.BLACK);
                            break;
                        case SnakeHead:
                            paint.setColor(Color.RED);
                            break;
                        case SnakeTail:
                            paint.setColor(Color.GREEN);
                            break;
                        case Food:
                            paint.setColor(Color.RED);
                            break;
                    }
                    canvas.drawCircle(x * tileSiteX + tileSiteX / 2f + circleSize / 2, y * tileSiteY + tileSiteY / 2f + circleSize / 2, circleSize, paint);
                }
            }
        }

    }

}
