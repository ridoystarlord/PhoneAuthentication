package com.ridoy.phoneauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    private TextView setverifyphone;
    private Button btn_Verifyotp;
    private EditText OTPbox;
    private String verificationId;
    private FirebaseAuth auth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        Toolbar home_toolbar=findViewById(R.id.login_otp_toolbar);
        setSupportActionBar(home_toolbar);
        getSupportActionBar().setTitle("Sign Up");

        auth=FirebaseAuth.getInstance();
        String mobile=getIntent().getStringExtra("mobile");

        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Sending OTP...");
        dialog.show();

        setverifyphone=findViewById(R.id.setverifyphone);
        setverifyphone.setText("Verify  "+mobile);
        btn_Verifyotp=findViewById(R.id.verifyotpbtn);
        OTPbox=findViewById(R.id.otpbox);

        sentOTPCode(mobile);
        btn_Verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTPCode(OTPbox.getText().toString());

            }
        });
    }
    private void verifyOTPCode(String otpcode) {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,otpcode);
        signInprocess(credential);
    }

    private void signInprocess(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    dialog.dismiss();

                    Intent intent = new Intent(OTPActivity.this, SignupActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(OTPActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sentOTPCode(String mobile) {
        PhoneAuthOptions options= PhoneAuthOptions.newBuilder()
                .setPhoneNumber(mobile)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        dialog.dismiss();
                        String sms=phoneAuthCredential.getSmsCode();
                        verifyOTPCode(sms);
                        OTPbox.setText(sms);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        dialog.dismiss();
                        Toast.makeText(OTPActivity.this, "OTP Sent Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        dialog.dismiss();
                        verificationId=s;
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}