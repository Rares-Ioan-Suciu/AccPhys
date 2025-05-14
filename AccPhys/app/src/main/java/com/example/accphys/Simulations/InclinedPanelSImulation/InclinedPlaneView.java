package com.example.accphys.Simulations.InclinedPanelSImulation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import android.os.Handler;
import android.widget.Toast;

public class InclinedPlaneView extends View {
    private final Paint paint = new Paint();
    private double angle;

    public static final double GRAVITY= 9.80665;
    public static final int RADIUS = 42;
    public static final int FRAME_RATE = 16;
    public final float PIXELS_PER_METER = 100f;

    public static double heightScreen;
    private com.example.accphys.Simulations.InclinedPanelSImulation.InclinePanel inclinePanel;

    private float totalTime = 0f;
    private double velocity = 0;
    private float objectY;
    private float lengthPlane;

    public float getObjectMass() {
        return objectMass;
    }

    public void setObjectMass(float objectMass) {
        this.objectMass = objectMass;
    }

    private float objectMass;

    public float getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public void setFrictionCoefficient(float frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }

    private float frictionCoefficient;

    private boolean isPaused = false;
    private final Handler handler;
    private Bitmap cachedPlaneBitmap;
    private boolean planeDrawn = false;

    public float getHeightPlane() {
        return heightPlane;
    }

    public void setHeightPlane(float heightPlane) {
        this.heightPlane = heightPlane;
    }

    public void setLengthPlane(float lengthPlane) {
        this.lengthPlane =lengthPlane;
    }

    private float heightPlane;
    private float distanceAlongIncline;
    private float distanceHorizontal  = 0;

    public InclinedPlaneView(Context context) {
        super(context);
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(10f);

        handler  = new Handler();
    }

    public InclinedPlaneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(20f);


