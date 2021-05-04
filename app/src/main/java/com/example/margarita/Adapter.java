package com.example.margarita;

import android.content.Context;
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
    List<Margarita> drinks; //duomenys, kuriuos mes perduodame Adapteriui per konstruktoriu

    public static final String ENTRY="com.corona.corona2.ENTRY";

    // create constructor to initialize context and data sent from SearchActivity
    public Adapter(Context context, List<Margarita> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.drinks = data;
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //sukuria vaizdo laikytuva/kortele (container_corona.xml)
        View view = inflater.inflate(R.layout.container_margarita, parent, false); //naudosime container_corona.xml
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { //turime sarasa, jame yra elementai. Pozicija yra tu elementu indeksas sarase. Numeruoja kortele, pvz. get(0) - paims pirma kortele
        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Margarita current = drinks.get(position);
        //uzpildysime kortele duomenimis is saraso
        myHolder.textName.setText(current.getName()); //myHolder yra kortele. KeyId
        myHolder.textTypes.setText("Drink Type: " + current.getTypes());
        myHolder.textCategory.setText("Category: " + current.getCategory());
        myHolder.textGlass.setText("Glass Type: " + current.getGlass());
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return drinks.size(); //gali parodyti, kiek is viso yra irasu
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //vidine klase MyHolder. Galesime spausti ant bet kurios korteles ismestos ir gauti daugiau duomenu apie toje korteleje esancius duomenis
        TextView textName;
        TextView textTypes;
        TextView textCategory;
        TextView textGlass;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.textName); //viska imame is container_corona.xml
            textTypes = (TextView) itemView.findViewById(R.id.textTypes);
            textCategory = (TextView) itemView.findViewById(R.id.textCategory);
            textGlass = (TextView) itemView.findViewById(R.id.textGlass);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "You clicked an item", Toast.LENGTH_LONG).show();
        }
    }
}
