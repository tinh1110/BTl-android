package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Activity.R;

import java.util.List;

import Activity.NavCategoryActivity;
import Model.CategoryModel;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> categoryModelList;

    public CategoryAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(categoryModelList.get(position).getImg_Url()).into(holder.cart_image);
        holder.name.setText(categoryModelList.get(position).getName());
        holder.description.setText(categoryModelList.get(position).getDescription());
        holder.discount.setText(categoryModelList.get(position).getDiscount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, NavCategoryActivity.class);
                i.putExtra("type",categoryModelList.get(holder.getAdapterPosition()).getType());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cart_image;
        TextView name,description,discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_image=itemView.findViewById(R.id.cart_img);
            name=itemView.findViewById(R.id.cart_name);
            description=itemView.findViewById(R.id.cart_des);
            discount=itemView.findViewById(R.id.cart_dis);
        }
    }
}
