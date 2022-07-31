package com.example.healing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Help extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        bottomNavigation = findViewById(R.id.bottomnav);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_wisata:
                        startActivity(new Intent(Help.this,Wisata.class));
                        finish();
                        break;
                    case R.id.nav_promosi:
                        if (!user.isEmailVerified()) {
                            startActivity(new Intent(Help.this,Profile.class));
                            Toast.makeText(Help.this, "Silahkan Verifikasi Akun Anda", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            startActivity(new Intent(Help.this,Promosi.class));
                        }
                        break;
                    case R.id.nav_home:
                        startActivity(new Intent(Help.this,MainActivity.class));
                        finish();
                        break;
                    case R.id.nav_profile:
                        if (!user.isEmailVerified()) {
                            startActivity(new Intent(Help.this,Profile.class));
                            Toast.makeText(Help.this, "Silahkan Verifikasi Akun Anda", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            startActivity(new Intent(Help.this,Profile.class));
                        }
                        break;
                }
                return true;
            }
        });
    }
}