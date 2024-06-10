package com.example.lab6_20203651_iot;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    String canal1 = "importanteDefault";
    ActivityMainBinding binding;
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

        String email = getIntent().getStringExtra("email");
        if (email != null) {
            Log.d("MainActivity", "User email: " + email);
        }

        // Crear un Bundle y pasar el email como argumento
        Bundle args = new Bundle();
        args.putString("email", email);

        binding.topAppBarUserFragment.setTitle("Ingresos y Egresos");

        Egresos = new ArrayList<>();
        Ingresos = new ArrayList<>();

        // Iniciamos Firestore
        db = FirebaseFirestore.getInstance();

        // Reemplazar el fragmento por defecto con Egresos_ingresos
        replaceFragment(new Egresos_ingresos(), args);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.egresos_ingresos_menu) {
                binding.topAppBarUserFragment.setTitle("Ingresos y Egresos");
                replaceFragment(new Egresos_ingresos(), args);
                return true;
            } else if (item.getItemId() == R.id.crear_menu) {
                binding.topAppBarUserFragment.setTitle("Crear Egresos e Ingresos");
                replaceFragment(new CrearEgresoIngreso(), args);
                return true;
            } else if (item.getItemId() == R.id.resumen_menu) {
                binding.topAppBarUserFragment.setTitle("Resumen");
                replaceFragment(new Resumen(), args);
                return true;
            } else if (item.getItemId() == R.id.sesion_menu) {
                binding.topAppBarUserFragment.setTitle("Cerrar Sesión");
                replaceFragment(new Sesion(), args);
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
        db = FirebaseFirestore.getInstance();
        loadEgresosIngresosFromFirestore();
    }


    private void loadEgresosIngresosFromFirestore() {
        db.collection("EgresosIngresos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Egresos.clear();
                        Ingresos.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ListElementEgresosIngresos listElementEgresosIngresos = document.toObject(ListElementEgresosIngresos.class);
                            if ("Egreso".equals(listElementEgresosIngresos.getEgreso())) {
                                Egresos.add(listElementEgresosIngresos);
                            } else if ("Ingreso".equals(listElementEgresosIngresos.getEgreso())) {
                                Ingresos.add(listElementEgresosIngresos);
                            }
                        }
                        navigationActivityViewModel.getEgresos().setValue(Egresos);
                        navigationActivityViewModel.getIngresos().setValue(Ingresos);
                    } else {
                        Log.d("msg-test", "Error getting documents: ", task.getException());
                    }
                });
    }

    /*
    private void loadEgresosIngresosFromFirestore() {
        Log.d("msg-test", "loadUsersFromFirestore called");

        // Limpia las listas antes de agregar nuevos datos
        Egresos.clear();
        Ingresos.clear();

        db.collection("EgresosIngresos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("msg-test", "Task is successful");

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ListElementEgresosIngresos listElementEgresosIngresos = document.toObject(ListElementEgresosIngresos.class);
                            Log.d("msg-test", "Processing user: " + listElementEgresosIngresos.getName());

                            if ("Egreso".equals(listElementEgresosIngresos.getEgreso())) {
                                Egresos.add(listElementEgresosIngresos);
                            } else if ("Ingreso".equals(listElementEgresosIngresos.getEgreso())) {
                                Ingresos.add(listElementEgresosIngresos);
                            }
                        }


                        navigationActivityViewModel.getEgresos().setValue(Egresos);
                        navigationActivityViewModel.getIngresos().setValue(Ingresos);
                        Dataholder.getInstance().setEgresos(Egresos);
                        Dataholder.getInstance().setIngresos(Ingresos);

                    } else {
                        Log.d("msg-test", "Error getting user documents: ", task.getException());
                    }
                });
    } */

    private void replaceFragment(Fragment fragment, Bundle args){

        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_egreso,fragment);
        fragmentTransaction.commit();
    }
}

