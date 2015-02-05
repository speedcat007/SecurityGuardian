package cn.edu.njust.securityguardian.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created by fookey on 15-2-4.
 */
public class SGConfig {

    private final String CONFIG_NAME="CONFIGS";
    private final String CONFIG_APP_LOCK="CONFIG_LOCK";/*应用锁*/
    private final String CONFIG_APP_LOCK_INIT="CONFIG_LOCK_INIT";/*应用锁是否初始化*/
    private final String CONFIG_IP_UPLOAD="CONFIG_IP_UPLOAD";/*上传ip*/
    private final String CONFIG_LEARN_INIT="CONFIG_LEARN_INIT";


    private Context context;
    private SharedPreferences sharedPreferences;

    public SGConfig(Context context) {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(CONFIG_NAME,Context.MODE_PRIVATE);
    }

    /*初始化appLock*/
    public void appLockInit(){
        boolean isInit=sharedPreferences.getBoolean(CONFIG_APP_LOCK_INIT,false);
        if(!isInit){
            Editor editor=sharedPreferences.edit();
            editor.putBoolean(CONFIG_APP_LOCK,false);
            editor.putBoolean(CONFIG_APP_LOCK_INIT,true);
            editor.apply();
        }
        Log.d("SGConfig","appLockInit():isInit = "+isInit);

    }

    public boolean isLearnInit(){
        boolean isInit=sharedPreferences.getBoolean(CONFIG_LEARN_INIT,false);
        if(!isInit){
            Editor editor=sharedPreferences.edit();
            editor.putBoolean(CONFIG_LEARN_INIT,true);
            editor.apply();
        }
        Log.d("SGConfig","isLearnInit():isInit = "+isInit);
        return isInit;
    }
}
