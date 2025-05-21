package com.example.kargocuillegal;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainRoomFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MainRoomFragment() {
        // Required empty public constructor
    }

    public static MainRoomFragment newInstance(String param1, String param2) {
        MainRoomFragment fragment = new MainRoomFragment();
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
        return inflater.inflate(R.layout.fragment_main_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bu alan artık boş kalabilir çünkü sadece masa gösterilecek

        // Aşağıdaki kodları artık kullanmıyoruz:

    /*
    ImageView alarmLight = view.findViewById(R.id.alarmLight);

    new Handler().postDelayed(() -> {
        alarmLight.setVisibility(View.VISIBLE);

        ObjectAnimator animator = ObjectAnimator.ofFloat(alarmLight, "alpha", 1f, 0.2f);
        animator.setDuration(400);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }, 3000);
    */

    /*
    ImageButton leftArrow = view.findViewById(R.id.leftArrow);
    ImageButton rightArrow = view.findViewById(R.id.rightArrow);

    // Örnek tıklama işlemleri vardıysa bunları da silebilirsin
    */


}
}
