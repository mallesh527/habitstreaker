package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView textView = view.findViewById(R.id.home_text);
        textView.setText("Today");

        ImageButton calButton = view.findViewById(R.id.cal_image);
        calButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            DateTimePicker.DateTimePickerFragment timePickerFragment = new DateTimePicker.DateTimePickerFragment();
            timePickerFragment.show(fragmentManager, "timePicker");
        });
        // Handle add button click
        ImageButton plusButton = view.findViewById(R.id.add_button_image);
        plusButton.setOnClickListener(v -> {
            // Navigate to AddHabit activity
            Intent intent = new Intent(requireActivity(), AddHabit.class);
            startActivity(intent);
        });

        return view;
    }
}