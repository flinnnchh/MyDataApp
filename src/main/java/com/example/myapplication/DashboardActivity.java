package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    // Deklarasi Variabel Komponen
    private EditText etNim, etNama, etProdi, etKelas, etAlamat, etEmail;
    private Button btnTambahData, btnLogout;
    private ListView listViewData;

    // Deklarasi Variabel untuk SharedPreferences (Sesi Login)
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MyDataAppPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    // Variabel untuk menampung data ListView
    private ArrayList<String> dataMahasiswaList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // 1. Cek Keamanan Sesi (Apakah user benar-benar sudah login?)
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            // Jika belum login, tendang kembali ke MainActivity (Halaman Login)
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return; // Hentikan eksekusi kode di bawahnya
        }

        // 2. Inisialisasi Komponen dari XML
        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        etProdi = findViewById(R.id.etProdi);
        etKelas = findViewById(R.id.etKelas);
        etAlamat = findViewById(R.id.etAlamat);
        etEmail = findViewById(R.id.etEmail);
        btnTambahData = findViewById(R.id.btnTambahData);
        btnLogout = findViewById(R.id.btnLogout);
        listViewData = findViewById(R.id.listViewData);

        // 3. Setup ArrayList dan Adapter untuk ListView
        dataMahasiswaList = new ArrayList<>();
        // Menggunakan layout bawaan android (simple_list_item_1) untuk list teks sederhana
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataMahasiswaList);
        listViewData.setAdapter(adapter);

        // 4. Logika Tombol "Tambah Data"
        btnTambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahData();
            }
        });

        // 5. Logika Tombol "Logout"
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hapus sesi login di SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Hapus semua data
                editor.apply();

                // Kembali ke halaman login
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Tutup DashboardActivity
            }
        });
    }

    // Method untuk menangani penambahan data ke ListView
    private void tambahData() {
        // Ambil nilai teks dari semua form input
        String nim = etNim.getText().toString().trim();
        String nama = etNama.getText().toString().trim();
        String prodi = etProdi.getText().toString().trim();
        String kelas = etKelas.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Validasi sederhana agar data utama tidak kosong
        if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty()) {
            Toast.makeText(this, "NIM, Nama, dan Prodi wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gabungkan data menjadi satu blok teks panjang
        String dataBaru = "NIM: " + nim + "\n" +
                "Nama: " + nama + "\n" +
                "Prodi/Kelas: " + prodi + " (" + kelas + ")\n" +
                "Alamat: " + alamat + "\n" +
                "Email: " + email;

        // Tambahkan ke dalam ArrayList
        dataMahasiswaList.add(dataBaru);

        // Beri tahu adapter bahwa ada data baru agar ListView diperbarui tampilannya
        adapter.notifyDataSetChanged();

        // Kosongkan form input setelah berhasil ditambah untuk input selanjutnya
        etNim.setText("");
        etNama.setText("");
        etProdi.setText("");
        etKelas.setText("");
        etAlamat.setText("");
        etEmail.setText("");

        Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
    }
}