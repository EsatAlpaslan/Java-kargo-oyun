package com.example.kargocuillegal;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.ImageButton;

public class MainRoomFragment extends Fragment {

    private ImageView partGrip, partBarrel, partCylinder, partHammer;
    private ImageView part_slide, part_spring, part_barrel2, part_frame, part_mag;

    private boolean barrelLockedToCylinder = false;
    private float barrelOffsetX = 0, barrelOffsetY = 0;
    private boolean gripLockedToCylinder = false;
    private float gripOffsetX = 0, gripOffsetY = 0;
    private boolean hammerLockedToCylinder = false;
    private float hammerOffsetX = 0, hammerOffsetY = 0;

    private boolean slideLocked = false;
    private float slideOffsetX = 0, slideOffsetY = 0;
    private boolean barrel2Locked = false;
    private float barrel2OffsetX = 0, barrel2OffsetY = 0;
    private boolean springLocked = false;
    private float springOffsetX = 0, springOffsetY = 0;
    private boolean magLocked = false;
    private float magOffsetX = 0, magOffsetY = 0;
    private boolean frameLocked = false;

    private TextView completionText;
    private Button nextDayButton;
    private int currentDay = 1;
    private boolean isCompleted = false;
    private TextView dayTimerText, dayText;
    private CountDownTimer countDownTimer;
    private boolean isTimeOver = false;
    private ImageButton orderBoardButton;
    private ImageView exampleGunImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Parçalar ve metinleri tanımla
        partGrip = view.findViewById(R.id.partGrip);
        partBarrel = view.findViewById(R.id.partBarrel);
        partCylinder = view.findViewById(R.id.partCylinder);
        partHammer = view.findViewById(R.id.partHammer);
        part_slide = view.findViewById(R.id.partSlide);
        part_spring = view.findViewById(R.id.partSpring);
        part_barrel2 = view.findViewById(R.id.partBarrel2);
        part_frame = view.findViewById(R.id.partFrame);
        part_mag = view.findViewById(R.id.partMag);

        completionText = view.findViewById(R.id.completionText);
        nextDayButton = view.findViewById(R.id.nextDayButton);
        dayTimerText = view.findViewById(R.id.dayTimerText);
        dayText = view.findViewById(R.id.dayText);
        orderBoardButton = view.findViewById(R.id.orderBoardButton);
        exampleGunImage = view.findViewById(R.id.exampleGunImage);

        startCountdown(15);

        nextDayButton.setOnClickListener(v -> {
            currentDay++;
            isCompleted = false;
            isTimeOver = false;
            nextDayButton.setVisibility(View.GONE);
            completionText.setVisibility(View.GONE);
            startCountdown(15);

            DisplayMetrics metrics = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            float screenWidth = metrics.widthPixels;
            float screenHeight = metrics.heightPixels;

            if (currentDay == 2) {
                partGrip.setVisibility(View.GONE);
                partBarrel.setVisibility(View.GONE);
                partHammer.setVisibility(View.GONE);
                partCylinder.setVisibility(View.GONE);

                part_slide.setVisibility(View.VISIBLE);
                part_spring.setVisibility(View.VISIBLE);
                part_barrel2.setVisibility(View.VISIBLE);
                part_frame.setVisibility(View.VISIBLE);
                part_mag.setVisibility(View.VISIBLE);

                part_slide.setEnabled(true);
                part_spring.setEnabled(true);
                part_barrel2.setEnabled(true);
                part_frame.setEnabled(true);
                part_mag.setEnabled(true);

                makeDraggable(part_slide);
                makeDraggable(part_spring);
                makeDraggable(part_barrel2);
                makeDraggable(part_frame);
                makeDraggable(part_mag);

                part_slide.setX(0.10f * screenWidth); part_slide.setY(0.08f * screenHeight);
                part_spring.setX(0.30f * screenWidth); part_spring.setY(0.08f * screenHeight);
                part_barrel2.setX(0.50f * screenWidth); part_barrel2.setY(0.08f * screenHeight);
                part_frame.setX(0.10f * screenWidth); part_frame.setY(0.25f * screenHeight);
                part_mag.setX(0.30f * screenWidth); part_mag.setY(0.25f * screenHeight);

                frameLocked = true;
                slideLocked = false;
                barrel2Locked = false;
                springLocked = false;
                magLocked = false;
            }

            dayText.setText("GÜN " + currentDay);
            Toast.makeText(getContext(), "GÜN " + currentDay + " başladı!", Toast.LENGTH_SHORT).show();
        });

        orderBoardButton.setOnClickListener(v -> {
            exampleGunImage.setVisibility(exampleGunImage.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        });
    }

    private void makeDraggable(ImageView partFrame) {
    }

    private void startCountdown(int seconds) {
        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                dayTimerText.setText("Kalan: " + secondsLeft + " sn");
            }

            @Override
            public void onFinish() {
                if (!isCompleted) {
                    dayTimerText.setText("Süreniz bitti!");
                    isTimeOver = true;
                    partGrip.setEnabled(false);
                    partBarrel.setEnabled(false);
                    partCylinder.setEnabled(false);
                    partHammer.setEnabled(false);
                    part_slide.setEnabled(false);
                    part_spring.setEnabled(false);
                    part_barrel2.setEnabled(false);
                    part_frame.setEnabled(false);
                    part_mag.setEnabled(false);
                } else {
                    dayTimerText.setText("");
                }
            }
        }.start();
    }

    private void checkIfAssemblyCompleted() {
        if (currentDay == 1 && barrelLockedToCylinder && gripLockedToCylinder && hammerLockedToCylinder) {
            finishAssembly();
        }

        if (currentDay == 2 && slideLocked && barrel2Locked && springLocked && magLocked && frameLocked) {
            finishAssembly();
        }
    }

    private void finishAssembly() {
        completionText.setVisibility(View.VISIBLE);
        nextDayButton.setVisibility(View.VISIBLE);
        dayTimerText.setVisibility(View.GONE);
        if (countDownTimer != null) countDownTimer.cancel();
        isCompleted = true;
    }
}
