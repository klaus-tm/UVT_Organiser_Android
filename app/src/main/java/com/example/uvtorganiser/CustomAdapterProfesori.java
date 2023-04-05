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

public class CustomAdapterProfesori extends RecyclerView.Adapter<CustomAdapterProfesori.MyViewHolder> {
    private Context context;
    private ArrayList numeProf, mailProf, telefonProf;

    public CustomAdapterProfesori(Context context, ArrayList numeProf, ArrayList mailProf, ArrayList telefonProf) {
        this.context = context;
        this.numeProf = numeProf;
        this.mailProf = mailProf;
        this.telefonProf = telefonProf;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_profi, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.numeProfText.setText(String.valueOf(numeProf.get(position)));
        holder.mailProfText.setText(String.valueOf(mailProf.get(position)));
        holder.telefonProfText.setText(String.valueOf(telefonProf.get(position)));
        holder.randProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog(numeProf.get(holder.getAdapterPosition()).toString(), view);
            }
        });
    }

    void confirmDialog(String numeProf, View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Stergere profesor");
        builder.setMessage("Esti sigur ca vrei sa stergi profesorul: " + numeProf + " ?");
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.deleteProf(numeProf);
                Navigation.findNavController(view).navigate(R.id.action_menuProfesori_self);
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
        return numeProf.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView numeProfText, mailProfText, telefonProfText;
        LinearLayout randProf;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            numeProfText = itemView.findViewById(R.id.numeProfText);
            mailProfText = itemView.findViewById(R.id.mailProfText);
            telefonProfText = itemView.findViewById(R.id.telefonProfText);
            randProf = itemView.findViewById(R.id.randProf);
        }
    }
}
