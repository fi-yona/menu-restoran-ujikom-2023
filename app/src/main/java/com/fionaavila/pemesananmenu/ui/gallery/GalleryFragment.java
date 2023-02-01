package com.fionaavila.pemesananmenu.ui.gallery;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fionaavila.pemesananmenu.R;
import com.fionaavila.pemesananmenu.databinding.FragmentGalleryBinding;

import java.sql.Time;
import java.util.Calendar;
import java.util.Locale;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    private EditText etJam, etTanggal;
    private ImageButton button_jam, button_tanggal;
    private int jam, menit, tahun, bulan, hari;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        button_tanggal = root.findViewById(R.id.pilih_tanggal);
        etTanggal = root.findViewById(R.id.isi_tanggal);

        button_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                tahun = calendar.get(Calendar.YEAR);
                bulan = calendar.get(Calendar.MONTH);
                hari = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogD = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog, dateSetListener, tahun, bulan, hari);

                dialogD.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                bulan = bulan +1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy" + hari + "/" + bulan + "/" + tahun);

                String date = hari + "/" + bulan + "/" + tahun;
                etTanggal.setText(date);
            }
        };

        button_jam = root.findViewById(R.id.pilih_jam);
        etJam = root.findViewById(R.id.isi_jam);

        button_jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                jam = calendar.get(Calendar.HOUR_OF_DAY);
                menit = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialogT;
                dialogT = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        jam = hourOfDay;
                        menit = minute;

                        if(jam <= 12){
                            etJam.setText(String.format(Locale.getDefault(), "%d:%d am", jam, menit));
                        }else{
                            etJam.setText(String.format(Locale.getDefault(), "%d:%d pm", jam, menit));
                        }
                    }
                }, jam, menit, true);
                dialogT.show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}