package com.example.budgetmanagerexpensetracker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;


// Instances of this class are fragments representing a single
// object in our collection.
public class StatsFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stats_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Sample data for the pie chart (you should replace this with your actual data)
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(25f, "Deposits"));
        entries.add(new PieEntry(35f, "Withdrawals"));

        PieDataSet dataSet = new PieDataSet(entries, "Transactions");
        dataSet.setColors(Color.RED, Color.GREEN); // Set colors for the pie chart slices

        PieData pieData = new PieData(dataSet);

        PieChart pieChart = view.findViewById(R.id.pieChart);
        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setDescription(null); // Remove the description label
        pieChart.invalidate(); // Refresh the chart
    }
}
