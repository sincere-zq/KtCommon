package cn.cloudwalk.libproject.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.Toast;

public class ToasterUtil {

    public static Toast showToast(Context context, Toast clear_toast, String message, final float ui_rotation) {
		class RotatedTextView extends View {
			private String text = "";
			private Paint paint = new Paint();
			private Rect bounds = new Rect();

			public RotatedTextView(String text, Context context) {
				super(context);
				this.text = text;
			}
			
			@Override 
			protected void onDraw(Canvas canvas) { 
				final float scale = getResources().getDisplayMetrics().density;
				paint.setTextSize(18 * scale + 0.5f);
				paint.setStyle(Paint.Style.FILL);
				paint.setColor(Color.rgb(75, 75, 75));
				paint.setShadowLayer(1, 0, 1, Color.BLACK);
				paint.getTextBounds(text, 0, text.length(), bounds);
				final int padding = (int) (14 * scale + 0.5f);
				final int offset_y = (int) (32 * scale + 0.5f);
				canvas.save();
				canvas.rotate(ui_rotation, canvas.getWidth()/2, canvas.getHeight()/2);
				canvas.drawRect(canvas.getWidth()/2 - bounds.width()/2 + bounds.left - padding, canvas.getHeight()/2 + bounds.top - padding + offset_y, canvas.getWidth()/2 - bounds.width()/2 + bounds.right + padding, canvas.getHeight()/2 + bounds.bottom + padding + offset_y, paint);
				paint.setColor(Color.WHITE);
				canvas.drawText(text, canvas.getWidth()/2 - bounds.width()/2, canvas.getHeight()/2 + offset_y, paint);
				canvas.restore();
			} 
		}

		if( clear_toast != null )
			clear_toast.cancel();
		Activity activity = (Activity)context;
		
		clear_toast = new Toast(activity);
		View text = new RotatedTextView(message, activity);
		clear_toast.setView(text);
		clear_toast.setDuration(Toast.LENGTH_SHORT);
		clear_toast.show();

		return clear_toast;
	}

    public static Toast showToast(Activity activity, Toast clear_toast, String message) {
    	
		class RotatedTextView extends View {
			private String text = "";
			private Paint paint = new Paint();
			private Rect bounds = new Rect();

			public RotatedTextView(String text, Context context) {
				super(context);
				this.text = text;
			}
			
			@Override 
			protected void onDraw(Canvas canvas) { 
				final float scale = getResources().getDisplayMetrics().density;
				paint.setTextSize(22 * scale + 0.5f);
				paint.setStyle(Paint.Style.FILL);
				paint.setColor(Color.rgb(75, 75, 75));
				paint.setShadowLayer(1, 0, 1, Color.BLACK);
				paint.getTextBounds(text, 0, text.length(), bounds);
				final int padding = (int) (14 * scale + 0.5f);
				final int offset_y = (int) (32 * scale + 0.5f);
				canvas.save();
				canvas.drawRect(canvas.getWidth()/2 - bounds.width()/2 + bounds.left - padding, canvas.getHeight()/2 + bounds.top - padding + offset_y, canvas.getWidth()/2 - bounds.width()/2 + bounds.right + padding, canvas.getHeight()/2 + bounds.bottom + padding + offset_y, paint);
				paint.setColor(Color.WHITE);
				canvas.drawText(text, canvas.getWidth()/2 - bounds.width()/2, canvas.getHeight()/2 + offset_y, paint);
				canvas.restore();
			} 
		}

		if( clear_toast != null )
			clear_toast.cancel();
		clear_toast = new Toast(activity);
		View text = new RotatedTextView(message, activity);
		clear_toast.setView(text);
		clear_toast.setDuration(Toast.LENGTH_LONG);
		clear_toast.show();

		return clear_toast;
	}
	
    
    public static Toast showToast(Context context, Toast clear_toast, String message) {
    	
		class RotatedTextView extends View {
			private String text = "";
			private Paint paint = new Paint();
			private Rect bounds = new Rect();

			public RotatedTextView(String text, Context context) {
				super(context);
				this.text = text;
			}
			
			@Override 
			protected void onDraw(Canvas canvas) { 
				final float scale = getResources().getDisplayMetrics().density;
				paint.setTextSize(22 * scale + 0.5f);
				paint.setStyle(Paint.Style.FILL);
				paint.setColor(Color.rgb(75, 75, 75));
				paint.setShadowLayer(1, 0, 1, Color.BLACK);
				paint.getTextBounds(text, 0, text.length(), bounds);
				final int padding = (int) (14 * scale + 0.5f);
				final int offset_y = (int) (32 * scale + 0.5f);
				canvas.save();
				canvas.drawRect(canvas.getWidth()/2 - bounds.width()/2 + bounds.left - padding, canvas.getHeight()/2 + bounds.top - padding + offset_y, canvas.getWidth()/2 - bounds.width()/2 + bounds.right + padding, canvas.getHeight()/2 + bounds.bottom + padding + offset_y, paint);
				paint.setColor(Color.WHITE);
				canvas.drawText(text, canvas.getWidth()/2 - bounds.width()/2, canvas.getHeight()/2 + offset_y, paint);
				canvas.restore();
			} 
		}

		if( clear_toast != null )
			clear_toast.cancel();
		clear_toast = new Toast(context);
		View text = new RotatedTextView(message, context);
		clear_toast.setView(text);
		clear_toast.setDuration(Toast.LENGTH_SHORT);
		clear_toast.show();

		return clear_toast;
	}
    
}
