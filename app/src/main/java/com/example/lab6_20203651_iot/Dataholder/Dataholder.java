package com.example.lab6_20203651_iot.Dataholder;

import com.example.lab6_20203651_iot.items.ListElementEgresosIngresos;
import com.google.android.gms.common.data.DataHolder;

import java.util.ArrayList;

public class Dataholder {

    private ArrayList<ListElementEgresosIngresos> Egresos;

    private ArrayList<ListElementEgresosIngresos> Ingresos;


    private static final Dataholder instance = new Dataholder();


    private Dataholder() {
        Egresos = new ArrayList<>();
        Ingresos = new ArrayList<>();

    }
    public static Dataholder getInstance() {
        return instance;
    }




    public ArrayList<ListElementEgresosIngresos> getEgresos() {
        return Egresos;
    }

    public void setEgresos(ArrayList<ListElementEgresosIngresos> egresos) {
        Egresos = egresos;
    }

    public ArrayList<ListElementEgresosIngresos> getIngresos() {
        return Ingresos;
    }

    public void setIngresos(ArrayList<ListElementEgresosIngresos> ingresos) {
        Ingresos = ingresos;
    }
}
