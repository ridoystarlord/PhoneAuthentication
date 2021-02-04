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

public class LoginActivity extends AppCompatActivity {

    private EditText login_edittext_phone;
    private Button login_btn_sentotp;
    private ProgressDialog progressDialog;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar home_toolbar=findViewById(R.id.login_toolbarid);
        setSupportActionBar(home_toolbar);
        getSupportActionBar().setTitle("Login");

        user= FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        login_edittext_phone = findViewById(R.id.login_edittext_phoneid);
        login_btn_sentotp = findViewById(R.id.login_btn_sentotpid);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        login_btn_sentotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = login_edittext_phone.getText().toString().trim();

                if (phone.isEmpty())
                {
                    login_edittext_phone.setError("Plz, Enter Your Phone Number");
                    login_edittext_phone.requestFocus();
                    return;
                }
                if (phone.length()<11 || phone.length()>11)
                {
                    login_edittext_phone.setError("Plz, Valid Phone Number");
                    login_edittext_phone.requestFocus();
                    return;
                }

                String phonenumber="+88"+phone;

                Intent intent=new Intent(LoginActivity.this,LoginOTPActivity.class);
                intent.putExtra("mobile",phonenumber);
                startActivity(intent);
            }
        });
    }
}