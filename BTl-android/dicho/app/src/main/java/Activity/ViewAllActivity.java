package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Adapter.ViewAllAdapter;
import Model.ViewallModel;

public class ViewAllActivity extends AppCompatActivity {
    RecyclerView viewall_Rec;
    List<ViewallModel> viewallModelList;
    ViewAllAdapter viewAllAdapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_all);
        db=FirebaseFirestore.getInstance();
        String type= getIntent().getStringExtra("type");
        viewall_Rec=findViewById(R.id.viewall_Rec);
        viewall_Rec.setLayoutManager(new GridLayoutManager(this,2));
        viewallModelList= new ArrayList<>();
        viewAllAdapter= new ViewAllAdapter(this,viewallModelList);

        viewall_Rec.setAdapter(viewAllAdapter);
        //Rau củ
        if(type!=null&& type.equalsIgnoreCase("Rau củ")){
            db.collection("AllProduction").whereEqualTo("type","Rau củ").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel viewallModel= documentSnapshot.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //Cá
        if(type!=null&& type.equalsIgnoreCase("Cá")){
            db.collection("AllProduction").whereEqualTo("type","Cá").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel viewallModel= documentSnapshot.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //thịt
        if(type!=null&& type.equalsIgnoreCase("Thịt")){
            db.collection("AllProduction").whereEqualTo("type","Thịt").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel viewallModel= documentSnapshot.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //Sữa
        if(type!=null&& type.equalsIgnoreCase("Sữa")){
            db.collection("AllProduction").whereEqualTo("type","Sữa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel viewallModel= documentSnapshot.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //Trứng
        if(type!=null&& type.equalsIgnoreCase("Trứng")){
            db.collection("AllProduction").whereEqualTo("type","Trứng").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel viewallModel= documentSnapshot.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //Hoa quả
        if(type!=null&& type.equalsIgnoreCase("Hoa quả")){
            db.collection("AllProduction").whereEqualTo("type","Hoa quả").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel viewallModel= documentSnapshot.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}