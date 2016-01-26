package com.m2dl.maf.makeafocal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
        if (!newPseudoEdit.getText().toString().isEmpty()) {
            User username = Session.instance().getCurrentUser();
            User u = new User(newPseudoEdit.getText().toString());
            if (username != null && username.pseudoExists(this)) {

                username.delete(this); // remove old user
                u.create(this); // Create a user with the new pseudal
                Session.instance().setCurrentUser(u);
                Toast.makeText(
                        ModificationPseudoActivity.this,
                        R.string.pseudo_changed,
                        Toast.LENGTH_SHORT).show();
//                setUsernameOnNavigationMenu(u.getUserName());
                this.finish();
            } else {
                u.create(this);
                Session.instance().setCurrentUser(u);
                Toast.makeText(
                        ModificationPseudoActivity.this,
                        R.string.pseudo_created,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(
                        ModificationPseudoActivity.this, MainActivity.class);
//                setUsernameOnNavigationMenu(u.getUserName());
                startActivity(intent);
            }
        } else {
            Toast.makeText(
                    ModificationPseudoActivity.this,
                    R.string.please_input_pseudo,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void setUsernameOnNavigationMenu(final String username) {
        MenuItem item = (MenuItem) findViewById(R.id.nav_user);
        item.setTitle(username);
    }

}
