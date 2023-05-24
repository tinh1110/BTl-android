package Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.CategoryModel;
import Model.ViewallModel;

public class AddProduct extends AppCompatActivity {
    private ImageView addImg;
    private TextInputLayout nameLayout,priceLayout,
            desLayout;
    private TextInputEditText name, price, des;
    private Spinner spinner;
    private FirebaseStorage storage;
    private ViewallModel product=new ViewallModel();
    private CategoryModel category=new CategoryModel();
    private AppCompatButton add;
    private FloatingActionButton addCat;
    private AlertDialog dialog;
    private ImageView addCatImg;
    FirebaseFirestore db;
    private List<String> cat= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initView();
        db= FirebaseFirestore.getInstance();
        cat.add("All");
        db.collection("CartegoryProductions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ViewallModel recommenedModel= document.toObject(ViewallModel.class);
                            cat.add(recommenedModel.getName());
                        }
                    } else {

                    }
                });
        spinner.setAdapter(new ArrayAdapter<>(this,R.layout.item_spinner,cat));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                product.setType(cat.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, 1);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            product.setName(name.getText().toString());
            product.setPrice(Integer.parseInt(price.getText().toString()));
            product.setDescription(des.getText().toString());
            Random r = new Random();
            float rate = Float.parseFloat(fmt(0f + r.nextFloat() * (5f - 0f)));
            product.setRate(rate+"");

            db.collection("AllProduction").document(product.getName()).set(product);
                Toast.makeText(AddProduct.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                finish();
            }


        });
        addCatImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initView() {
        addImg = findViewById(R.id.add_book_img);
        nameLayout = findViewById(R.id.edt_add_book_name);
        priceLayout = findViewById(R.id.edt_add_book_price);
        desLayout = findViewById(R.id.edt_add_book_des);
        name = findViewById(R.id.textInput_add_book_name);
        price = findViewById(R.id.textInput_add_book_price);
        des = findViewById(R.id.textInput_add_book_des);
        add = findViewById(R.id.add_book_btn);
        addCat = findViewById(R.id.add_cat);
        spinner = findViewById(R.id.spinner_add_book);
        storage = FirebaseStorage.getInstance();
        addCatImg = findViewById(R.id.add_cat);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            Uri profileUri=data.getData();
            addImg.setImageURI(profileUri);
            final StorageReference reference= storage.getReference().child("product")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddProduct.this, "Đang upload", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            product.setImg_Url(uri.toString());
                            Toast.makeText(AddProduct.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%.2f",d);
    }
}