package com.example.accphys;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.welcomeFragment,
                R.id.questionFragment,
                R.id.theoryFragment,
                R.id.helpFragment,
                R.id.simulatorFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        navController.addOnDestinationChangedListener(this::onDestinationChanged);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_welcome) {
            navController.navigate(R.id.welcomeFragment);
            return true;
        } else if (id == R.id.nav_questions) {
            navController.navigate(R.id.questionFragment);
            return true;
        } else if (id == R.id.nav_theory) {
            navController.navigate(R.id.theoryFragment);
            return true;
        } else if (id == R.id.nav_help) {
            navController.navigate(R.id.helpFragment);
            return true;
        } else if (id == R.id.nav_simulator) {
            navController.navigate(R.id.simulatorFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDestinationChanged(@NonNull NavController controller,
                                     @NonNull NavDestination destination,
                                     @Nullable Bundle arguments) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            boolean isTopLevel = destination.getId() == R.id.welcomeFragment
                    || destination.getId() == R.id.questionFragment
                    || destination.getId() == R.id.theoryFragment
                    || destination.getId() == R.id.helpFragment
                    || destination.getId() == R.id.simulatorFragment;
            actionBar.setDisplayHomeAsUpEnabled(!isTopLevel);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, (Openable) null);
    }

}
