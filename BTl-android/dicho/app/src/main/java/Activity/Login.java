package Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class Login extends AppCompatActivity {
    private Button buttonlog;
    private EditText emailuser,passuser;
    private TextView forgotpass;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init();
    }
    public void init() {
        emailuser = findViewById(R.id.useremail);
        passuser = findViewById(R.id.pass);
        forgotpass = findViewById(R.id.edtforgotpass);
        buttonlog = (Button) findViewById(R.id.btlogin);
        progressDialog= new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        forgotpass.setOnClickListener(view -> {
            Intent i =new Intent(Login.this, ResetPass.class);
            startActivity(i);
        });
        buttonlog.setOnClickListener(view -> login());
    }
    public void login(){
        String email=emailuser.getText().toString().trim();
        String password=passuser.getText().toString().trim();
        if(email.isEmpty()){
            emailuser.setError("Bạn cần nhập email!");
            emailuser.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailuser.setError("Email không đúng vui lòng kiểm tra lại!");
            emailuser.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passuser.setError("Bạn chưa nhập mật khẩu!");
            passuser.requestFocus();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, Normal_User.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}