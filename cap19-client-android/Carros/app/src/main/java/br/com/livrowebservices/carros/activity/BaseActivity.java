package br.com.livrowebservices.carros.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.fragment.AboutDialog;
import br.com.livrowebservices.carros.utils.NavDrawerUtil;
import livroandroid.lib.utils.IntentUtils;

/**
 * Created by ricardo on 08/08/15.
 */
public class BaseActivity extends livroandroid.lib.activity.BaseActivity {
    protected DrawerLayout drawerLayout;

    // Configura a Toolbar
    protected void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Configura o Nav Drawer
    protected void setupNavDrawer(final NavigationView.OnNavigationItemSelectedListener navListener) {
        // Drawer Layout
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            NavDrawerUtil.setHeaderValues(navigationView, R.id.containerNavDrawerListViewHeader, R.drawable.nav_drawer_header, R.drawable.ic_logo_user, R.string.nav_drawer_username, R.string.nav_drawer_email);
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            menuItem.setChecked(true);


                            drawerLayout.closeDrawers();
                            if (navListener != null) {
                                navListener.onNavigationItemSelected(menuItem);
                            }
                            return true;
                        }
                    });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Abre o menu lateral
    protected void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    // Fecha o menu lateral
    protected void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
