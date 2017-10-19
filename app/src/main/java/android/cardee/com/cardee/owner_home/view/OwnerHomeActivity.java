package android.cardee.com.cardee.owner_home.view;

import android.cardee.com.cardee.R;
import android.cardee.com.cardee.owner_home.view.helper.BottomNavigationHelper;
import android.cardee.com.cardee.owner_home.view.listener.ViewModeChangeListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

public class OwnerHomeActivity extends AppCompatActivity implements ViewModeChangeListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        AHBottomNavigation bottomMenu = (AHBottomNavigation) findViewById(R.id.bottom_menu);
        BottomNavigationHelper.prepare(bottomMenu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onViewModeChange(OptionMenuMode mode) {

    }
}
