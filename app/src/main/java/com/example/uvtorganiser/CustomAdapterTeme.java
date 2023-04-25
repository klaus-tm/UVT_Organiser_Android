package com.example.uvtorganiser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CustomAdapterTeme extends RecyclerView.Adapter<CustomAdapterTeme.MyViewHolder> {
    private Context context;
    private ArrayList numeDisciplina, detaliiTema, termen, terminata;

    public CustomAdapterTeme(Context context, ArrayList numeDisciplina, ArrayList detaliiTema, ArrayList termen, ArrayList terminata) {
        this.context = context;
        this.numeDisciplina = numeDisciplina;
        this.detaliiTema = detaliiTema;
        this.termen = termen;
        this.terminata = terminata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_teme, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.numeDisciplinaText.setText(String.valueOf(numeDisciplina.get(position)));
        holder.detaliiTemaText.setText(String.valueOf(detaliiTema.get(position)));
        holder.termenText.setText(String.valueOf(termen.get(position)));
        holder.terminataText.setText(String.valueOf(terminata.get(position)));
        holder.randTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog(detaliiTema.get(holder.getAdapterPosition()).toString(), numeDisciplina.get(holder.getAdapterPosition()).toString(), view);
            }
        });
    }

    void confirmDialog(String detaliiTema, String numeDisciplina, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Marcare tema ca terminata");
        builder.setMessage("Esti sigur ca vrei sa marchezi tema: " + detaliiTema + " ca terminata ?");
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.makeTerminata(detaliiTema, numeDisciplina);
                Navigation.findNavController(view).navigate(R.id.action_menuTeme_self);
            }
        });
        builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return numeDisciplina.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView numeDisciplinaText, detaliiTemaText, termenText, terminataText;
        LinearLayout randTema;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            numeDisciplinaText = itemView.findViewById(R.id.numeDisciplinaTemaText);
            detaliiTemaText = itemView.findViewById(R.id.detaliiTemaText);
            termenText = itemView.findViewById(R.id.termenTemaText);
            terminataText = itemView.findViewById(R.id.terminataText);
            randTema = itemView.findViewById(R.id.randTema);
        }
    }
}
