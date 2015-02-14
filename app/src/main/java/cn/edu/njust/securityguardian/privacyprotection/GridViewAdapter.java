package cn.edu.njust.securityguardian.privacyprotection;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by fookey on 15-2-10.
 */
public class GridViewAdapter extends SimpleAdapter {

    private Context context;
    private int resource;
    private List<? extends Map<String,?>> data;
    private String[] from;
    private int[] to;

    public GridViewAdapter(Context context, List<? extends Map<String, ?>> data,
                           int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context=context;
        this.resource=resource;
        this.data=data;
        this.from=from;
        this.to=to;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    //    LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view;
        int count;
        View[] holder;

        count=from.length;

        holder=new View[count];

        if(convertView==null||convertView.getTag()==null){
            view=layoutInflater.inflate(resource,parent,false);
            for(int i=0;i<count;i++){
                holder[i]=view.findViewById(to[i]);
            }
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(View[])view.getTag();
        }
        Map dataSet=data.get(position);
        for(int i=0;i<count;i++){
            View v=holder[i];
            Object data=dataSet.get(from[i]);
            if(v instanceof TextView){
                   setViewText((TextView)v,data.toString());
               }else if (v instanceof ImageView){
                   ((ImageView) v).setImageDrawable((Drawable)data);
               }else{
                   throw new IllegalStateException(v.getClass().getName()+"is not a "+
                   "view that can be bounds by this SimpleAdapter");
               }
        }
        view=super.getView(position, convertView,parent);
        if(view==null){
            System.out.print("hahahah");
        }

        return view;
    }


}
