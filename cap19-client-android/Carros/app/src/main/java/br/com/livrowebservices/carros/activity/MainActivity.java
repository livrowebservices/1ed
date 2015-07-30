package br.com.livrowebservices.carros.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.livrowebservices.carros.fragment.AboutDialog;
import br.com.livrowebservices.carros.fragment.CarrosFragment;
import br.com.livrowebservices.carros.R;
import livroandroid.lib.activity.BaseActivity;
import livroandroid.lib.utils.IntentUtils;

public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    private CoordinatorLayout coordinatorLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);

        // FAB Button
        findViewById(R.id.btAddCarro).setOnClickListener(onClickAddCarro());
    }

    private View.OnClickListener onClickAddCarro() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar
//                        .make(coordinatorLayout, "Adicionar carro.", Snackbar.LENGTH_LONG)
//                        .setAction("Ok", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "OK!", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .show();

                // Compat
                Intent intent = new Intent(getActivity(),CarroEditActivity.class);
                //ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), img, key);
                ActivityCompat.startActivity(getActivity(), intent, null);
            }
        };
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Tabs e ViewPager
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            if (position == 0) {
                args.putString("tipo", "classicos");
            } else if (position == 1) {
                args.putString("tipo", "esportivos");
            } else {
                args.putString("tipo", "luxo");
            }
            Fragment f = new CarrosFragment();
            f.setArguments(args);
            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.classicos);
                case 1:
                    return getString(R.string.esportivos);
                default:
                    return getString(R.string.luxo);
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
//        Snackbar
//                .make(coordinatorLayout, "Tab: " + tab.getText(), Snackbar.LENGTH_LONG)
//                .setAction("Ok", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "OK!", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .show();
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
        int id = item.getItemId();
        if (id == R.id.action_about) {
            AboutDialog.showAbout(getSupportFragmentManager());
            return true;
        } else if (id == R.id.action_site) {
            IntentUtils.openBrowser(this,getString(R.string.site_livro_webservice));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
