package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Activity.R;

import java.text.BreakIterator;
import java.util.List;

import Model.CartModel;
import Model.ViewallModel;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    Context context;
    List<CartModel> ModelList;
    public HistoryAdapter(Context context, List<CartModel> ModelList) {
        this.context = context;
        this.ModelList = ModelList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(ModelList.get(position).getProductName());
        holder.price.setText("Price:"+ModelList.get(position).getProductPrice());
        holder.date.setText("Date:"+ModelList.get(position).getCurrentDate());
        holder.quantity.setText("Quantity:"+String.valueOf(ModelList.get(position).getTotalQuantity()));
    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,date,quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.cart_name);
            price=itemView.findViewById(R.id.Price);
            date=itemView.findViewById(R.id.Date);
            quantity=itemView.findViewById(R.id.Quantity);
        }
    }
}
