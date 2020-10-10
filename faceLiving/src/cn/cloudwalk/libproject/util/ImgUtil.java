package cn.cloudwalk.libproject.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ImgUtil {
	private static final String TAG = LogUtils.makeLogTag("ImgUtil");
    public static final int COLOR_FormatI420 = 1;
    public static final int COLOR_FormatNV21 = 2;

	// ****************解决图片加载内存溢出问题
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			int inSampleSizeH = Math.round((float) height / (float) reqHeight);

			int inSampleSizeW = Math.round((float) width / (float) reqWidth);

			inSampleSize = Math.max(inSampleSizeH, inSampleSizeW);
		}
		if (inSampleSize>1 && inSampleSize % 2 == 1)
			inSampleSize++;
		while (width / inSampleSize > reqWidth || height / inSampleSize > reqHeight) {
			inSampleSize++;
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static Bitmap decodeSampledBitmapFromPath(String filePath, int reqWidth, int reqHeight,
			Bitmap.Config config) {
        Bitmap bitmap = null;
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		int inSampleSize= calculateInSampleSize(options, reqWidth, reqHeight);
		options.inSampleSize = inSampleSize;
		try {
		// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			if (config == null)
				config = Config.RGB_565;
			options.inPreferredConfig = config;
			bitmap=BitmapFactory.decodeFile(filePath, options);
		} catch (OutOfMemoryError e) {
			try {
				options.inSampleSize=inSampleSize+2;
				options.inJustDecodeBounds = false;
				if (config == null)
					config = Config.RGB_565;
				options.inPreferredConfig = config;
				bitmap=BitmapFactory.decodeFile(filePath, options);
			} catch (OutOfMemoryError e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return bitmap;
	}

	public static Bitmap byteArrayBGRToBitmap(byte[] data, int width, int height) {
		int frameSize = width * height;
		int[] rgba = new int[frameSize];

		for (int i = 0; i < height * width; i++) {
			int r = (0xff & ((int) data[i *3]));
			int g = (0xff & ((int) data[i * 3 + 1]));
			int b = (0xff & ((int) data[i * 3 + 2]));

			r = r < 0 ? 0 : (r > 255 ? 255 : r);
			g = g < 0 ? 0 : (g > 255 ? 255 : g);
			b = b < 0 ? 0 : (b > 255 ? 255 : b);

			rgba[i] = 0xff000000 + (b << 16) + (g << 8) + r;
		}

		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bmp.setPixels(rgba, 0, width, 0, 0, width, height);
		return bmp;
	}

	// ****************解决图片加载内存溢出问题
	/**
	 * 缩小图片(精准缩放w/h为指定值)避免出现内存溢出
	 * 
	 * @param imgPath
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomPic(String imgPath, int w, int h, Bitmap.Config config) {
		Bitmap bitmap = decodeSampledBitmapFromPath(imgPath, w, h, config);
		return bitmap;
	}

	/**
	 * 缩小图片(精准缩放w/h为指定值)
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		Bitmap newbmp = null;
		try {
			if (bitmap != null) {
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				if (width < w && height < h)
					return bitmap;// 不进行放大操作

				Matrix matrix = new Matrix();
				float scaleWidht = ((float) w / width);/// 940/1922
				float scaleHeight = ((float) h / height);
				if (scaleWidht > scaleHeight)
					matrix.postScale(scaleHeight, scaleHeight);
				else
					matrix.postScale(scaleWidht, scaleWidht);
				newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
				bitmap.recycle();
				bitmap = null;
			}
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newbmp;
	}

	/**
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		Bitmap bmpGrayscale = null;
		try {
			int width, height;
			height = bmpOriginal.getHeight();
			width = bmpOriginal.getWidth();
			bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
			Canvas c = new Canvas(bmpGrayscale);
			Paint paint = new Paint();
			ColorMatrix cm = new ColorMatrix();
			cm.setSaturation(0);
			ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
			paint.setColorFilter(f);
			c.drawBitmap(bmpOriginal, 0, 0, paint);
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bmpGrayscale;
	}

	/**
	 * 计算缩放图片的宽高
	 * 
	 * @param img_size
	 * @param square_size
	 * @return
	 */
	public static int[] scaleImageSize(int[] img_size, int square_size) {
		if (img_size[0] <= square_size && img_size[1] <= square_size)
			return img_size;
		double ratio = square_size / (double) Math.max(img_size[0], img_size[1]);
		return new int[] { (int) (img_size[0] * ratio), (int) (img_size[1] * ratio) };
	}

	/**
	 * 创建缩略图
	 * 
	 * @param context
	 * @param largeImagePath
	 *            原始大图路径
	 * @param square_size
	 *            输出图片宽度
	 * @return
	 * @throws IOException
	 */
	public static Bitmap createImageThumbnail(Context context, String largeImagePath, int square_size)
			throws IOException {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 1;
		// 原始图片bitmap
		Bitmap cur_bitmap = getBitmapByPath(largeImagePath, opts);

		if (cur_bitmap == null)
			return null;
		// 原始图片的高宽
		int[] cur_img_size = new int[] { cur_bitmap.getWidth(), cur_bitmap.getHeight() };

		// 计算原始图片缩放后的宽高
		int[] new_img_size = scaleImageSize(cur_img_size, square_size);

		if (new_img_size[0] > square_size) {
			// 生成缩放后的bitmap
			return zoomBitmap(cur_bitmap, new_img_size[0], new_img_size[1]);
		} else {
			return cur_bitmap;
		}

	}

	/**
	 * 保存图片为PNG
	 * 
	 * @param bitmap
	 * @param path
	 */
	public static void savePNG_After(Bitmap bitmap, String path, int quality) {
		File file = new File(path);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(CompressFormat.PNG, quality, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap jpgByteArrayToBitmap(byte[] data) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		return bitmap;
	}

	/**
	 * 保存图片为JPEG
	 * 
	 * @param bitmap
	 * @param path
	 */
	public static void saveJPGE_After(Bitmap bitmap, String path, int quality) {
		File file = new File(path);

		try {
			FileOutputStream out = new FileOutputStream(file);

			if (bitmap.compress(CompressFormat.JPEG, quality, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Drawable 转 Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * Bitmap 转 Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawableByBD(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * byte[] 转 bitmap
	 * 
	 * @param b
	 * @return
	 */
	public static Bitmap bytesToBimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {

			return null;
		}
	}

	/**
	 * bitmap 转 byte[]
	 * 
	 * @param b
	 * @param format
	 *            {@link CompressFormat }
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap b, CompressFormat format, int quality) {
		if (b == null) {
			return null;
		}
       
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		b.compress(format, quality, o);
		byte[] data=o.toByteArray();
		 try {
			o.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 让Gallery上能马上看到该图片
	 */
	private static void scanPhoto(Context ctx, String imgFileName) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File file = new File(imgFileName);
		Uri contentUri = Uri.fromFile(file);
		mediaScanIntent.setData(contentUri);
		ctx.sendBroadcast(mediaScanIntent);
	}

	/**
	 * 获取bitmap
	 * 
	 * @param file
	 * @return
	 */
	public static Bitmap getBitmapByFile(File file) {
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}

	/**
	 * 获取bitmap
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getBitmapByPath(String filePath) {
		return getBitmapByPath(filePath, null);
	}

	/**
	 * 使用当前时间戳拼接一个唯一的文件名
	 * 
	 * 
	 * @return
	 */
	public static String getTempFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SS");
		String fileName = format.format(new Timestamp(System.currentTimeMillis()));
		return fileName;
	}

	/**
	 * 获取bitmap
	 * 
	 * @param filePath
	 * @param opts
	 * @return
	 */
	public static Bitmap getBitmapByPath(String filePath, BitmapFactory.Options opts) {
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis, null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}

	private static String getPathDeprecated(Context context, Uri uri) {
		if (uri == null) {
			return null;
		}
		String[] projection = { Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		return uri.getPath();
	}

	/**
	 * 通过url获取文件路径
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getSmartFilePath(Context context, Uri uri) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return getPathDeprecated(context, uri);
		}
		return getPath(context, uri);
	}

	// /**
	// *
	// * getImagePath:获取图片路径. <br/>
	// *
	// * @author:284891377 Date: 2016-4-14 下午6:16:28
	// * @param uri
	// * @param context
	// * @return
	// * @since JDK 1.7
	// */
	// public static String getImagePath(Uri uri, Activity context) {
	// File photoFile = null;
	// String saveDir = Util.getDiskCacheDir(context);
	// try {
	// InputStream pictureInputStream =
	// context.getContentResolver().openInputStream(uri);
	//
	// photoFile = new File(saveDir, "temppic.jpg");
	// photoFile.createNewFile();
	//
	// OutputStream out = new FileOutputStream(photoFile);
	// byte[] buf = new byte[1024];
	// int len;
	// while ((len = pictureInputStream.read(buf)) > 0) {
	// out.write(buf, 0, len);
	// }
	// out.close();
	// pictureInputStream.close();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// if (photoFile == null) {
	// return "";
	// } else {
	// return photoFile.getAbsolutePath();
	// }
	//
	// }

	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	public static String md5(String paramString) {
		String returnStr;
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramString.getBytes());
			returnStr = byteToHexString(localMessageDigest.digest());
			return returnStr;
		} catch (Exception e) {
			return paramString;
		}
	}

	public static void startImgIntent(Activity act, int requestCode) {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			act.startActivityForResult(Intent.createChooser(intent, "选择图片"), requestCode);
		} else {
			intent = new Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			act.startActivityForResult(Intent.createChooser(intent, "选择图片"), requestCode);
		}
	}

	/**
	 * 将指定byte数组转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}

	// ************图片角度
	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/*
	 * 旋转图片
	 * 
	 * @param angle
	 * 
	 * @param bitmap
	 * 
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		// 创建新的图片
		Bitmap resizedBitmap = null;
		try {
			Matrix matrix = new Matrix();
			matrix.postRotate(angle);

			resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resizedBitmap;
	}

	/************ 系统相册显示 *****************/
	/**
	 * 
	 * insertImage:保存图片到系统图库. <br/>
	 * 文件路径为sdcard/DICM/Camera.<br/>
	 * 注意:文件名不可以自己指定,系统随机生成.<br/>
	 * 
	 * @author:284891377 Date: 2016-5-10 上午10:37:49
	 * @param ctx
	 * @param source
	 *            Bitmap
	 * @param title
	 * @param description
	 * @return image url
	 * @since JDK 1.7
	 */
	public static final String saveImageToGallery(Context ctx, Bitmap source, String title, String description) {
		ContentResolver cr = ctx.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(Images.Media.TITLE, title);
		values.put(Images.Media.DISPLAY_NAME, title);
		values.put(Images.Media.DESCRIPTION, description);
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		// Add the date meta data to ensure the image is added at the front of
		// the gallery
		values.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
		values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());

		Uri url = null;
		String stringUrl = null; /* value to be returned */

		try {
			url = cr.insert(Images.Media.EXTERNAL_CONTENT_URI, values);

			if (source != null) {
				OutputStream imageOut = cr.openOutputStream(url);
				try {
					source.compress(CompressFormat.JPEG, 50, imageOut);
				} finally {
					imageOut.close();
				}

				long id = ContentUris.parseId(url);
				// Wait until MINI_KIND thumbnail is generated.
				Bitmap miniThumb = Images.Thumbnails.getThumbnail(cr, id, Images.Thumbnails.MINI_KIND, null);
				// This is for backward compatibility.
				storeThumbnail(cr, miniThumb, id, 50F, 50F, Images.Thumbnails.MICRO_KIND);
			} else {
				cr.delete(url, null, null);
				url = null;
			}
		} catch (Exception e) {
			if (url != null) {
				cr.delete(url, null, null);
				url = null;
			}
		}

		if (url != null) {
			stringUrl = url.toString();
		}

		return stringUrl;
	}

	/**
	 * A copy of the Android internals StoreThumbnail method, it used with the
	 * insertImage to populate the
	 * android.provider.MediaStore.Images.Media#insertImage with all the correct
	 * meta data. The StoreThumbnail method is private so it must be duplicated
	 * here.
	 * 
	 * @see Images.Media (StoreThumbnail private method)
	 */
	private static final Bitmap storeThumbnail(ContentResolver cr, Bitmap source, long id, float width, float height,
			int kind) {

		// create the matrix to scale it
		try {
		Matrix matrix = new Matrix();

		float scaleX = width / source.getWidth();
		float scaleY = height / source.getHeight();

		matrix.setScale(scaleX, scaleY);

		Bitmap thumb = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

		ContentValues values = new ContentValues(4);
		values.put(Images.Thumbnails.KIND, kind);
		values.put(Images.Thumbnails.IMAGE_ID, (int) id);
		values.put(Images.Thumbnails.HEIGHT, thumb.getHeight());
		values.put(Images.Thumbnails.WIDTH, thumb.getWidth());

		Uri url = cr.insert(Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

		
			OutputStream thumbOut = cr.openOutputStream(url);
			thumb.compress(CompressFormat.JPEG, 100, thumbOut);
			thumbOut.close();
			return thumb;
		} catch (Exception ex) {
			return null;
		} catch (Error ex) {
			return null;
		}
	}

	/**
	 * 
	 * saveImageToGallery:保存图片到sd卡指定目录,并在系统相册显示. <br/>
	 * 注意：图片名称和路径可以自己指定 <br/>
	 * 
	 * @author:284891377 Date: 2016-5-10 上午10:46:07
	 * @param context
	 * @param bmp
	 * @param dirPath
	 *            sd卡文件夹路径(dirName必须自己创建)
	 * @since JDK 1.7
	 */
	public static void saveImageToGallery(Context context, Bitmap bmp, String dirPath) {

		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(dirPath, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
		try {
			Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 最后通知图库更新
		context.sendBroadcast(
				new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
	}

	/************ 系统相册显示 *****************/
	/**
	 * 
	 * addRects:添加人脸框 <br/>
	 * 
	 * @author:284891377 Date: 2016年6月27日 下午5:54:22
	 * @param rects
	 * @param src
	 * @return
	 * @since JDK 1.7
	 */
	public static Bitmap addRects(Rect[] rects, Bitmap src) {
		Bitmap newBM = null;
		try {
			int w = src.getWidth();
			int h = src.getHeight();
			Paint paint = new Paint();
			int color = Color.rgb(98, 212, 68);
			paint.setColor(color);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(9f);
			paint.setAlpha(180);

			newBM = Bitmap.createBitmap(w, h, Config.ARGB_4444);
			Canvas canvas = new Canvas(newBM);
			canvas.drawBitmap(src, 0, 0, null);

			int len = rects.length;
			for (int i = 0; i < len; i++) {
				canvas.drawRect(rects[i], paint);
			}
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newBM;
	}
	/**
	 * Android图片压缩工具，仿微信朋友圈压缩策略
	 * 来自:https://github.com/Curzibn/Luban
	 * @param file 源文件底地址
	 * @param thumbFilePath 压缩生成文件地址
     * @return
     */
     public static File weixinCompress( File file,String thumbFilePath) {


	double size;
	String filePath = file.getAbsolutePath();
    //旋转角度
	int angle = getImageSpinAngle(filePath);

	//原图尺寸
	BitmapFactory.Options options = new BitmapFactory.Options();
	options.inJustDecodeBounds = true;
	options.inSampleSize = 1;
	BitmapFactory.decodeFile(filePath, options);
	int width = options.outWidth;
	int height = options.outHeight;

	int thumbW = width % 2 == 1 ? width + 1 : width;
	int thumbH = height % 2 == 1 ? height + 1 : height;

	width = thumbW > thumbH ? thumbH : thumbW;
	height = thumbW > thumbH ? thumbW : thumbH;

	double scale = ((double) width / height);

	if (scale <= 1 && scale > 0.5625) {
		if (height < 1664) {
			if (file.length() / 1024 < 150) return file;

			size = (width * height) / Math.pow(1664, 2) * 150;
			size = size < 60 ? 60 : size;
		} else if (height >= 1664 && height < 4990) {
			thumbW = width / 2;
			thumbH = height / 2;
			size = (thumbW * thumbH) / Math.pow(2495, 2) * 300;
			size = size < 60 ? 60 : size;
		} else if (height >= 4990 && height < 10240) {
			thumbW = width / 4;
			thumbH = height / 4;
			size = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
			size = size < 100 ? 100 : size;
		} else {
			int multiple = height / 1280 == 0 ? 1 : height / 1280;
			thumbW = width / multiple;
			thumbH = height / multiple;
			size = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
			size = size < 100 ? 100 : size;
		}
	} else if (scale <= 0.5625 && scale > 0.5) {
		if (height < 1280 && file.length() / 1024 < 200) return file;

		int multiple = height / 1280 == 0 ? 1 : height / 1280;
		thumbW = width / multiple;
		thumbH = height / multiple;
		size = (thumbW * thumbH) / (1440.0 * 2560.0) * 400;
		size = size < 100 ? 100 : size;
	} else {
		int multiple = (int) Math.ceil(height / (1280.0 / scale));
		thumbW = width / multiple;
		thumbH = height / multiple;
		size = ((thumbW * thumbH) / (1280.0 * (1280 / scale))) * 500;
		size = size < 100 ? 100 : size;
	}

	return compress(filePath, thumbFilePath, thumbW, thumbH, angle, (long) size);
}
	/**
	 * obtain the image rotation angle
	 *
	 * @param path path of target image
	 */
	private static int getImageSpinAngle(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	/**
	 * obtain the thumbnail that specify the size
	 *
	 * @param imagePath the target image path
	 * @param width     the width of thumbnail
	 * @param height    the height of thumbnail
	 * @return {@link Bitmap}
	 */
	private static Bitmap compress(String imagePath, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, options);

		int outH = options.outHeight;
		int outW = options.outWidth;
		int inSampleSize = 1;

		if (outH > height || outW > width) {
			int halfH = outH / 2;
			int halfW = outW / 2;

			while ((halfH / inSampleSize) > height && (halfW / inSampleSize) > width) {
				inSampleSize *= 2;
			}
		}

		options.inSampleSize = inSampleSize;

		options.inJustDecodeBounds = false;

		int heightRatio = (int) Math.ceil(options.outHeight / (float) height);
		int widthRatio = (int) Math.ceil(options.outWidth / (float) width);

		if (heightRatio > 1 || widthRatio > 1) {
			if (heightRatio > widthRatio) {
				options.inSampleSize = heightRatio;
			} else {
				options.inSampleSize = widthRatio;
			}
		}
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(imagePath, options);
	}
	/**
	 * 指定参数压缩图片
	 * create the thumbnail with the true rotate angle
	 *
	 * @param largeImagePath the big image path
	 * @param thumbFilePath  the thumbnail path
	 * @param width          width of thumbnail
	 * @param height         height of thumbnail
	 * @param angle          rotation angle of thumbnail
	 * @param size           the file size of image
	 */
	private static File compress(String largeImagePath, String thumbFilePath, int width, int height, int angle, long size) {
		Bitmap thbBitmap = compress(largeImagePath, width, height);

		if(angle>0)thbBitmap = rotatingImage(angle, thbBitmap);

		return saveImage(thumbFilePath, thbBitmap, size);
	}
	/**
	 * 旋转图片
	 * rotate the image with specified angle
	 *
	 * @param angle  the angle will be rotating 旋转的角度
	 * @param bitmap target image               目标图片
	 */
	private static Bitmap rotatingImage(int angle, Bitmap bitmap) {
		//rotate image
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);

		//create a new image
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	/**
	 * 保存图片到指定路径
	 * Save image with specified size
	 *
	 * @param filePath the image file save path 储存路径
	 * @param bitmap   the image what be save   目标图片
	 * @param size     the file size of image   期望大小
	 */
	private static File saveImage(String filePath, Bitmap bitmap, long size) {

		File result = new File(filePath.substring(0, filePath.lastIndexOf("/")));

		if (!result.exists() && !result.mkdirs()) return null;

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		int options = 100;
		bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);

		while (stream.toByteArray().length / 1024 > size && options > 6) {
			stream.reset();
			options -= 6;
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);
		}
       LogUtils.LOGE(TAG, "jpg保存质量:"+options+"图片大小:"+bitmap.getWidth()+";"+bitmap.getHeight());
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(stream.toByteArray());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new File(filePath);
	}

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public static byte[] getDataFromImage(Image image, int colorFormat) {
//        if (colorFormat != COLOR_FormatI420 && colorFormat != COLOR_FormatNV21) {
//            throw new IllegalArgumentException("only support COLOR_FormatI420 " + "and COLOR_FormatNV21");
//        }
//        Rect crop = image.getCropRect();
//        int format = image.getFormat();
//        int width = crop.width();
//        int height = crop.height();
//        Image.Plane[] planes = image.getPlanes();
//        byte[] data = new byte[width * height * ImageFormat.getBitsPerPixel(format) / 8];
//        byte[] rowData = new byte[planes[0].getRowStride()];
//        int channelOffset = 0;
//        int outputStride = 1;
//        for (int i = 0; i < planes.length; i++) {
//            switch (i) {
//                case 0:
//                    channelOffset = 0;
//                    outputStride = 1;
//                    break;
//                case 1:
//                    if (colorFormat == COLOR_FormatI420) {
//                        channelOffset = width * height;
//                        outputStride = 1;
//                    } else if (colorFormat == COLOR_FormatNV21) {
//                        channelOffset = width * height + 1;
//                        outputStride = 2;
//                    }
//                    break;
//                case 2:
//                    if (colorFormat == COLOR_FormatI420) {
//                        channelOffset = (int) (width * height * 1.25);
//                        outputStride = 1;
//                    } else if (colorFormat == COLOR_FormatNV21) {
//                        channelOffset = width * height;
//                        outputStride = 2;
//                    }
//                    break;
//            }
//            ByteBuffer buffer = planes[i].getBuffer();
//            int rowStride = planes[i].getRowStride();
//            int pixelStride = planes[i].getPixelStride();
//
//            int shift = (i == 0) ? 0 : 1;
//            int w = width >> shift;
//            int h = height >> shift;
//            buffer.position(rowStride * (crop.top >> shift) + pixelStride * (crop.left >> shift));
//            for (int row = 0; row < h; row++) {
//                int length;
//                if (pixelStride == 1 && outputStride == 1) {
//                    length = w;
//                    buffer.get(data, channelOffset, length);
//                    channelOffset += length;
//                } else {
//                    length = (w - 1) * pixelStride + 1;
//                    buffer.get(rowData, 0, length);
//                    for (int col = 0; col < w; col++) {
//                        data[channelOffset] = rowData[col * pixelStride];
//                        channelOffset += outputStride;
//                    }
//                }
//                if (row < h - 1) {
//                    buffer.position(buffer.position() + rowStride - length);
//                }
//            }
//        }
//        return data;
//    }
}
