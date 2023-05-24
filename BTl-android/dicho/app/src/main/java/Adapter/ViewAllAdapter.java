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
import Model.ViewallModel;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {
    Context context;
    List<ViewallModel> viewallModelList;

    public ViewAllAdapter(Context context, List<ViewallModel> viewallModelList) {
        this.context = context;
        this.viewallModelList = viewallModelList;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(viewallModelList.get(position).getImg_Url()).into(holder.viewall_img);
        holder.name.setText(viewallModelList.get(position).getName());
        holder.price.setText(String.valueOf(viewallModelList.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailedActivity.class);
                i.putExtra("detail",viewallModelList.get(holder.getAdapterPosition()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewallModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,price;
        ImageView viewall_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewall_img=itemView.findViewById(R.id.viewall_img);
            name=itemView.findViewById(R.id.viewall_name);
            price=itemView.findViewById(R.id.viewall_price);
        }
    }
}
