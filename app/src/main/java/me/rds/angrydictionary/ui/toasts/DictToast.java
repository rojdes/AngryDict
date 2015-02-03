package me.rds.angrydictionary.ui.toasts;

import android.app.Service;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import me.rds.angrydictionary.R;

/**
 * Created by D1m11n on 02.02.2015.
 */
public class DictToast  {


    private static final int OFFSET_Y=100;


    public static void createToast(Context context,String msg){
        LayoutInflater inflater= (LayoutInflater) context.getApplicationContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View customToastroot =inflater.inflate(R.layout.toast, null, false);
        Toast customtoast=new Toast(context);
        customtoast.setView(customToastroot);
        customtoast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0, OFFSET_Y);
        customtoast.setDuration(Toast.LENGTH_LONG);
        customtoast.setText(String.valueOf(msg));
        customtoast.show();

    }

}
