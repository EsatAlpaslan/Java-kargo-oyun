package com.example.kargocuillegal;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class RoomSelectionFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private MediaPlayer mediaPlayer;

    public RoomSelectionFragment() {
        // Required empty public constructor
    }

    public static RoomSelectionFragment newInstance(String param1, String param2) {
        RoomSelectionFragment fragment = new RoomSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_selection, container, false);

        Button btnLeftDoor = view.findViewById(R.id.btnLeftDoor);
        Button btnMiddleDoor = view.findViewById(R.id.btnMiddleDoor);
        Button btnRightDoor = view.findViewById(R.id.btnRightDoor);

        // Sol kapı (Temizlik ve Depo)
        btnLeftDoor.setOnClickListener(v -> {
            playDoorSound();
            Navigation.findNavController(v).navigate(R.id.action_roomSelectionFragment_to_mainRoomFragment);
        });

        // Orta kapı (Müşteri Hizmetleri)
        btnMiddleDoor.setOnClickListener(v -> {
            playDoorSound();
            Navigation.findNavController(v).navigate(R.id.action_roomSelectionFragment_to_customerServiceFragment);
        });

        // Sağ kapı (Kargo/Dağıtım)
        btnRightDoor.setOnClickListener(v -> {
            playDoorSound();
            Navigation.findNavController(v).navigate(R.id.action_roomSelectionFragment_to_cargoRoomFragment);
        });

        return view;
    }

    private void playDoorSound() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.main_door_open);
        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.seekTo(500); // 0.5. saniyeye sar
                mp.start();

                // 🔕 1 saniye sonra sesi durdur
                new Handler().postDelayed(() -> {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }, 900);
            });
        }
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

