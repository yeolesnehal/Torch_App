package edu.sjsu.android.torchapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TorchActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager lightSensorManager;
    private Sensor lightSensor;
    private TextView sensorValues;
    private TextView data;
    private CameraManager camManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightSensor = lightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorValues = findViewById(R.id.sensorValues);
        data = findViewById(R.id.data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lightSensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lightSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("Message", "Inside sensorChanged method");
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float mLightQuantity = event.values[0];
            sensorValues.setText("Light Sensor Value: " + mLightQuantity);
            if (mLightQuantity < 10) {
                data.setText("TORCH ON");
                Log.d("Sensor Value", "Sensor Value is less than 10");
                try {
                    String cameraId = camManager.getCameraIdList()[0];
                    camManager.setTorchMode(cameraId, true);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }


            } else {
                data.setText("TORCH OFF");
                Log.d("Sensor Value", "Sensor Value is greater than 10");
                try {
                    String cameraId = camManager.getCameraIdList()[0];
                    camManager.setTorchMode(cameraId, false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Add code for onAccuracyChanged here
    }

    public void finishTorchActivity(View view)
    {
        TorchActivity.this.finish();
    }

}
