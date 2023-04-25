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

public class CustomAdapterNote extends RecyclerView.Adapter<CustomAdapterNote.MyViewHolder> {
    private Context context;
    private ArrayList numeDisciplina, detaliiNota, Nota, peste5;

    public CustomAdapterNote(Context context, ArrayList numeDisciplina, ArrayList detaliiNota, ArrayList nota, ArrayList peste5) {
        this.context = context;
        this.numeDisciplina = numeDisciplina;
        this.detaliiNota = detaliiNota;
        this.Nota = nota;
        this.peste5 = peste5;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_note, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.numeDisciplinaText.setText(String.valueOf(numeDisciplina.get(position)));
        holder.detaliiNotaText.setText(String.valueOf(detaliiNota.get(position)));
        holder.NotaText.setText(String.valueOf(Nota.get(position)));
        holder.peste5Text.setText(String.valueOf(peste5.get(position)));
        holder.randNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog(detaliiNota.get(holder.getAdapterPosition()).toString(), numeDisciplina.get(holder.getAdapterPosition()).toString(), view);
            }
        });
    }

    void confirmDialog(String detaliiNota, String numeDisciplina, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Stergere nota");
        builder.setMessage("Esti sigur ca vrei sa stergi nota: " + detaliiNota + " ?");
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.deleteNota(detaliiNota, numeDisciplina);
                Navigation.findNavController(view).navigate(R.id.action_menuNote_self);
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
        TextView numeDisciplinaText, detaliiNotaText, NotaText, peste5Text;
        LinearLayout randNota;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            numeDisciplinaText = itemView.findViewById(R.id.numeDisciplinaNotaText);
            detaliiNotaText = itemView.findViewById(R.id.detaliiNotaText);
            NotaText = itemView.findViewById(R.id.NotaText);
            peste5Text = itemView.findViewById(R.id.peste5Text);
            randNota = itemView.findViewById(R.id.randNota);
        }
    }
}