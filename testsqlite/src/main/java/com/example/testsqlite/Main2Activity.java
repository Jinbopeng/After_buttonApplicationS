package com.example.testsqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URI;

public class Main2Activity extends Activity {
    TextView tv;
    DBHelper dbHelper;
    ListView listView;
    ContentResolver resolver;
    String id;
    String name;
    String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        select();
        //给listview注册上下文菜单
        listView = (ListView) findViewById(R.id.listview);
        registerForContextMenu(listView);

        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    //建立上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("操作");
        getMenuInflater().inflate(R.menu.mymenu, menu);
    }

    //建立上下文菜单点击事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        View view = info.targetView;
        id = ((TextView) view.findViewById(R.id.txtID)).getText().toString();
        name = ((TextView) view.findViewById(R.id.txtName)).getText().toString();
        age = ((TextView) view.findViewById(R.id.txtAge)).getText().toString();

        switch (item.getItemId()) {
            case R.id.menu_item1:
                delete(id);
                return true;
            case R.id.menu_item2:
                update(id, name, age);
                return true;
            default:
                return false;
        }
    }

    //显示数据
    public void select() {
        tv = (TextView) findViewById(R.id.tv1);
        listView = (ListView) findViewById(R.id.listview);
        String[] from = new String[]{"_id", "name", "age"};
        int[] to = new int[]{R.id.txtID, R.id.txtName, R.id.txtAge};
        resolver = getContentResolver();
        Uri selecturi = Uri.parse("content://com.pengjinbo.personprovider/person");
        Cursor cursor = resolver.query(selecturi, new String[]{"_id", "name", "age"}, null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(Main2Activity.this, R.layout.listview, cursor, from, to);
        tv.setText("查询到了" + cursor.getCount() + "条记录" + "(版本号为1)");
        listView.setAdapter(adapter);
        Toast.makeText(Main2Activity.this, "select完成", Toast.LENGTH_SHORT).show();
    }

    //添加数据
    public void add() {
        Intent intent = new Intent(Main2Activity.this, AddActivity.class);
        Bundle bundle = new Bundle();
        //bundle.putString("test","test");
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);
        Toast.makeText(Main2Activity.this, "点击了添加数据", Toast.LENGTH_SHORT).show();
    }

    //删除记录
    public void delete(String myid) {
        final int heiid=Integer.parseInt(myid);
        if(heiid>=0)
        {
            new AlertDialog.Builder(this).setTitle("删除" + heiid)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            ContentResolver resolver = getContentResolver();
                            Uri deleteUri = Uri.parse("content://com.pengjinbo.personprovider/person");
                            int count=resolver.delete(deleteUri, "_id=?", new String[]{""+heiid});

                            Toast.makeText(getApplicationContext(), "记录删除成功", Toast.LENGTH_SHORT).show();
                       select();
                        }
                    })
                    .setNegativeButton("取消", null) .show();
        }
    }

    //修改记录
    public void update(String myid, String myname, String myage) {
        Intent intent = new Intent(Main2Activity.this, UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", myid);
        bundle.putString("name", myname);
        bundle.putString("age", myage);
        intent.putExtras(bundle);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 100) {
                Bundle bundle = data.getExtras();
                int flag = bundle.getInt("flag");
                if (flag == 1) {
                    Toast.makeText(Main2Activity.this, "到了flag=1", Toast.LENGTH_SHORT).show();
                    String name = bundle.getString("name");
                    String a = bundle.getString("age");
                    int age = Integer.parseInt(a);
                    resolver = getContentResolver();
                    Uri insertUri = Uri.parse("content://com.pengjinbo.personprovider/person");
                    ContentValues values = new ContentValues();
                    values.put("name", name);
                    values.put("age", age);
                    resolver.insert(insertUri, values);
                    select();
                    Toast.makeText(Main2Activity.this, "已添加记录", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Main2Activity.this, "已经取消添加记录", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == 101) {
            if (resultCode == 101) {
                Bundle bundle = data.getExtras();
                int flag = bundle.getInt("flag");
                if(flag==1){
                    int id=Integer.parseInt(bundle.getString("id"));
                    String name = bundle.getString("name");
                    String a = bundle.getString("age");
                    int age= Integer.parseInt(a);
                    resolver = getContentResolver();
                    Uri updateUri = Uri.parse("content://com.pengjinbo.personprovider/person");
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("age",age);
                    resolver.update(updateUri, values, "_id=?",
                            new String[]{""+id});
                    select();
                    Toast.makeText(Main2Activity.this,"已修改了记录记录",Toast.LENGTH_SHORT).show();
                    }
            } else {
                Toast.makeText(Main2Activity.this, "已取消修改了记录", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
