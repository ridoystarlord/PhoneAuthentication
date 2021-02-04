package com.ridoy.phoneauthentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity {

    private Button btn_signup;
    private EditText signup_name,signup_sscpoint,signup_sscyear,signup_hscpoint,signup_hscyear;
    private CircleImageView imageView;

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    ProgressDialog dialog;
    Uri selectedImage;

    String name,phone,uid,sscpoint,sscyear,hscpoint,hscyear;
    String imageURL="No Images";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar home_toolbar=findViewById(R.id.signup_toolbarid);
        setSupportActionBar(home_toolbar);
        getSupportActionBar().setTitle("Setup Your Profile");

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait...\nWe are Setup Your Profile...");

        signup_name = findViewById(R.id.signup_edittext_nameid);
        signup_sscpoint = findViewById(R.id.signup_edittext_sscpointid);
        signup_sscyear =findViewById(R.id.signup_edittext_sscyearid);
        signup_hscpoint=findViewById(R.id.signup_edittext_hscpointid);
        signup_hscyear=findViewById(R.id.signup_edittext_hscyearid);
        imageView=findViewById(R.id.signup_circleimageviewid);
        btn_signup =  findViewById(R.id.btn_signupid);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = signup_name.getText().toString().trim();
                sscpoint = signup_sscpoint.getText().toString().trim();
                sscyear = signup_sscyear.getText().toString().trim();
                hscpoint = signup_hscpoint.getText().toString().trim();
                hscyear = signup_hscyear.getText().toString().trim();

                if (name.isEmpty())
                {
                    signup_name.setError("Plz, Enter Your Name");
                    signup_name.requestFocus();
                    return;
                }
                if (sscpoint.isEmpty())
                {
                    signup_sscpoint.setError("Plz, Enter Your SSC Point");
                    signup_sscpoint.requestFocus();
                    return;
                }
                if (sscyear.isEmpty())
                {
                    signup_sscyear.setError("Plz, Enter Your SSC YEAR");
                    signup_sscyear.requestFocus();
                    return;
                }
                if (hscpoint.isEmpty())
                {
                    signup_hscpoint.setError("Plz, Enter Your HSC Point");
                    signup_hscpoint.requestFocus();
                    return;
                }
                if (hscyear.isEmpty())
                {
                    signup_hscyear.setError("Plz, Enter Your HSC YEAR");
                    signup_hscyear.requestFocus();
                    return;
                }

                dialog.show();

                if (selectedImage!=null){
                    final StorageReference reference=storage.getReference().child("ProfileImages").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageURL=uri.toString();
                                        uid=auth.getUid();
                                        phone=auth.getCurrentUser().getPhoneNumber();

                                        SaveUserInformation userInformation=new SaveUserInformation(uid,name,imageURL,sscpoint,sscyear,hscpoint,hscyear);
                                        database.getReference().child("Users")
                                                .child(uid)
                                                .setValue(userInformation)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialog.dismiss();
                                                        Intent intent=new Intent(SignupActivity.this,HomeActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }
                                });

                            }
                        }
                    });
                }
                else {

                    uid=auth.getUid();
                    phone=auth.getCurrentUser().getPhoneNumber();

                    SaveUserInformation userInformation=new SaveUserInformation(uid,name,imageURL,sscpoint,sscyear,hscpoint,hscyear);
                    database.getReference().child("Users")
                            .child(uid)
                            .setValue(userInformation)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();
                                    Intent intent=new Intent(SignupActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data !=null){
            if (data.getData() !=null){
                imageView.setImageURI(data.getData());
                selectedImage=data.getData();
            }
        }
    }
}