package cn.edu.njust.securityguardian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import cn.edu.njust.securityguardian.privacyprotection.PrivacyProtectionActivity;
import cn.edu.njust.securityguardian.securityprotection.SecurityProtectionActivity;


public class MainActivity extends Activity implements OnClickListener{

    private LinearLayout ll_privacy_protection;
    private LinearLayout ll_security_protection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        ll_privacy_protection=(LinearLayout)findViewById(R.id.ll_privacy_protection);
        ll_security_protection=(LinearLayout)findViewById(R.id.ll_security_protection);
        ll_privacy_protection.setOnClickListener(this);
        ll_security_protection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.ll_privacy_protection :
                intent=new Intent(MainActivity.this, PrivacyProtectionActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_security_protection:
                intent=new Intent(MainActivity.this,SecurityProtectionActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
