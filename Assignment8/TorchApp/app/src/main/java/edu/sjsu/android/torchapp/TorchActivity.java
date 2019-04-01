package edu.sjsu.android.torchapp;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class TorchActivity extends AppCompatActivity {
    SensorManager lightSensorManager;
    Sensor lightSensor;
    TextView data;
    TextView sensorValues;
    private float mLightQuantity;
    //Camera camera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = (TextView)findViewById(R.id.data);
        sensorValues = (TextView)findViewById(R.id.sensorValues);

        lightSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightSensor =lightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onResume(){
        super.onResume();
        lightSensorManager.registerListener(lightSensorListener, lightSensor, 2*1000*1000);
    }

    public void onPause(){
        super.onPause();
        lightSensorManager.unregisterListener(lightSensorListener);
    }

    private SensorEventListener lightSensorListener= new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            mLightQuantity = event.values[0];
            if(mLightQuantity < 10f) {
                sensorValues.setText("Light Sensor Values: " + mLightQuantity);
                //on();
                data.setText("Turned On");

            }
            else{
                sensorValues.setText("Light Sensor Values: " + mLightQuantity);
                //off();
                data.setText("Turned Off");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

  /* public void on(){
        camera = Camera.open();
        Camera.Parameters p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();
    }

    public void off() {
        camera = Camera.open();
        Camera.Parameters p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(p);
        camera.startPreview();
    }*/

    public void finishTorchActivity(View view){
        TorchActivity.this.finish();
    }

}
