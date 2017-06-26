package lpaa.earound.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private final static String TAG = "DatePickerFragment";

    private EditText day;

    public void setDay(EditText day) {
        this.day = day;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Log.e(TAG, "onDateSet: accedo a picker" );
        String monthEdit;
        if(++month<10) {
            monthEdit = "0"+ month;
        } else {
            monthEdit = String.valueOf(month);
        }
        this.day.setText(year+"-"+monthEdit+"-"+day);
    }
}
