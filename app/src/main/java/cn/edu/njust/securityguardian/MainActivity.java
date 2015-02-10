package cn.edu.njust.securityguardian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import cn.edu.njust.securityguardian.log.LogActivity;
import cn.edu.njust.securityguardian.privacy.PrivacyActivity;
import cn.edu.njust.securityguardian.ui.ImageItemView;


public class MainActivity extends Activity {

    private ImageItemView iiv_log;
    private ImageItemView iiv_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iiv_log = (ImageItemView)findViewById(R.id.iiv_log);
        iiv_log.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LogActivity.class);
                startActivity(intent);
            }
        });
        iiv_privacy=(ImageItemView) findViewById(R.id.iiv_privacy);
        iiv_privacy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, PrivacyActivity.class);
                startActivity(intent);
            }
        });


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
