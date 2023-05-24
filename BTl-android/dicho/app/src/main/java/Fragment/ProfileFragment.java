package Fragment;

import android.content.Intent;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.Activity.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Activity.ChangePassword;
import Activity.Manage;
import Activity.UpdateUserActivity;
import Activity.Welcome;
import de.hdodenhof.circleimageview.CircleImageView;
import user.User;


public class ProfileFragment extends Fragment {

    CircleImageView profileImg;
    TextView uname,uphone,uaddress,usex,uemail;
    Button update,logout,change,Manage;
    FirebaseDatabase db;
    FirebaseUser user;
    DatabaseReference reference;
    String UserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
         profileImg = root.findViewById(R.id.User_profile_img);
        uname = root.findViewById(R.id.user_name);
        uphone = root.findViewById(R.id.user_phone);
        uaddress = root.findViewById(R.id.user_address);
        usex=root.findViewById(R.id.user_sex);
        uemail=root.findViewById(R.id.user_email);
        update = root.findViewById(R.id.button_update);
        logout=root.findViewById(R.id.button_logout);
        change=root.findViewById(R.id.button_change);
        Manage=root.findViewById(R.id.button_Manage);
        db=FirebaseDatabase.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        UserId=user.getUid();

        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile=snapshot.getValue(User.class);
                if(userprofile!=null){

                    uname.setText(userprofile.getName());
                    uemail.setText(userprofile.getEmail());
                    uphone.setText(userprofile.getPhone());
                    uaddress.setText(userprofile.getAddress());
                    usex.setText(userprofile.getSex());
                    Glide.with(getContext()).load(userprofile.getProfileImg()).into(profileImg);
                    if(userprofile.getEmail().equals("anhnhim1110@gmail.com")){
                        Manage.setVisibility(View.VISIBLE);
                    }else {
                        Manage.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi"+error.getMessage()+"!", Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), UpdateUserActivity.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(getContext(), Welcome.class));
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ChangePassword.class);
                startActivity(i);
            }
        });
        Manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Manage.class));
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile=snapshot.getValue(User.class);
                if(userprofile!=null){

                    uname.setText(userprofile.getName());
                    uemail.setText(userprofile.getEmail());
                    uphone.setText(userprofile.getPhone());
                    uaddress.setText(userprofile.getAddress());
                    usex.setText(userprofile.getSex());
                    Glide.with(getContext()).load(userprofile.getProfileImg()).into(profileImg);
                    if(userprofile.getEmail().equals("anhnhim1110@gmail.com")){
                        Manage.setVisibility(View.VISIBLE);
                    }else {
                        Manage.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi"+error.getMessage()+"!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}