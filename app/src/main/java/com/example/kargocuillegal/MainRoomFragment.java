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

    private int currentDay = 1; // Başlangıçta Gün 1
    private boolean isCompleted = false;
    private TextView dayTimerText;
    private CountDownTimer countDownTimer;
    private TextView dayText;
    private boolean isTimeOver = false;
    private ImageButton orderBoardButton;
    private TextView orderText;
    private ImageView exampleGunImage;



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

            // ✔ Arayüz sıfırla
            nextDayButton.setVisibility(View.GONE);
            completionText.setVisibility(View.GONE);
            isCompleted = false;

            // Sayaç yeniden başlasın
            startCountdown(15);

            // ✔ Parçaları tekrar görünür ve sürüklenebilir yap
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

            // ✔ Konumlarını sıfırla (ilk konuma al – örnek değerler)
// Önce ekran boyutlarını al:
            DisplayMetrics metrics = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            float screenWidth = metrics.widthPixels;
            float screenHeight = metrics.heightPixels;

// Sonra parçaları oranla konumlandır:
            partGrip.setX(0.10f * screenWidth); partGrip.setY(0.08f * screenHeight);
            partCylinder.setX(0.30f * screenWidth); partCylinder.setY(0.08f * screenHeight);
            partBarrel.setX(0.50f * screenWidth); partBarrel.setY(0.08f * screenHeight);
            partHammer.setX(0.70f * screenWidth); partHammer.setY(0.08f * screenHeight);


            // ✔ Kilit durumlarını sıfırla
            gripLockedToCylinder = false;
            barrelLockedToCylinder = false;
            hammerLockedToCylinder = false;

            // ✔ Gün metnini güncelle
            TextView dayText = requireView().findViewById(R.id.dayText);
            dayText.setText("GÜN " + currentDay);

            Toast.makeText(getContext(), "GÜN " + currentDay + " başladı!", Toast.LENGTH_SHORT).show();
        });

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
                if (isTimeOver) return false; // süre bittiyse hareket ettirme
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

                        // Eğer cylinder hareket ediyorsa ve barrel ona kilitliyse birlikte hareket etsin
                        // Eğer cylinder hareket ediyorsa ve barrel ona kilitliyse
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



                        return true;

                    case MotionEvent.ACTION_UP:
                        // Parmağı bıraktığında kontrol et
                        checkGripCylinderMerge();
                        checkCylinderBarrelMerge();
                        checkCylinderHammerMerge();
                        checkIfCompleted();
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

        float gripCenterX = gripLoc[0] + partGrip.getWidth() / 2f;
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

    private void checkIfAssemblyCompleted() {
        if (barrelLockedToCylinder && gripLockedToCylinder && hammerLockedToCylinder) {
            completionText.setVisibility(View.VISIBLE);
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
}
