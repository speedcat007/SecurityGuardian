package cn.edu.njust.securityguardian.log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fookey on 15-2-4.
 *
 */
public class LogDbOpenHelper extends SQLiteOpenHelper{

    public static String DATABASE_NAME="securityguardian.db";
    public static final String TABLE_NAME  ="log";
    public static int DATABASE_VERSION=1;

    /*表中的字段*/
    public static final String INFO_ID     ="_id";
    public static final String INFO_FILE_NAME ="file_name";
    public static final String INFO_DATA   ="data";
    public static final String INFO_TIME   ="time";
    public static final String INFO_WAY    ="way";
    public static final String INFO_APP    ="app";
    public static final String INFO_APP_IP ="app_ip";
    public static final String INFO_PORT   ="port";
    public static final String INFO_THREADSIZE = "thread_size";
    public static final String INFO_MAXSIZE="maxsize";

    /*创建表的SQL语句*/
    private static final String CREATETABLE ="CREATE TABLE "+TABLE_NAME+" ("
            +INFO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +INFO_FILE_NAME+" TEXT NOT NULL,"
            +INFO_DATA+" TEXT,"
            +INFO_TIME+" TEXT,"
            +INFO_WAY+" TEXT,"
            +INFO_APP+" TEXT,"
            +INFO_APP_IP+" TEXT,"
            +INFO_PORT+" TEXT,"
            +INFO_THREADSIZE+" TEXT,"
            +INFO_MAXSIZE+" TEXT"
            +")";

    public LogDbOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        DATABASE_NAME=name;
        DATABASE_VERSION=version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String info_data,String info_time,String info_way,
                           String info_app,String info_app_ip,String info_port,String info_threadsize,String info_maxsize){
        ContentValues cv=new ContentValues();
        cv.put(INFO_FILE_NAME,info_time+" "+info_data);
        cv.put(INFO_DATA,info_data);
        cv.put(INFO_TIME,info_time);
        cv.put(INFO_WAY,info_way);
        cv.put(INFO_APP,info_app);
        cv.put(INFO_APP_IP,info_app_ip);
        cv.put(INFO_PORT,info_port);
        cv.put(INFO_THREADSIZE,info_threadsize);
        cv.put(INFO_MAXSIZE,info_maxsize);
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(TABLE_NAME,null,cv);
    }

    public void deleteData(String[] info_file_names){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(TABLE_NAME,INFO_FILE_NAME+"=?",info_file_names);
    }

    /*得到日志文件名的集合*/
    public List<String> getLogFileNames(){
        List<String> logfilenames=new ArrayList<>();
        /*结果集的列名*/
        String[] result_columns=new String[]{
                INFO_ID,INFO_FILE_NAME
        };

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME, result_columns, null, null, null, null, null);
        /*INFO_FILE_NAME的索引*/
        int index_infoname=cursor.getColumnIndexOrThrow(INFO_FILE_NAME);

        while(cursor.moveToNext()){
            logfilenames.add(cursor.getString(index_infoname));
        }
        cursor.close();

        return  logfilenames;
    }

}
