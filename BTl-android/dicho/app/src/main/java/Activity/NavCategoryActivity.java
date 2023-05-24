package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Adapter.NavCategoryAdapter;
import Model.NavCategoryModel;
import Model.ViewallModel;

public class NavCategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ViewallModel> navCategoryModelList;
    NavCategoryAdapter navCategoryAdapter;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nav_category);
        String type= getIntent().getStringExtra("type");
        recyclerView=findViewById(R.id.nav_det_cat_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        navCategoryModelList=new ArrayList<ViewallModel>();

        navCategoryAdapter=new NavCategoryAdapter(this,navCategoryModelList);

        recyclerView.setAdapter(navCategoryAdapter);
        if(type!=null&& type.equalsIgnoreCase("Sữa")){
            db.collection("AllProduction").whereEqualTo("type","Sữa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel navCategoryModel= documentSnapshot.toObject(ViewallModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type!=null&& type.equalsIgnoreCase("Thịt")){
            db.collection("AllProduction").whereEqualTo("type","Thịt").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel navCategoryModel= documentSnapshot.toObject(ViewallModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type!=null&& type.equalsIgnoreCase("Rau củ")){
            db.collection("AllProduction").whereEqualTo("type","Rau củ").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel navCategoryModel= documentSnapshot.toObject(ViewallModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type!=null&& type.equalsIgnoreCase("Cá")){
            db.collection("AllProduction").whereEqualTo("type","Cá").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel navCategoryModel= documentSnapshot.toObject(ViewallModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type!=null&& type.equalsIgnoreCase("Thức ăn nhẹ")){
            db.collection("AllProduction").whereEqualTo("type","Thức ăn nhẹ").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel navCategoryModel= documentSnapshot.toObject(ViewallModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        if(type!=null&& type.equalsIgnoreCase("Nước giải khát")){
            db.collection("AllProduction").whereEqualTo("type","Nước giải khát").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewallModel navCategoryModel= documentSnapshot.toObject(ViewallModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

}