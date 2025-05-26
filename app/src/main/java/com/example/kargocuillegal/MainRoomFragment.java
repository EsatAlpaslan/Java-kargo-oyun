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
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.ImageButton;


public class MainRoomFragment extends Fragment {

    private ImageView partGrip, partBarrel, partCylinder, partHammer;
    private ImageView part_slide, part_spring, part_barrel2, part_frame, part_mag;

    private boolean barrelLockedToCylinder = false;
    private float barrelOffsetX = 0;  // Barrel'ın cylinder'a göre X farkı
    private float barrelOffsetY = 0;  // Barrel'ın cylinder'a göre Y farkı

    //yeni eklendi
    private boolean gripLockedToCylinder = false;
    private float gripOffsetX = 0;
    private float gripOffsetY = 0;

    private boolean hammerLockedToCylinder = false;
    private float hammerOffsetX = 0;
    private float hammerOffsetY = 0;
    private TextView completionText;
    private Button nextDayButton;

    private boolean slideLocked = false;
    private float slideOffsetX = 0, slideOffsetY = 0;

    private boolean barrel2Locked = false;
    private float barrel2OffsetX = 0, barrel2OffsetY = 0;

    private boolean springLocked = false;
    private float springOffsetX = 0, springOffsetY = 0;

    private boolean magLocked = false;
    private float magOffsetX = 0, magOffsetY = 0;

    private boolean frameLocked = false;

    private boolean deagleSlideLocked = false;
    private float deagleSlideOffsetX = 0, deagleSlideOffsetY = 0;

    private boolean deagleBarrelLocked = false;
    private float deagleBarrelOffsetX = 0, deagleBarrelOffsetY = 0;

    private boolean deagleSpringLocked = false;
    private float deagleSpringOffsetX = 0, deagleSpringOffsetY = 0;

    private boolean deagleMagLocked = false;
    private float deagleMagOffsetX = 0, deagleMagOffsetY = 0;






    private int currentDay = 1; // Başlangıçta Gün 1
    private boolean isCompleted = false;
    private TextView dayTimerText;
    private CountDownTimer countDownTimer;
    private TextView dayText;
    private boolean isTimeOver = false;
    private ImageButton orderBoardButton;
    private TextView orderText;
    private ImageView exampleGunImage;
    private ImageView partDeagleFrame, partDeagleSlide, partDeagleBarrel, partDeagleSpring, partDeagleMag;




