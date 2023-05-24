package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import Model.NavCategoryModel;
import Model.ViewallModel;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailed_img,additem,subitem;
    int total_quantity=1,total_price=0;
    TextView quantity,price,rating,des;
    Button add_to_cart;
    ActionBar toolbar;
    ViewallModel viewallModel=null;
    NavCategoryModel navCategoryModel=null;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detailed);

        toolbar = getSupportActionBar();

        toolbar.setTitle("Chi tiết sản phẩm");

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        final Object object=getIntent().getSerializableExtra("detail");

        if(object instanceof ViewallModel){
            viewallModel=(ViewallModel) object;
        }
        final Object object2=getIntent().getSerializableExtra("detail");

        if(object2 instanceof NavCategoryModel){
            navCategoryModel=(NavCategoryModel) object2;
        }
        quantity=findViewById(R.id.quantity);
        detailed_img=findViewById(R.id.detailed_img);
        additem=findViewById(R.id.add_item);
        subitem=findViewById(R.id.sub_item);
        price=findViewById(R.id.detailed_price);
        rating=findViewById(R.id.detailed_rate);
        des=findViewById(R.id.detailed_des);

        if(viewallModel!=null){
            Glide.with(getApplicationContext()).load(viewallModel.getImg_Url()).into(detailed_img);
            rating.setText(viewallModel.getRate());
            price.setText(viewallModel.getPrice()+"đ");
            des.setText(viewallModel.getDescription());
            total_price=viewallModel.getPrice()*total_quantity;
        }

        if(navCategoryModel!=null){
            Glide.with(getApplicationContext()).load(navCategoryModel.getImg_Url()).into(detailed_img);
            rating.setText(navCategoryModel.getRate());
            price.setText(navCategoryModel.getPrice()+"đ");
            des.setText(navCategoryModel.getDescription());
            total_price=  (navCategoryModel.getPrice())*total_quantity;
        }

        add_to_cart=findViewById(R.id.add_to_cart);
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        additem.setOnClickListener(view -> {
            if(total_quantity<10){
                total_quantity++;
                quantity.setText(String.valueOf(total_quantity));
                total_price=viewallModel.getPrice()*total_quantity;
            }
        });
        subitem.setOnClickListener(view -> {
            if(total_quantity>1){
                total_quantity--;
                quantity.setText(String.valueOf(total_quantity));
                total_price=viewallModel.getPrice()*total_quantity;
            }
        });
    }
    private void add(){
        String CurrentDate,CurrentTime;
        Calendar calForDate= Calendar.getInstance();

        SimpleDateFormat currnetdate= new SimpleDateFormat("dd/MM/yyyy");
        CurrentDate=currnetdate.format(calForDate.getTime());

        SimpleDateFormat currnettime= new SimpleDateFormat("HH:mm");
        CurrentTime=currnettime.format(calForDate.getTime());

        HashMap<String,Object> cart=new HashMap<>();
        cart.put("productName",viewallModel.getName());
        cart.put("productPrice",price.getText().toString());
        cart.put("currentDate",CurrentDate);
        cart.put("currentTime",CurrentTime);
        cart.put("totalQuantity",quantity.getText().toString());
        cart.put("totalPrice",total_price);
        firebaseFirestore.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                .collection("AddToCart").add(cart).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Đã thêm vào giỏ hàng vui lòng kiểm tra giỏ hàng của bạn", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}