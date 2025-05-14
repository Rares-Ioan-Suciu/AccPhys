package com.example.accphys.Simulations.FreeFallSimulation;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.os.Handler;

import androidx.annotation.NonNull;


public class FreeFall extends View {
    public static final int RADIUS = 42;
    public static final int FRAME_RATE = 16;
    public final float PIXELS_PER_METER = 100f;

    public final float WALL_OBJECT = 60f;

    public static double heightScreen;
    private Panel panel;

    private float totalTime = 0f;
    private float metersTraversed = 0f;
    private double velocity = 0;
    private float objectY;
    private float objectMass;
    private float previousY = 0;

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double gravity = 9.80665;


    private boolean isPaused = false;

    public float getObjectMass() {
        return objectMass;
    }

    public void setObjectMass(float objectMass) {
        this.objectMass = objectMass;
    }

    private final Paint paint;
    private final Handler handler;


    public FreeFall(Context context){
        super(context);
        paint = new Paint();
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        paint.setColor(0xFFFFFFFF);
        handler = new Handler();
    }

    private final Runnable updater = new Runnable() {
        @Override
        public void run() {

            if(isPaused)
            {
                handler.postDelayed(this, FRAME_RATE);
                return;
            }
            float deltaTime = FRAME_RATE / 450f;
            totalTime += deltaTime;
            velocity += gravity * deltaTime;
            objectY += (float) velocity;

            float deltaY = Math.abs(objectY - previousY);
            metersTraversed += deltaY / PIXELS_PER_METER;
            previousY = objectY;


            if (velocity > 0) {
                paint.setColor(Color.GREEN);
            } else if (velocity < 0) {
                paint.setColor(Color.BLUE);
            }
            if (objectY + RADIUS + 43>= heightScreen) {
                objectY = (float) (heightScreen - RADIUS -43);
                velocity = -velocity * objectMass/ WALL_OBJECT;
                paint.setColor(Color.YELLOW);

                if (Math.abs(velocity) < 1.5) {
                    velocity = 0;
                    objectY = (float) (heightScreen - RADIUS - 43);
                    totalTime += FRAME_RATE / 400f;

                    invalidate();
                    handler.removeCallbacks(this);
                    return;
                }
            }

            invalidate();
            handler.postDelayed(this, FRAME_RATE);
        }
    };


    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        double displayedVelocity = (Math.abs(velocity) < 0.01) ? 0 : velocity;
        double kineticEnergy = objectMass * Math.pow(displayedVelocity, 2) / 2;
        double heightMeters = (heightScreen - objectY - RADIUS) / PIXELS_PER_METER;
        double potentialEnergy = objectMass * gravity * heightMeters;


        if(displayedVelocity==0)
            potentialEnergy = 0;

        float centerX = getWidth() / 2f;
        float centerY = objectY + RADIUS;
        float halfSide = RADIUS;

        canvas.drawRect(centerX - halfSide, centerY - halfSide,centerX + halfSide, centerY + halfSide, paint);
        if (panel != null) {
            panel.setTime(totalTime);
            panel.setDistance(metersTraversed);
            panel.setVelocity(displayedVelocity);
            panel.setKineticEnergy(kineticEnergy);
            panel.setPotentialEnergy(potentialEnergy);
        }

        if(velocity == 0)
        {
            System.out.println(objectY - heightScreen);
        }

        heightScreen = getHeight();
    }

    public void startAnimation() {
        previousY = objectY;
        handler.post(updater);
    }
    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void restart() {
        handler.removeCallbacks(updater);
        objectY = 0;
        previousY = objectY;
        totalTime = 0;
        velocity = 0;

        paint.setColor(Color.WHITE);
        invalidate();
    }



}