    public MainRoomFragment() {
        // Boş constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Parçaları tanımla
        partGrip = view.findViewById(R.id.partGrip);
        partBarrel = view.findViewById(R.id.partBarrel);
        partCylinder = view.findViewById(R.id.partCylinder);
        partHammer = view.findViewById(R.id.partHammer);

        part_slide = view.findViewById(R.id.partSlide);
        part_spring = view.findViewById(R.id.partSpring);
        part_barrel2 = view.findViewById(R.id.partBarrel2);
        part_frame = view.findViewById(R.id.partFrame);
        part_mag = view.findViewById(R.id.partMag);

        partDeagleFrame = view.findViewById(R.id.partDeagleFrame);
        partDeagleSlide = view.findViewById(R.id.partDeagleSlide);
        partDeagleBarrel = view.findViewById(R.id.partDeagleBarrel);
        partDeagleSpring = view.findViewById(R.id.partDeagleSpring);
        partDeagleMag = view.findViewById(R.id.partDeagleMag);



        // Sürüklenebilir yap
        makeDraggable(partGrip);
        makeDraggable(partBarrel);
        makeDraggable(partCylinder);
        makeDraggable(partHammer);

        completionText = view.findViewById(R.id.completionText);
        nextDayButton = view.findViewById(R.id.nextDayButton);

        // yeni gün için
        TextView completionText = view.findViewById(R.id.completionText);
        Button nextDayButton = view.findViewById(R.id.nextDayButton);// daha önce tanımlandıysa

        nextDayButton.setOnClickListener(v -> {
            currentDay++;

            nextDayButton.setVisibility(View.GONE);
            completionText.setVisibility(View.GONE);
            isCompleted = false;

            startCountdown(15);

            DisplayMetrics metrics = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            float screenWidth = metrics.widthPixels;
            float screenHeight = metrics.heightPixels;

            if (currentDay == 1) {
                partGrip.setVisibility(View.VISIBLE);
                partBarrel.setVisibility(View.VISIBLE);
                partHammer.setVisibility(View.VISIBLE);
                partCylinder.setVisibility(View.VISIBLE);

                part_slide.setVisibility(View.GONE);
                part_spring.setVisibility(View.GONE);
                part_barrel2.setVisibility(View.GONE);
                part_frame.setVisibility(View.GONE);
                part_mag.setVisibility(View.GONE);

                partDeagleFrame.setVisibility(View.GONE);
                partDeagleSlide.setVisibility(View.GONE);
                partDeagleBarrel.setVisibility(View.GONE);
                partDeagleSpring.setVisibility(View.GONE);
                partDeagleMag.setVisibility(View.GONE);
            }

            if (currentDay == 2) {
                // Revolver parçalarını gizle
                partGrip.setVisibility(View.GONE);
                partBarrel.setVisibility(View.GONE);
                partHammer.setVisibility(View.GONE);
                partCylinder.setVisibility(View.GONE);

                // Glock parçalarını göster
                part_slide.setVisibility(View.VISIBLE);
                part_spring.setVisibility(View.VISIBLE);
                part_barrel2.setVisibility(View.VISIBLE);
                part_frame.setVisibility(View.VISIBLE);
                part_mag.setVisibility(View.VISIBLE);

                // Deagle parçalarını göster
                partDeagleFrame.setVisibility(View.VISIBLE);
                partDeagleSlide.setVisibility(View.VISIBLE);
                partDeagleBarrel.setVisibility(View.VISIBLE);
                partDeagleSpring.setVisibility(View.VISIBLE);
                partDeagleMag.setVisibility(View.VISIBLE);

                // 📌 GLOCK konumları
                part_slide.setX(0.07f * screenWidth); part_slide.setY(0.05f * screenHeight);
                part_spring.setX(0.38f * screenWidth); part_spring.setY(0.11f * screenHeight);
                part_barrel2.setX(0.23f * screenWidth); part_barrel2.setY(0.77f * screenHeight);
                part_frame.setX(0.78f * screenWidth); part_frame.setY(0.67f * screenHeight);
                part_mag.setX(0.036f * screenWidth); part_mag.setY(0.63f * screenHeight);

                // 📌 DEAGLE konumları
                partDeagleSlide.setX(0.5f * screenWidth); partDeagleSlide.setY(0.76f * screenHeight);
                partDeagleBarrel.setX(0.7f * screenWidth); partDeagleBarrel.setY(0.08f * screenHeight);
                partDeagleSpring.setX(0.9f * screenWidth); partDeagleSpring.setY(0.18f * screenHeight);
                partDeagleFrame.setX(0.05f * screenWidth); partDeagleFrame.setY(0.3f * screenHeight);
                partDeagleMag.setX(0.88f * screenWidth); partDeagleMag.setY(0.36f * screenHeight);

                // Sürüklenebilir yap
                makeDraggable(part_slide);
                makeDraggable(part_spring);
                makeDraggable(part_barrel2);
                makeDraggable(part_frame);
                makeDraggable(part_mag);

                makeDraggable(partDeagleFrame);
                makeDraggable(partDeagleSlide);
                makeDraggable(partDeagleBarrel);
                makeDraggable(partDeagleSpring);
                makeDraggable(partDeagleMag);

                // Üstte görünmesi gereken parçalar
                part_frame.bringToFront();
                part_slide.bringToFront();
                partDeagleFrame.bringToFront();
                partDeagleSlide.bringToFront();
            } else {
                // Gün 1 veya diğer günler için revolver parçalarını göster
                partGrip.setVisibility(View.VISIBLE);
                partGrip.setEnabled(true);
                partBarrel.setVisibility(View.VISIBLE);
                partBarrel.setEnabled(true);
                partHammer.setVisibility(View.VISIBLE);
                partHammer.setEnabled(true);
                partCylinder.setVisibility(View.VISIBLE);
                partCylinder.setEnabled(true);

                makeDraggable(partGrip);
                makeDraggable(partBarrel);
                makeDraggable(partHammer);
                makeDraggable(partCylinder);

                partGrip.setX(0.10f * screenWidth); partGrip.setY(0.08f * screenHeight);
                partCylinder.setX(0.30f * screenWidth); partCylinder.setY(0.08f * screenHeight);
                partBarrel.setX(0.50f * screenWidth); partBarrel.setY(0.08f * screenHeight);
                partHammer.setX(0.70f * screenWidth); partHammer.setY(0.08f * screenHeight);
            }

            // Revolver kilit sıfırlama
            gripLockedToCylinder = false;
            barrelLockedToCylinder = false;
            hammerLockedToCylinder = false;

            TextView dayText = requireView().findViewById(R.id.dayText);
            dayText.setText("GÜN " + currentDay);

            Toast.makeText(getContext(), "GÜN " + currentDay + " başladı!", Toast.LENGTH_SHORT).show();
        });


        orderBoardButton = view.findViewById(R.id.orderBoardButton);

        if (currentDay == 3) {
            orderBoardButton.setVisibility(View.VISIBLE);
        } else {
            orderBoardButton.setVisibility(View.GONE);
        }



        dayTimerText = view.findViewById(R.id.dayTimerText);
        startCountdown(15);
        dayText = view.findViewById(R.id.dayText);


        orderBoardButton = view.findViewById(R.id.orderBoardButton);
        exampleGunImage = view.findViewById(R.id.exampleGunImage);

        orderBoardButton.setOnClickListener(v -> {
            // Eğer görünürse gizle, gizliyse göster
            if (exampleGunImage.getVisibility() == View.VISIBLE) {
                exampleGunImage.setVisibility(View.GONE);
            } else {
                exampleGunImage.setVisibility(View.VISIBLE);
            }
        });

    }

