package Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class ResetPass extends AppCompatActivity {
    private EditText emailuser;
    private Button resetpass;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset_pass);
        emailuser=(EditText)findViewById(R.id.edtemailresetpass);
        resetpass=(Button) findViewById(R.id.btnresetpass);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        resetpass.setOnClickListener(v->reset());
    }
    public void reset(){
        String email= emailuser.getText().toString().trim();
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
        progressDialog.setMessage("Đang gửi yêu cầu...");
        progressDialog.show();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressDialog.cancel();
                    Toast.makeText(ResetPass.this, "Kiểm tra email để khôi phục", Toast.LENGTH_LONG).show();
                }
                else {
                    progressDialog.cancel();
                    Toast.makeText(ResetPass.this, "Thất bại vui lòng kiểm tra lại thông tin", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}