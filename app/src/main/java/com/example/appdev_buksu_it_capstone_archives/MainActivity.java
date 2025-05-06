package com.example.appdev_buksu_it_capstone_archives;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CapstoneAdapter adapter;
    private EditText searchBar;

    private List<CapstoneModel> capstoneList;
    private List<CapstoneModel> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewCapstones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchBar = findViewById(R.id.searchBar);

        capstoneList = new ArrayList<>();
        capstoneList.add(new CapstoneModel("Smart Flood Detector", "Juan Dela Cruz, Maria Reyes", "IoT"));
        capstoneList.add(new CapstoneModel("QR-Based Attendance", "Ana Lim, Carlo Diaz", "Mobile App"));
        capstoneList.add(new CapstoneModel("E-Learning LMS", "Grace Santos, Leo Cruz", "Web Development"));

        adapter = new CapstoneAdapter(capstoneList);
        recyclerView.setAdapter(adapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void filter(String text) {
        filteredList = new ArrayList<>();
        for (CapstoneModel item : capstoneList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase()) ||
                    item.getCategory().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);
    }
}