        handler  = new Handler();
    }

    private void drawInclinedPlane(Canvas canvas) {
        int width = getWidth() / 2 + 40;
        int height = getHeight();

        float endX = getWidth();
        float endY = (float) (height - Math.tan(Math.toRadians(angle)) * (endX - width));

        setHeightPlane(endY);
        setLengthPlane(endY);
        canvas.drawLine(width, height, endX, endY, paint);

        Paint basePaint = new Paint();
        basePaint.setColor(Color.DKGRAY);
        basePaint.setStrokeWidth(30f);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), basePaint);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cachedPlaneBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cachedPlaneCanvas = new Canvas(cachedPlaneBitmap);
        drawInclinedPlane(cachedPlaneCanvas);
        planeDrawn = true;
    }

    private void drawRect(Canvas canvas, float pivotX, float pivotY) {
        canvas.save();
        canvas.translate(pivotX, pivotY);
        canvas.rotate((float) -angle);
        canvas.drawRect(-2 * RADIUS, -2 * RADIUS, 0, 0, paint);
        canvas.restore();
    }

    private boolean onIncline = true;
    private float horizontalX;
    private void drawMovingObject(Canvas canvas) {
        if(onIncline) {
            float inclineStartX = getWidth();
            float inclineStartY = getHeightPlane();

            float dx = (float) (-distanceAlongIncline * PIXELS_PER_METER * Math.cos(Math.toRadians(angle)));
            float dy = (float) (+distanceAlongIncline * PIXELS_PER_METER * Math.sin(Math.toRadians(angle)));

            float pivotX = inclineStartX + dx;
            float pivotY = inclineStartY + dy;
            drawRect(canvas, pivotX, pivotY);
        }
        else{
            float deltaTime = FRAME_RATE / 1000f;
            horizontalX += (float) (velocity * deltaTime * PIXELS_PER_METER);
            distanceHorizontal += (float) Math.abs(velocity * deltaTime);
            float baseY = getHeight();
            float right = (horizontalX + (float) getWidth() / 2  + 40);
            canvas.drawRect(right - 2 * RADIUS, baseY - 2 * RADIUS, right, baseY, paint);

        }

        heightScreen = getHeight();
    }




    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);


        if(angle!=0) {
            if (planeDrawn && cachedPlaneBitmap != null) {
                canvas.drawBitmap(cachedPlaneBitmap, 0, 0, null);
            }
            drawMovingObject(canvas);
        }
    }

    private final Runnable updater = new Runnable() {
        @Override
        public void run() {
            if (isPaused) {
                handler.postDelayed(this, FRAME_RATE);
                return;
            }

            float deltaTime = FRAME_RATE / 1000f;

            if (onIncline) {
                double inclineAcceleration = GRAVITY * (Math.sin(Math.toRadians(angle)) - frictionCoefficient * Math.cos(Math.toRadians(angle)));
                velocity += inclineAcceleration * deltaTime;

                if (Math.sin(Math.toRadians(angle)) <= frictionCoefficient * Math.cos(Math.toRadians(angle))) {
                    velocity = 0;
                    Toast.makeText(getContext(), "The object is not able to move with such friction", Toast.LENGTH_SHORT).show();
                    handler.removeCallbacks(this);
                    return;
                }

                distanceAlongIncline += (float) (velocity * deltaTime);
            } else {
                double horizontalAcceleration = -GRAVITY * frictionCoefficient;
                velocity += horizontalAcceleration * deltaTime;

                if (horizontalX + (float) (velocity * deltaTime * PIXELS_PER_METER) + (float) getWidth() / 2 + 40 - 2 * RADIUS <= 0) {
                    velocity *= -0.3;
                }

                if (Math.abs(velocity) < 0.1 && velocity != 0) {
                    velocity = 0;
                    paint.setColor(Color.YELLOW);
                    handler.removeCallbacks(this);
                    return;
                }
            }

            if (velocity > 0) {
                paint.setColor(Color.GREEN);
            } else if (velocity < 0) {
                paint.setColor(Color.BLUE);
            }

            totalTime += deltaTime;

            if (inclinePanel != null) {
                double displayedVelocity = (Math.abs(velocity) < 0.2) ? 0 : velocity;
                double kineticEnergy = objectMass * Math.pow(displayedVelocity, 2) / 2;

                if (onIncline) {
                    float inclineStartY = getHeightPlane();
                    float dy = (float) (distanceAlongIncline * PIXELS_PER_METER * Math.sin(Math.toRadians(angle)));
                    objectY = inclineStartY + dy;
                } else {
                    objectY = getHeight();
                }
                double heightMeters = (heightScreen - objectY) / PIXELS_PER_METER;
                double potentialEnergy = objectMass * GRAVITY * heightMeters;



                double totalDistance = distanceAlongIncline + distanceHorizontal;
                inclinePanel.setTime(totalTime);
                inclinePanel.setDistance(totalDistance);
                inclinePanel.setVelocity(displayedVelocity);
                inclinePanel.setKineticEnergy(kineticEnergy);
                inclinePanel.setPotentialEnergy(potentialEnergy);
            }

            double inclineLength = (getHeight() - getHeightPlane()) / Math.sin(Math.toRadians(angle));
            if (distanceAlongIncline * PIXELS_PER_METER >= inclineLength - 2 * RADIUS - 30 && onIncline) {
                onIncline = false;
                horizontalX = 0;
                velocity *= -1;
            }

            invalidate();
            handler.postDelayed(this, FRAME_RATE);
        }
    };


    public double getAngle() {
        return angle;
    }
    public void setAngle(double angle){
        this.angle = angle;

        if (getWidth() > 0 && getHeight() > 0) {
            cachedPlaneBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas cachedPlaneCanvas = new Canvas(cachedPlaneBitmap);
            drawInclinedPlane(cachedPlaneCanvas);
            planeDrawn = true;
        }
    }

    public void startAnimation() {
        handler.post(updater);
    }
    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setPanel(com.example.accphys.Simulations.InclinedPanelSImulation.InclinePanel inclinePanel) {
        this.inclinePanel = inclinePanel;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void restart() {
        handler.removeCallbacks(updater);

        totalTime = 0;
        velocity = 0;
        onIncline = true;
        distanceAlongIncline = 0;

        paint.setColor(Color.DKGRAY);
        invalidate();
    }

}