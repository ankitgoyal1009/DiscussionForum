package com.sample.discussionforum.common.ui;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sample.discussionforum.R;
import com.sample.discussionforum.login.LoginViewModel;
import com.sample.discussionforum.login.ui.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

/**
 * This is the base activity which has logout functionality and all activities which needs this can extend from base activity.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_logout) {
            LoginViewModel viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
            viewModel.logout();
            LoginActivity.startActivity(this);
            this.finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
