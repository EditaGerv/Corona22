package com.example.corona2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> { //Adapter praplecia RecyclerView.Adapter klase
    private Context context; //ateina is SearchActivity lango
    private LayoutInflater inflater;
    List<Corona> data; //duomenys, kuriuos mes perduodame Adapteriui per konstruktoriu

    public static final String ENTRY="com.corona.corona2.ENTRY";

    // create constructor to initialize context and data sent from SearchActivity
    public Adapter(Context context, List<Corona> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //sukuria vaizdo laikytuva/kortele (container_corona.xml)
        View view = inflater.inflate(R.layout.container_corona, parent, false); //naudosime container_corona.xml
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { //turime sarasa, jame yra elementai. Pozicija yra tu elementu indeksas sarase. Numeruoja kortele, pvz. get(0) - paims pirma kortele
        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Corona current = data.get(position);
        //uzpildysime kortele duomenimis is saraso
        myHolder.textKeyId.setText(current.getKeyId()); //myHolder yra kortele. KeyId
        myHolder.textLastUpdate.setText("Last update: " + current.getLastUpdate());
        myHolder.textConfirmed.setText("Confirmed: " + current.getConfirmed());
        myHolder.textDeaths.setText("Deaths: " + current.getDeaths());
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size(); //gali parodyti, kiek is viso yra irasu
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //vidine klase MyHolder. Galesime spausti ant bet kurios korteles ismestos ir gauti daugiau duomenu apie toje korteleje esancius duomenis
        TextView textKeyId;
        TextView textLastUpdate;
        TextView textConfirmed;
        TextView textDeaths;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textKeyId = (TextView) itemView.findViewById(R.id.textKeyId); //viska imame is container_corona.xml
            textLastUpdate = (TextView) itemView.findViewById(R.id.textLastUpdate);
            textConfirmed = (TextView) itemView.findViewById(R.id.textConfirmed);
            textDeaths = (TextView) itemView.findViewById(R.id.textDeaths);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {
//            Intent goToNewEntryActivity = new Intent(context, NewEntryActivity.class);
//            int itemPosition = getAdapterPosition();
//            Corona corona = data.get(itemPosition);
//
//            goToNewEntryActivity.putExtra(ENTRY,corona);
//            context.startActivity(goToNewEntryActivity);
            Toast.makeText(context, "You clicked an item", Toast.LENGTH_LONG).show();
        }
    }
}
