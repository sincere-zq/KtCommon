package cn.cloudwalk.libproject.util;

import java.io.IOException;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

public class ThumbnailUtil {
	static final String TAG = "ThumbnailUtil";

	/**
	 * 获取图库缩略图
	 * 
	 * @param uri
	 *            图片uri
	 * @return
	 */
	public static Bitmap getDicmThumbnail(Uri uri, Context ctx) {
		String[] imageInfo = getRealPathFromURI(uri, ctx);
		if (imageInfo == null || imageInfo.length < 2)
			return null;
		return getThumbnail(ctx.getContentResolver(), Long.parseLong(imageInfo[1]));
	}

	/**
	 * 获取MediaStore.Images.Media._ID
	 * 
	 * @param contentUri
	 * @param ctx
	 * @return
	 */
	public static String[] getRealPathFromURI(Uri contentUri, Context ctx) {
		String[] proj = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
		String[] resStrs=null;
		Cursor cursor = null;
		try {
			 cursor = ctx.getContentResolver().query(contentUri, proj, null, null, null);
			int path_index = cursor.getColumnIndexOrThrow(proj[0]);
			int id_index = cursor.getColumnIndexOrThrow(proj[1]);
			cursor.moveToFirst();
			resStrs = new String[] { cursor.getString(path_index), cursor.getLong(id_index) + "" };
			cursor.close();
		} catch (Exception e) {
			if(cursor!=null)cursor.close();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resStrs;
	}

	/**
	 * 获取图库缩略图
	 * 
	 * @param contentResolver
	 * @param id
	 * @return
	 */
	public static Bitmap getThumbnail(ContentResolver contentResolver, long id) {
		Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Images.Media.DATA }, // Which columns
				// to return
				MediaStore.Images.Media._ID + "=?", // Which rows to return
				new String[] { String.valueOf(id) }, // Selection arguments
				null);// order

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			String filePath = cursor.getString(0);
			cursor.close();
			int rotation = 0;
			try {
				ExifInterface exifInterface = new ExifInterface(filePath);
				int exifRotation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_UNDEFINED);
				if (exifRotation != ExifInterface.ORIENTATION_UNDEFINED) {
					switch (exifRotation) {
					case ExifInterface.ORIENTATION_ROTATE_180:
						rotation = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						rotation = 270;
						break;
					case ExifInterface.ORIENTATION_ROTATE_90:
						rotation = 90;
						break;
					}
				}
			} catch (IOException e) {
				LogUtils.LOGE(TAG, e.toString());

			}
			Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, id,
					MediaStore.Images.Thumbnails.MINI_KIND, null);
			int width = bitmap.getWidth();
			int hight = bitmap.getHeight();
			if (rotation != 0) {
				Matrix matrix = new Matrix();
				matrix.setRotate(rotation);

				bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, hight, matrix, true);
			}
			LogUtils.LOGE(TAG, "缩略图长宽:" + width + "" + hight);
			return bitmap;
		} else
			return null;
	}
}
