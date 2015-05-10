package cn.edu.njust.securityguardian.privacyprotection.permission;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;

import com.yzy.supercleanmaster.bean.AppProcessInfo;

import java.util.List;

/**
 * Created by fookey on 15-5-10.
 */
public class PermissionService extends Service {

    public static final int PERMISSION_ALL=0;
    public static final int PERMISSION_READ_SMS=1;
    public static final int PERMISSION_SEND_SMS=2;
    public static final int PERMISSION_CONTACTS=3;
    public static final int PERMISSION_CALL=4;
    public static final int PERMISSION_CALL_LOG=5;
    public static final int PERMISSION_CALL_STAT=6;
    public static final int PERMISSION_NETWORK=7;
    public static final int PERMISSION_WIFI=8;
    public static final int PERMISSION_BLUETOOTH=9;
    public static final int PERMISSION_READ_INFO=10;

    public static final String ACTION_SCAN = "cn.edu.njust.securityguardian.privacyprotection.permission.SCAN";
    private static final String TAG = "PermisssionService";

    private OnProgressActionListener mActionListener;
    private boolean isScan=true;
    ActivityManager activityManager = null;
    List<AppProcessInfo> list = null;
    PackageManager packageManager = null;
    Context mContext;

    public static interface OnProgressActionListener {
        public void onScanStarted(Context context);

        public void onScanProgressUpdated(Context context, int current, int max);

        public void onScanCompleted(Context context, List<AppProcessInfo> apps);

    }
    public class PermissionServiceBinder extends Binder {

        public PermissionService getService() {
            return PermissionService.this;
        }
    }

    private PermissionServiceBinder mBinder = new PermissionServiceBinder();


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


}
