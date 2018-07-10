package com.example.testsqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends Activity {
   // EditText ed1;
   //EditText ed2;
   // EditText ed3;
    int flag=1;
    Bundle bundle;
    Intent intent;
    String id;
    String name;
    String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

         intent=getIntent();
         bundle=intent.getExtras();
         id=bundle.getString("id");
         name=bundle.getString("name");
         age=bundle.getString("age");


        final EditText ed1=(EditText)findViewById(R.id.et_upno);
        final EditText ed2=(EditText)findViewById(R.id.et_upname);
        final EditText ed3=(EditText)findViewById(R.id.et_upage);
         ed1.setText(id);
         ed2.setText(name);
         ed3.setText(age);
         ed1.setEnabled(false);

        Button btn_shi=(Button)findViewById(R.id.btn_up);
        Button btn_cancel=(Button)findViewById(R.id.btn_upcancel);
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_up:
                         //ed2=(EditText)findViewById(R.id.et_upname);
                         //ed3=(EditText)findViewById(R.id.et_upage);
                        String myname=ed2.getText().toString();
                        String myage=ed3.getText().toString();
                        bundle.putString("name",myname);
                        bundle.putString("age",myage);
                        bundle.putInt("flag",flag);
                        intent.putExtras(bundle);
                        setResult(101,intent);
                        finish();
                        break;
                    case R.id.btn_upcancel:
                        flag=0;
                        bundle.putInt("flag",flag);
                        intent.putExtras(bundle);
                        setResult(101,intent);
                        finish();
                        break;
                }
            }
        };
        btn_shi.setOnClickListener(listener);
        btn_cancel.setOnClickListener(listener);
        /*btn_mysave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateActivity.this,"到了这里0!",Toast.LENGTH_SHORT).show();

                //ed2=(EditText)findViewById(R.id.et_upname);
                //ed3=(EditText)findViewById(R.id.et_upage);

                Toast.makeText(UpdateActivity.this,"到了这里1!",Toast.LENGTH_SHORT).show();
                String myname=ed2.getText().toString();
                String myage=ed3.getText().toString();
                bundle.putString("name",myname);
                bundle.putString("age",myage);
                bundle.putInt("flag",flag);
                intent.putExtras(bundle);
                Toast.makeText(UpdateActivity.this,"到了这里2!",Toast.LENGTH_SHORT).show();
                setResult(101,intent);
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                bundle.putInt("flag",flag);
                intent.putExtras(bundle);
                setResult(101,intent);
                finish();
            }
        });*/

    }
}