    // Sürükle-bırak fonksiyonu + birleşme kontrolü
    private void makeDraggable(ImageView imageView) {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            float dX = 0, dY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isTimeOver) return false;

                // 🔐 Glock parçaları kilitlendiyse tekrar oynatılamasın
                if (currentDay == 2) {
                    if (v == part_slide && slideLocked) return false;
                    if (v == part_barrel2 && barrel2Locked) return false;
                    if (v == part_spring && springLocked) return false;
                    if (v == part_mag && magLocked) return false;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float newX = event.getRawX() + dX;
                        float newY = event.getRawY() + dY;

                        v.setX(newX);
                        v.setY(newY);

                        // 🔧 Revolver parçaları birlikte hareket
                        if (v.getId() == R.id.partCylinder && barrelLockedToCylinder) {
                            partBarrel.setX(newX + barrelOffsetX);
                            partBarrel.setY(newY + barrelOffsetY);
                        }
                        if (v.getId() == R.id.partCylinder && gripLockedToCylinder) {
                            partGrip.setX(newX + gripOffsetX);
                            partGrip.setY(newY + gripOffsetY);
                        }
                        if (v.getId() == R.id.partCylinder && hammerLockedToCylinder) {
                            partHammer.setX(newX + hammerOffsetX);
                            partHammer.setY(newY + hammerOffsetY);
                        }

                        // 🔧 Glock birleşme kontrolleri ve bağlı taşıma
                        if (currentDay == 2) {
                            checkSlideFrameMerge();
                            checkBarrel2ToFrame();
                            checkSpringToBarrel2();
                            checkMagToFrame();

                            // Frame hareket ederse bağlı glock parçaları da sürüklensin
                            if (v.getId() == R.id.partFrame) {
                                if (slideLocked) {
                                    part_slide.setX(newX + slideOffsetX);
                                    part_slide.setY(newY + slideOffsetY);
                                }
                                if (barrel2Locked) {
                                    part_barrel2.setX(newX + barrel2OffsetX);
                                    part_barrel2.setY(newY + barrel2OffsetY);
                                }
                                if (magLocked) {
                                    part_mag.setX(newX + magOffsetX);
                                    part_mag.setY(newY + magOffsetY);
                                }
                            }

                            if (v.getId() == R.id.partBarrel2 && springLocked) {
                                part_spring.setX(newX + springOffsetX);
                                part_spring.setY(newY + springOffsetY);
                            }

                            // 🔧 Deagle birleşme kontrolleri
                            checkDeagleSlideFrameMerge();
                            checkDeagleBarrelToFrame();
                            checkDeagleSpringToBarrel();
                            checkDeagleMagToFrame();

                            // Frame hareket ederse bağlı deagle parçaları da sürüklensin
                            if (v.getId() == R.id.partDeagleFrame) {
                                if (deagleSlideLocked) {
                                    partDeagleSlide.setX(newX + deagleSlideOffsetX);
                                    partDeagleSlide.setY(newY + deagleSlideOffsetY);
                                }
                                if (deagleBarrelLocked) {
                                    partDeagleBarrel.setX(newX + deagleBarrelOffsetX);
                                    partDeagleBarrel.setY(newY + deagleBarrelOffsetY);
                                }
                                if (deagleMagLocked) {
                                    partDeagleMag.setX(newX + deagleMagOffsetX);
                                    partDeagleMag.setY(newY + deagleMagOffsetY);
                                }
                            }

                            if (v.getId() == R.id.partDeagleBarrel && deagleSpringLocked) {
                                partDeagleSpring.setX(newX + deagleSpringOffsetX);
                                partDeagleSpring.setY(newY + deagleSpringOffsetY);
                            }
                        }

                        return true;


                    case MotionEvent.ACTION_UP:
                        checkGripCylinderMerge();
                        checkCylinderBarrelMerge();
                        checkCylinderHammerMerge();
                        checkIfAssemblyCompleted();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }


