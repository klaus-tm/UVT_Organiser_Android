package com.example.uvtorganiser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterDiscipline extends RecyclerView.Adapter<CustomAdapterDiscipline.MyViewHolder> {
    private Context context;
    private ArrayList numeDisciplina, salaCurs, salaSeminar;

    public CustomAdapterDiscipline(Context context, ArrayList numeDisciplina, ArrayList salaCurs, ArrayList salaSeminar) {
        this.context = context;
        this.numeDisciplina = numeDisciplina;
        this.salaCurs = salaCurs;
        this.salaSeminar = salaSeminar;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_discipline, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.numeDisciplinaText.setText(String.valueOf(numeDisciplina.get(position)));
        holder.salaCursText.setText(String.valueOf(salaCurs.get(position)));
        holder.salaSeminarText.setText(String.valueOf(salaSeminar.get(position)));
        holder.randDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog(numeDisciplina.get(holder.getAdapterPosition()).toString(), view);
            }
        });
    }

    void confirmDialog(String numeDisciplina, View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Stergere disciplina");
        builder.setMessage("Esti sigur ca vrei sa stergi disciplina: " + numeDisciplina + " ?");
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.deleteDisciplina(numeDisciplina);
                Navigation.findNavController(view).navigate(R.id.action_menuDiscipline_self);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView numeDisciplinaText, salaCursText, salaSeminarText;
        LinearLayout randDisciplina;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            numeDisciplinaText = itemView.findViewById(R.id.numeDisciplinaText);
            salaCursText = itemView.findViewById(R.id.salaCursText);
            salaSeminarText = itemView.findViewById(R.id.salaSeminarText);
            randDisciplina = itemView.findViewById(R.id.randDisciplina);
        }
    }
}
