package me.rds.angrydictionary.ui.activities.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import me.rds.angrydictionary.R;
import me.rds.angrydictionary.ui.activities.adapters.model.DifficultyLevel;

/**
 * Created by D1m11n on 20.01.2015.
 */
public class DifficultyAdapter extends ArrayAdapter<String> {

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     */

    private static final int ID_LAYOUT= R.layout.item_spn_difficulty;
    private static final int ID_TEXT=R.id.spn_tv;
    private static final int ID_IMAGE=R.id.spn_iv;
    private static String [] list;
    private static DifficultyAdapter mDifficultyAdapter;
    private Context mContext;




    private DifficultyAdapter(Context context) {
        super(context, ID_LAYOUT,ID_TEXT);
        mContext=context;
    }

    public static DifficultyAdapter getInstance(Context context){
        if(mDifficultyAdapter==null) {
            list = DifficultyLevel.getLevelsList(context);
            mDifficultyAdapter= new DifficultyAdapter(context);
        }
        return mDifficultyAdapter;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String getItem(int position) {
        return list[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return showView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
      return showView(position,convertView,parent);
    }

    private View showView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if( convertView == null ){
            LayoutInflater mInflater = ( LayoutInflater )mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            holder = new ViewHolder();
            convertView = mInflater.inflate(ID_LAYOUT, null, false );
            generateHolder( convertView, holder );
            convertView.setTag( holder );
        }else
            holder = ( ViewHolder )convertView.getTag();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
            holder.iv.setAlpha(0.95F/(5-getItemId(position)) + 0.05F);
        else{
            holder.iv.setAlpha((int)(245/(5-getItemId(position)))+ 10);
        }
        holder.tv.setText(getItem(position));
        return convertView;
    }

    private ViewHolder generateHolder(View convertView, ViewHolder holder) {
        holder.iv= ( ImageView )convertView.findViewById( ID_IMAGE );
        holder.tv = ( TextView )convertView.findViewById( ID_TEXT );
        return holder;
    }

    private class ViewHolder{

        TextView tv;
        ImageView iv;
    }
}
