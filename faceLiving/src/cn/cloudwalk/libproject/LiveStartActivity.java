

package cn.cloudwalk.libproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.cloudwalk.libproject.callback.NoDoubleClickListener;
import cn.cloudwalk.libproject.util.LogUtils;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * ClassName: LiveStartActivity <br/>
 * Description: <br/>
 * date: 2016-5-16 上午9:17:24 <br/>
 *
 * @author 284891377
 * @since JDK 1.7
 */
public class LiveStartActivity extends TemplatedActivity {
    private final String TAG = LogUtils.makeLogTag("LiveStartActivity");
    Button mBt_startdect;
    private boolean isGranted;
    public static LiveStartActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.cloudwalk_layout_facedect_start);
        setTitle(R.string.cloudwalk_live_title);
        mBt_startdect = (Button) findViewById(R.id.bt_startdect);
        mBt_startdect.setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
                if (v.getId() == R.id.bt_startdect) {
                    Log.i("isgranted", isGranted + " 11");
                    isGranted = false;
                    requestPermission();
                    Log.i("isgranted", isGranted + " 22");

                }
            }
        });
    }

    public void requestPermission() {
        Log.i("hahaha", ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) + "");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PERMISSION_GRANTED) {
            //先判断有没有权限 ，没有就在这里进行权限的申请
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            isGranted = true;
            //说明已经获取到摄像头权限了
            startActivity(new Intent(LiveStartActivity.this, LiveActivity.class));
            finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    isGranted = true;
                    startActivity(new Intent(LiveStartActivity.this, LiveActivity.class));
                    finish();
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    isGranted = false;
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}