package com.example.kargocuillegal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RoomSelectionFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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

        // Sol kapıya etkileşim
        Button btnLeftDoor = view.findViewById(R.id.btnLeftDoor);
        btnLeftDoor.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_roomSelectionFragment_to_mainRoomFragment)
        );

        return view;
    }
}
