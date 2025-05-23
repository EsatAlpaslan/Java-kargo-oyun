package com.example.kargocuillegal;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class IntroFragment extends Fragment {

    private MediaPlayer mediaPlayer;

    public IntroFragment() {
        // Boş constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        Button btnStart = view.findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {
            // 🎵 Ses dosyasını hemen başlat
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.oyna_button_sesi);
            mediaPlayer.start();

            // 🚀 Anında fragment geçişi
            Navigation.findNavController(v).navigate(R.id.action_introFragment_to_roomSelectionFragment);
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
