package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Deklarasi Variabel
    private EditText etUsername, etPassword;
    private Button btnLogin, btnCancel;
    private SharedPreferences sharedPreferences;

    // Nama file penyimpanan SharedPreferences
    private static final String PREF_NAME = "MyDataAppPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi Komponen berdasarkan ID di XML
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Cek apakah user sudah login sebelumnya
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            goToDashboard();
        }

        // Logika Tombol Cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUsername.setText("");
                etPassword.setText("");
            }
        });

        // Logika Tombol Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validasi input kosong
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "User dan Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (username.equals("admin") && password.equals("admin123")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEY_IS_LOGGED_IN, true);
                    editor.apply();

                    Toast.makeText(MainActivity.this, "Anda Berhasil Login", Toast.LENGTH_SHORT).show();
                    goToDashboard();
                }
                else {
                    Toast.makeText(MainActivity.this, "Username dan Password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Intent untuk pindah halaman
    private void goToDashboard() {
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish(); // Menutup MainActivity agar tidak bisa di-back
    }
}