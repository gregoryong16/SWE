package com.example.cz2006.ui.fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cz2006.MainActivity;
import com.example.cz2006.R;
import com.example.cz2006.adapters.VersionsAdapter;
import com.example.cz2006.classes.ElectricityData;
import com.example.cz2006.classes.Response;
import com.example.cz2006.classes.Versions;
import com.example.cz2006.classes.WaterData;
import com.example.cz2006.databinding.FragmentWaterBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private SharedViewModel sharedViewModel;
    private FragmentWaterBinding binding;

    private List<WaterData> hourlyWater;
    private List<WaterData> dailyWater;
    private List<WaterData> monthlyWater;

    private List<String> xHourly;
    private List<String> xDaily;
    private List<String> xMonthly;

    private BarChart chart;
    private BarDataSet barDataSet;
    private BarData barData;
    private List<BarEntry> barEntryList = new ArrayList<>();
    int[] color;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        binding = FragmentWaterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        color = new int[] {
                ContextCompat.getColor(getContext(), R.color.lightBlue),
                ContextCompat.getColor(getContext(), R.color.purple),
                ContextCompat.getColor(getContext(), R.color.teal_700),
                ContextCompat.getColor(getContext(), R.color.gray)
        };
        Spinner spinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.duration, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        xHourly = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.hourly)));
        xDaily = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.daily)));
        xMonthly = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.monthly)));
        chart = binding.barChartView;
        configureChart();

        TextView textTotal = binding.textTotal;
        TextView textUsed = binding.textUsed;
        TextView textRemaining = binding.textRemaining;
        TextView textCost = binding.textCost;
        RecyclerView recyclerView = binding.recyclerView;

        sharedViewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                textTotal.setText("$" + (int)(response.getSummary().getWaterCost() + response.getSummary().getElectricityCost()));
                textUsed.setText((int)response.getSummary().getWaterUsage() + "Litres");
                textRemaining.setText((int)response.getSummary().getWaterRemaining() + "Litres");
                textCost.setText("$" + (int)response.getSummary().getWaterCost());

                hourlyWater = response.getHourlyWater();
                dailyWater = response.getDailyWater();
                monthlyWater = response.getMonthlyWater();

                for(WaterData e: hourlyWater) {
                    barEntryList.add(new BarEntry(e.getDate(), new float[]{
                            e.getWashingMachine(), e.getToiletFlush(), e.getShower(), e.getTaps()
                    }));
                }
                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xHourly));
                configureAndUpdate();

                float washingMachineUsage = monthlyWater.get(monthlyWater.size() - 1).getWashingMachine();
                float toiletFlushUsage = monthlyWater.get(monthlyWater.size() - 1).getToiletFlush();
                float showerUsage = monthlyWater.get(monthlyWater.size() - 1).getShower();
                float tapsUsage = monthlyWater.get(monthlyWater.size() - 1).getTaps();
                float rate = (float) 0.00121;

                List<Versions> versionsList = new ArrayList<>();
                versionsList.add(new Versions("Washing Machine", "$" + (int)(washingMachineUsage * rate), (int)washingMachineUsage + " Litres", "Always run your washing machine on a full load.\n\nWater efficient washing machine(5 ticks)."));
                versionsList.add(new Versions("Toilet Flush", "$" + (int)(toiletFlushUsage * rate), (int)toiletFlushUsage + " Litres", "Put a plastic bottle filled with water in your toilet tank to reduce the amount of water used per flush.\n\nUse half flush unless necessary."));
                versionsList.add(new Versions("Shower", "$" + (int)(showerUsage * rate), (int)showerUsage + " Litres", "Take shorter showers.\n\nTurn water off while applying soap."));
                versionsList.add(new Versions("Taps", "$" + (int)(tapsUsage * rate), (int)tapsUsage + " Litres", "Switch the tap off while brushing your teeth, don’t keep the tap running!\n\nRecycle the water used to wash your rice to water your plants!"));
                VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList);
                recyclerView.setAdapter(versionsAdapter);

                if(response.getUserData().getWaterBudget() != 0) {
                    int used = (int) response.getSummary().getWaterUsage();
                    int remaining = (int) response.getSummary().getWaterRemaining();
                    sendNotification(used * 100 / (used + remaining));
                }
            }
        });
        return root;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if(hourlyWater == null)
        return;

        String range = parent.getItemAtPosition(pos).toString();
        barEntryList.clear();
        List<WaterData> waterDataList = null;
        switch(range) {
            case "Hourly":
                waterDataList = hourlyWater;
                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xHourly));
                break;

            case "Daily":
                waterDataList = dailyWater;
                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xDaily));
                break;

            case "Monthly":
                waterDataList = monthlyWater;
                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xMonthly));
                break;
        }
        for(WaterData e: waterDataList)
            barEntryList.add(new BarEntry(e.getDate(), new float[]{
                    e.getWashingMachine(), e.getToiletFlush(), e.getShower(), e.getTaps()
            }));
        configureAndUpdate();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void configureChart() {
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisLeft().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setScaleEnabled(false);

        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisRight().setAxisMinimum(0);
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setLabelCount(6);
    }

    public void configureAndUpdate() {
        barDataSet = new BarDataSet(barEntryList, "");
        barData = new BarData(barDataSet);
        barDataSet.setColors(color);
        barDataSet.setStackLabels(new String[]{"Washing Machine", "Toilet Flush", "Shower", "Taps"});
        barData.setDrawValues(false);
        chart.setData(barData);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    public void sendNotification(int i) {
        if(i < 25 || MainActivity.waterAlert)
            return;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("0", "0", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getActivity(), "0")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Water Limit Alert")
                .setContentText("You have used " + i + "% of your water limit!")
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getActivity());
        notificationManager.notify(0, builder.build());
        MainActivity.waterAlert = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}