package com.example.pjs4;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import model.DataBase;
import model.FireBase;

public class MainActivity extends AppCompatActivity {

    private Button btn_go, btn_register;
    private static DataBase dataBase;
    private EditText ed1, ed2;

    private String login;
    private String pwd;

    private FirebaseAuth mAuth;
    private FireBase Fb;

    public MainActivity() {
    }

    //rajouter la view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        Fb = FireBase.getInstance();

        btn_go = (Button) findViewById(R.id.button_goAccueil);
        btn_register = findViewById(R.id.button_register);

        /* call database */
        dataBase = new DataBase(this);
        SQLiteDatabase db = dataBase.getWritableDatabase();
        dataBase.onCreate(db);

        ed1 = findViewById(R.id.input_login);
        ed2 = findViewById(R.id.input_pwd);

        btn_go.setOnClickListener(v -> {
            intialize();
            authentication();
        });

        btn_register.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, SignUpActivity.class))
        );
    }

    private void intialize() {
        login = ed1.getText().toString();
        pwd = ed2.getText().toString();
    }

    private void authentication() {
        mAuth.signInWithEmailAndPassword(login, pwd)
                .addOnCompleteListener(MainActivity.this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Log.d("Authentication", "Success");
                        openAccueil();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("Authentication", "Failed");
                        Toast.makeText(MainActivity.this, "Authentification échouée",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openAccueil() {
        Intent in = new Intent(MainActivity.this, Root.class);
        startActivity(in);
    }

    public void gogoooo(View view) {
        Intent in = new Intent(MainActivity.this, Root.class);
        startActivity(in);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            openAccueil();
        }
    }

    public static DataBase getDataBase() {
        return dataBase;
    }

}
