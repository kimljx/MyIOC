package cn.myioc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.myioc.xioc.BaseActivity;
import cn.myioc.xioc.ContentView;
import cn.myioc.xioc.OnClick;
import cn.myioc.xioc.OnLongClick;
import cn.myioc.xioc.XView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @XView(R.id.button1)
    Button b1;
    @XView(R.id.button2)
    Button b2;

    @OnClick(R.id.button1)
    public void click(View view){
        Toast.makeText(MainActivity.this,"hahahha   "+b1,Toast.LENGTH_LONG).show();
    }


    @OnLongClick(R.id.button2)
    public boolean longClick(View view){
        Toast.makeText(MainActivity.this,"long hahahha   "+b2,Toast.LENGTH_LONG).show();
        return true;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        (new View(this)).setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });
//    }

}
