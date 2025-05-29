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
//import android.media.MediaPlayer;



public class MainRoomFragment extends Fragment {

    private ImageView partGrip, partBarrel, partCylinder, partHammer;
    private ImageView part_slide, part_spring, part_barrel2, part_frame, part_mag;

    private boolean barrelLockedToCylinder = false;
    private float barrelOffsetX = 0;
    private float barrelOffsetY = 0;

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

    private boolean springLockedToBarrel2 = false;
    private float springToBarrel2OffsetX = 0;
    private float springToBarrel2OffsetY = 0;

    private boolean magLocked = false;
    private float magOffsetX = 0, magOffsetY = 0;

    private boolean frameLocked = false;

    private boolean deagleSlideLocked = false;
    private float deagleSlideOffsetX = 0, deagleSlideOffsetY = 0;

    private boolean deagleBarrelLocked = false;
    private float deagleBarrelOffsetX = 0, deagleBarrelOffsetY = 0;

    private boolean deagleSpringLocked = false;
    private float deagleSpringOffsetX = 0, deagleSpringOffsetY = 0;

    private boolean deagleSpringLockedToBarrel = false;
    private float deagleSpringToBarrelOffsetX = 0;
    private float deagleSpringToBarrelOffsetY = 0;

    private boolean deagleMagLocked = false;
    private float deagleMagOffsetX = 0, deagleMagOffsetY = 0;

    private int currentDay = 1;
    private boolean isCompleted = false;
    private TextView dayTimerText;
    private CountDownTimer countDownTimer;
    private TextView dayText;
    private boolean isTimeOver = false;

    private ImageButton orderBoardButton;
    private TextView orderText;
    private ImageView exampleGunImage;

    private ImageView partDeagleFrame, partDeagleSlide, partDeagleBarrel, partDeagleSpring, partDeagleMag;

    // Gün 3: USP parçaları
    private ImageView partUDPust, partUDPgovde, partUDPsarjor, partUDPuc, partUDPyay;

    // GOVDE'ye bağlananlar
    private boolean isUstLockedToGovde = false;
    private float ustOffsetX = 0, ustOffsetY = 0;

    private boolean isSarjorLockedToGovde = false;
    private float sarjorOffsetX = 0, sarjorOffsetY = 0;

    // UST'ye bağlananlar
    private boolean isUcLockedToUst = false;
    private float ucOffsetX = 0, ucOffsetY = 0;

    private boolean isYayLockedToUst = false;
    private float yayUstOffsetX = 0, yayUstOffsetY = 0;

    // UC'ye bağlananlar
    private boolean isYayInsideUc = false;
    private float yayUcOffsetX = 0, yayUcOffsetY = 0;

    private boolean isUcLockedToGovde = false;
    private float ucGovdeOffsetX = 0, ucGovdeOffsetY = 0;


    private boolean isNear(View part1, View part2, float thresholdPx) {
        float dx = Math.abs(part1.getX() - part2.getX());
        float dy = Math.abs(part1.getY() - part2.getY());
        return dx < thresholdPx && dy < thresholdPx;
    }


