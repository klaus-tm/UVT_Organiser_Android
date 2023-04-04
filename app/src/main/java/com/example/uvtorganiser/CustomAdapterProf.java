package com.example.uvtorganiser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterProf extends RecyclerView.Adapter<CustomAdapterProf.MyViewHolder> {
    private Context context;
    private ArrayList numeProf, mailProf, telefonProf;

    public CustomAdapterProf(Context context, ArrayList numeProf, ArrayList mailProf, ArrayList telefonProf) {
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
    }

    @Override
    public int getItemCount() {
        return numeProf.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView numeProfText, mailProfText, telefonProfText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            numeProfText = itemView.findViewById(R.id.numeProfText);
            mailProfText = itemView.findViewById(R.id.mailProfText);
            telefonProfText = itemView.findViewById(R.id.telefonProfText);
        }
    }
}
