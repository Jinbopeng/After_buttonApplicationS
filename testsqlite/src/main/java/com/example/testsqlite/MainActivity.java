package com.example.testsqlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.solver.widgets.Helper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private int    DB_VERSION=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置数据
        final Person person=new Person();
        final Person person2=new Person();
        person.setName("彭锦波");
        //person.setId(1);
        person.setAge(21);
        person2.setName("gakki");
        //person2.setId(1);
        person2.setAge(30);

        Button btn1=(Button)findViewById(R.id.button1);
        Button btn2=(Button)findViewById(R.id.button2);
        Button btn3=(Button)findViewById(R.id.button3);
        Button btn4=(Button)findViewById(R.id.button4);
        Button btn5=(Button)findViewById(R.id.button5);
        Button btn6=(Button)findViewById(R.id.button6);

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button1:
                        create();
                        break;
                    case R.id.button2:
                        insert(person);
                        break;
                    case R.id.button3:
                        deleteall();
                        break;
                    case R.id.button4:
                        changeit(person.getId(),person2);
                        break;
                    case R.id.button5:
                        selectdata();
                        break;
                    case R.id.button6:
                        update();
                        break;

                }

            }
        };
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);

    }

    public void create(){
        DBHelper helper=new DBHelper(getApplicationContext(),"test.db",null,DB_VERSION);
        SQLiteDatabase db=helper.getWritableDatabase();
        db.close();
        Toast.makeText(MainActivity.this,"已经建表了",Toast.LENGTH_SHORT).show();
    }
    public void insert(Person person){
        DBHelper helper=new DBHelper(getApplicationContext(),"test.db",null,DB_VERSION);
        SQLiteDatabase db=helper.getWritableDatabase();

        db.execSQL("insert into person values (null,?,?)",new Object[]{person.getName(),person.getAge()});
        db.close();
        Toast.makeText(MainActivity.this,"已经添加记录",Toast.LENGTH_SHORT).show();
    }
    public void changeit(int id ,Person person){
        DBHelper dbHelper=new DBHelper(getApplicationContext(),"test.db",null,DB_VERSION);
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        db.execSQL("update person  set age=? , name=? where id=?",
                new Object[]{person.getAge(),person.getName(),id});
        db.close();
        Toast.makeText(MainActivity.this,"已经修改数据",Toast.LENGTH_SHORT).show();
    }
    public void deletebyid(int id){
        DBHelper dbHelper=new DBHelper(getApplicationContext(),"test.db",null,DB_VERSION);
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        db.execSQL("delete from person where id=?",new Object[]{id});
        db.close();
        Toast.makeText(MainActivity.this,"已经删除记录",Toast.LENGTH_SHORT).show();
    }
    public void deleteall(){
        DBHelper dbHelper=new DBHelper(getApplicationContext(),"test.db",null,DB_VERSION);
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        db.execSQL("delete from person",new Object[]{});

        db.close();
        Toast.makeText(MainActivity.this,"已经删除全部记录",Toast.LENGTH_SHORT).show();

    }
    public void selectdata(){
        DBHelper dbHelper=new DBHelper(getApplicationContext(),"test.db",null,DB_VERSION);
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        Cursor cursor=db.rawQuery("select * from person where age>?",new String[]{"10"});

        TextView tv=(TextView)findViewById(R.id.textView);
        tv.setText("查询到"+cursor.getCount()+"条记录(当前数据库版本号="+DB_VERSION+")");
        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            int age=cursor.getInt(cursor.getColumnIndex("age"));
            tv.setText(tv.getText()+"\n"+"id="+id+"\t姓名="+name+"\t年龄="+age);
        }
        cursor.close();
        db.close();
        Toast.makeText(MainActivity.this,"已经查询记录",Toast.LENGTH_SHORT).show();
    }
    public void update(){
        DB_VERSION++;
        DBHelper dbHelper=new DBHelper(getApplicationContext(),"test.db",null,DB_VERSION);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.close();
    }

}
