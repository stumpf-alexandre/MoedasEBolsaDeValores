package com.example.ramonsl.moedashoje;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StockAdapter extends ArrayAdapter<Stocks>{
    public StockAdapter(@NonNull Context context, @NonNull List<Stocks> objects){
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Stocks handbag = getItem(position);

        View listStocks = convertView;
        if (convertView == null){
            listStocks = LayoutInflater.from(getContext()).inflate(R.layout.stocks_itens, null);
        }
        TextView nome = listStocks.findViewById(R.id.text_nome);
        TextView location = listStocks.findViewById(R.id.text_location);
        TextView variation = listStocks.findViewById(R.id.text_variation);

        nome.setText(handbag.getCname());
        location.setText(handbag.getLocal());
        variation.setText(handbag.getCotacao());

        return listStocks;
    }
}