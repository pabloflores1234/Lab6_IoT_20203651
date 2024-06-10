package com.example.lab6_20203651_iot;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab6_20203651_iot.ViewModel.NavigationActivityViewModel;
import com.example.lab6_20203651_iot.items.EgresosIngresosAdapter;
import com.example.lab6_20203651_iot.items.ListElementEgresosIngresos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class Egresos_ingresos extends Fragment {

    private ArrayList<ListElementEgresosIngresos> ingresosList = new ArrayList<>();
    private ArrayList<ListElementEgresosIngresos> egresosList = new ArrayList<>();

    private EgresosIngresosAdapter listEgresosIngresosAdapter;
    private RecyclerView recyclerViewSites;
    private NavigationActivityViewModel navigationActivityViewModel;

    private TabLayout tabLayout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_egresos_ingresos, container, false);
        setHasOptionsMenu(true);
        navigationActivityViewModel = new ViewModelProvider(requireActivity()).get(NavigationActivityViewModel.class);
        initializeViews(view);
        String email = getArguments().getString("email");
        observeViewModel(email);
        return view;
    }

    private void observeViewModel(String email) {
        if (navigationActivityViewModel != null) {
            navigationActivityViewModel.getIngresos().observe(getViewLifecycleOwner(), ingresos -> {
                ingresosList.clear();
                for (ListElementEgresosIngresos ingreso : ingresos) {
                    if (ingreso.getEmail_user().equals(email)) {
                        ingresosList.add(ingreso);
                    }
                }
                if (tabLayout != null && tabLayout.getSelectedTabPosition() == 0) {
                    listEgresosIngresosAdapter.setItems(ingresosList);
                    listEgresosIngresosAdapter.notifyDataSetChanged();
                }
            });

            navigationActivityViewModel.getEgresos().observe(getViewLifecycleOwner(), egresos -> {
                egresosList.clear();
                for (ListElementEgresosIngresos egreso : egresos) {
                    if (egreso.getEmail_user().equals(email)) {
                        egresosList.add(egreso);
                    }
                }
                if (tabLayout != null && tabLayout.getSelectedTabPosition() == 1) {
                    listEgresosIngresosAdapter.setItems(egresosList);
                    listEgresosIngresosAdapter.notifyDataSetChanged();
                }
            });
        }
    }


    private void initializeViews(View view) {
        listEgresosIngresosAdapter = new EgresosIngresosAdapter(new ArrayList<>(), getContext(), item -> {
            // Handle item click if needed
        });

        recyclerViewSites = view.findViewById(R.id.listReportes);
        recyclerViewSites.setHasFixedSize(true);
        recyclerViewSites.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSites.setAdapter(listEgresosIngresosAdapter);

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    listEgresosIngresosAdapter.setItems(ingresosList);
                } else if (tab.getPosition() == 1) {
                    listEgresosIngresosAdapter.setItems(egresosList);
                }
                listEgresosIngresosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}
