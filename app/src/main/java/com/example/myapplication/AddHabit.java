package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class AddHabit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        // Find the container where buttons will be added
        LinearLayout buttonContainer = findViewById(R.id.button_container);

        // Array of button texts and emojis
        String[] buttonData = {
                "🏃 Exercise",
                "📖 Read",
                "💧 Hydrate",
                "🛏️ Sleep",
                "🧘 Meditate",
                "🍎 Eat Healthy"
        };

        // Create buttons dynamically
        for (String text : buttonData) {
            MaterialButton button = new MaterialButton(this);
            button.setText(text);
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    400
            ));
            button.setTextSize(40);
            button.setPadding(16, 16, 16, 16);
            button.setBackgroundColor(Color.parseColor("#83def2"));

            // Add button to the container
            buttonContainer.addView(button);
        }
    }
}
