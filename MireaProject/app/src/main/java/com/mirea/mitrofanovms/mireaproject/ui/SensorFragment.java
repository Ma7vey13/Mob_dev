package com.mirea.mitrofanovms.mireaproject.ui;

import static android.content.Context.SENSOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.mirea.mitrofanovms.mireaproject.databinding.FragmentSensorBinding;

public class SensorFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor magneticFieldSensor;
    private Sensor accelerometerSensor;
    private FragmentSensorBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSensorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sensorManager = (SensorManager) requireActivity().getSystemService(SENSOR_SERVICE);
        magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
    }
    private float[] accelerometerValues = new float[3];
    private float[] magnetometerValues = new float[3];
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event == null) return;

        float[] rotationMatrix = new float[9];
        float[] orientationValues = new float[3];

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerValues, 0, 3);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerValues, 0, 3);
        }

        if (SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerValues, magnetometerValues)) {
            SensorManager.getOrientation(rotationMatrix, orientationValues);
            float azimuthInRadians = orientationValues[0]; // азимут в радианах
            float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);

            // Корректировка угла (0 - север, 90 - восток и т.д.)
            azimuthInDegrees = (azimuthInDegrees + 360) % 360;

            // Отображение направления
            String direction;
            if (azimuthInDegrees >= 315 || azimuthInDegrees < 45) {
                direction = "Север";
            } else if (azimuthInDegrees >= 45 && azimuthInDegrees < 135) {
                direction = "Восток";
            } else if (azimuthInDegrees >= 135 && azimuthInDegrees < 225) {
                direction = "Юг";
            } else {
                direction = "Запад";
            }

            binding.compassDirection.setText("Направление: " + direction + " (" + String.format("%.1f", azimuthInDegrees) + "°)");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Обработка изменения точности сенсоров
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sensorManager.unregisterListener(this);
        binding = null;
    }
}