    public MainRoomFragment() {
        // Boş constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Müzik sesini kıs
        musicmanager.setVolume(0.1f);  // veya 0.2f gibi

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

        makeDraggable(partGrip);
        makeDraggable(partBarrel);
        makeDraggable(partCylinder);
        makeDraggable(partHammer);

        completionText = view.findViewById(R.id.completionText);
        nextDayButton = view.findViewById(R.id.nextDayButton);
        orderBoardButton = view.findViewById(R.id.orderBoardButton);
        exampleGunImage = view.findViewById(R.id.exampleGunImage);
        dayTimerText = view.findViewById(R.id.dayTimerText);
        dayText = view.findViewById(R.id.dayText);

        partUDPust = view.findViewById(R.id.partUDPust);
        partUDPgovde = view.findViewById(R.id.partUDPgovde);
        partUDPsarjor = view.findViewById(R.id.partUDPsarjor);
        partUDPuc = view.findViewById(R.id.partUDPuc);
        partUDPyay = view.findViewById(R.id.partUDPyay);


        // Sayaç başlat (güne göre süre belirle)
        if (currentDay == 1) {
            startCountdown(15);
        } else if (currentDay == 2) {
            startCountdown(21);
        } else if (currentDay == 3) {
            startCountdown(30);
        }

        // Sarı buton sadece gün 3'te görünür
        orderBoardButton.setVisibility(currentDay == 3 ? View.VISIBLE : View.GONE);

        orderBoardButton.setOnClickListener(v -> {
            if (exampleGunImage.getVisibility() == View.VISIBLE) {
                exampleGunImage.setVisibility(View.GONE);
            } else {
                exampleGunImage.setVisibility(View.VISIBLE);
            }
        });

        nextDayButton.setOnClickListener(v -> {
            currentDay++;
            nextDayButton.setVisibility(View.GONE);
            completionText.setVisibility(View.GONE);
            isCompleted = false;

            if (currentDay == 1) {
                startCountdown(15);
            } else if (currentDay == 2) {
                startCountdown(25);
            } else if (currentDay == 3) {
                startCountdown(12);
            }

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
            } else if (currentDay == 2) {
                partGrip.setVisibility(View.GONE);
                partBarrel.setVisibility(View.GONE);
                partHammer.setVisibility(View.GONE);
                partCylinder.setVisibility(View.GONE);

                part_slide.setVisibility(View.VISIBLE);
                part_spring.setVisibility(View.VISIBLE);
                part_barrel2.setVisibility(View.VISIBLE);
                part_frame.setVisibility(View.VISIBLE);
                part_mag.setVisibility(View.VISIBLE);

                partDeagleFrame.setVisibility(View.VISIBLE);
                partDeagleSlide.setVisibility(View.VISIBLE);
                partDeagleBarrel.setVisibility(View.VISIBLE);
                partDeagleSpring.setVisibility(View.VISIBLE);
                partDeagleMag.setVisibility(View.VISIBLE);

                // GLOCK parçalarını konumlandır
                part_slide.setX(0.07f * screenWidth);
                part_slide.setY(0.05f * screenHeight);
                part_spring.setX(0.38f * screenWidth);
                part_spring.setY(0.11f * screenHeight);
                part_barrel2.setX(0.23f * screenWidth);
                part_barrel2.setY(0.77f * screenHeight);
                part_frame.setX(0.78f * screenWidth);
                part_frame.setY(0.67f * screenHeight);
                part_mag.setX(0.036f * screenWidth);
                part_mag.setY(0.63f * screenHeight);

                // DEAGLE parçalarını konumlandır
                partDeagleSlide.setX(0.5f * screenWidth);
                partDeagleSlide.setY(0.76f * screenHeight);
                partDeagleBarrel.setX(0.7f * screenWidth);
                partDeagleBarrel.setY(0.08f * screenHeight);
                partDeagleSpring.setX(0.9f * screenWidth);
                partDeagleSpring.setY(0.18f * screenHeight);
                partDeagleFrame.setX(0.05f * screenWidth);
                partDeagleFrame.setY(0.3f * screenHeight);
                partDeagleMag.setX(0.88f * screenWidth);
                partDeagleMag.setY(0.36f * screenHeight);

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

                part_frame.bringToFront();
                part_slide.bringToFront();
                partDeagleFrame.bringToFront();
                partDeagleSlide.bringToFront();
            } else if (currentDay == 3) {
                // Eski parçaları gizle
                partGrip.setVisibility(View.GONE);
                partBarrel.setVisibility(View.GONE);
                partHammer.setVisibility(View.GONE);
                partCylinder.setVisibility(View.GONE);

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

                // Oranlı boyutlandırma
                int partSize = (int) (0.16f * screenWidth);

                ImageView[] udpParts = new ImageView[]{
                        partUDPust, partUDPgovde, partUDPsarjor,
                        partUDPuc, partUDPyay
                };

                for (ImageView part : udpParts) {
                    ViewGroup.LayoutParams params = part.getLayoutParams();
                    params.width = partSize;
                    params.height = partSize;
                    part.setLayoutParams(params);
                }

                // Görünür yap
                for (ImageView part : udpParts) {
                    part.setVisibility(View.VISIBLE);
                    makeDraggable(part);
                }
                // Konumlandır
                partUDPust.setX(0.05f * screenWidth);
                partUDPust.setY(0.10f * screenHeight);
                partUDPgovde.setX(0.25f * screenWidth);
                partUDPgovde.setY(0.20f * screenHeight);
                partUDPsarjor.setX(0.45f * screenWidth);
                partUDPsarjor.setY(0.30f * screenHeight);
                partUDPuc.setX(0.65f * screenWidth);
                partUDPuc.setY(0.40f * screenHeight);
                partUDPyay.setX(0.15f * screenWidth);
                partUDPyay.setY(0.55f * screenHeight);

                // Öne al
                partUDPgovde.bringToFront();
                partUDPust.bringToFront();

            } else {
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

                partGrip.setX(0.10f * screenWidth);
                partGrip.setY(0.08f * screenHeight);
                partCylinder.setX(0.30f * screenWidth);
                partCylinder.setY(0.08f * screenHeight);
                partBarrel.setX(0.50f * screenWidth);
                partBarrel.setY(0.08f * screenHeight);
                partHammer.setX(0.70f * screenWidth);
                partHammer.setY(0.08f * screenHeight);
            }

            gripLockedToCylinder = false;
            barrelLockedToCylinder = false;
            hammerLockedToCylinder = false;

            dayText.setText("GÜN " + currentDay);
            Toast.makeText(getContext(), "GÜN " + currentDay + " başladı!", Toast.LENGTH_SHORT).show();
        });


    }


    // Sürükle-bırak fonksiyonu + birleşme kontrolü
    private void makeDraggable(ImageView imageView) {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            float dX = 0, dY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isTimeOver) return false;

                if (currentDay == 2) {
                    if (v == part_slide && slideLocked) return false;
                    if (v == part_barrel2 && barrel2Locked) return false;
                    if (v == part_spring && springLocked) return false;
                    if (v == part_mag && magLocked) return false;
                    if (v == partDeagleSlide && deagleSlideLocked) return false;
                    if (v == partDeagleBarrel && deagleBarrelLocked) return false;
                    if (v == partDeagleSpring && deagleSpringLocked) return false;
                    if (v == partDeagleMag && deagleMagLocked) return false;
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

                        // Revolver bağlı hareket
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

                        // Gün 2 - Glock & Deagle bağlı hareket
                        if (currentDay == 2) {
                            if (v.getId() == R.id.partFrame) {
                                if (slideLocked) {
                                    part_slide.setX(newX + slideOffsetX);
                                    part_slide.setY(newY + slideOffsetY);
                                }
                                if (barrel2Locked) {
                                    part_barrel2.setX(newX + barrel2OffsetX);
                                    part_barrel2.setY(newY + barrel2OffsetY);
                                }
                                if (springLocked) {
                                    part_spring.setX(newX + springOffsetX);
                                    part_spring.setY(newY + springOffsetY);
                                }
                                if (magLocked) {
                                    part_mag.setX(newX + magOffsetX);
                                    part_mag.setY(newY + magOffsetY);
                                }
                            }

                            if (v.getId() == R.id.partBarrel2 && springLockedToBarrel2) {
                                part_spring.setX(newX + springToBarrel2OffsetX);
                                part_spring.setY(newY + springToBarrel2OffsetY);
                            }

                            if (v.getId() == R.id.partDeagleBarrel && deagleSpringLockedToBarrel) {
                                partDeagleSpring.setX(newX + deagleSpringToBarrelOffsetX);
                                partDeagleSpring.setY(newY + deagleSpringToBarrelOffsetY);
                            }

                            if (v.getId() == R.id.partDeagleFrame) {
                                if (deagleSlideLocked) {
                                    partDeagleSlide.setX(newX + deagleSlideOffsetX);
                                    partDeagleSlide.setY(newY + deagleSlideOffsetY);
                                }
                                if (deagleBarrelLocked) {
                                    partDeagleBarrel.setX(newX + deagleBarrelOffsetX);
                                    partDeagleBarrel.setY(newY + deagleBarrelOffsetY);
                                }
                                if (deagleSpringLocked) {
                                    partDeagleSpring.setX(newX + deagleSpringOffsetX);
                                    partDeagleSpring.setY(newY + deagleSpringOffsetY);
                                }
                                if (deagleMagLocked) {
                                    partDeagleMag.setX(newX + deagleMagOffsetX);
                                    partDeagleMag.setY(newY + deagleMagOffsetY);
                                }
                            }
                        }

                        // Gün 3 - USP parçaları bağlı hareket
                        if (currentDay == 3) {
                            if (v.getId() == R.id.partUDPgovde) {
                                if (isUstLockedToGovde) {
                                    partUDPust.setX(newX + ustOffsetX);
                                    partUDPust.setY(newY + ustOffsetY);

                                    if (isYayLockedToUst) {
                                        partUDPyay.setX(partUDPust.getX() + yayUstOffsetX);
                                        partUDPyay.setY(partUDPust.getY() + yayUstOffsetY);
                                    }
                                }

                                if (isUcLockedToGovde) {
                                    partUDPuc.setX(newX + ucGovdeOffsetX);
                                    partUDPuc.setY(newY + ucGovdeOffsetY);

                                    if (isYayInsideUc) {
                                        partUDPyay.setX(partUDPuc.getX() + yayUcOffsetX);
                                        partUDPyay.setY(partUDPuc.getY() + yayUcOffsetY);
                                    }
                                }

                                if (isSarjorLockedToGovde) {
                                    partUDPsarjor.setX(newX + sarjorOffsetX);
                                    partUDPsarjor.setY(newY + sarjorOffsetY);
                                }
                            }

                            if (v.getId() == R.id.partUDPust) {
                                if (isYayLockedToUst) {
                                    partUDPyay.setX(newX + yayUstOffsetX);
                                    partUDPyay.setY(newY + yayUstOffsetY);
                                }
                            }

                            if (v.getId() == R.id.partUDPuc && isYayInsideUc) {
                                partUDPyay.setX(newX + yayUcOffsetX);
                                partUDPyay.setY(newY + yayUcOffsetY);
                            }
                        }

                        return true;

                    case MotionEvent.ACTION_UP:
                        checkGripCylinderMerge();
                        checkCylinderBarrelMerge();
                        checkCylinderHammerMerge();

                        if (currentDay == 2) {
                            checkSlideFrameMerge();
                            checkBarrel2ToFrame();
                            checkSpringToBarrel2();
                            checkMagToFrame();
                            checkDeagleSlideFrameMerge();
                            checkDeagleBarrelToFrame();
                            checkDeagleSpringToBarrel();
                            checkDeagleMagToFrame();
                        }

                        if (currentDay == 3) {
                            checkUDPustToGovde();
                            checkUDPucToGovde();
                            checkUDPsarjorToGovde();
                            checkUDPyayToUst();
                        }

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
           // playMergeSound();
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
           // playMergeSound();
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

        if (distance < 100) {
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
           // playMergeSound();
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
          //  playMergeSound();
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
            springToBarrel2OffsetX = targetX - part_barrel2.getX();
            springToBarrel2OffsetY = targetY - part_barrel2.getY();
            springLockedToBarrel2 = true;
           // playMergeSound();
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

        if (distance < 115) {
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
           // playMergeSound();
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

        if (distance < 100) {
            float offsetX = -0.13f * partDeagleFrame.getWidth();
            float offsetY = -0.31f * partDeagleSlide.getHeight();

            float targetX = partDeagleFrame.getX() + offsetX;
            float targetY = partDeagleFrame.getY() + offsetY;

            partDeagleSlide.setX(targetX);
            partDeagleSlide.setY(targetY);

            deagleSlideOffsetX = targetX - partDeagleFrame.getX();
            deagleSlideOffsetY = targetY - partDeagleFrame.getY();

            partDeagleSlide.setEnabled(false);
            deagleSlideLocked = true;
            //playMergeSound();
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

        if (distance < 100) {
            float offsetX = -0.017f * partDeagleFrame.getWidth();
            float offsetY = -0.12f * partDeagleFrame.getHeight();

            float targetX = partDeagleFrame.getX() + offsetX;
            float targetY = partDeagleFrame.getY() + offsetY;

            partDeagleBarrel.setX(targetX);
            partDeagleBarrel.setY(targetY);

            deagleBarrelOffsetX = targetX - partDeagleFrame.getX();
            deagleBarrelOffsetY = targetY - partDeagleFrame.getY();

            partDeagleBarrel.setEnabled(false);
            deagleBarrelLocked = true;
            //playMergeSound();
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

            // 🔒 Kilitleme ve bağlı taşıma için offset'leri kaydet
            deagleSpringToBarrelOffsetX = offsetX;
            deagleSpringToBarrelOffsetY = offsetY;
            deagleSpringLockedToBarrel = true;

            partDeagleSpring.setEnabled(false);
            deagleSpringLocked = true;
           // playMergeSound();
            checkIfAssemblyCompleted();
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
            float offsetX = 0.2f * partDeagleMag.getWidth();
            float offsetY = 0.25f * partDeagleMag.getHeight();

            float targetX = partDeagleFrame.getX() + offsetX;
            float targetY = partDeagleFrame.getY() + offsetY;

            partDeagleMag.setX(targetX);
            partDeagleMag.setY(targetY);

            deagleMagOffsetX = targetX - partDeagleFrame.getX();
            deagleMagOffsetY = targetY - partDeagleFrame.getY();


            partDeagleMag.setEnabled(false);
            deagleMagLocked = true;
           // playMergeSound();
            checkIfAssemblyCompleted(); // Genel tamamlama kontrolü
        }
    }


    private void checkUDPustToGovde() {
        int[] ustLoc = new int[2];
        int[] govdeLoc = new int[2];

        partUDPust.getLocationOnScreen(ustLoc);
        partUDPgovde.getLocationOnScreen(govdeLoc);

        float ustCenterX = ustLoc[0] + partUDPust.getWidth() / 2f;
        float ustCenterY = ustLoc[1] + partUDPust.getHeight() / 2f;

        float govdeCenterX = govdeLoc[0] + partUDPgovde.getWidth() / 2f;
        float govdeCenterY = govdeLoc[1] + partUDPgovde.getHeight() / 2f;

        double distance = Math.hypot(ustCenterX - govdeCenterX, ustCenterY - govdeCenterY);

        if (distance < 130) {
            float offsetX = 0f * partUDPgovde.getWidth();
            float offsetY = -0.26f * partUDPust.getHeight();

            float targetX = partUDPgovde.getX() + offsetX;
            float targetY = partUDPgovde.getY() + offsetY;

            partUDPust.setX(targetX);
            partUDPust.setY(targetY);

            ustOffsetX = offsetX;
            ustOffsetY = offsetY;
            isUstLockedToGovde = true;

            partUDPust.setEnabled(false);
           // playMergeSound();
        }
    }

    private void checkUDPucToGovde() {
        int[] ucLoc = new int[2];
        int[] govdeLoc = new int[2];

        partUDPuc.getLocationOnScreen(ucLoc);
        partUDPgovde.getLocationOnScreen(govdeLoc);

        float ucCenterX = ucLoc[0] + partUDPuc.getWidth() / 2f;
        float ucCenterY = ucLoc[1] + partUDPuc.getHeight() / 2f;

        float govdeCenterX = govdeLoc[0] + partUDPgovde.getWidth() * -0.2f; // daha sola
        float govdeCenterY = govdeLoc[1] + partUDPgovde.getHeight() * 0.20f; // daha yukarı


        double distance = Math.hypot(ucCenterX - govdeCenterX, ucCenterY - govdeCenterY);

        if (distance < 100) {
            float offsetX = -0.94f * partUDPgovde.getWidth();   // sağa kilitle
            float offsetY = -0.29f * partUDPgovde.getHeight();  // yukarı kilitle

            float targetX = partUDPgovde.getX() + offsetX;
            float targetY = partUDPgovde.getY() + offsetY;

            partUDPuc.setX(targetX);
            partUDPuc.setY(targetY);

            ucGovdeOffsetX = offsetX;
            ucGovdeOffsetY = offsetY;
            isUcLockedToGovde = true;

            partUDPuc.setEnabled(false);
            //playMergeSound();
        }
    }


    private void checkUDPsarjorToGovde() {
        int[] sarjorLoc = new int[2];
        int[] govdeLoc = new int[2];

        partUDPsarjor.getLocationOnScreen(sarjorLoc);
        partUDPgovde.getLocationOnScreen(govdeLoc);

        float sarjorCenterX = sarjorLoc[0] + partUDPsarjor.getWidth() / 2f;
        float sarjorCenterY = sarjorLoc[1] + partUDPsarjor.getHeight() / 2f;

        float govdeCenterX = govdeLoc[0] + partUDPgovde.getWidth() / 2f;
        float govdeCenterY = govdeLoc[1] + partUDPgovde.getHeight() / 2f;

        double distance = Math.hypot(sarjorCenterX - govdeCenterX, sarjorCenterY - govdeCenterY);

        if (distance < 130) {
            float offsetX = 0.29f * partUDPsarjor.getWidth();
            float offsetY = 0f * partUDPsarjor.getHeight();

            float targetX = partUDPgovde.getX() + offsetX;
            float targetY = partUDPgovde.getY() + offsetY;

            partUDPsarjor.setX(targetX);
            partUDPsarjor.setY(targetY);

            sarjorOffsetX = offsetX;
            sarjorOffsetY = offsetY;
            isSarjorLockedToGovde = true;

            partUDPsarjor.setEnabled(false);
            //playMergeSound();
        }
    }

    private void checkUDPyayToUst() {
        int[] yayLoc = new int[2];
        int[] ustLoc = new int[2];

        partUDPyay.getLocationOnScreen(yayLoc);
        partUDPust.getLocationOnScreen(ustLoc);

        float yayCenterX = yayLoc[0] + partUDPyay.getWidth() / 2f;
        float yayCenterY = yayLoc[1] + partUDPyay.getHeight() / 2f;

        float ustCenterX = ustLoc[0] + partUDPust.getWidth() / 2f;
        float ustCenterY = ustLoc[1] + partUDPust.getHeight() / 2f;

        double distance = Math.hypot(yayCenterX - ustCenterX, yayCenterY - ustCenterY);

        if (distance < 130) {
            // 🔧 Yay'ı ust'un soluna hizala
            float offsetX = 0.01f * partUDPyay.getWidth(); // yay'ın sağ kenarı ust'un soluna denk gelsin
            float offsetY = 0f * partUDPust.getHeight();

            float targetX = partUDPust.getX() + offsetX;
            float targetY = partUDPust.getY() + offsetY;

            partUDPyay.setX(targetX);
            partUDPyay.setY(targetY);

            yayUstOffsetX = offsetX;
            yayUstOffsetY = offsetY;
            isYayLockedToUst = true;

            partUDPyay.setEnabled(false);
           // playMergeSound();
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
            //playMergeSound();
        }

        if (currentDay == 2 &&
                slideLocked && barrel2Locked && springLocked && magLocked &&
                deagleSlideLocked && deagleBarrelLocked && deagleSpringLocked && deagleMagLocked) {

            completionText.setVisibility(View.VISIBLE);
            nextDayButton.setVisibility(View.VISIBLE);
            dayTimerText.setVisibility(View.GONE);

            if (countDownTimer != null) countDownTimer.cancel();
            isCompleted = true;
          //  playMergeSound();
        }
        if (currentDay == 3 &&
                isUstLockedToGovde &&
                isUcLockedToGovde &&
                isSarjorLockedToGovde &&
                isYayLockedToUst) {

            completionText.setVisibility(View.VISIBLE);
            completionText.setText("Silah başarıyla tamamlandı!");

            nextDayButton.setVisibility(View.VISIBLE);
            nextDayButton.setText("Devamı yakında...");
            nextDayButton.setEnabled(false);          // ❌ Tıklanamaz
            nextDayButton.setAlpha(0.7f);             // (isteğe bağlı) soluk göster

            dayTimerText.setVisibility(View.GONE);

            if (countDownTimer != null) countDownTimer.cancel();
            isCompleted = true;
           // playMergeSound();
        }
    }

    private void startCountdown(int seconds) {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Eski sayaç varsa iptal et
        }

        // Sayaç görünür ve üstte olsun
        dayTimerText.setVisibility(View.VISIBLE);
        dayTimerText.bringToFront();
        dayTimerText.setText("Kalan: " + seconds + " sn");

        countDownTimer = new CountDownTimer(seconds * 1000L, 1000) {
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

                    if (currentDay == 1) {
                        partGrip.setEnabled(false);
                        partBarrel.setEnabled(false);
                        partCylinder.setEnabled(false);
                        partHammer.setEnabled(false);
                    }

                    if (currentDay == 2) {
                        part_slide.setEnabled(false);
                        part_spring.setEnabled(false);
                        part_barrel2.setEnabled(false);
                        part_frame.setEnabled(false);
                        part_mag.setEnabled(false);

                        partDeagleFrame.setEnabled(false);
                        partDeagleSlide.setEnabled(false);
                        partDeagleBarrel.setEnabled(false);
                        partDeagleSpring.setEnabled(false);
                        partDeagleMag.setEnabled(false);
                    }

                    if (currentDay == 3) {
                        // Gün 3’te süre dolunca yapılacak işlem
                        // Örn: yeni parçaları gizle, ekranı dondur, skoru göster vs.
                    }
                } else {
                    // Silah tamamlandıysa sayaç temizlenir
                    dayTimerText.setText("");
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
    //private void playMergeSound() {
    // MediaPlayer mergeSound = MediaPlayer.create(getContext(), R.raw.handgun_clip);
    //mergeSound.setOnCompletionListener(mp -> mp.release());
    //mergeSound.start();
//}
}