    private void checkGripCylinderMerge() {
        int[] gripLoc = new int[2];
        int[] cylLoc = new int[2];

        partGrip.getLocationOnScreen(gripLoc);
        partCylinder.getLocationOnScreen(cylLoc);

        float gripCenterX = gripLoc[0] + partGrip.getWidth() / 1.5f;
        float gripCenterY = gripLoc[1] + partGrip.getHeight() / 2f;

        float cylCenterX = cylLoc[0] + partCylinder.getWidth() / 2f;
        float cylCenterY = cylLoc[1] + partCylinder.getHeight() / 2f;

        double distance = Math.hypot(gripCenterX - cylCenterX, gripCenterY - cylCenterY);

        if (distance < 160) {
            // 🔧 Cihazdan bağımsız sabitleme için offset oranları kullan:
            float offsetX = -0.45f * partGrip.getWidth();  // sola kaydır
            float offsetY = -0.52f * partGrip.getHeight(); // yukarı kaydır

            float targetX = partCylinder.getX() + (partCylinder.getWidth() - partGrip.getWidth()) / 2f + offsetX;
            float targetY = partCylinder.getY() + (partCylinder.getHeight() - partGrip.getHeight()) / 2f + offsetY;

            partGrip.setX(targetX);
            partGrip.setY(targetY);

            gripOffsetX = targetX - partCylinder.getX();
            gripOffsetY = targetY - partCylinder.getY();
            gripLockedToCylinder = true;

            partGrip.setEnabled(false);
            checkIfAssemblyCompleted();

        }
    }




