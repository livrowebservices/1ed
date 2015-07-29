package br.com.livrowebservices.carros.fragment;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import livroandroid.lib.fragment.BaseFragment;

/**
 * Created by ricardo on 29/07/15.
 */
public class BaseLibFragment extends BaseFragment {

    protected void snack(View view,int msg, final Runnable runnable) {
        snack(view, getString(msg), runnable);
    }

    protected void snack(View view,int msg) {
        snack(view, getString(msg), null);
    }

    protected void snack(View view,String msg) {
        snack(view, msg, null);
    }

    protected void snack(View view,String msg, final Runnable runnable) {
        Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                })
                .show();
    }
}
