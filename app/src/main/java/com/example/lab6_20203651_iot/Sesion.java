package com.example.lab6_20203651_iot;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

public class Sesion extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sesion, container, false);

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Obtener referencia al botón de cerrar sesión
        Button buttonLogout = view.findViewById(R.id.buttonLogout);

        // Configurar un OnClickListener para el botón
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar sesión
                mAuth.signOut();

                // Redirigir a la pantalla de inicio de sesión
                // (en este caso, a la actividad de inicio de sesión)
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish(); // Terminar la actividad actual
            }
        });

        return view;
    }
}