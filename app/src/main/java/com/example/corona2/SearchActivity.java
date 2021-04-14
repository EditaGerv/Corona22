package com.example.corona2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import static android.app.ProgressDialog.*;

public class SearchActivity extends AppCompatActivity {
    public static final String COVID_API = "https://covid19-api.weedmark.systems/api/v1/stats";
    private ArrayList<Corona> coronaList = new ArrayList<Corona>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //bus paleidziama nauja gija - nuskaitymui JSON is API
        AsyncFetch asyncFetch = new AsyncFetch(); //susikuriame klase
        asyncFetch.execute();
    }

    private class AsyncFetch extends AsyncTask<String, String, JSONObject> { //extends reiskia, kad prapleciam tevine klase AsyncFetch, kuri atsakinga uz lygiagretuma, uz giju kurima
        //visi metodeliai tures tokius parametrus ir vienas is ju grazins JSONObject
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        @Override
        protected void onPreExecute() { // sis metodas buv vykdoma spries doinbackground, paprasysime vartotojo palaukti, kol mes gausime duomenis is API
            super.onPreExecute();
            progressDialog.setMessage(getResources().getString(R.string.search_loading_data));
            progressDialog.setCancelable(false); //reiskia, kad negales atsaukti, tures islaukti
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) { //Jis bus vykdomas tuo metu, kai vartotojas matys progress dialoga, jis skirtas gavimui JSON is API
            try { //try bloke visada yra blokas, kuriame gali kilti klaidu ir tai bus apdorojama isimtyse ir del to programa neuzlus, o vartotojui bus isvesti pranesimai
                JSONObject jsonObject = JSON.readJsonFromUrl(COVID_API); //jeigu viskas bus gerai grazinsim jsonobject
                return jsonObject;
            } catch (IOException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(), //pateiksim  papildoma informacija
                        Toast.LENGTH_LONG
                ).show();
            } catch (JSONException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) { //padaryk kazka po, pagrindinis executina doinbackground
            progressDialog.dismiss();

            int statusCode = 0;
            try {
                statusCode = jsonObject.getInt("statusCode"); //kai prieinam prie jsonobject visad reikes surround with try/catch
            } catch (JSONException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
            if (statusCode == HttpURLConnection.HTTP_OK) { //yra bibliotekos kur aprasyti statuscode
                //System.err.println(jsonObject.toString());   //spausdina terminala, pasitikrinimui; bandys spausdinti kaip klaida, kita spalva
                JSONArray jsonArray = null;
                try {
                    jsonArray = JSON.getJSONArray(jsonObject);
                    coronaList = JSON.getList(jsonArray);
                    System.out.println("Lithuania covidStats:" + JSON.getCoronaListByCountry(coronaList, "Lithuania"));
                } catch (JSONException e) {
                    System.out.println(getResources().getString(R.string.search_error_reading_data) + e.getMessage());
                }
                //                System.err.println(jsonObject.toString());
                //                Toast.makeText(
                //                        SearchActivity.this,
                //                        jsonObject.toString(),
                //                        Toast.LENGTH_LONG
                //                ).show();
            } else { //kazkas nepavyko (serveris negrazino 200 code)
                String message = null;
                try {
                    message = jsonObject.getString("message");
                } catch (JSONException e) {
                    Toast.makeText(
                            SearchActivity.this,
                            getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + message, //jeigu viskas bus gerai, mes turesime message (kintamasis)
                        Toast.LENGTH_LONG
                ).show();
            } //baigiasi else
        } //baigiasi onPostExecute
    } //baigiasi AsyncFetch klase
} //baigiasi Search Activity class
