package Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Adapter.RecommenedAdapter;
import Adapter.ViewAllAdapter;
import Model.ViewallModel;

public class ShopFragment extends Fragment {

    RecyclerView popularrec,exploreRec,recommendedRec;
    FirebaseFirestore db;
    //recommended
    List<ViewallModel> recommenedModelList;
    RecommenedAdapter recommenedAdapter;
    ///search
    EditText searchbar;
    private List<ViewallModel> viewallModelList;
    private RecyclerView recyclerViewSearch;
    private ViewAllAdapter viewAllAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root=  inflater.inflate(R.layout.fragment_shop, container, false);
        recommendedRec = root.findViewById(R.id.recommended_rec);
        //firebasedeclare
        db=FirebaseFirestore.getInstance();
        recommendedRec.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recommenedModelList= new ArrayList<>();
        recommenedAdapter=new RecommenedAdapter(getActivity(),recommenedModelList);
        recommendedRec.setAdapter(recommenedAdapter);
        db.collection("AllProduction")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ViewallModel recommenedModel= document.toObject(ViewallModel.class);
                            recommenedModelList.add(recommenedModel);
                            recommenedAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Lỗi"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
        //Search
        searchbar=root.findViewById(R.id.search_box);
        recyclerViewSearch=root.findViewById(R.id.search_rec);
        recyclerViewSearch.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        viewallModelList= new ArrayList<>();
        viewAllAdapter= new ViewAllAdapter(getContext(),viewallModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    viewallModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }
                else {
                    searchProduct(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        return root;
    }

    private void searchProduct(String type) {

        CollectionReference collectionRef = FirebaseFirestore.getInstance().collection("AllProduction");
        Query query = collectionRef.whereGreaterThanOrEqualTo("name", type).whereLessThanOrEqualTo("name", type + "\uf8ff");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()&&task.getResult()!=null){
                    viewallModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                    for(DocumentSnapshot doc: task.getResult().getDocuments()){
                        ViewallModel viewallModel=doc.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("loi",type);
            }
        });



    }

}