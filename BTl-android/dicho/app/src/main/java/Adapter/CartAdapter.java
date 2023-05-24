package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import Model.CartModel;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<CartModel> cartModelList;
    int totalPrice=0;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {

        holder.name.setText(cartModelList.get(position).getProductName());
        holder.price.setText(cartModelList.get(position).getProductPrice());
        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.time.setText(cartModelList.get(position).getCurrentTime());
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity());
        holder.totalprice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                        .collection("AddToCart")
                        .document(cartModelList.get(holder.getAdapterPosition()).getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    cartModelList.remove(cartModelList.get(holder.getAdapterPosition()));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Đã xóa khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(context, "Lỗi "+task.getException().getMessage()+"!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,date,time,quantity,totalprice;
        ImageView deleteItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.product_price);
            name=itemView.findViewById(R.id.product_name);
            date=itemView.findViewById(R.id.product_date);
            time=itemView.findViewById(R.id.product_time);
            quantity=itemView.findViewById(R.id.product_quantity);
            totalprice=itemView.findViewById(R.id.product_total_price);
            deleteItem=itemView.findViewById(R.id.delete);
        }
    }
}
