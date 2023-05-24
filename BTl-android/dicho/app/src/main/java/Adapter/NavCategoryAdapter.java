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

import Activity.DetailedActivity;
import Activity.NavCategoryActivity;
import Model.NavCategoryModel;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHolder> {
    Context context;
    List<NavCategoryModel> navCategoryModelList;

    public NavCategoryAdapter(Context context, List<NavCategoryModel> navCategoryModelList) {
        this.context = context;
        this.navCategoryModelList = navCategoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_cat_det_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(navCategoryModelList.get(position).getImg_Url()).into(holder.img_nav_cat_det);
        holder.price.setText(String.valueOf(navCategoryModelList.get(position).getPrice()));
        holder.name.setText(navCategoryModelList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailedActivity.class);
                i.putExtra("detail",navCategoryModelList.get(holder.getAdapterPosition()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return navCategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price;
        ImageView img_nav_cat_det;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cat_name_navdet);
            price=itemView.findViewById(R.id.price);
            img_nav_cat_det=itemView.findViewById(R.id.nav_cat_img);
        }
    }
}
