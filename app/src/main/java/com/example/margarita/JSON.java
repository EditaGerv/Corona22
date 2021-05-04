package com.example.margarita;

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
    public static ArrayList<Margarita> getList(JSONArray jsonArray) throws JSONException {
        //<> kokios klase objektus talpinsim, sarasai gali talpinti tik 1 tipo elementus
        ArrayList<Margarita> margaritaList = new ArrayList<Margarita>();
        //Isimti data is JSON ir issaugoti Corona objektu sarase coronaList
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Margarita margarita = new Margarita( //Margarita konstruktorius, susideda is 5 elemntu. 1 elementas - idDrink, todel trauksime idDrink
            //public Margarita(String idDrink, String strDrink, String strTags, String strCategory, String strGlass)
                    jsonObject.getString("idDrink"), //idDrink rasoma taip, kaip JSON duomenyse. Traukiant raktus is JSON, jie turi buti IDENTISKI
                    jsonObject.getString("strDrink"),
                    jsonObject.getString("strAlcoholic"), //visi raktai yra String tipo, eilutes, nors ir grazins int skaiciu
                    jsonObject.getString("strCategory"),
                    jsonObject.getString("strGlass")
            );
            margaritaList.add(margarita);
        }
        return margaritaList;
    }

    public static JSONArray getJSONArray(JSONObject json) throws JSONException {
        //pasalinama is JSON'o visa nereikalinga informacija(metaduomenys), paliekant tik covid19stats masyva
//    int jsonLength = jsonObject.toString().length();
//    String covid19Stats = "{"+jsonObject.toString().substring(96, jsonLength)+"}"; //substring metodas kuris iskerpa dali simboliu is eilutes, nuo 96 iki pacio galo

        //Konvertacija String i JSON objekta
//    JSONObject jsonobject1 = new JSONObject(covid19Stats);
    // JSONObject į JSONArray
        JSONArray jsonArray = json.getJSONArray("drinks");
        return jsonArray;
    }

    public static ArrayList<Margarita> getMargaritaListByQuery(ArrayList<Margarita> margaritaArrayList, String name){
        ArrayList<Margarita> margaritaListByQuery = new ArrayList<Margarita>();
        for (Margarita margarita : margaritaArrayList) { // kaireje bus sukuriamas tos klases objektas per kurios sarasa iteruojama (desineje)
            if(margarita.getName().contains(name)){
                margaritaListByQuery.add(margarita); //contains stringo metodas kuris iesko zodzio dalies
            }
        }
        return margaritaListByQuery;
    }
}