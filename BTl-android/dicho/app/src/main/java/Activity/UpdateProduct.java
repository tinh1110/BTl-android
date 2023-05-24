package Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

import com.bumptech.glide.Glide;
import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.ViewallModel;

public class UpdateProduct extends AppCompatActivity {
    private ImageView addImg;
    private TextInputLayout nameLayout,priceLayout,desLayout;
    private FirebaseStorage storage;
    private TextInputEditText name, price, des;
    private AppCompatButton update, delete;
    private Spinner spinner;
    private ViewallModel product;
    FirebaseFirestore db;
    private List<String> cat= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        initView();
        db= FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        String idPro = intent.getStringExtra("id");
        product = (ViewallModel) intent.getSerializableExtra("product");
        cat.add("All");
        Log.d("loi2",idPro);

        db.collection("CartegoryProductions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ViewallModel recommenedModel= document.toObject(ViewallModel.class);
                            cat.add(recommenedModel.getName());
                            spinner.setAdapter(new ArrayAdapter<>(this,R.layout.item_spinner,cat));
                            int position = 0;
                            for (int i =0;i<cat.size();i++){
                                if (cat.get(i).equals(product.getType())){
                                    position = i;
                                    Log.d("list",position+"");
                                    break;
                                }
                            }
                            spinner.setSelection(position);

                        }
                    } else {

                    }
                });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                product.setType(cat.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Glide.with(getApplication()).load(product.getImg_Url()).into(addImg);
        name.setText(product.getName());
        des.setText(product.getDescription());
        price.setText(String.valueOf(product.getPrice()));
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, 1);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setName(name.getText().toString());
                product.setPrice(Integer.parseInt(price.getText().toString()));
                product.setDescription(des.getText().toString());
                Log.d("product", product.toString());
                db.collection("AllProduction").document(idPro).set(product);
                Toast.makeText(UpdateProduct.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("AllProduction").document(idPro).delete();
                Toast.makeText(UpdateProduct.this, "Xóa thành công", Toast.LENGTH_SHORT).show();

                finish();
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
        spinner = findViewById(R.id.spinner_add_book);
        update = findViewById(R.id.update_book_btn);
        delete = findViewById(R.id.delete_book_btn);

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
                    Toast.makeText(UpdateProduct.this, "Đang upload", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            product.setImg_Url(uri.toString());
                            Toast.makeText(UpdateProduct.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}