package Activity;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import Adapter.HistoryAdapter;
import Adapter.RecommenedAdapter;
import Model.CartModel;
import Model.ViewallModel;

public class History extends AppCompatActivity {
    FirebaseFirestore db;

    RecyclerView rec;
    List<CartModel> ModelList;
    HistoryAdapter historyAdapter;
    FirebaseAuth firebaseAuth;
    private List<CartModel> cartModelList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        rec = findViewById(R.id.history);
        db=FirebaseFirestore.getInstance();
        ModelList= new ArrayList<>();
        historyAdapter=new HistoryAdapter(this,ModelList);
        firebaseAuth= FirebaseAuth.getInstance();
        rec.setAdapter(historyAdapter);
        db.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                .collection("MyOrder")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CartModel cartModel= document.toObject(CartModel.class);
                            Log.d("loi", String.valueOf(cartModel));
                            ModelList.add(cartModel);
                            historyAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(this, "Lỗi"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}