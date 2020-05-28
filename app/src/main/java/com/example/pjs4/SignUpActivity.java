package com.example.pjs4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    EditText etEmail,etPassword,etLogin,etConPass;
    Button btnRegister;
    String email,login,pass,conPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.input_mail);
        etPassword = findViewById(R.id.input_mdp);
        etConPass = findViewById(R.id.input_mdp2);
        etLogin = findViewById(R.id.input_login);

        btnRegister = findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            register();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }


    public void initialize(){
            email= etEmail.getText().toString();
            login = etLogin.getText().toString();
            pass = etPassword.getText().toString();
            conPass=etConPass.getText().toString();

        }

        public boolean validate(){
            boolean valid = true;
            if(login.isEmpty()){
                etLogin.setError("Login manquant");
                etLogin.requestFocus();
                valid =false;

            }

            else if(pass.length()<6){
                etPassword.setError("Le mot de passe doit contenir au moins 6 caractères");
                etPassword.requestFocus();
                valid =false;
            }
            else if(conPass.isEmpty()) {
                etConPass.setError("Confirmez votre mot de passe");
                etConPass.requestFocus();
                valid =false;
            }
            else if (!pass.equals(conPass)){
                etConPass.setError("Les mots de passe ne correspondent pas ");
                etConPass.requestFocus();
                valid =false;
            }
            else if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.setError("Email manquant/incorrect");
                etEmail.requestFocus();
                valid =false;

            }

            return valid;
        }

        public void onSignUpSuccess(){
            mAuth.createUserWithEmailAndPassword(email, pass);

        }

        public void register (){
        initialize();
        if(!validate()){
            Toast.makeText(this,"Inscription incorrecte", Toast.LENGTH_SHORT).show();
        }else{
            onSignUpSuccess();
        }

        }

}
