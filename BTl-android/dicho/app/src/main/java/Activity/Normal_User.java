package Activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.Activity.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import Fragment.*;


public class Normal_User extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        toolbar = getSupportActionBar();
        loadFragment(new ShopFragment());
        toolbar.setTitle("Shop");
        bottomNavigationView =(BottomNavigationView) findViewById(R.id.navigation_user);
        bottomNavigationView.setSelectedItemId(R.id.navigation_shop);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()){

                case R.id.navigation_shop:
                    toolbar.setTitle("Cửa Hàng");
                    fragment=new ShopFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_gifts:
                    toolbar.setTitle("Giỏ Hàng");
                    fragment=new CartFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_cart:
                    toolbar.setTitle("Danh mục");
                    fragment=new CategoryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("Cá nhân");
                    fragment=new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        });
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}