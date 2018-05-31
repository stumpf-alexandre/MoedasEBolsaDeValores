package com.example.ramonsl.moedashoje;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ramonsl on 08/05/2018.
 */

public class CurrenciesAdapter extends ArrayAdapter<Currencies> {

    public CurrenciesAdapter(@NonNull Context context, @NonNull List<Currencies> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Currencies coin= getItem(position);

        View listCurrencies= convertView;
        if(convertView==null) {
            listCurrencies= LayoutInflater.from(getContext()).inflate(R.layout.currencies_item,null);

        }
        TextView name= listCurrencies.findViewById(R.id.txtCoin);
        TextView txtBuy= listCurrencies.findViewById(R.id.txtCompra);
        TextView txtSell= listCurrencies.findViewById(R.id.txtVenda);
        TextView variacao= listCurrencies.findViewById(R.id.txtVariacao);
        name.setText(coin.getName());
        txtBuy.setText(coin.getBuy());
        txtSell.setText(coin.getSell());
        variacao.setText(coin.getVariation());


        return listCurrencies;
    }
}
