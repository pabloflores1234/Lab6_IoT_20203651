package com.example.lab6_20203651_iot;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.lab6_20203651_iot.items.ListElementEgresosIngresos;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CrearEgresoIngreso extends Fragment {

    private MaterialAutoCompleteTextView selectEgresoIngreso;
    private ArrayAdapter<String> typeOptionsAdapter;
    private EditText editNombre, editMonto;
    private FirebaseFirestore db;

    private String[] typeOptionsPlatita = {"Ingreso", "Egreso"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crear_egreso_ingreso, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectEgresoIngreso = view.findViewById(R.id.selectEgresoIngreso);
        editNombre = view.findViewById(R.id.editNombre);
        editMonto = view.findViewById(R.id.editMonto);
        db = FirebaseFirestore.getInstance();

        typeOptionsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, typeOptionsPlatita);
        selectEgresoIngreso.setAdapter(typeOptionsAdapter);

        // Assuming you have a button to trigger the creation of the new entry
        view.findViewById(R.id.btnCrear).setOnClickListener(v -> createNewEgresoIngreso());
    }

    private void createNewEgresoIngreso() {

        String email = getArguments().getString("email");
        if (areFieldsEmpty()) {
            Toast.makeText(getContext(), "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
        } else {
            String nombre = editNombre.getText().toString();
            String monto = editMonto.getText().toString();
            String platita = selectEgresoIngreso.getText().toString();
            String email_user = email;

            LocalDate fechaActual = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                fechaActual = LocalDate.now();
            }
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            }
            String fecha_ingreso = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                fecha_ingreso = fechaActual.format(formatter);
            }

            ListElementEgresosIngresos listElementEgresosIngresos = new ListElementEgresosIngresos(nombre,fecha_ingreso,monto,platita,email_user);

            SaveIngresoEgresoToFirestore(listElementEgresosIngresos);
        }
    }

    private boolean areFieldsEmpty() {
        return editMonto.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty();
    }

    private void SaveIngresoEgresoToFirestore(ListElementEgresosIngresos listElement) {
        String email = getArguments().getString("email");
        db.collection("EgresosIngresos")
                .document(listElement.getName())
                .set(listElement, SetOptions.merge())
                .addOnSuccessListener(unused -> {
                    // Handle success
                    if (getActivity() != null) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("email", email); // Pasar el correo electrónico como un extra
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    e.printStackTrace();
                });
    }

}
