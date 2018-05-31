package com.example.ramonsl.moedashoje;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ramonsl on 09/05/2018.
 */


public class CurrenciesHttp {
    public static String URL="https://api.hgbrasil.com/finance/quotations?format=json&key=4aaa9ac3";


    private static HttpURLConnection connectar(String urlWebservice) {

        final int SEGUNDOS = 10000;

        try {
            java.net.URL url = new URL(urlWebservice);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;

        } catch (IOException e) {
            Log.d("ERRO", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static boolean hasConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }


    public static Currencies getCurrenciesFromJson(JSONObject json){
        String name;
        Double buy;
        Double sell;
        Double variation;
        Currencies coin=null;

        try {
            name=json.getString("name");
            buy=json.getDouble("buy");
            sell= json.getDouble("sell");
            variation= json.getDouble("variation");
            coin = new Currencies(name,buy,sell,variation);
        }catch (JSONException ex){
            Log.d("ERROR",ex.getMessage());
        }
        return coin;
    }

    public static ArrayList<Currencies> readJson(JSONObject json) {
        ArrayList<Currencies> arrayList = new ArrayList<>();
        try {
            JSONObject results = json.getJSONObject("results");
            JSONObject jsonCurrencies = results.getJSONObject("currencies");
            JSONObject btc = jsonCurrencies.getJSONObject("BTC");
            JSONObject eur = jsonCurrencies.getJSONObject("EUR");
            JSONObject usd = jsonCurrencies.getJSONObject("USD");

            arrayList.add(getCurrenciesFromJson(btc));
            arrayList.add(getCurrenciesFromJson(eur));
            arrayList.add(getCurrenciesFromJson(usd));
        } catch (JSONException e) {

            Log.d("Json", e.getMessage());
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<Stocks> readJsonStocks(JSONObject json) {
        ArrayList<Stocks> arrayList = new ArrayList<>();
        try {
            JSONObject result = json.getJSONObject("result");
            JSONObject jsonStocks = result.getJSONObject("stocks");
            JSONObject ibo = jsonStocks.getJSONObject("IBO");
            JSONObject nasd = jsonStocks.getJSONObject("NASD");
            JSONObject cac = jsonStocks.getJSONObject("CAC");
            JSONObject nik = jsonStocks.getJSONObject("NIK");

            Stocks stk1 = new Stocks(ibo.getString("nome"), ibo.getString("location"), ibo.getDouble("variation"));
            Stocks stk2 = new Stocks(nasd.getString("nome"), nasd.getString("location"), nasd.getDouble("variation"));
            Stocks stk3 = new Stocks(cac.getString("nome"), cac.getString("location"), cac.getDouble("variation"));
            Stocks stk4 = new Stocks(nik.getString("nome"), nik.getString("location"), nik.getDouble("variation"));
            arrayList.add(stk1);
            arrayList.add(stk2);
            arrayList.add(stk3);
            arrayList.add(stk4);
            return arrayList;
        } catch (JSONException e) {
            Log.d("Json", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Currencies> loadCurrencies() {
        try {
            HttpURLConnection connection = connectar(URL);
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(inputStream));
                ArrayList<Currencies>  currenciesList =readJson(json);
                return currenciesList;
            }

        } catch (Exception e) {
            Log.d("ERRO", e.getMessage());
        }
        return null;
    }

    public static ArrayList<Stocks> loadStocks(){
        try {
            HttpURLConnection connection = connectar(URL);
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(inputStream));
                ArrayList<Stocks> stocksList = readJsonStocks(json);
                return stocksList;
            }
        }catch (Exception e){
            Log.d("ERROR", e.getMessage());
        }
        return null;
    }

    private static String bytesParaString(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int byteslidos;
        try {
            while ((byteslidos = inputStream.read(buffer)) != -1) {
                bufferzao.write(buffer, 0, byteslidos);

            }
            return new String(bufferzao.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
