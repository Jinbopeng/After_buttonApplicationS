package com.example.testsqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends Activity {
    Intent intent;
    Bundle bundle;
    int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Button btn_sub=(Button)findViewById(R.id.btn_sub);
        Button btn_can=(Button)findViewById(R.id.btn_cancel);
        intent=getIntent();
        bundle=intent.getExtras();
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switch(v.getId()){
                   case R.id.btn_sub:
                       flag=1;
                       EditText ed1=(EditText)findViewById(R.id.et_name);
                       EditText ed2= (EditText)findViewById(R.id.et_age);
                       String name=ed1.getText().toString();
                       String age=ed2.getText().toString();
                       bundle.putString("name",name);
                       bundle.putString("age",age);
                       bundle.putInt("flag",flag);
                       intent.putExtras(bundle);
                       setResult(100,intent);
                       finish();
                       break;
                   case R.id.btn_cancel:
                       flag=0;
                       bundle.putInt("flag",flag);
                       intent.putExtras(bundle);
                       setResult(100,intent);
                       finish();
                       break;
               }

            }
        };
        btn_sub.setOnClickListener(listener);
        btn_can.setOnClickListener(listener);
    }
}
