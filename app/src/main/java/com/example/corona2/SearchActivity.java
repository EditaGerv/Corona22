package com.example.corona2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import static android.app.ProgressDialog.*;

public class SearchActivity extends AppCompatActivity { //cia yra globalus kintamieji, kurie aprasomi klases virsuje
    public static final String COVID_API = "https://covid19-api.weedmark.systems/api/v1/stats"; //kreipiames i URL, kad duotu si API
    private ArrayList<Corona> coronaList = new ArrayList<Corona>();

    private RecyclerView recyclerView; //korteliu vaizdas
    private Adapter adapter; // tarpininkas tarp search activity ir xml (conteiner, kur atvaizduosime korteles). Apjungia 2 skirtingas klases, dalis

    private SearchView searchView = null; //paieskos vaizdas, kuriame piesime

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //bus paleidziama nauja gija - nuskaitymui JSON is API
        AsyncFetch asyncFetch = new AsyncFetch(); //susikuriame klase
        asyncFetch.execute(); //execute iskviecia metodus toje klaseje sukurtoje (t. y. AsyncFetch)
    }

    public boolean onCreateOptionsMenu(Menu menu) {//kad butu galimybe spausti ir ieskoti reikia perresyti metoda
        // adds item to action bar
        getMenuInflater().inflate(R.menu.search, menu);
        // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search); // sukuria paieskos laukeli
        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
            searchView.setIconified(false);
        }
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) { //atsiranda simbolis paieskos
        return super.onOptionsItemSelected(item);
    }
// Every time when you press search button on keypad an Activity is recreated which in turn calls this function

    @Override

    protected void onNewIntent(Intent intent) { //kiekviena karta paspaudus paieskot mygtuka vykdoma paieskos funkcija
// Get search query
        //super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
            //is visu valstybiu coronos saraso sukuriamas sarasas pagal vartotojo ieskoma query
            ArrayList<Corona> coronaListByCountry = JSON.getCoronaListByCountry(coronaList, query);
            if (coronaListByCountry.size() == 0) {
                Toast.makeText(this, getResources().getString(R.string.search_no_results) + query, Toast.LENGTH_SHORT).show();
            }
            //duomenu perdavimas adapteriui ir sukurimas recyclerview
            recyclerView = (RecyclerView) findViewById(R.id.corona_list);
            adapter = new Adapter(SearchActivity.this, coronaListByCountry);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        }
    }

    private class AsyncFetch extends AsyncTask<String, String, JSONObject> { //extends reiskia, kad prapleciam tevine klase AsyncFetch, kuri atsakinga uz lygiagretuma, uz giju kurima
        //visi metodeliai tures tokius parametrus ir vienas is ju grazins JSONObject
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        @Override
        protected void onPreExecute() { // sis metodas bus vykdomas pries doinbackground, paprasysime vartotojo palaukti, kol mes gausime duomenis is API
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
            if (statusCode == HttpURLConnection.HTTP_OK) { //yra bibliotekos kur aprasyti statuscode,  jei viskas OK, galime tiketis JSON
                //System.err.println(jsonObject.toString());   //spausdina terminala, pasitikrinimui; bandys spausdinti kaip klaida, kita spalva
                JSONArray jsonArray = null;
                try {
                    jsonArray = JSON.getJSONArray(jsonObject);
                    coronaList = JSON.getList(jsonArray);

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
