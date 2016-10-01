package brad.tw.myiotest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.tv);

        sp = getSharedPreferences("gamedata", MODE_PRIVATE);
        editor = sp.edit();

    }
    public void test1(View v){
        editor.putString("user", "Brad");
        editor.putBoolean("sound", true);
        editor.putInt("level", 4);
        editor.commit();
        Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT).show();
    }
    public void test2(View v){
        int level = sp.getInt("level",-1);
        String name = sp.getString("user","nobody");
        boolean sound = sp.getBoolean("sound",false);
        tv.setText("User:" + name + "\n" +
                    "Sound:" + (sound?"On":"Off") + "\n" +
                    "Lv.:" + level);
    }

    public void test3(View v){
        try {
            FileOutputStream fout = openFileOutput("app.dat",MODE_PRIVATE);
            fout.write("Hello, World\nHello, Brad".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "Save2 OK", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void test4(View v){
        try {
            FileInputStream fin = openFileInput("app.dat");
            int c; StringBuffer sb = new StringBuffer();
            while ( (c = fin.read()) != -1){
                sb.append((char)c);
            }
            fin.close();
            tv.setText(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
