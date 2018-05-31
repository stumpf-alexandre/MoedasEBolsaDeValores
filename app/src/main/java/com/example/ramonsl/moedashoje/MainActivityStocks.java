package com.example.ramonsl.moedashoje;

import android.app.ActivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivityStocks extends AppCompatActivity{
    StocksTask task;
    ArrayList<Stocks> stocks;
    ListView listView;
    StockAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_list);
        listView = findViewById(R.id.list);
        search();
    }

    private void search(){
        if (stocks == null){
            stocks = new ArrayList<Stocks>();
        }
        adapter = new StockAdapter(getApplicationContext(), stocks);
        listView.setAdapter(adapter);
        if (task == null){
            if (CurrenciesHttp.hasConnected(this)){
                startDownload();
            }
            else {
                Toast.makeText(getApplicationContext(), "Sem conexão...", Toast.LENGTH_LONG).show();
            }
        }
        else if (task.getStatus() == AsyncTask.Status.RUNNING){
            Toast.makeText(getApplicationContext(), ".....", Toast.LENGTH_LONG).show();
        }
    }

    public void startDownload(){
        if (task == null || task.getStatus() != AsyncTask.Status.RUNNING){
            task = new StocksTask();
            task.execute();
        }
    }

    class StocksTask extends AsyncTask<Void, Void, ArrayList<Stocks>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgress(true);
            Toast.makeText(getApplicationContext(), "Pronto...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected ArrayList<Stocks> doInBackground(Void... strings) {
            ArrayList<Stocks> handbagList = CurrenciesHttp.loadStocks();
            return handbagList;
        }
        @Override
        protected void onPostExecute(ArrayList<Stocks> handbag) {
            super.onPostExecute(handbag);
            //     showProgress(false);
            if (handbag != null) {
                stocks.clear();
                stocks.addAll(handbag);
                adapter.notifyDataSetChanged();
            } else {

                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }
}