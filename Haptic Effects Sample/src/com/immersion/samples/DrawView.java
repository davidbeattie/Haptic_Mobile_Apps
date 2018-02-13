package com.immersion.samples;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class DrawView extends View {
	
	VibeSystem m_vibration;	
   private ColorBall colorballs; // array that holds the balls
   private int balID = 0; // variable to know what ball is being dragged
    
    public DrawView(Context context,VibeSystem vibes) 
    {
        super(context);
        setFocusable(true);
        m_vibration =vibes;
        
        //necessary for getting the touch events
        
        // setting the start point for the balls
        Point point1 = new Point();
        point1.x = 130;
        point1.y = 240;
        Point point2 = new Point();
        point2.x = 100;
        point2.y = 20;
        Point point3 = new Point();
        point3.x = 150;
        point3.y = 20;
        
                             
        // declare each ball with the ColorBall class
        colorballs = new ColorBall(context,R.drawable.bol_rood, point1);       
    }
    
    // the method that draws the balls
    @Override 
    protected void onDraw(Canvas canvas) {
    	
        Paint x = new Paint();
        x.setColor(Color.LTGRAY);
        x.setTextSize(18);
        x.setAntiAlias(true);
        canvas.drawText("Drag the ball around the screen!", 22, 180, x);
        	
    	//draw the balls on the canvas
    	
            canvas.drawBitmap(colorballs.getBitmap(), colorballs.getX(), colorballs.getY(), null);
    }
    // events when touching the screen
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction(); 
        
        int X = (int)event.getX(); 
        int Y = (int)event.getY(); 

        switch (eventaction ) { 

        case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball
        	balID = 0;
        	

        		// check if inside the bounds of the ball (circle)
        		// get the center for the ball
        		int centerX = colorballs.getX() + 25;
        		int centerY = colorballs.getY() + 25;
        		
        		// calculate the radius from the touch to the center of the ball
        		double radCircle  = Math.sqrt( (double) (((centerX-X)*(centerX-X)) + (centerY-Y)*(centerY-Y)));
        		
        		// if the radius is smaller then 23 (radius of a ball is 22), then it must be on the ball
        		if (radCircle < 33){
        			m_vibration.playEffect(23);
        			balID = colorballs.getID();
                    break;
        		}

        		// check all the bounds of the ball (square)
        		//if (X > ball.getX() && X < ball.getX()+50 && Y > ball.getY() && Y < ball.getY()+50){
                //	balID = ball.getID();
                //	break;
                //}
             
             break; 


        case MotionEvent.ACTION_MOVE:   // touch drag with the ball
        	// move the balls the same as the finger
        	
            if (balID > 0) {
            	colorballs.setX(X-25);
            	colorballs.setY(Y-25);
            }
        	
            break; 

        case MotionEvent.ACTION_UP:
        	m_vibration.stopEffects();
       		// touch drop - just do things here after dropping

             break; 
        } 
        // redraw the canvas
        invalidate(); 
        return true; 
	
    }
}
