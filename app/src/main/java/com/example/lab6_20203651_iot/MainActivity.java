package com.example.lab6_20203651_iot;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab6_20203651_iot.Dataholder.Dataholder;
import com.example.lab6_20203651_iot.ViewModel.NavigationActivityViewModel;
import com.example.lab6_20203651_iot.items.ListElementEgresosIngresos;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.lab6_20203651_iot.databinding.ActivityMainBinding;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    String canal1 = "importanteDefault";
    ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;

    FirebaseFirestore db;
    NavigationActivityViewModel navigationActivityViewModel;
    private ArrayList<ListElementEgresosIngresos> Egresos, Ingresos;

    public static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = binding.bottomNavigation;
        navigationActivityViewModel = new ViewModelProvider(this).get(NavigationActivityViewModel.class);

        // Recuperar el valor de inicio desde el Intent
        String inicio = getIntent().getStringExtra("inicio");
        if (inicio != null) {
            navigationActivityViewModel.getInicio().setValue(inicio);
        }

        binding.topAppBarUserFragment.setTitle("Ingresos y Egresos");

        Egresos = new ArrayList<>();
        Ingresos = new ArrayList<>();


        Toolbar toolbar = binding.topAppBarUserFragment;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBarUserFragment);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.topAppBarUserFragment.setTitle("Ingresos y Egresos");
                replaceFragment(new Egresos_ingresos());
            }
        }, 1000);


        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.egresos_ingresos_menu) {
                binding.topAppBarUserFragment.setTitle("Ingresos y Egresos");
                replaceFragment(new Egresos_ingresos());
                return true;
            } else if (item.getItemId() == R.id.crear_menu) {
                binding.topAppBarUserFragment.setTitle("Crear Egresos e Ingresos");
                replaceFragment(new CrearEgresoIngreso());
                return true;
            } else if (item.getItemId() == R.id.resumen_menu) {
                binding.topAppBarUserFragment.setTitle("Resumen");
                replaceFragment(new Resumen());
                return true;
            } else if (item.getItemId() == R.id.sesion_menu) {
                binding.topAppBarUserFragment.setTitle("Cerrar Sesión");
                replaceFragment(new Sesion());
                return true;
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    private void loadData() {
        Egresos.clear();
        Ingresos.clear();

        db = FirebaseFirestore.getInstance();
        loadEgresosIngresosFromFirestore();
    }

    private void loadEgresosIngresosFromFirestore() {
        Log.d("msg-test", "loadUsersFromFirestore called");

        Egresos.clear();
        Ingresos.clear();
        db.collection("EgresosIngresos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("msg-test", "Task is successful");

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ListElementEgresosIngresos listElementEgresosIngresos = document.toObject(ListElementEgresosIngresos.class);
                            Log.d("msg-test", "Processing info plata: " + listElementEgresosIngresos.getName());

                            if ("Egreso".equals(listElementEgresosIngresos.getEgreso())) {
                                Egresos.add(listElementEgresosIngresos);
                            } else if ("Ingreso".equals(listElementEgresosIngresos.getEgreso())) {
                                Ingresos.add(listElementEgresosIngresos);
                            }
                        }

                        Log.d("msg-test", "Active users count: " + Egresos.size());
                        Log.d("msg-test", "Inactive users count: " + Ingresos.size());

                        navigationActivityViewModel.getEgresos().setValue(Egresos);
                        navigationActivityViewModel.getIngresos().setValue(Ingresos);

                        Dataholder.getInstance().setEgresos(Egresos);
                        Dataholder.getInstance().setIngresos(Ingresos);
                    } else {
                        Log.d("msg-test", "Error getting user documents: ", task.getException());
                    }
                });
    }





    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_egreso,fragment);
        fragmentTransaction.commit();
    }




}