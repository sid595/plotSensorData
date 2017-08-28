package com.acad_example.nilea.environmentsensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class getData extends AppCompatActivity implements SensorEventListener {

    public static SensorManager sensorManager;
    public static Sensor light, accel, magnetometer, proximity;
    TextView lightDisp,mgnDispX, mgnDispY, mgnDispZ, accelDispX,accelDispY,accelDispZ,proximityDisp;
    Button plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        plot = (Button) findViewById(R.id.button);

        lightDisp = (TextView) findViewById(R.id.textView_1);
        accelDispX = (TextView) findViewById(R.id.textView_21);
        accelDispY = (TextView) findViewById(R.id.textView_22);
        accelDispZ = (TextView) findViewById(R.id.textView_23);
        mgnDispX = (TextView) findViewById(R.id.textView_31);
        mgnDispY = (TextView) findViewById(R.id.textView_32);
        mgnDispZ = (TextView) findViewById(R.id.textView_33);
        proximityDisp = (TextView) findViewById(R.id.textView_4);

        lightDisp.setText("Initializing Light Sensor");
        proximityDisp.setText("Initializing Proximity Sensor");

        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);

        plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plotData();
            }
        });
        /*
        if(light == null){
            lightDisp.setText("Light Sensore Does not exists");
        } else {
            lightExists = true;
        }

        if(pressure == null){
            pressureDisp.setText("Pressure Sensore Does not exists");
        } else {
            pressureExists = true;
        }
        if(temperature == null){
            temperatureDisp.setText("Temperature Sensore Does not exists");
        } else {
            temperatureExists = true;
        }
        if(humidity == null){
            humidityDisp.setText("Humidity Sensore Does not exists");
        } else {
            humidityExists = true;
        }
        */

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        int type = sensor.getType();
        switch(type) {
            case Sensor.TYPE_LIGHT:
                lightDisp.setText("Light Sensor: " + sensorEvent.values[0]);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accelDispX.setText("X Value Accel: " + sensorEvent.values[0]);
                accelDispY.setText("Y Value Accel: " + sensorEvent.values[1]);
                accelDispZ.setText("Z Value Accel: " + sensorEvent.values[2]);
                break;
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                mgnDispX.setText("X Value Gyro: " + sensorEvent.values[0]);
                mgnDispY.setText("Y Value Gyro: " + sensorEvent.values[1]);
                mgnDispZ.setText("Z Value Gyro: " + sensorEvent.values[2]);
                break;
            case Sensor.TYPE_PROXIMITY:
                 proximityDisp.setText("Proximity: " + sensorEvent.values[0]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void plotData(){

        Intent intent = new Intent(this,plotSensorData.class);
        //intent.putExtra("Staer");
        startActivity(intent);
    }

}
