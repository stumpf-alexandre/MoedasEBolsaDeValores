package com.example.ramonsl.moedashoje;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    CurrenciesTask mTask;
    ArrayList<Currencies> mCurrencies;
    ListView mListCurrencies;
    ArrayAdapter<Currencies> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListCurrencies = findViewById(R.id.listCoins);
        Button btn = findViewById(R.id.btn_bolsa);
        search();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityStocks.class);
                startActivity(intent);
            }
        });
    }

    private void search() {
        if (mCurrencies == null) {
            mCurrencies = new ArrayList<Currencies>();
        }

        mAdapter = new CurrenciesAdapter(getApplicationContext(), mCurrencies);
        mListCurrencies.setAdapter(mAdapter);
        if (mTask == null) {
            if (CurrenciesHttp.hasConnected(this)) {
                startDownload();
            } else {
                Toast.makeText(getApplicationContext(), "Sem conexão...", Toast.LENGTH_LONG).show();
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(getApplicationContext(), "......", Toast.LENGTH_LONG).show();
        }
    }


    public void startDownload() {
        if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new CurrenciesTask();
            mTask.execute();
        }
    }

    //INNER CLASS ASICRONA
    class CurrenciesTask extends AsyncTask<Void, Void, ArrayList<Currencies>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgress(true);
            Toast.makeText(getApplicationContext(), "Pronto...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected ArrayList<Currencies> doInBackground(Void... strings) {
            ArrayList<Currencies> coinsList = CurrenciesHttp.loadCurrencies();
            return coinsList;
        }
        @Override
        protected void onPostExecute(ArrayList<Currencies> coins) {
            super.onPostExecute(coins);
            //     showProgress(false);
            if (coins != null) {
                mCurrencies.clear();
                mCurrencies.addAll(coins);
                mAdapter.notifyDataSetChanged();
            } else {

                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }
}
