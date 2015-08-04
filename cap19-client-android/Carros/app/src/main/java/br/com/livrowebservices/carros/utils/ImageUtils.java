package br.com.livrowebservices.carros.utils;

import android.content.Context;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import br.com.livrowebservices.carros.R;

/**
 * Created by ricardo on 30/07/15.
 */
public class ImageUtils {
    public static void setImage(Context context, String url_img, ImageView img) {
        if(url_img != null && url_img.trim().length() > 0 && URLUtil.isValidUrl(url_img)) {
            Picasso.with(context).load(url_img).into(img);
        } else {
            img.setImageResource(R.drawable.placeholder);
        }
    }

    public static void setImage(Context context, File file, ImageView img) {
        if(file != null && file.exists()) {
            Picasso.with(context).load(file).into(img);
        } else {
            img.setImageResource(R.drawable.placeholder);
        }
    }
}
