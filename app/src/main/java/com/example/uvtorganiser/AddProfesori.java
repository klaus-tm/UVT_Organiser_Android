package com.example.uvtorganiser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddProfesori extends Fragment {
    Button addProf;
    EditText numeProf, mailProf, telefonProf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_profesori, container, false);

        addProf = view.findViewById(R.id.addProf);
        numeProf = view.findViewById(R.id.NumeProf);
        mailProf = view.findViewById(R.id.MailProf);
        telefonProf = view.findViewById(R.id.TelefonProf);
        addProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(AddProfesori.super.getContext());
                db.addProf(numeProf.getText().toString().trim(), mailProf.getText().toString().trim(), telefonProf.getText().toString().trim());
                Navigation.findNavController(view).navigate(R.id.action_addProfi_to_menuProfesori);
            }
        });
        return view;
    }
}