    private void checkCylinderBarrelMerge() {
        int[] barrelLoc = new int[2];
        int[] cylinderLoc = new int[2];

        partBarrel.getLocationOnScreen(barrelLoc);
        partCylinder.getLocationOnScreen(cylinderLoc);

        float barrelCenterX = barrelLoc[0] + partBarrel.getWidth() / 2f;
        float barrelCenterY = barrelLoc[1] + partBarrel.getHeight() / 2f;

        float cylinderCenterX = cylinderLoc[0] + partCylinder.getWidth() / 2f;
        float cylinderCenterY = cylinderLoc[1] + partCylinder.getHeight() / 2f;

        double distance = Math.hypot(barrelCenterX - cylinderCenterX, barrelCenterY - cylinderCenterY);

        if (distance < 100) {
            // 📏 Cihazdan bağımsız oransal offset
            float offsetX = -0.085f * partBarrel.getWidth();     // biraz sola
            float offsetY = +0.07f * partBarrel.getHeight();    // biraz aşağı

            float targetX = partCylinder.getX() + (partCylinder.getWidth() - partBarrel.getWidth()) / 2f + offsetX;
            float targetY = partCylinder.getY() + offsetY;

            partBarrel.setX(targetX);
            partBarrel.setY(targetY);

            // 🔒 Hareket için offset farklarını kaydet
            barrelOffsetX = targetX - partCylinder.getX();
            barrelOffsetY = targetY - partCylinder.getY();
            barrelLockedToCylinder = true;

            partBarrel.setEnabled(false);
            checkIfAssemblyCompleted();

        }
    }


    private void checkCylinderHammerMerge() {
        int[] hammerLoc = new int[2];
        int[] cylinderLoc = new int[2];

        partHammer.getLocationOnScreen(hammerLoc);
        partCylinder.getLocationOnScreen(cylinderLoc);

        float hammerCenterX = hammerLoc[0] + partHammer.getWidth() / 2f;
        float hammerCenterY = hammerLoc[1] + partHammer.getHeight() / 2f;

        float cylinderCenterX = cylinderLoc[0] + partCylinder.getWidth() / 2f;
        float cylinderCenterY = cylinderLoc[1] + partCylinder.getHeight() / 2f;

        double distance = Math.hypot(hammerCenterX - cylinderCenterX, hammerCenterY - cylinderCenterY);

        if (distance < 150) {
            // 📏 Oranlı offset (görsel boyutuna göre ayarlanır)
            float offsetX = 0.47f * partCylinder.getWidth();   // sağa kaydır
            float offsetY = 0.10f * partHammer.getHeight();   // yukarı kaydır

            float targetX = partCylinder.getX() + offsetX;
            float targetY = partCylinder.getY() + offsetY;

            partHammer.setX(targetX);
            partHammer.setY(targetY);

            hammerOffsetX = targetX - partCylinder.getX();
            hammerOffsetY = targetY - partCylinder.getY();
            hammerLockedToCylinder = true;

            partHammer.setEnabled(false);
            checkIfAssemblyCompleted();

        }
    }
    private void checkSlideFrameMerge() {
        int[] slideLoc = new int[2];
        int[] frameLoc = new int[2];

        part_slide.getLocationOnScreen(slideLoc);
        part_frame.getLocationOnScreen(frameLoc);

        float slideCenterX = slideLoc[0] + part_slide.getWidth() / 2f;
        float slideCenterY = slideLoc[1] + part_slide.getHeight() / 2f;

        float frameCenterX = frameLoc[0] + part_frame.getWidth() / 2f;
        float frameCenterY = frameLoc[1] + part_frame.getHeight() / 2f;

        double distance = Math.hypot(slideCenterX - frameCenterX, slideCenterY - frameCenterY);

        if (distance < 150) {
            float offsetX = 0.05f * part_frame.getWidth();
            float offsetY = -0.43f * part_slide.getHeight();

            float targetX = part_frame.getX() + offsetX;
            float targetY = part_frame.getY() + offsetY;

            part_slide.setX(targetX);
            part_slide.setY(targetY);

            slideOffsetX = targetX - part_frame.getX();
            slideOffsetY = targetY - part_frame.getY();

            part_slide.setEnabled(false);
            slideLocked = true;

            checkIfAssemblyCompleted(); // bunu aşağıda güncelleyeceğiz
        }
    }

