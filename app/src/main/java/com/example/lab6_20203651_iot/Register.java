package com.example.lab6_20203651_iot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextConfirmationPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmationPassword = findViewById(R.id.editTextConfirmationPassword);

        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText() != null ? editTextEmail.getText().toString().trim() : "";
                String contra = editTextPassword.getText() != null ? editTextPassword.getText().toString().trim() : "";
                String confirmacontra = editTextConfirmationPassword.getText() != null ? editTextConfirmationPassword.getText().toString().trim() : "";

                if (email.isEmpty()) {
                    editTextEmail.setError("Email es requerido");
                    editTextEmail.requestFocus();
                    return;
                }

                if (contra.isEmpty()) {
                    editTextPassword.setError("Contraseña es requerida");
                    editTextPassword.requestFocus();
                    return;
                }

                if (confirmacontra.isEmpty()) {
                    editTextConfirmationPassword.setError("Confirmación de contraseña es requerida");
                    editTextConfirmationPassword.requestFocus();
                    return;
                }

                if (!contra.equals(confirmacontra)) {
                    editTextConfirmationPassword.setError("Las contraseñas no coinciden");
                    editTextConfirmationPassword.requestFocus();
                    return;
                }

                Log.d("RegisterActivity", "Email: " + email);
                Log.d("RegisterActivity", "Password: " + contra);

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, contra)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {

                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(Register.this, "Correo de verificación enviado", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(Register.this, "Error al enviar correo de verificación", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


                                        Intent intent = new Intent(Register.this, Login.class);
                                        intent.putExtra("USER_EMAIL", email);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    String falla = "Error al registrarse";
                                    if (task.getException() != null) {
                                        String codigoError = ((FirebaseAuthException) task.getException()).getErrorCode();
                                        switch (codigoError) {
                                            case "ERROR_INVALID_EMAIL":
                                                falla = "Ingresa un formato de correo electrónico válido";
                                                break;
                                            case "ERROR_WEAK_PASSWORD":
                                                falla = "La contraseña debe tener al menos 6 caracteres";
                                                break;
                                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                                falla = "El correo electrónico ya está en uso";
                                                break;
                                            default:
                                                falla = task.getException().getMessage();
                                                break;
                                        }
                                    }
                                    Toast.makeText(Register.this, falla, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}