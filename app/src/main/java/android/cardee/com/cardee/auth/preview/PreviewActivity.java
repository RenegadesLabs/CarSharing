package android.cardee.com.cardee.auth.preview;

import android.cardee.com.cardee.R;
import android.cardee.com.cardee.auth.register.RegisterActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
    }

    public void onLogInClicked(View view) {

    }

    public void onSignUpClicked(View view) {
        new Intent(this, RegisterActivity.class);
    }
}
