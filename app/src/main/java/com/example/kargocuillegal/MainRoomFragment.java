package com.example.kargocuillegal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainRoomFragment extends Fragment {

    private ImageView partGrip, partBarrel, partCylinder, partHammer;
    private boolean barrelLockedToCylinder = false;
    private float barrelOffsetX = 0;  // Barrel'ın cylinder'a göre X farkı
    private float barrelOffsetY = 0;  // Barrel'ın cylinder'a göre Y farkı

    //yeni eklendi
    private boolean gripLockedToCylinder = false;
    private float gripOffsetX = 0;
    private float gripOffsetY = 0;

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
    }

    // Sürükle-bırak fonksiyonu + birleşme kontrolü
    private void makeDraggable(ImageView imageView) {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            float dX = 0, dY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
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

                        // Eğer cylinder hareket ediyorsa ve grip ona kilitliyse
                        if (v.getId() == R.id.partCylinder && gripLockedToCylinder) {
                            partGrip.setX(newX + gripOffsetX);
                            partGrip.setY(newY + gripOffsetY);
                        }


                        return true;

                    case MotionEvent.ACTION_UP:
                        // Parmağı bıraktığında kontrol et
                        checkGripCylinderMerge();
                        checkCylinderBarrelMerge();
                        checkCylinderHammerMerge();
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

        // 🔧 Birleşme mesafesini daha hassas yap (150 çok fazlaydı)
        if (distance < 150) {
            // 🔧 Konum düzeltme (senin verdiğin değerlere göre):
            float offsetX = -150f;
            float offsetY = -105f;

            float targetX = partCylinder.getX() + (partCylinder.getWidth() - partGrip.getWidth()) / 2f + offsetX;
            float targetY = partCylinder.getY() + partCylinder.getHeight() / 2f - partGrip.getHeight() / 2f + offsetY;

            partGrip.setX(targetX);
            partGrip.setY(targetY);

            // 🔒 Kilitleme ve birlikte hareket için offsetleri kaydet
            gripOffsetX = targetX - partCylinder.getX();
            gripOffsetY = targetY - partCylinder.getY();
            gripLockedToCylinder = true;

            partGrip.setEnabled(false);
        }
    }



    private void checkCylinderBarrelMerge() {
        float bx = partBarrel.getX() + partBarrel.getWidth() / 2f;
        float by = partBarrel.getY() + partBarrel.getHeight() / 2f;

        float cx = partCylinder.getX() + partCylinder.getWidth() / 2f;
        float cy = partCylinder.getY() + partCylinder.getHeight() / 2f;

        double distance = Math.hypot(bx - cx, by - cy);

        if (distance < 100) {
            float targetX = partCylinder.getX() + (partCylinder.getWidth() - partBarrel.getWidth()) / 2f - 14;
            float targetY = partCylinder.getY() + 12;

            partBarrel.setX(targetX);
            partBarrel.setY(targetY);

            // Farkı kaydet
            barrelOffsetX = partBarrel.getX() - partCylinder.getX();
            barrelOffsetY = partBarrel.getY() - partCylinder.getY();

            partBarrel.setEnabled(false);
            barrelLockedToCylinder = true;
        }
    }

    private void checkCylinderHammerMerge() {
        float hx = partHammer.getX();
        float hy = partHammer.getY();

        float cx = partCylinder.getX() + partCylinder.getWidth();
        float cy = partCylinder.getY();

        double distance = Math.hypot(hx - cx, hy - cy);

        if (distance < 80) {
            partHammer.setX(cx - partHammer.getWidth() / 2f);
            partHammer.setY(cy - partHammer.getHeight() / 2f);
            partHammer.setEnabled(false);
        }
    }
}
