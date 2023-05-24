package Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import user.User;

public class UpdateUserActivity extends AppCompatActivity {
    CircleImageView profileImg;
    EditText name,phone,address;
    Button update;
    RadioButton male,female,other;

    FirebaseStorage storage;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase db;
    FirebaseFirestore firebaseFirestore;
    DatabaseReference myref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_user);
        //firebase
        storage=FirebaseStorage.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        profileImg=findViewById(R.id.profile_img);
        update=findViewById(R.id.update_profile);
        name=findViewById(R.id.edt_profile_name);
        phone=findViewById(R.id.edt_profile_phone);
        address=findViewById(R.id.edt_profile_add);
        male=findViewById(R.id.radioButtonMale);
        female=findViewById(R.id.radioButtonFemale);
        other=findViewById(R.id.radioButtonOther);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentuserId=user.getUid();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
         myref=firebaseDatabase.getReference("Users").child(currentuserId);

        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile != null) {
                    Glide.with(getApplication()).load(userprofile.getProfileImg()).into(profileImg);
                    name.setText(userprofile.getName());
                    phone.setText(userprofile.getPhone());
                    address.setText(userprofile.getAddress());
                    if (userprofile.getSex().equals("Nam")){
                        male.setChecked(true);
                    } else if (userprofile.getSex().equals("Nữ")) {
                        female.setChecked(true);
                    }else other.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateUserActivity.this, "Lỗi" + error.getMessage() + "!", Toast.LENGTH_SHORT).show();
            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,33);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }
    private void updateProfile(){

        String uname= name.getText().toString().trim();
        String uphone=phone.getText().toString().trim();
        String uaddress=address.getText().toString().trim();
        String usex;
        if(male.isChecked()){
            usex="Nam";
        }
        else if(female.isChecked()){
            usex="Nữ";
        }
        else usex="Khác";
        Map<String,Object> infor= new HashMap<>();
        infor.put("name",uname);
        infor.put("phone",uphone);
        infor.put("address",uaddress);
        infor.put("sex",usex);

        myref.updateChildren(infor, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(UpdateUserActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            Uri profileUri=data.getData();
            profileImg.setImageURI(profileUri);
            final StorageReference reference= storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UpdateUserActivity.this, "Đang upload", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            db.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profileImg").setValue(uri.toString());
                            Toast.makeText(UpdateUserActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}