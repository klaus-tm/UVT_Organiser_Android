package com.example.uvtorganiser;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterOrar extends RecyclerView.Adapter<CustomAdapterOrar.MyViewHolder> {
    private Context context;
    private ArrayList profesor, disciplina, sala, ora;
    private String zi;

    public CustomAdapterOrar(Context context, ArrayList profesor, ArrayList disciplina, ArrayList sala, ArrayList ora, String zi) {
        this.context = context;
        this.profesor = profesor;
        this.disciplina = disciplina;
        this.sala = sala;
        this.ora = ora;
        this.zi = zi;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_orar, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.profesor.setText(String.valueOf(profesor.get(position)));
        holder.disciplina.setText(String.valueOf(disciplina.get(position)));
        holder.sala.setText(String.valueOf(sala.get(position)));
        holder.ora.setText(String.valueOf(ora.get(position)));

        holder.randOra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog(ora.get(holder.getAdapterPosition()).toString(), view);
            }
        });
    }

    private void confirmDialog(String ora, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Stergere ora");
        builder.setMessage("Esti sigur ca vrei sa stergi ora din intervalul: " + ora + " ?");
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.deleteOra(ora, CustomAdapterOrar.this.zi);
                if (CustomAdapterOrar.this.zi.equals("luni"))
                    Navigation.findNavController(view).navigate(R.id.action_orarLuni_self);
                else if (CustomAdapterOrar.this.zi.equals("marti"))
                    Navigation.findNavController(view).navigate(R.id.action_orarMarti_self);
                else if (CustomAdapterOrar.this.zi.equals("miercuri"))
                    Navigation.findNavController(view).navigate(R.id.action_orarMiercuri_self);
                else if (CustomAdapterOrar.this.zi.equals("joi"))
                    Navigation.findNavController(view).navigate(R.id.action_orarJoi_self);
                else
                    Navigation.findNavController(view).navigate(R.id.action_orarVineri_self);
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
        return profesor.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView profesor, disciplina, sala, ora;
        LinearLayout randOra;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profesor = itemView.findViewById(R.id.profesorText);
            disciplina = itemView.findViewById(R.id.disciplinaText);
            sala = itemView.findViewById(R.id.salaText);
            ora = itemView.findViewById(R.id.oraText);
            randOra = itemView.findViewById(R.id.randOrar);
        }
    }
}
