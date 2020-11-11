package com.example.zulfikaranshari.rentku;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateTimePickers extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // menggunakan tanggal saat inisebgai default pada saat menunjukkan fragmen.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // mengembalikan fragmen
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }



    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        halamanUtama activity = (halamanUtama) getActivity();
        // mengaktifkan method pada aktivitas main untyuk menetapkan nilai jam.
        activity.processDatePickerResult(i, i1, i2);
    }
}


