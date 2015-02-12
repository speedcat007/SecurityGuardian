package cn.edu.njust.securityguardian.privacy;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.njust.securityguardian.R;
import cn.edu.njust.securityguardian.ui.CheckableRelativeLayout;
import cn.edu.njust.securityguardian.ui.Rotate3dAnimation;

/**
 * Created by fookey on 15-2-7.
 *
 */
public class PrivacyActivity extends Activity {

    private GridView gv_app_lock;
    private Button btn_open_app_lock;
    private boolean islocked=false;
    private float centerX;
    private float centerY;
    private ArrayList<HashMap<String,Object>> item;
    private ArrayList<String> locked_app_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock);

        gv_app_lock=(GridView)findViewById(R.id.gv_app_lock);
        btn_open_app_lock=(Button)findViewById(R.id.btn_open_app_lock);

        locked_app_names=new ArrayList<String>();
        PackageManager pm=getPackageManager();
        List<PackageInfo> installedPackageList=pm.getInstalledPackages(0);
        item=new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<installedPackageList.size();i++){
            if((installedPackageList.get(i).applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("app_icon", pm.getApplicationIcon(installedPackageList.get(i).applicationInfo));
                hashMap.put("app_name", installedPackageList.get(i).applicationInfo.loadLabel(pm));
                hashMap.put("package_name", installedPackageList.get(i).packageName);
                item.add(hashMap);
            }
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
        gv_app_lock.setOnItemClickListener(appLockGVItemClickListener());

        btn_open_app_lock.setOnClickListener(btnAppLockOnClickListener());

    }

    private OnItemClickListener appLockGVItemClickListener(){
        return new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                CheckableRelativeLayout v=(CheckableRelativeLayout)view;
                ImageView imageView=(ImageView)view.findViewById(R.id.iv_app_icon);
                centerX=imageView.getWidth()/2f;
                centerY=imageView.getHeight()/2f;
                Rotate3dAnimation rotation;
                HashMap<String,Object> hashMap=item.get(position);
                if(v.isChecked()){
                    rotation=new Rotate3dAnimation(0,90,centerX,centerY,0,false);
                    locked_app_names.add((String)hashMap.get("package_name"));
                }else {
                    rotation=new Rotate3dAnimation(360,270,centerX,centerY,0,false);
                    locked_app_names.remove((String)hashMap.get("package_name"));
                }
                if(locked_app_names.size()!=0){
                    btn_open_app_lock.setVisibility(View.VISIBLE);
                }else{
                    btn_open_app_lock.setVisibility(View.GONE);
                }
                rotation.setDuration(200);
                rotation.setFillAfter(true);
                rotation.setInterpolator(new AccelerateInterpolator());
                rotation.setAnimationListener(rotationAnimationListener(view));
                imageView.startAnimation(rotation);
            }
        };
    }

    private AnimationListener rotationAnimationListener(final View view){
        return new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                ImageView imageView=(ImageView)view.findViewById(R.id.iv_app_icon);
                Rotate3dAnimation rotation;
                if(((CheckableRelativeLayout)view).isChecked()){
                    rotation=new Rotate3dAnimation(270,360,centerX,centerY,0,false);
                }else {
                    rotation=new Rotate3dAnimation(90,0,centerX,centerY,0,false);
                }
                rotation.setDuration(200);
                rotation.setFillAfter(true);
                rotation.setInterpolator(new DecelerateInterpolator());
                imageView.startAnimation(rotation);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }

    private OnClickListener btnAppLockOnClickListener(){
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(islocked){
                    islocked=false;
                    btn_open_app_lock.setText("关闭应用锁");
                }else{
                    islocked=true;
                    btn_open_app_lock.setText("开启应用锁");
                }
            }
        };
    }

}
