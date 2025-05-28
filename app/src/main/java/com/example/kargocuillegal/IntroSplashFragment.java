package com.example.kargocuillegal;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class IntroSplashFragment extends Fragment {

    private TextView splashText;
    private int clickStep = 0;

    private final String introText = "Gizli işler yapan bir kargo şirketindesiniz.\n\n" +
            "Günler geçtikçe artan siparişler, kısıtlı süre ve karmaşık parçalarla dolu bir masa...\n\n" +
            "Her gün size verilen süre içerisinde\n" +
            "doğru silah parçalarını doğru yerlere sürükleyip\n" +
            "eksiksiz bir şekilde birleştirin.\n\n" +
            "Hızlı olun, dikkatli olun.\n" +
            "Siparişler beklemez.";

    private final String warningText = "!WARNING!\n\n" +
            "Bu oyun tamamen hayal ürünüdür.\n" +
            "Gerçek kişi, kurum veya olaylarla herhangi bir ilgisi yoktur.\n" +
            "İçeriğinde silah montajı görselleri olsa da,\n" +
            "amacı yalnızca bulmaca tarzı bir kurgu sunmaktır.";

    public IntroSplashFragment() {
        // Boş constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_splash, container, false);

        splashText = view.findViewById(R.id.splashText);
        splashText.setText(introText);  // İlk metin gösterilir

        view.setOnClickListener(v -> {
            clickStep++;

            if (clickStep == 1) {
                // !WARNING! yazısını ve uyarı metnini kırmızı olarak göster
                splashText.setTextColor(Color.RED);
                splashText.setText(warningText);
            } else if (clickStep == 2) {
                // IntroFragment’a geç
                Navigation.findNavController(view).navigate(R.id.action_introSplashFragment_to_introFragment);
            }
        });

        return view;
    }
}
