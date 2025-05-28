package com.example.kargocuillegal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class IntroFragment extends Fragment {

    private boolean isMusicPlaying = true;
    private ImageButton musicToggleButton;

    public IntroFragment() {
        // Boş constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        // 🎵 Müzik başlat ve sesi tam aç
        musicmanager.startMusic(requireContext());
        musicmanager.setVolume(0.3f);  // Ses tamamen açık

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnStart = view.findViewById(R.id.btnStart);
        musicToggleButton = view.findViewById(R.id.btnMusic);

        // 🎵 Uygulama genel müziği başlat
        musicmanager.startMusic(requireContext());
        updateMusicButtonIcon(); // ilk ikon durumunu ayarla

        // 🔊 Müzik aç/kapat butonu
        musicToggleButton.setOnClickListener(v -> {
            if (musicmanager.isMusicPlaying()) {
                musicmanager.pauseMusic();
            } else {
                musicmanager.resumeMusic();
            }
            updateMusicButtonIcon(); // ikon değiştir
        });

        // 🚀 Oyuna Başla
        btnStart.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_introFragment_to_roomSelectionFragment);
        });
    }

    private void updateMusicButtonIcon() {
        if (musicmanager.isMusicPlaying()) {
            musicToggleButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off); // müzik açık
        } else {
            musicToggleButton.setImageResource(android.R.drawable.ic_lock_silent_mode); // müzik kapalı
        }
    }
}
