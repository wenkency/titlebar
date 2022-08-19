package cn.carhouse.titlebar.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;

/**
 * Sensor监听，主要监听旋转变化
 */
public class SensorUtils implements SensorEventListener {
    private final Sensor sensor;
    private final LifecycleCallbacks callbacks;
    private Activity activity;
    private SensorManager sensorManager;
    private onSensorChangeListener onSensorChangeListener;
    private int rotation;

    public SensorUtils(Activity activity) {
        this.activity = activity;
        rotation = getRotation();
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        callbacks = new LifecycleCallbacks() {
            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                if (SensorUtils.this.activity == activity) {
                    stop();
                }
            }
        };
    }

    public void start() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        activity.getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
        activity.getApplication().unregisterActivityLifecycleCallbacks(callbacks);
    }

    private int getRotation() {
        return activity.getWindowManager().getDefaultDisplay().getRotation();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
            return;
        }
        int rt = getRotation();
        if (rotation == rt) {
            return;
        }
        rotation = rt;
        // 0: Surface.Rotation_0 竖屏正向，1: Surface.Rotation_90 横屏正向，
        // 2: Surface.Rotation_180 竖屏反向， 3: Surface.Rotation_270 横屏反向
        if (onSensorChangeListener != null) {
            onSensorChangeListener.onSensorChanged(event, rotation);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setOnSensorChangeListener(SensorUtils.onSensorChangeListener onSensorChangeListener) {
        this.onSensorChangeListener = onSensorChangeListener;
    }

    public interface onSensorChangeListener {
        void onSensorChanged(SensorEvent event, int rotation);
    }

}
