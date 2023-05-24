package Adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import Activity.DetailedActivity;
import Activity.UpdateProduct;
import Model.ViewallModel;

public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.ViewHolder>  {
    List<ViewallModel> list;
    List<String> listid;
    Context context;
    FirebaseFirestore db;
    public ManageAdapter(Context context, List<ViewallModel> list,List<String> listid) {
        this.context = context;
        this.list = list;
        this.listid = listid;
    }
    @NonNull
    @Override
    public ManageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ManageAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        db= FirebaseFirestore.getInstance();

        Glide.with(context).load(list.get(position).getImg_Url()).into(holder.img);
        holder.price.setText(list.get(position).getPrice()+"đ");
        holder.name.setText(list.get(position).getName());
        holder.rate.setText(String.valueOf(list.get(position).getRate()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, UpdateProduct.class);
                i.putExtra("id",listid.get(position));
                i.putExtra("product",list.get(position));
                context.startActivity(i);
            }
        });
    }
    public void setList(List<ViewallModel> list,List<String> listid) {
        this.list = list;
        this.listid=listid;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, rate, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img =  itemView.findViewById(R.id.item_home_img);
            name =  itemView.findViewById(R.id.item_name_home);
            rate =  itemView.findViewById(R.id.item_rate_home);
            price =  itemView.findViewById(R.id.item_price_home);
        }
    }
}
