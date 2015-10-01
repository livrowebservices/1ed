package br.com.livrowebservices.carros.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.fragment.AboutDialog;
import br.com.livrowebservices.carros.fragment.adapter.TabsAdapter;
import br.com.livrowebservices.carros.utils.PermissionUtils;
import livroandroid.lib.utils.IntentUtils;

public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private CoordinatorLayout coordinatorLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        // Toolbar
        setupToolbar();

        setupNavDrawer(this);

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        TabsAdapter adapter = new TabsAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

        // Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setOnTabSelectedListener(this);

        // FAB Button
        findViewById(R.id.btAddCarro).setOnClickListener(onClickAddCarro());
    }

    private View.OnClickListener onClickAddCarro() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Compat
                Intent intent = new Intent(getActivity(), CarroActivity.class);
                intent.putExtra("editMode", true);
                //ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), img, key);
                ActivityCompat.startActivity(getActivity(), intent, null);
            }
        };
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        snack(coordinatorLayout, "Clicou em: " + menuItem);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_about:
            case R.id.action_about:
                AboutDialog.showAbout(getSupportFragmentManager());
                return true;
            case R.id.action_site:
                IntentUtils.openBrowser(this, getString(R.string.site_livro_webservice));
                return true;
            case R.id.nav_item_config:
                snack(drawerLayout, "Clicou em config");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
