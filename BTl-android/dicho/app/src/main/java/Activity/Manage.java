package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Adapter.ManageAdapter;
import Adapter.RecommenedAdapter;
import Model.CategoryModel;
import Model.NavCategoryModel;
import Model.ViewallModel;

public class Manage extends AppCompatActivity {
    private RecyclerView rec;
    private ManageAdapter adapter;
    private List<ViewallModel> listAll= new ArrayList<>();

    private List<CategoryModel> listCate = new ArrayList<>();
    private Spinner spinner;
    FirebaseFirestore db;
    private FloatingActionButton add;
    private List<String> cat = new ArrayList<>();
    private List<String> listid = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        initView();
        db= FirebaseFirestore.getInstance();
        rec.setLayoutManager(new GridLayoutManager(this, 2));
        listAll= new ArrayList<>();

        adapter=new ManageAdapter(this,listAll,listid);
        cat.add("All");
        ImageView back = findViewById(R.id.imgBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        db.collection("CartegoryProductions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ViewallModel recommenedModel= document.toObject(ViewallModel.class);
                            cat.add(recommenedModel.getName());
                        }
                    } else {

                    }
                });
        spinner.setAdapter(new ArrayAdapter<>(this,R.layout.item_spinner,cat));
        rec.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Manage.this, AddProduct.class);
                startActivity(i);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(cat.get(position).equals("All")){
                    listAll.clear();
                    listid.clear();
                    db.collection("AllProduction")
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        ViewallModel recommenedModel= document.toObject(ViewallModel.class);
                                        listid.add(document.getId());
                                        listAll.add(recommenedModel);
                                        adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    listAll.clear();
                                    listid.clear();
                                    adapter.setList(listAll,listid);
                                    Toast.makeText(getApplication(), "Lỗi"+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }else {
                    listAll.clear();
                    listid.clear();
                        db.collection("AllProduction").whereEqualTo("type",cat.get(position)).get().
                                addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                                    ViewallModel recommenedModel= documentSnapshot.toObject(ViewallModel.class);
                                    listAll.add(recommenedModel);
                                    listid.add(documentSnapshot.getId());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });

                    adapter.setList(listAll,listid);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        listAll.clear();
        listid.clear();
        db.collection("AllProduction")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ViewallModel recommenedModel = document.toObject(ViewallModel.class);
                            listid.add(document.getId());
                            listAll.add(recommenedModel);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void initView() {
        rec = findViewById(R.id.rec_manage_product);
        spinner = findViewById(R.id.spinner);
        add = findViewById(R.id.add_fab);
        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner, cat));
        rec.setLayoutManager(new GridLayoutManager(this, 2));
    }
}