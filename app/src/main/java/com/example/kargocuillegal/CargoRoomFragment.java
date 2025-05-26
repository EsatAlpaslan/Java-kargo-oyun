package com.example.kargocuillegal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class CargoRoomFragment extends Fragment {

    public CargoRoomFragment() {
        // Gerekli boş kurucu
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cargo_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button backButton = view.findViewById(R.id.btnBackToRoomSelection);
        backButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.roomSelectionFragment);
        });
    }
}
