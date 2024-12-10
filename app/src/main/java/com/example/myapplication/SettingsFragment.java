package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public class SettingsFragment extends Fragment {
    private static final String PREFS_NAME = "ThemePreferences";
    private static final String THEME_KEY = "selected_theme";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Find the container where buttons will be added
        LinearLayout buttonContainer = view.findViewById(R.id.settings_button_container);

        // Array of button texts
        String[] buttonData = {
                "Language",
                "Subscription",
                "Theme",
                "Backups",
                "Share App",
                "Review App",
                "Contact Developer"
        };

        // Create buttons dynamically
        for (String text : buttonData) {
            MaterialButton button = new MaterialButton(requireContext());
            button.setText(text);
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    200 // Set height as per your need
            ));
            button.setTextSize(18); // Adjust text size
            button.setPadding(16, 16, 16, 16);
            button.setBackgroundColor(Color.parseColor("#83def2")); // Background color in hex

            // Add specific actions for buttons
            if (text.equals("Theme")) {
                button.setOnClickListener(v -> showThemeDialog());
            } else if (text.equals("Share App")) {
                button.setOnClickListener(v -> shareApp());
            }

            // Add button to the container
            buttonContainer.addView(button);
        }

        return view;
    }

    private void showThemeDialog() {
        // Options for theme selection
        String[] themeOptions = {"Dark", "Light", "System Default"};

        // Get current theme preference
        SharedPreferences preferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int currentTheme = preferences.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        // Show dialog
        new AlertDialog.Builder(requireContext())
                .setTitle("Choose Theme")
                .setSingleChoiceItems(themeOptions, getThemeIndex(currentTheme), (dialog, which) -> {
                    // Save and apply the selected theme
                    int selectedTheme = getThemeFromIndex(which);
                    saveThemePreference(selectedTheme);
                    AppCompatDelegate.setDefaultNightMode(selectedTheme);
                    dialog.dismiss();
                })
                .show();
    }

    private int getThemeIndex(int theme) {
        switch (theme) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                return 0;
            case AppCompatDelegate.MODE_NIGHT_NO:
                return 1;
            default:
                return 2;
        }
    }

    private int getThemeFromIndex(int index) {
        switch (index) {
            case 0:
                return AppCompatDelegate.MODE_NIGHT_YES;
            case 1:
                return AppCompatDelegate.MODE_NIGHT_NO;
            default:
                return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        }
    }

    private void saveThemePreference(int theme) {
        SharedPreferences preferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(THEME_KEY, theme);
        editor.apply();
    }

    private void shareApp() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this amazing app: ");
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, "Share App");
        startActivity(shareIntent);
    }
}
