package com.example.sensorbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //buat sensor manager dulu
    private SensorManager mSensorManager;

    //baca textview
    private TextView mTextLightSensor;
    private TextView mTextProximitySensor;

    //mendefine sensor
    private Sensor mLightSensor;
    private Sensor mProximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //mendapatkan semua sensor di device ini
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        //print disini
        for (Sensor currentSensor : sensorList){
            Log.d("Sensor : ", currentSensor.getName());

        }

        mTextLightSensor = findViewById(R.id.light_label);
        mTextProximitySensor = findViewById(R.id.proximity_label);

        //assign sensor
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    //listen event sensor
    @Override
    protected void onStart() {
        super.onStart();

        if(mLightSensor != null) {
            mSensorManager.registerListener(this, mLightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }
        if(mProximitySensor != null) {
            mSensorManager.registerListener(this, mProximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //untuk tau sensor yg keupdate yg mana? sek type light atau proximity
        int type = sensorEvent.sensor.getType();
        //baca result
        float result = sensorEvent.values[0];
        switch (type){
            case Sensor.TYPE_LIGHT:
                //baca result butuhnya float
                //klo yg ketrigger sensor light harus update labelnya
                mTextLightSensor.setText(getResources().getString(R.string.light_text, result));
                break;

            case Sensor.TYPE_PROXIMITY:
                //baca result butuhnya float
                //klo yg ketrigger sensor proximity harus update labelnya
                mTextProximitySensor.setText(getResources().getString(R.string.proximity_text, result));

                break;

            default:
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}