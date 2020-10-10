/**
 * Project Name:cwFaceForDev3
 * File Name:TemplatedActivity.java
 * Package Name:cn.cloudwalk.dev.mobilebank
 * Date:2016-5-11上午10:46:19
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 * <p/>
 * <p/>
 * Project Name:cwFaceForDev3
 * File Name:TemplatedActivity.java
 * Package Name:cn.cloudwalk.dev.mobilebank
 * Date:2016-5-11上午10:46:19
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 */
/**
 * Project Name:cwFaceForDev3
 * File Name:TemplatedActivity.java
 * Package Name:cn.cloudwalk.dev.mobilebank
 * Date:2016-5-11上午10:46:19
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 *
 */

package cn.cloudwalk.libproject;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.widget.Toast;
import cn.cloudwalk.libproject.progressHUD.CwProgressHUD;
import cn.cloudwalk.libproject.util.Util;

/**
 * ClassName: BaseActivity <br/>
 * Description: <br/>
 * date: 2016-5-11 上午10:46:19 <br/>
 *
 * @author 284891377
 * @version
 * @since JDK 1.7
 */
public class BaseActivity extends Activity {
	public CwProgressHUD processDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// 进度框
		processDialog = CwProgressHUD.create(this).setStyle(CwProgressHUD.Style.SPIN_INDETERMINATE)
				.setLabel(getString(R.string.cloudwalk_faceverifying)).setCancellable(true).setAnimationSpeed(2)
				.setCancellable(false).setDimAmount(0.5f);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (processDialog != null && processDialog.isShowing()) {
			processDialog.dismiss();
		}

	}

	/**
	 * 图片选择
	 *
	 * @param requestCode
	 */
	public void IntentPhoto(int requestCode) {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(Intent.createChooser(intent, "选择图片"), requestCode);
		} else {
			intent = new Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			startActivityForResult(Intent.createChooser(intent, "选择图片"), requestCode);
		}

	}

	/**
	 * 打开照相机
	 *
	 * @param requestCode
	 */
	public File intentCamera(int requestCode, Context ctx) {
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		File cametaImgFile = new File(Util.getDiskCacheDir(ctx),
				"cameraImg" + String.valueOf(System.currentTimeMillis()) + ".jpg");
		Uri mUri = Uri.fromFile(cametaImgFile);
		cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);
		cameraIntent.putExtra("return-data", true);
		startActivityForResult(cameraIntent, requestCode);
		return cametaImgFile;
	}

	/**
	 *
	 * makeToast:显示toast信息 <br/>
	 * 
	 * @author:284891377 Date: 2016-5-18 上午11:30:49
	 * @param msg
	 * @since JDK 1.7
	 */
	public void makeToast(String msg) {
		Toast.makeText(this, msg + "", Toast.LENGTH_LONG).show();
	}

	public void startCls(Class cls) {
		startActivity(new Intent(this, cls));
	}

	
}
