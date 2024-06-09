package com.example.lab6_20203651_iot.ViewModel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab6_20203651_iot.items.ListElementEgresosIngresos;

import java.util.ArrayList;

public class NavigationActivityViewModel extends ViewModel{

    private MutableLiveData<ArrayList<ListElementEgresosIngresos>> Egresos = new MutableLiveData<>();

    private MutableLiveData<ArrayList<ListElementEgresosIngresos>> Ingresos = new MutableLiveData<>();

    private MutableLiveData<String> inicio = new MutableLiveData<>();


    public MutableLiveData<ArrayList<ListElementEgresosIngresos>> getEgresos() {
        return Egresos;
    }

    public void setEgresos(MutableLiveData<ArrayList<ListElementEgresosIngresos>> egresos) {
        Egresos = egresos;
    }

    public MutableLiveData<ArrayList<ListElementEgresosIngresos>> getIngresos() {
        return Ingresos;
    }

    public void setIngresos(MutableLiveData<ArrayList<ListElementEgresosIngresos>> ingresos) {
        Ingresos = ingresos;
    }

    public MutableLiveData<String> getInicio() {
        return inicio;
    }

    public void setInicio(MutableLiveData<String> inicio) {
        this.inicio = inicio;
    }
}
