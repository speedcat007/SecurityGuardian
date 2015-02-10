package cn.edu.njust.securityguardian.privacy;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.njust.securityguardian.R;

/**
 * Created by fookey on 15-2-7.
 *
 */
public class PrivacyActivity extends Activity {

    private GridView gv_app_lock;
    private ImageView iv_app_icon;
    private TextView tv_app_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock);

        gv_app_lock=(GridView)findViewById(R.id.gv_app_lock);

        PackageManager pm=getPackageManager();
        List<PackageInfo> installedPackageList=pm.getInstalledPackages(0);
        ArrayList<HashMap<String,Object>> item=new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<installedPackageList.size();i++){
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("app_icon",pm.getApplicationIcon(installedPackageList.get(i).applicationInfo));
            hashMap.put("app_name",installedPackageList.get(i).applicationInfo.loadLabel(pm));
            item.add(hashMap);
        }
        SimpleAdapter gridViewAdapter=new SimpleAdapter(this,item,R.layout.item_app,
                new String[]{"app_icon","app_name"},new int[]{R.id.iv_app_icon,R.id.tv_app_name});

        ViewBinder viewBinder=new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView){
                    ImageView imageView=(ImageView)view;
                    imageView.setImageDrawable((Drawable)data);
                    imageView.setMaxHeight(60);
                    imageView.setMaxWidth(60);
                    return true;
                }
                return false;
            }
        };
        gridViewAdapter.setViewBinder(viewBinder);
        gv_app_lock.setAdapter(gridViewAdapter);
        gv_app_lock.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }


}
