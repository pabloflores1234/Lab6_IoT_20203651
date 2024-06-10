package com.example.lab6_20203651_iot.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab6_20203651_iot.R;

import java.util.List;

public class EgresosIngresosAdapter extends RecyclerView.Adapter<EgresosIngresosAdapter.ViewHolder> {

    private List<ListElementEgresosIngresos> nData;
    private LayoutInflater nInflater;
    private Context context;
    final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListElementEgresosIngresos item);
    }

    public EgresosIngresosAdapter(List<ListElementEgresosIngresos> itemList, Context context, OnItemClickListener listener) {
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.nData = itemList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.egresos_ingresos_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bindData(nData.get(position));
    }

    public void setItems(List<ListElementEgresosIngresos> items) {
        nData = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, fecha, monto;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.egresos_ingresos_TextView);
            fecha = itemView.findViewById(R.id.fechaTextView);
            monto = itemView.findViewById(R.id.egreso_ingreso_cantidad_TextView);
        }

        void bindData(final ListElementEgresosIngresos item) {
            name.setText(item.getName());
            fecha.setText(item.getFecha_ingreso());
            monto.setText(item.getMonto());
            itemView.setOnClickListener(view -> listener.onItemClick(item));
        }
    }
}