    private void checkBarrel2ToFrame() {
        int[] barrelLoc = new int[2];
        int[] frameLoc = new int[2];

        part_barrel2.getLocationOnScreen(barrelLoc);
        part_frame.getLocationOnScreen(frameLoc);

        float barrelCenterX = barrelLoc[0] + part_barrel2.getWidth() / 2f;
        float barrelCenterY = barrelLoc[1] + part_barrel2.getHeight() / 2f;

        float frameCenterX = frameLoc[0] + part_frame.getWidth() / 2f;
        float frameCenterY = frameLoc[1] + part_frame.getHeight() / 2f;

        double distance = Math.hypot(barrelCenterX - frameCenterX, barrelCenterY - frameCenterY);

        if (distance < 100) {
            float offsetX = 0.36f * part_frame.getWidth();
            float offsetY = -0.18f * part_frame.getHeight();

            float targetX = part_frame.getX() + offsetX;
            float targetY = part_frame.getY() + offsetY;

            part_barrel2.setX(targetX);
            part_barrel2.setY(targetY);

            barrel2OffsetX = targetX - part_frame.getX();
            barrel2OffsetY = targetY - part_frame.getY();

            part_barrel2.setEnabled(false);
            barrel2Locked = true;

            checkIfAssemblyCompleted();
        }
    }

    private void checkSpringToBarrel2() {
        int[] springLoc = new int[2];
        int[] barrelLoc = new int[2];

        part_spring.getLocationOnScreen(springLoc);
        part_barrel2.getLocationOnScreen(barrelLoc);

        float springCenterX = springLoc[0] + part_spring.getWidth() / 2f;
        float springCenterY = springLoc[1] + part_spring.getHeight() / 2f;

        float barrelCenterX = barrelLoc[0] + part_barrel2.getWidth() / 2f;
        float barrelCenterY = barrelLoc[1] + part_barrel2.getHeight() / 2f;

        double distance = Math.hypot(springCenterX - barrelCenterX, springCenterY - barrelCenterY);

        if (distance < 150) {
            float offsetX = -0.1f * part_spring.getWidth();
            float offsetY = 0.1f * part_spring.getHeight();

            float targetX = part_barrel2.getX() + offsetX;
            float targetY = part_barrel2.getY() + offsetY;

            part_spring.setX(targetX);
            part_spring.setY(targetY);

            // 🔗 Bağlantı offsetlerini kaydet
            springOffsetX = targetX - part_barrel2.getX();
            springOffsetY = targetY - part_barrel2.getY();

            // 🔒 Kilitlenme aktif
            springLocked = true;

            // 🔕 Artık sürüklenemesin
            part_spring.setEnabled(false);

            checkIfAssemblyCompleted();
        }
    }


