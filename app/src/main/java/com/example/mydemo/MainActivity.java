package com.example.mydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mydemo.util.DisplayUtil;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = (TextView)findViewById(R.id.textview);
        textview.setText("好好肝！");
        textview.setTextColor(0xFF00FF00);
        showScreenInfo();

     //   Intent intent = new Intent(Intent.ACTION_MAIN);
     //   intent.addCategory(Intent.CATEGORY_LAUNCHER);
     //   ComponentName cn = new ComponentName(String.valueOf("com.example.junior"), "com.example.junior.activity.AppStarterActivity");
     //   intent.setComponent(cn);
     //   startActivity(intent);

       // doStartApplicationWithPackageName(String.valueOf("com.example.junior"));
        doStartApplicationWithPackageName(String.valueOf("com.tencent.qqmusiccar"));
    }
    private void showScreenInfo(){
        int width = DisplayUtil.getSreenWidth(this);
        int height = DisplayUtil.getSreenHeight(this);
        float density = DisplayUtil.getSreenDensity(this);
        String info = String.format("当前屏幕的宽为%dpx,高为%dpx,像素密度为%f",width,height,density);
        textview.setText(info);
    }
    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }

}
