package br.com.livrowebservices.carros.fragment.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.fragment.CarrosFragment;
import br.com.livrowebservices.carros.fragment.FavoritosFragment;

/**
 * Created by ricardo on 29/08/15.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private Context context;

    public TabsAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        Fragment f = null;
        if (position == 0) {
            // todos
            f = new CarrosFragment();
        }else if (position == 1) {
            args.putString("tipo", "classicos");
            f = new CarrosFragment();
        } else if (position == 2) {
            args.putString("tipo", "esportivos");
            f = new CarrosFragment();
        } else if (position == 3) {
            args.putString("tipo", "luxo");
            f = new CarrosFragment();
        } else {
            f = new FavoritosFragment();
        }

        f.setArguments(args);
        return f;
    }

    @Override
    public int getCount() {
        return 5;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.todos);
            case 1:
                return context.getString(R.string.classicos);
            case 2:
                return context.getString(R.string.esportivos);
            case 3:
                return context.getString(R.string.luxo);
            default:
                return context.getString(R.string.favoritos);
        }
    }
}