    private void checkMagToFrame() {
        int[] magLoc = new int[2];
        int[] frameLoc = new int[2];

        part_mag.getLocationOnScreen(magLoc);
        part_frame.getLocationOnScreen(frameLoc);

        float magCenterX = magLoc[0] + part_mag.getWidth() / 2f;
        float magCenterY = magLoc[1] + part_mag.getHeight() / 2f;

        float frameCenterX = frameLoc[0] + part_frame.getWidth() / 2f;
        float frameCenterY = frameLoc[1] + part_frame.getHeight() / 2f;

        double distance = Math.hypot(magCenterX - frameCenterX, magCenterY - frameCenterY);

        if (distance < 100) {
            float offsetX = -0.227f * part_mag.getWidth();
            float offsetY = 0.18f * part_mag.getHeight();

            float targetX = part_frame.getX() + offsetX;
            float targetY = part_frame.getY() + offsetY;

            part_mag.setX(targetX);
            part_mag.setY(targetY);

            magOffsetX = targetX - part_frame.getX();
            magOffsetY = targetY - part_frame.getY();

            part_mag.setEnabled(false);
            magLocked = true;

            checkIfAssemblyCompleted();
        }
    }
    private void checkDeagleSlideFrameMerge() {
        int[] slideLoc = new int[2];
        int[] frameLoc = new int[2];

        partDeagleSlide.getLocationOnScreen(slideLoc);
        partDeagleFrame.getLocationOnScreen(frameLoc);

        float slideCenterX = slideLoc[0] + partDeagleSlide.getWidth() / 2f;
        float slideCenterY = slideLoc[1] + partDeagleSlide.getHeight() / 2f;

        float frameCenterX = frameLoc[0] + partDeagleFrame.getWidth() / 2f;
        float frameCenterY = frameLoc[1] + partDeagleFrame.getHeight() / 2f;

        double distance = Math.hypot(slideCenterX - frameCenterX, slideCenterY - frameCenterY);

        if (distance < 150) {
            float offsetX = 0.05f * partDeagleFrame.getWidth();
            float offsetY = -0.43f * partDeagleSlide.getHeight();

            float targetX = partDeagleFrame.getX() + offsetX;
            float targetY = partDeagleFrame.getY() + offsetY;

            partDeagleSlide.setX(targetX);
            partDeagleSlide.setY(targetY);

            deagleSlideOffsetX = targetX - partDeagleFrame.getX();
            deagleSlideOffsetY = targetY - partDeagleFrame.getY();

            partDeagleSlide.setEnabled(false);
            deagleSlideLocked = true;

            checkIfAssemblyCompleted();
        }
    }
    private void checkDeagleBarrelToFrame() {
        int[] barrelLoc = new int[2];
        int[] frameLoc = new int[2];

        partDeagleBarrel.getLocationOnScreen(barrelLoc);
        partDeagleFrame.getLocationOnScreen(frameLoc);

        float barrelCenterX = barrelLoc[0] + partDeagleBarrel.getWidth() / 2f;
        float barrelCenterY = barrelLoc[1] + partDeagleBarrel.getHeight() / 2f;

        float frameCenterX = frameLoc[0] + partDeagleFrame.getWidth() / 2f;
        float frameCenterY = frameLoc[1] + partDeagleFrame.getHeight() / 2f;

        double distance = Math.hypot(barrelCenterX - frameCenterX, barrelCenterY - frameCenterY);

        if (distance < 150) {
            float offsetX = 0.36f * partDeagleFrame.getWidth();
            float offsetY = -0.18f * partDeagleFrame.getHeight();

            float targetX = partDeagleFrame.getX() + offsetX;
            float targetY = partDeagleFrame.getY() + offsetY;

            partDeagleBarrel.setX(targetX);
            partDeagleBarrel.setY(targetY);

            deagleBarrelOffsetX = targetX - partDeagleFrame.getX();
            deagleBarrelOffsetY = targetY - partDeagleFrame.getY();

            partDeagleBarrel.setEnabled(false);
            deagleBarrelLocked = true;

            checkIfAssemblyCompleted(); // Tüm parçalar tamamlandıysa kontrol et
        }
    }
    private void checkDeagleSpringToBarrel() {
        int[] springLoc = new int[2];
        int[] barrelLoc = new int[2];

        partDeagleSpring.getLocationOnScreen(springLoc);
        partDeagleBarrel.getLocationOnScreen(barrelLoc);

        float springCenterX = springLoc[0] + partDeagleSpring.getWidth() / 2f;
        float springCenterY = springLoc[1] + partDeagleSpring.getHeight() / 2f;

        float barrelCenterX = barrelLoc[0] + partDeagleBarrel.getWidth() / 2f;
        float barrelCenterY = barrelLoc[1] + partDeagleBarrel.getHeight() / 2f;

        double distance = Math.hypot(springCenterX - barrelCenterX, springCenterY - barrelCenterY);

        if (distance < 150) {
            float offsetX = -0.1f * partDeagleSpring.getWidth();
            float offsetY = 0.1f * partDeagleSpring.getHeight();

            float targetX = partDeagleBarrel.getX() + offsetX;
            float targetY = partDeagleBarrel.getY() + offsetY;

            partDeagleSpring.setX(targetX);
            partDeagleSpring.setY(targetY);

            deagleSpringOffsetX = targetX - partDeagleBarrel.getX();
            deagleSpringOffsetY = targetY - partDeagleBarrel.getY();

            partDeagleSpring.setEnabled(false);
            deagleSpringLocked = true;

            checkIfAssemblyCompleted(); // Parçalar tamamsa tetikler
        }
    }

