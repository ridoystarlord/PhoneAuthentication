package com.ridoy.phoneauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseUser user;
    TextView name,phone,sscpoint,sscyear,hscpoint,hscyear;
    private CircleImageView circleImageView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar home_toolbar=findViewById(R.id.home_toolbarid);
        setSupportActionBar(home_toolbar);
        getSupportActionBar().setTitle("Profile Information");

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");

        name=findViewById(R.id.username);
        hscyear=findViewById(R.id.userhscyear);
        phone=findViewById(R.id.userphone);
        sscpoint=findViewById(R.id.usersscpoint);
        sscyear=findViewById(R.id.usersscyear);
        hscpoint=findViewById(R.id.userhscpoint);
        circleImageView=findViewById(R.id.imageview);
        String id=user.getUid();

        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait...\nInformation Retriving...");
        dialog.show();

        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SaveUserInformation userInformation=snapshot.getValue(SaveUserInformation.class);
                Glide.with(getApplicationContext()).load(userInformation.getProfileImageUrl()).into(circleImageView);
                name.setText("Username: "+userInformation.getName());
                phone.setText("Phone Number: "+user.getPhoneNumber());
                sscpoint.setText("SSC Point: "+userInformation.getSscpoint());
                sscyear.setText("SSC Year: "+userInformation.getSscyear());
                hscpoint.setText("HSC Point: "+userInformation.getHscpoint());
                hscyear.setText("HSC Year: "+userInformation.getHscyear());
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                dialog.dismiss();
                Toast.makeText(HomeActivity.this, "No Information Found\n"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}