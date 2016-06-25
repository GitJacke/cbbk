package example.com.cbk.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class DBhelper  {
    private MyDBHelper myDBHelper;
    private static DBhelper dBhelper;
    private static SQLiteDatabase db;
    private static DBhelper getInstance(Context context){
        if(dBhelper==null){
            synchronized (DBhelper.class){
                if(dBhelper==null){
                    dBhelper=new DBhelper(context);
                }
            }
        }
        return dBhelper;
    }

    private  DBhelper(Context context){
        myDBHelper=new MyDBHelper(context);
    }

    public static DBhelper getDBHelper(Context context){
        return getInstance(context);
    }

    //添加
    public boolean insert(String tableName,ContentValues contentValues){
        db=myDBHelper.getWritableDatabase();
        //-1为失败
        return db.insert(tableName,null,contentValues)>0?true:false;

    }

    /**
     * @param tableName 表名
     * @param columns 字段名
     * @param selection where 语句
     * @param selectionArgs where 语句里面的占位符所对应的值
     * @param groupBy 分组 -->null is ok
     * @param having     --> null is ok
     * @param orderBy 排列顺序 -->null is ok
     * @return
     */
    //查询
    public Cursor query(String tableName,String[] columns,String selection,String[] selectionArgs,
                        String groupBy,String having,String orderBy){
        db=myDBHelper.getReadableDatabase();
       return db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    /*
    查询所有
     */
    public Cursor queryAll(String tableName){
        db=myDBHelper.getReadableDatabase();
        return db.query(tableName,null,null,null,null,null,null);
    }

    //删除条件项
    public boolean delete(String tbName,String seletion,String[] selectionArgs){
        db=myDBHelper.getWritableDatabase();
       return db.delete(tbName,seletion,selectionArgs)==1?true:false;
    }

    /**
     * @param tbName 表名
     * @param contentValues 数据对
     * @param seletion 条件
     * @param selectionArgs 条件所对应的值
     * @return
     */
    public boolean updata(String tbName,ContentValues contentValues,String seletion,String[] selectionArgs){
        db=myDBHelper.getWritableDatabase();
        return  db.update(tbName,contentValues,seletion,selectionArgs)>0?true:false;
    }

    //只要URL 和列名 表名
    public boolean isExists(String tabName ,String columnName,String args){
        db=myDBHelper.getReadableDatabase();
        Cursor cursor= db.query(tabName,new String[]{columnName},columnName+"=?",new String[]{args},null,null,
                null,null);
        //返回数据为0条 并且对象不为空
        if(cursor.getCount()==0&&cursor!=null) {
            return false;
        }
        if(cursor==null){
            throw new IllegalArgumentException("args error");
        }
        return true;
    }


}
