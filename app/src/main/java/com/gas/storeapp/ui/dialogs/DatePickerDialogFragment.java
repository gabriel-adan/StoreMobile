package com.gas.storeapp.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private View text;

    public DatePickerDialogFragment(View text) {
        this.text = text;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String d = null, m = null;
        if (day < 10)
            d = "0" + day;
        else
            d = day + "";

        month++;
        if (month < 10)
            m = "0" + month;
        else
            m = month + "";
        String date = year + "/" + m + "/" + d;
        if (text instanceof TextView)
            ((TextView) text).setText(date);
        if (text instanceof EditText)
            ((EditText) text).setText(date);
    }
}
