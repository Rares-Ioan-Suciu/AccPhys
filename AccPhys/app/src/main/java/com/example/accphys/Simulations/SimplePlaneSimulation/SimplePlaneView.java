package com.example.accphys.Simulations.SimplePlaneSimulation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class SimplePlaneView extends View {

    private final Paint paint = new Paint();
    private double velocity = 0;
    private final Handler handler;
    private boolean isPaused = false;

    public static final double GRAVITY = 9.80665;
    public static final int RADIUS = 42;
    public static final int FRAME_RATE = 16;
    public final float PIXELS_PER_METER = 100f;

    private float totalTime = 0f;
    private float distanceHorizontal = 0f;
    private float objectMass = 1f;
    private float frictionCoefficient = 0.05f;
    public int direction;
    public static double heightScreen;
    public double acceleration;
    public double rightX;

    public double getForce() {
        return force;
    }

    public void setForce(double force) {
        this.force = Math.abs(force);
        this.direction = (int) Math.signum(force);
        setAcceleration();
    }

    public double force;

    public SimplePlanePanel getSimplePanel() {
        return simplePanel;
    }

    public void setSimplePanel(SimplePlanePanel simplePanel) {
        this.simplePanel = simplePanel;
    }

    public SimplePlanePanel simplePanel;
    public void setAcceleration() {
        double frictionForce = frictionCoefficient * objectMass * GRAVITY;
        if (Math.abs(force) > frictionForce) {
            this.acceleration = (Math.abs(force) - frictionForce) / objectMass;
        } else {
            this.acceleration = 0;
            if (velocity != 0) {
                this.acceleration = -frictionForce / objectMass * Math.signum(velocity);
            }
        }
    }
    public SimplePlaneView(Context context) {
        super(context);
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(10f);
        handler = new Handler();
        setAcceleration();

    }

    public SimplePlaneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(20f);
        handler = new Handler();
        setAcceleration();

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rightX = w / 2f + RADIUS / 2f;
    }

    private void drawMovingObject(@NonNull Canvas canvas) {
        float deltaTime = FRAME_RATE / 1000f;
        distanceHorizontal += (float) Math.abs(velocity * deltaTime);
        float baseY = getHeight();
        canvas.drawRect((float) (rightX - 2 * RADIUS), baseY - 2 * RADIUS, (float) rightX, baseY, paint);
        heightScreen = getHeight();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        drawMovingObject(canvas);
    }

    private final Runnable updater = new Runnable() {
        @Override
        public void run() {
            if (isPaused) {
                handler.postDelayed(this, FRAME_RATE);
                return;
            }

            float deltaTime = FRAME_RATE / 1000f;
            velocity += acceleration * deltaTime * direction;
            System.out.println(velocity);
            System.out.println(acceleration);
            rightX += velocity * deltaTime * PIXELS_PER_METER;



            if (rightX - RADIUS <= 0 || rightX + RADIUS >= getWidth()) {
                direction *= -1;
                velocity *= direction * 0.6f;
                rightX = Math.max(RADIUS, Math.min(getWidth() - RADIUS, rightX));
            }
            if (Math.abs(velocity) < 0.3 && velocity != 0) {
                velocity = 0;
                paint.setColor(Color.YELLOW);
            }

            if (velocity > 0) {
                paint.setColor(Color.GREEN);
            } else if (velocity < 0) {
                paint.setColor(Color.BLUE);
            }

            totalTime += deltaTime;
            if (simplePanel != null) {
                double displayedVelocity = (Math.abs(velocity) < 0.2) ? 0 : velocity;
                double kineticEnergy = objectMass * Math.pow(displayedVelocity, 2) / 2;
                double momentum = objectMass * displayedVelocity;
                double totalDistance = distanceHorizontal;
                simplePanel.setTime(totalTime);
                simplePanel.setDistance(totalDistance);
                simplePanel.setVelocity(displayedVelocity);
                simplePanel.setKineticEnergy(kineticEnergy);
                simplePanel.setMomentum(momentum);

                if(velocity == 0)
                {
                    paint.setColor(Color.YELLOW);
                    handler.removeCallbacks(this);
                    invalidate();
                    return;
                }
            }

            invalidate();
            handler.postDelayed(this, FRAME_RATE);
        }
    };

    public void startAnimation() {
        handler.post(updater);
    }

    public void restart() {
        handler.removeCallbacks(updater);
        totalTime = 0;
        velocity = 0;
        distanceHorizontal = 0;
        rightX = getWidth()/2f + RADIUS/2f;
        paint.setColor(Color.DKGRAY);
        invalidate();
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setObjectMass(float objectMass) {
        this.objectMass = objectMass;
    }

    public void setFrictionCoefficient(float frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
