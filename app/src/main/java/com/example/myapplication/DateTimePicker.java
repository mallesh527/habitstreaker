package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DateTimePicker {
    private static final String TAG = "DateTimePicker";
    private static final String PREFS_NAME = "MyAppPreferences";

    public static class DateTimePickerFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Context context = getContext();
            if (context == null) {
                Log.e(TAG, "Context is null. Fragment might not be attached to an activity.");
                return super.onCreateDialog(savedInstanceState);
            }

            try {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                return new DatePickerDialog(
                        context,
                        (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                            new TimePickerDialog(
                                    context,
                                    (timePicker, hourOfDay, selectedMinute) -> {
                                        saveDateTime(
                                                context,
                                                selectedYear,
                                                selectedMonth,
                                                selectedDay,
                                                hourOfDay,
                                                selectedMinute
                                        );
                                    },
                                    hour,
                                    minute,
                                    android.text.format.DateFormat.is24HourFormat(context)
                            ).show();
                        },
                        year, month, day
                );
            } catch (Exception e) {
                Log.e(TAG, "Exception in onCreateDialog", e);
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return super.onCreateDialog(savedInstanceState);
            }
        }


        private void saveDateTime(Context context, int year, int month, int day, int hourOfDay, int minute) {
            try {
                // Null check
                if (context == null) {
                    Log.e(TAG, "Context is null");
                    return;
                }

                // Get SharedPreferences
                SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                // Create editor
                SharedPreferences.Editor editor = sharedPref.edit();

                // Put values
                editor.putInt("selected_year", year);
                editor.putInt("selected_month", month);
                editor.putInt("selected_day", day);
                editor.putInt("selected_hour", hourOfDay);
                editor.putInt("selected_minute", minute);

                // Commit changes
                boolean success = editor.commit();

                // Log result and show toast
                if (success) {
                    Log.d(TAG, "DateTime saved successfully");
                    Toast.makeText(context, "Date and Time Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Failed to save DateTime");
                    Toast.makeText(context, "Failed to save Date and Time", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception in saveDateTime", e);
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}