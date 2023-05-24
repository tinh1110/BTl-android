package Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Activity.History;
import Activity.Normal_User;
import Activity.PlaceOrderActivity;
import Adapter.CartAdapter;
import Model.CartModel;

public class CartFragment extends Fragment {
    RecyclerView cart_RC;
    List<CartModel> cartModelList;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    CartAdapter cartAdapter;
    TextView totalAmount;
    Button buynow,history;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_gift, container, false);
        cart_RC=root.findViewById(R.id.recyclerview);
        totalAmount=root.findViewById(R.id.totalAmount);
        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        buynow=root.findViewById(R.id.buy_now);
        history=root.findViewById(R.id.history);
        cart_RC.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartModelList = new ArrayList<>();
        cartAdapter =new CartAdapter(getActivity(), cartModelList);
        cart_RC.setAdapter(cartAdapter);
        db.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){

                        String documentID= documentSnapshot.getId();

                        CartModel cartModel= documentSnapshot.toObject(CartModel.class);
                        cartModel.setId(documentID);
                        cartModelList.add(cartModel);
                        cartAdapter.notifyDataSetChanged();
                    }
                    calculateTotalAmount();
                }
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), History.class));
            }
        });
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartModelList.size()>0) {
                    Intent i = new Intent(getContext(), PlaceOrderActivity.class);
                    i.putExtra("itemlist", (Serializable) cartModelList);
//                    cartModelList.clear();
                    delete();
                    startActivity(i);
                }else {
                    Toast.makeText(view.getContext(), "Bạn chưa có mặt hàng nào! Hãy thêm một vài món vào giỏ hàng", Toast.LENGTH_LONG).show();
                    Intent i= new Intent(getContext(), Normal_User.class);
                    startActivity(i);
                }
            }
        });
        return root;
    }

    private void delete() {
        for (CartModel c:cartModelList){
            db.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                    .collection("AddToCart")
                    .document(c.getId()).delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){


                            }
                            else
                                Toast.makeText(getActivity(), "Lỗi "+task.getException().getMessage()+"!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        Toast.makeText(getActivity(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
        cartModelList.clear();


    }

    private void calculateTotalAmount() {
        double totalamount=0.0;
        for(CartModel cartModel:cartModelList){
            totalamount+=cartModel.getTotalPrice();
        }
        totalAmount.setText("Thành Tiền: "+totalamount+" đ");
    }

}