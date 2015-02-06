package cn.edu.njust.securityguardian.log;

import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.njust.securityguardian.R;
import cn.edu.njust.securityguardian.config.SGConfig;

/**
 * Created by fookey on 15-2-2.
 */
public class LogActivity extends ListActivity {

    private List<String> fileNames = null;
    private LogDbOpenHelper logDbOpenHelper;
    private SGConfig sgConfig;
    private ListView listView;
    private ArrayList<HashMap<String,Object>> arrayList;
    private SimpleAdapter simpleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        logDbOpenHelper=new LogDbOpenHelper(this,LogDbOpenHelper.DATABASE_NAME,null,LogDbOpenHelper.DATABASE_VERSION);
        sgConfig=new SGConfig(this);
        listView=(ListView)findViewById(R.id.lv_log_file_list);

        arrayList=new ArrayList<HashMap<String, Object>>();
        fileNames=logDbOpenHelper.getLogFileNames();
        for(int i=0;i<fileNames.size();i++){
            HashMap<String,Object> item=new HashMap<String,Object>();
            item.put("filename","日志"+i+" 时间"+fileNames.get(i));
            arrayList.add(item);
        }

        simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.item_log,
                new String[]{"filename"},new int[]{R.id.tv_log_item_name});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(onItemClick());
        listView.setOnItemLongClickListener(onItemLongClick());
    }
    private OnItemClickListener onItemClick(){
        OnItemClickListener onItemClickListener=new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(LogActivity.this,LogDetailsActivity.class);
                intent.putExtra(LogDbOpenHelper.INFO_FILE_NAME,fileNames.get(position));
                startActivity(intent);
            }
        };
        return onItemClickListener;
    }

    private OnItemLongClickListener onItemLongClick(){
        OnItemLongClickListener onItemLongClickListener=new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String filename=fileNames.get(position);
                new Builder(LogActivity.this).setTitle("提示")
                        .setMessage("日志"+position+" 时间："+filename+"\n\t        确认要删除么？")
                        .setPositiveButton("确定",new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logDbOpenHelper.deleteData(new String[]{filename});
                                arrayList.remove(position);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消",new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                return true;
            }
        };
        return  onItemLongClickListener;
    }

}
