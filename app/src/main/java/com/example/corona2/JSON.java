package com.example.corona2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//leidzia nuskaityti json is url adresiuko situ 2 metodu deka
public class JSON {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    //vartotojui niekad nereiks visu duomenu, reikes konkrecios valstybes duomenu, todel mes turesim issitraukti is JSON tik tai, kas mus domina, reikia, kad grazintu sarasa be duomenu, kurie nereikalingi, sis metodas mums grazins arraylist, per parametrus mes perduosime json sarasa ir ji konvertuos
    //ArrayList yra Corona klases objektu sarasas
    public static ArrayList<Corona> getList(JSONArray jsonArray) throws JSONException {
        //<> kokios klase objektus talpinsim, sarasai gali talpinti tik 1 tipo elementus
        ArrayList<Corona> coronaList = new ArrayList<Corona>();
        //Isimti data is JSON ir issaugoti Corona objektu sarase coronaList
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Corona corona = new Corona(
                    //public Corona(String country, String lastUpdate, String keyId, int confirmed, int deaths)
                    jsonObject.getString("country"),
                    jsonObject.getString("lastUpdate"),
                    jsonObject.getString("keyId"),
                    jsonObject.getInt("confirmed"),
                    jsonObject.getInt("deaths")
            );
            coronaList.add(corona);
        }
        return coronaList;
    }

    public static JSONArray getJSONArray(JSONObject jsonObject) throws JSONException {
        //pasalinama is JSON'o visa nereikalinga informacija(metaduomenys), paliekant tik covid19stats masyva
    int jsonLength = jsonObject.toString().length();
    String covid19Stats = "{"+jsonObject.toString().substring(96, jsonLength)+"}"; //substring metodas kuris iskerpa dali simboliu is eilutes, nuo 96 iki pacio galo

        //Konvertacija String i JSON objekta
    JSONObject jsonobject1 = new JSONObject(covid19Stats);
    // JSONObject į JSONArray
        JSONArray jsonArray = jsonobject1.getJSONArray("covid19Stats");
        return jsonArray;
    }

    public static ArrayList<Corona> getCoronaListByCountry(ArrayList<Corona> coronaArrayList, String country){
        ArrayList<Corona> coronaListByCountry = new ArrayList<Corona>();
        for (Corona corona : coronaArrayList) { // kaireje bus sukuriamas tos klases objektas per kurios sarasa iteruojama (desineje)
            if(corona.getKeyId().contains(country)){
                coronaListByCountry.add(corona); //contains stringo metodas kuris iesko zodzio dalies
            }
        }
        return coronaListByCountry;
    }
}