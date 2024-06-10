package com.example.lab6_20203651_iot.items;

import java.io.Serializable;

public class ListElementEgresosIngresos implements Serializable {

    private String name;
    private String fecha_ingreso;
    private String monto;
    private String egreso;
    private String email_user;

    // Constructor sin argumentos requerido para Firestore
    public ListElementEgresosIngresos() {
        // Aquí puedes inicializar valores predeterminados si es necesario
    }

    // Constructor con argumentos
    public ListElementEgresosIngresos(String name, String fecha_ingreso, String monto, String egreso, String email_user) {
        this.name = name;
        this.fecha_ingreso = fecha_ingreso;
        this.monto = monto;
        this.egreso = egreso;
        this.email_user = email_user;
    }

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getEgreso() {
        return egreso;
    }

    public void setEgreso(String egreso) {
        this.egreso = egreso;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }
}

