package cn.edu.njust.securityguardian.privacyprotection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import cn.edu.njust.securityguardian.R;
import cn.edu.njust.securityguardian.privacyprotection.applock.AppLockActivity;

/**
 * Created by fookey on 15-2-7.
 *
 */
public class PrivacyProtectionActivity extends Activity {

    private RelativeLayout rl_permission_manager;
    private RelativeLayout rl_app_lock;
    private RelativeLayout rl_file_crypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_protection);

        rl_permission_manager=(RelativeLayout)findViewById(R.id.rl_permission_manager);
        rl_app_lock=(RelativeLayout)findViewById(R.id.rl_app_lock);
        rl_file_crypt=(RelativeLayout)findViewById(R.id.rl_file_crypt);

        rl_app_lock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PrivacyProtectionActivity.this, AppLockActivity.class);
                startActivity(intent);
            }
        });

    }


}
