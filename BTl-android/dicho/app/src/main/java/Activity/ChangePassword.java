package Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import user.User;

public class ChangePassword extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText oldP,newP, conP;
    AppCompatButton change;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        firebaseAuth = FirebaseAuth.getInstance();
        oldP = findViewById(R.id.txtOld);
        newP = findViewById(R.id.txtNewP);
        conP = findViewById(R.id.txtConfP);
        user=FirebaseAuth.getInstance().getCurrentUser();
        change = findViewById(R.id.buttonchange);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        String uid=user.getUid();
        DatabaseReference myref=firebaseDatabase.getReference("Users").child(uid);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPass = newP.getText().toString();
                String oldPass =oldP.getText().toString();
                String conPass = conP.getText().toString();
                Log.d("Loi",newPass );
                myref.addValueEventListener (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userprofile=snapshot.getValue(User.class);

                        if (oldPass.equals(userprofile.getPassword()) &&newPass.equals(conPass) &&
                                newPass.length()>=6){
                            Map<String,Object> map = new HashMap<>();
                            map.put("password",newPass);
                            Log.d("Loif",newPass );
                            user.updatePassword(newPass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ChangePassword.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChangePassword.this, "Thay đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                                }
                            });

                            myref.updateChildren(map, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    startActivity(new Intent(ChangePassword.this, Normal_User.class));
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ChangePassword.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





    }
}