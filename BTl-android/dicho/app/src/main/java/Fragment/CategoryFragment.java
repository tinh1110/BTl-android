package Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Activity.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Adapter.CategoryAdapter;
import Model.CategoryModel;


public class CategoryFragment extends Fragment {
    RecyclerView cartRC;
    List<CategoryModel> categoryModelList;
    FirebaseFirestore db;
    CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_cart, container, false);
        cartRC=root.findViewById(R.id.cart_rec);
        db=FirebaseFirestore.getInstance();
        cartRC.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        categoryModelList = new ArrayList<>();
        categoryAdapter =new CategoryAdapter(getActivity(), categoryModelList);
        cartRC.setAdapter(categoryAdapter);
        db.collection("CartegoryProductions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            CategoryModel categoryModel = document.toObject(CategoryModel.class);
                            categoryModelList.add(categoryModel);
                            categoryAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Lỗi"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
        return root;
    }
}