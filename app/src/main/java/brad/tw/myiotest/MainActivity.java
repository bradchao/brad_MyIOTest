package brad.tw.myiotest;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView tv;

    private File sdroot, dir1, dir2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.tv);

        sp = getSharedPreferences("gamedata", MODE_PRIVATE);
        editor = sp.edit();

        // check permission
        if (Build.VERSION.SDK_INT>=23) {
            checkPermission();
        }


        sdroot = Environment.getExternalStorageDirectory();
        Log.v("brad", sdroot.getAbsolutePath());

        File[] dir = sdroot.listFiles();
        for (File p : dir){
            Log.v("brad", p.getName());
        }

        dir1 = new File(sdroot, "dir1");
        dir2 = new File(sdroot, "Android/data/" + getPackageName());

        if (!dir1.exists())dir1.mkdirs();
        if (!dir2.exists())dir2.mkdirs();



    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // 願意
        }

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
            FileOutputStream fout = openFileOutput("app.dat",MODE_APPEND);
            fout.write("Hello, World\nHello, Brad\n".getBytes());
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

    public void test5(View v){
        File file1 = new File(dir1, "test1.txt");
        try {
            FileOutputStream fout = new FileOutputStream(file1);
            fout.write("Hello, Brad".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "Save1 OK", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file2 = new File(dir2, "test1.txt");
        try {
            FileOutputStream fout = new FileOutputStream(file2);
            fout.write("Hello, Brad\nHello, World\nOK123\n".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "Save2 OK", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void test6(View v){
        File file2 = new File(dir2, "test1.txt");
        try {
            FileInputStream fin =
                    new FileInputStream(file2);
            byte[] buf = new byte[(int)file2.length()];
            fin.read(buf);
            fin.close();

            tv.setText(new String(buf,0,buf.length));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
