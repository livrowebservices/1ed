package br.com.livrowebservices.carros.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ricardo on 08/08/15.
 */
public class NavDrawerUtil {
    public static void setHeaderValues(View navDrawerView, int listViewContainerId, int imgNavDrawerHeaderId, int imgUserUserPhotoId, int stringNavUserName, int stringNavUserEmail) {

        View view = navDrawerView.findViewById(listViewContainerId);
        if(view == null) {
            return;
        }

        view.setVisibility(View.VISIBLE);

        ImageView imgUserBackground = (ImageView) view.findViewById(br.com.livroandroid.androidutils.R.id.imgUserBackground);

        if (imgUserBackground == null) {
            return;
        }

        imgUserBackground.setImageResource(imgNavDrawerHeaderId);

        TextView tUserName = (TextView) view.findViewById(br.com.livroandroid.androidutils.R.id.tUserName);
        TextView tUserEmail = (TextView) view.findViewById(br.com.livroandroid.androidutils.R.id.tUserEmail);

        ImageView imgUserPhoto = (ImageView) view.findViewById(br.com.livroandroid.androidutils.R.id.imgUserPhoto);
        if (imgUserPhoto != null) {
            imgUserPhoto.setImageResource(imgUserUserPhotoId);
        }

        if (tUserName != null && tUserEmail != null) {
            tUserName.setText(stringNavUserName);
            tUserEmail.setText(stringNavUserEmail);
        }
    }
}
