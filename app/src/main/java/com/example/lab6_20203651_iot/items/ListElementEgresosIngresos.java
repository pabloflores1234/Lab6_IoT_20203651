package com.example.lab6_20203651_iot.items;

import java.io.Serializable;

public class ListElementEgresosIngresos implements Serializable {

    private String name;
    private String fecha_ingreso;
    private String monto;

    private String egreso;

    public ListElementEgresosIngresos(String name, String fecha_ingreso, String monto,String egreso) {
        this.name = name;
        this.fecha_ingreso = fecha_ingreso;
        this.monto = monto;
        this.setEgreso(egreso);
    }


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
}