    private void checkDeagleMagToFrame() {
        int[] magLoc = new int[2];
        int[] frameLoc = new int[2];

        partDeagleMag.getLocationOnScreen(magLoc);
        partDeagleFrame.getLocationOnScreen(frameLoc);

        float magCenterX = magLoc[0] + partDeagleMag.getWidth() / 2f;
        float magCenterY = magLoc[1] + partDeagleMag.getHeight() / 2f;

        float frameCenterX = frameLoc[0] + partDeagleFrame.getWidth() / 2f;
        float frameCenterY = frameLoc[1] + partDeagleFrame.getHeight() / 2f;

        double distance = Math.hypot(magCenterX - frameCenterX, magCenterY - frameCenterY);

        if (distance < 100) {
            float offsetX = -0.227f * partDeagleMag.getWidth();
            float offsetY = 0.18f * partDeagleMag.getHeight();

            float targetX = partDeagleFrame.getX() + offsetX;
            float targetY = partDeagleFrame.getY() + offsetY;

            partDeagleMag.setX(targetX);
            partDeagleMag.setY(targetY);

            deagleMagOffsetX = targetX - partDeagleFrame.getX();
            deagleMagOffsetY = targetY - partDeagleFrame.getY();

            partDeagleMag.setEnabled(false);
            deagleMagLocked = true;

            checkIfAssemblyCompleted(); // Genel tamamlama kontrolü
        }
    }



    private void checkIfAssemblyCompleted() {
        if (currentDay == 1 &&
                barrelLockedToCylinder &&
                gripLockedToCylinder &&
                hammerLockedToCylinder) {

            completionText.setVisibility(View.VISIBLE);
            nextDayButton.setVisibility(View.VISIBLE);
            dayTimerText.setVisibility(View.GONE);

            if (countDownTimer != null) countDownTimer.cancel();
            isCompleted = true;
        }

        if (currentDay == 2 &&
                slideLocked &&
                barrel2Locked &&
                springLocked &&
                magLocked &&
                frameLocked) {

            completionText.setVisibility(View.VISIBLE);
            nextDayButton.setVisibility(View.VISIBLE);
            dayTimerText.setVisibility(View.GONE);

            if (countDownTimer != null) countDownTimer.cancel();
            isCompleted = true;
        }
    }


    private void checkIfCompleted() {
        if (gripLockedToCylinder && barrelLockedToCylinder && hammerLockedToCylinder && !isCompleted) {
            completionText.setVisibility(View.VISIBLE);
            nextDayButton.setVisibility(View.VISIBLE);
            dayTimerText.setVisibility(View.GONE); // Sayaç gizlensin

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            isCompleted = true;
        }
    }

    private void checkIffCompleted() {
        if (slideLocked  && barrel2Locked && springLocked && magLocked && !isCompleted) {
            completionText.setVisibility(View.VISIBLE);
            nextDayButton.setVisibility(View.VISIBLE);
            dayTimerText.setVisibility(View.GONE); // Sayaç gizlensin

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            isCompleted = true;
        }
    }



    private void startCountdown(int seconds) {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // eski sayaç varsa iptal et
        }

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

                    // Parçaları devre dışı bırak
                    partGrip.setEnabled(false);
                    partBarrel.setEnabled(false);
                    partCylinder.setEnabled(false);
                    partHammer.setEnabled(false);
                } else {
                    dayTimerText.setText(""); // Silah tamamlandıysa sayaç boş kalsın
                }
            }

        };

        countDownTimer.start();
    }
    private void randomize(View view, float screenWidth, float screenHeight) {
        Random random = new Random();

        float randomX = 0.1f * screenWidth + random.nextFloat() * 0.8f * screenWidth;
        float randomY = 0.1f * screenHeight + random.nextFloat() * 0.8f * screenHeight;

        view.setX(randomX);
        view.setY(randomY);
    }


}
