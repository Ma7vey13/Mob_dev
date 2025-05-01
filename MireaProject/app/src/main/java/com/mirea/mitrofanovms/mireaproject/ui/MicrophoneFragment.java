package com.mirea.mitrofanovms.mireaproject.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.mirea.mitrofanovms.mireaproject.databinding.FragmentMicrophoneBinding;

public class MicrophoneFragment extends Fragment {

    private FragmentMicrophoneBinding binding;
    private boolean isRecording = false;
    private MediaRecorder mediaRecorder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMicrophoneBinding.inflate(inflater, container, false);
        binding.btnStop.setOnClickListener(v -> {
            stopRecording();
        });
        binding.btnRecord.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                return;
            }
            startRecording();
        });

        return binding.getRoot();
    }

    private void startRecording() {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(requireContext().getFilesDir().getAbsolutePath() + "/audio.3gp");
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            binding.btnRecord.setEnabled(false);
            binding.btnStop.setEnabled(true);
            Toast.makeText(requireContext(), "Recording started", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error recording audio", Toast.LENGTH_SHORT).show();
        }
    }
    private void stopRecording() {
        if (isRecording) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            binding.btnRecord.setEnabled(true);
            binding.btnStop.setEnabled(false);
            Toast.makeText(requireContext(), "Recording saved", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaRecorder != null) {
            mediaRecorder.release();
        }
    }
}
