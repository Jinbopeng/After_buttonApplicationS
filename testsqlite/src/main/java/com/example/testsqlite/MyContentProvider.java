package com.example.testsqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.regex.Matcher;

public class MyContentProvider extends ContentProvider {
    private DBHelper dbHelper;
    private static final UriMatcher MATCHER=new UriMatcher(UriMatcher.NO_MATCH);
    private static  final int PERSONS=1;
    private static  final int PERSON=2;
    static {
        MATCHER.addURI("com.pengjinbo.personprovider","person",PERSONS);
        MATCHER.addURI("com.pengjinbo.personprovider","person/#",PERSON);
    }
    TextView tv;


    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;
        switch ( MATCHER.match(uri) ) {
            case PERSONS:
                count = db.delete("person", selection, selectionArgs);
                return count;
            case PERSON:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.delete("person", where, selectionArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (MATCHER.match(uri)){
            case PERSONS:
                 return "vnd.android.cursor.dir/person";
            case PERSON:
                return "vnd.android.cursor.item/person";
                default:
                    throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch ( MATCHER.match(uri) ) {
            case PERSONS:
                long rowid = db.insert("person", null, values);
                Uri insertUri = ContentUris.withAppendedId(uri, rowid);// 得到新增记录的Uri
                return insertUri;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper=new DBHelper(this.getContext(),"test.db",null,1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (MATCHER.match(uri)) {
            case PERSONS:
                return db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
            case PERSON:
                long id = ContentUris.parseId(uri); //使用ContentUris类的parse方法获得_id值
                String where = "_id=" + id; //注：创建person表时已经用的是"_id"
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where; //将_id参数补充到条件中
                }
                 return db.query("person", projection, where, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;
        switch ( MATCHER.match(uri) ) {
            case PERSONS:
                count = db.update("person", values, selection, selectionArgs);
                return count;
            case PERSON:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.update("person", values, where, selectionArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }
}
