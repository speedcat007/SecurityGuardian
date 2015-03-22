package cn.edu.njust.securityguardian.privacyprotection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import cn.edu.njust.securityguardian.R;
import cn.edu.njust.securityguardian.privacyprotection.filecrypt.FileExplorerActivity;
import cn.edu.njust.securityguardian.privacyprotection.permission.PermissionManagerActivity;

/**
 * Created by fookey on 15-2-7.
 *
 */
public class PrivacyProtectionActivity extends Activity implements OnClickListener{

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

        rl_app_lock.setOnClickListener(this);
        rl_permission_manager.setOnClickListener(this);
        rl_file_crypt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.rl_permission_manager:
                intent=new Intent(this, PermissionManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_app_lock:
                intent=new Intent(PrivacyProtectionActivity.this, PermissionManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_file_crypt:
                intent=new Intent(this, FileExplorerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
