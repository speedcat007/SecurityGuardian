package cn.edu.njust.securityguardian.privacy;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fookey on 15-2-7.
 *
 */
public class PrivacyDbOpenHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME="securityguardian.db";
    public static final String TABLE_NAME  ="privacy";
    public static int DATABASE_VERSION=1;

    /*表中的字段*/
    public static final String INFO_ID  ="_id";
    public static final String INFO_PACKAGE_NAME="package_name";
    public static final String INFO_LOCKED="_locked";

    private static final String CREATE_TABLE="CREATE TABLE " +TABLE_NAME+"("
            +INFO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +INFO_PACKAGE_NAME+" TEXT,"
            +INFO_LOCKED+" INTEGER,"
            +")";
    private Context context;

    public PrivacyDbOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        DATABASE_NAME= name;
        DATABASE_VERSION=version;
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    //    PackageManager packageManager=context.getPackageManager();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXITS "+TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String info_package_name,Integer info_locked){
        ContentValues contentValues=new ContentValues();
        contentValues.put(INFO_PACKAGE_NAME,info_package_name);
        contentValues.put(INFO_LOCKED,info_locked);
        this.getWritableDatabase().insert(TABLE_NAME,null,contentValues);
    }

    public void deleteData(String[] info_package_names){
        this.getWritableDatabase().delete(TABLE_NAME,INFO_PACKAGE_NAME+"=?",info_package_names);

    }
    /*初始化table*/
    public void initTable(){
        List<String> lockedPackageNames=getLockedPackageNames();
        onUpgrade(this.getWritableDatabase(),DATABASE_VERSION,DATABASE_VERSION);
        PackageManager packageManager=context.getPackageManager();
        List<PackageInfo> packageInfoList=packageManager.getInstalledPackages(0);
        for(int i=0;i<packageInfoList.size();i++){
            PackageInfo packageInfo=packageInfoList.get(i);
            if(lockedPackageNames.contains(packageInfo.packageName)){
                insertData(packageInfo.packageName,1);
            }else{
                insertData(packageInfo.packageName,0);
            }
        }

    }

    public List<String> getLockedPackageNames(){
        List<String> stringList=new ArrayList<>();
        Cursor cursor=this.getWritableDatabase().query(TABLE_NAME,new String[]{INFO_PACKAGE_NAME},INFO_LOCKED+"=1",null,null,null,null);
        while(cursor.moveToNext()){
            stringList.add(cursor.getString(cursor.getColumnIndex(INFO_PACKAGE_NAME)));
        }
        return stringList;
    }
}
