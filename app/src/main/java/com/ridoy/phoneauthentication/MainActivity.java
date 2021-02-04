package com.ridoy.phoneauthentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText signup_edittext_phone;
    private Button btn_sentotp,login;
    private ProgressDialog progressDialog;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar home_toolbar=findViewById(R.id.login_toolbarid);
        setSupportActionBar(home_toolbar);
        getSupportActionBar().setTitle("Sign Up");


        user= FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        signup_edittext_phone = findViewById(R.id.signUp_edittext_phoneid);
        btn_sentotp = findViewById(R.id.btn_sentotpid);
        login = findViewById(R.id.loginbtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        btn_sentotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = signup_edittext_phone.getText().toString().trim();

                if (phone.isEmpty())
                {
                    signup_edittext_phone.setError("Plz, Enter Your Phone Number");
                    signup_edittext_phone.requestFocus();
                    return;
                }
                if (phone.length()<11 || phone.length()>11)
                {
                    signup_edittext_phone.setError("Plz, Valid Phone Number");
                    signup_edittext_phone.requestFocus();
                    return;
                }

                String phonenumber="+88"+phone;

                Intent intent=new Intent(MainActivity.this,OTPActivity.class);
                intent.putExtra("mobile",phonenumber);
                startActivity(intent);
            }
        });

    }
}