package com.example.ramonsl.moedashoje;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

public class Stocks implements Serializable{

    private String cName;
    private String local;
    private Double cotacao;

    public Stocks(String cName, String local, double cotacao){
        this.cName = cName;
        this.local = local;
        this.cotacao = cotacao;
    }

    public String getCname() {
        return cName;
    }

    public String getLocal() {
        return local;
    }

    public String getCotacao() {
        return String.valueOf(cotacao);
    }

    @Override
    public String toString(){
        return "Stocks{" + "cName='" + cName + '\'' + '}';
    }
}
