package cn.edu.njust.securityguardian.log;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.njust.securityguardian.R;

/**
 * Created by fookey on 15-2-5.
 *
 */
public class LogDetailsActivity extends Activity{

    LogDbOpenHelper logDbOpenHelper;
    /*构造AlertDialog.Builder（使用AlertDialog）*/
    Builder builder;
    TextView tv_info_date;
    TextView tv_info_time;
    TextView tv_info_way;
    TextView tv_info_app;
    TextView tv_info_app_ip;
    TextView tv_info_package_size_controller;
    TextView tv_info_package_size;
    Button btn_info_save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_details);

        logDbOpenHelper=new LogDbOpenHelper(this,LogDbOpenHelper.DATABASE_NAME,null,LogDbOpenHelper.DATABASE_VERSION);
        /*设置alertdialog*/
        builder=new Builder(this)
                .setTitle("提示")
                .setMessage("报告保存成功！");
        builder.setPositiveButton("确定",new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        tv_info_date=(TextView)findViewById(R.id.tv_log_date);
        tv_info_app=(TextView)findViewById(R.id.tv_log_app);
        tv_info_app_ip=(TextView)findViewById(R.id.tv_log_app_ip);
        tv_info_package_size=(TextView)findViewById(R.id.tv_log_package_size);
        tv_info_package_size_controller=(TextView)findViewById(R.id.tv_log_package_size_controller);
        tv_info_time=(TextView)findViewById(R.id.tv_log_time);
        tv_info_way=(TextView)findViewById(R.id.tv_log_way);
        btn_info_save=(Button)findViewById(R.id.btn_log_save);

        Intent intent=getIntent();
        String filename=intent.getStringExtra(LogDbOpenHelper.INFO_FILE_NAME);
        Cursor cursor=logDbOpenHelper.getData(new String[]{filename});
        while(cursor.moveToNext()){
            tv_info_app.setText(cursor.getString(cursor.getColumnIndex(LogDbOpenHelper.INFO_APP)));
            tv_info_app_ip.setText(cursor.getString(cursor.getColumnIndex(LogDbOpenHelper.INFO_APP_IP)));
            tv_info_package_size.setText(cursor.getString(cursor.getColumnIndex(LogDbOpenHelper.INFO_PACKAGE_SIZE)));
            tv_info_package_size_controller.setText(cursor.getString(cursor.getColumnIndex(LogDbOpenHelper.INFO_PACKAGE_SIZE_CONTROLLOR)));
            tv_info_time.setText(cursor.getString(cursor.getColumnIndex(LogDbOpenHelper.INFO_TIME)));
            tv_info_way.setText(cursor.getString(cursor.getColumnIndex(LogDbOpenHelper.INFO_WAY)));
            tv_info_date.setText(cursor.getString(cursor.getColumnIndex(LogDbOpenHelper.INFO_DATE)));

        }
        cursor.close();



    }
}
