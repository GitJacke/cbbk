package example.com.cbk.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    private  static String name="cbk.db";//数据库名字
    private static  int version=1;
   public static final String COLLECTION="collection";//收藏
   public static final String RECODE="recode";//浏览记录

    public MyDBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建两个表
        String collectionsql=
                "CREATE TABLE IF NOT EXISTS [collection] ([_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "[title] TEXT,[url] TEXT,[time] TEXT ,[desc] TEXT,[recount] INTEGER)";//不指定长度

        String recorde="CREATE TABLE IF NOT EXISTS [recode]([_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                "[title] TEXT ,[url] TEXT)";
        db.execSQL(collectionsql);
        db.execSQL(recorde);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
