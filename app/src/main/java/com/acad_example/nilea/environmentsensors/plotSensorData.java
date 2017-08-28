package com.acad_example.nilea.environmentsensors;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class plotSensorData extends AppCompatActivity  implements SensorEventListener{

    public static SensorManager sensorManager;
    public static Sensor light, accel, magnetometer, proximity;
    public static GraphView lightGraph, accelGraph,mgnGraph,proximityGraph;
    public static LineGraphSeries<DataPoint> lSeries, aSeries, mSeries, pSeries;
    public static long lInt = 0, aInt = 0, mInt = 0, pInt = 0;
    Button close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_sensor_data);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        close = (Button) findViewById(R.id.button2);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lSeries = new LineGraphSeries<>(new DataPoint[] { new DataPoint(lInt,0)});
        aSeries = new LineGraphSeries<>(new DataPoint[] { new DataPoint(aInt,0)});
        mSeries = new LineGraphSeries<>(new DataPoint[] { new DataPoint(mInt,0)});
        pSeries = new LineGraphSeries<>(new DataPoint[] { new DataPoint(pInt,0)});

        lightGraph = (GraphView) findViewById(R.id.graph1);
        lightGraph.setTitle("Light Sensor Data");
        accelGraph = (GraphView) findViewById(R.id.graph2);
        accelGraph.setTitle("Accelerometer Data");
        mgnGraph = (GraphView) findViewById(R.id.graph3);
        mgnGraph.setTitle("Magnetometer(Compass) Data");
        proximityGraph = (GraphView) findViewById(R.id.graph4);
        proximityGraph.setTitle("Proximity Sensor Data");

        lightGraph.addSeries(lSeries);
        accelGraph.addSeries(aSeries);
        mgnGraph.addSeries(mSeries);
        proximityGraph.addSeries(pSeries);

        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        int type = sensor.getType();
        switch(type) {
            case Sensor.TYPE_LIGHT:
                updateLightGraph newupdate = new updateLightGraph(sensorEvent.values[0]);
                newupdate.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                updateAccelGraph accupdate = new updateAccelGraph(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
                accupdate.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                updateMgnGraph mgnupdate = new updateMgnGraph(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
                mgnupdate.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case Sensor.TYPE_PROXIMITY:
                updateProximityGraph proximityupdate = new updateProximityGraph(sensorEvent.values[0]);
                proximityupdate.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
        }
    }


    public static class updateProximityGraph extends AsyncTask<Void, Void, Void> {

        double newVal=0;
        public updateProximityGraph(double value) {
            newVal = value;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            proximityGraph.removeSeries(pSeries);
            pInt++;
            if(pInt >= 999999999){
                pInt = 0;
                pSeries.resetData(new DataPoint[] { new DataPoint(pInt,0)});
            }else{
                pSeries.appendData(new DataPoint(pInt,newVal),true,100,false);
            }
            proximityGraph.addSeries(pSeries);
        }
    }

    public static class updateLightGraph extends AsyncTask<Void, Void, Void> {

        double newVal=0;
        public updateLightGraph(double value) {
            newVal = value;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            lightGraph.removeSeries(lSeries);
            lInt++;
            if(lInt >= 999999999){
                lInt = 0;
                lSeries.resetData(new DataPoint[] { new DataPoint(lInt,0)});
            }else{
                lSeries.appendData(new DataPoint(lInt,newVal),true,100,false);
            }
            lightGraph.addSeries(lSeries);
        }
    }


    public static class updateAccelGraph extends AsyncTask<Void, Void, Void> {

        double newX=0, newY =0, newZ = 0, fVal = 0;
        public updateAccelGraph(double valueX, double valueY, double valueZ) {
            newX = valueX;
            newY = valueY;
            newZ = valueZ;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fVal = newX*newX + newY*newY + newZ*newZ;
            fVal = Math.sqrt(fVal);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            accelGraph.removeSeries(aSeries);
            aInt++;
            if(aInt >= 999999999){
                aInt = 0;
                aSeries.resetData(new DataPoint[] { new DataPoint(aInt,0)});
            }else{
                aSeries.appendData(new DataPoint(aInt,fVal),true,100,false);
            }
            accelGraph.addSeries(aSeries);
        }
    }


    public static class updateMgnGraph extends AsyncTask<Void, Void, Void> {

        double newX=0, newY =0, newZ = 0, fVal = 0;
        public updateMgnGraph(double valueX, double valueY, double valueZ) {
            newX = valueX;
            newY = valueY;
            newZ = valueZ;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fVal = newX*newX + newY*newY + newZ*newZ;
            fVal = Math.sqrt(fVal);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mgnGraph.removeSeries(mSeries);
            mInt++;
            if(mInt >= 999999999){
                mInt = 0;
                mSeries.resetData(new DataPoint[] { new DataPoint(mInt,0)});
            }else{
                mSeries.appendData(new DataPoint(mInt,fVal),true,100,false);
            }
            mgnGraph.addSeries(mSeries);
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

}
