package com.m2dl.maf.makeafocal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.m2dl.maf.makeafocal.model.Session;
import com.m2dl.maf.makeafocal.model.User;

public class ModificationPseudoActivity extends AppCompatActivity {
    private EditText newPseudoEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_pseudo);
        newPseudoEdit = (EditText) findViewById(R.id.newPseudo);
        }

    public void changePseudo(View v) {
        if (!newPseudoEdit.getText().toString().equals("")) {
            User usernameActual = Session.instance().getCurrentUser();
            User u = new User(this, newPseudoEdit.getText().toString());
            if (usernameActual.pseudoExists()) {

                usernameActual.delete(); // remove old user
                u.create(); // Create a user with the new pseudal
                Session.instance().setCurrentUser(u);
                Toast.makeText(ModificationPseudoActivity.this, "Pseudo changé", Toast.LENGTH_SHORT).show();
                this.finish();
            } else {
                u.create();
                Session.instance().setCurrentUser(u);
                Toast.makeText(ModificationPseudoActivity.this, "Pseudo créer", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        } else {
            Toast.makeText(ModificationPseudoActivity.this, "Veuillez saisir un pseudo", Toast.LENGTH_SHORT).show();
        }
    }
}
