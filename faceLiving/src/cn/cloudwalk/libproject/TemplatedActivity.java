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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.cloudwalk.libproject.util.DisplayUtil;

/**
 * ClassName: TemplatedActivity <br/>
 * Description: <br/>
 * date: 2016-5-11 上午10:46:19 <br/>
 *
 * @author 284891377
 * @version
 * @since JDK 1.7
 */
public class TemplatedActivity extends BaseActivity {

	private LinearLayout mContainer;
	protected View mActionLayout;
	protected TextView mTitle;
	protected ImageView mLeftBtn;
	protected ImageView mRightBtn;
	protected TextView mRightText;

	protected boolean hasActionBar() {
		return true;
	}

	@Override
	public void setContentView(int layoutResID) {
		if (hasActionBar()) {
			View root = getLayoutInflater().inflate(layoutResID, null);
			mContainer.addView(root, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT));
			super.setContentView(mContainer);
		} else {
			super.setContentView(layoutResID);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		if (hasActionBar()) {
			mContainer = new LinearLayout(this);
			mContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
			mContainer.setOrientation(LinearLayout.VERTICAL);
			mActionLayout = getLayoutInflater().inflate(R.layout.cloudwalk_actionbar_layout, null);
			mTitle = (TextView) mActionLayout.findViewById(R.id.actionbar_title);
			mLeftBtn = (ImageView) mActionLayout.findViewById(R.id.actionbar_left_btn);
			mRightBtn = (ImageView) mActionLayout.findViewById(R.id.actionbar_right_btn);
			mRightText = (TextView) mActionLayout.findViewById(R.id.actionbar_right_text);
			mTitle.setOnClickListener(listener);
			mLeftBtn.setOnClickListener(listener);
			mRightBtn.setOnClickListener(listener);
			mRightText.setOnClickListener(listener);
			// mContainer.addView(mActionLayout);
			mContainer.addView(mActionLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					DisplayUtil.dip2px(this, 45)));
		}

	}

	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			final int id = v.getId();

			if (id == R.id.actionbar_left_btn)
				onLeftClick(v);
			if (id == R.id.actionbar_title)
				onClickTitle(v);
			if (id == R.id.actionbar_right_btn || id == R.id.actionbar_right_text)
				onRightClick(v);

		}
	};


	public void setActionBarBg(int colorResId){
		mContainer.setBackgroundColor(colorResId);
	}
	@Override
	public void setTitle(CharSequence title) {
		if (hasActionBar()) {
			mTitle.setText(title);
			mTitle.setBackgroundDrawable(null);
		} else {
			super.setTitle(title);
		}
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(getText(titleId));
	}

	protected void setLeftBtnIcon(Drawable icon) {
		if (hasActionBar()) {
			mLeftBtn.setImageDrawable(icon);
			mLeftBtn.setVisibility(View.VISIBLE);
		}
	}

	protected void setTitleIcon(int iconResID) {
		setTitleIcon(getResources().getDrawable(iconResID));
	}

	protected void setTitleIcon(Drawable icon) {
		if (hasActionBar()) {
			mTitle.setText(null);
			mTitle.setBackgroundDrawable(icon);
		}
	}

	protected void setLeftBtnIcon(int iconResID) {
		setLeftBtnIcon(getResources().getDrawable(iconResID));
	}

	protected void setRightBtnIcon(Drawable icon) {
		if (hasActionBar()) {
			mRightText.setVisibility(View.GONE);
			mRightBtn.setVisibility(View.GONE);
			mRightBtn.setImageDrawable(icon);
		}
	}

	protected void setRightText(int resId) {
		setRightText(getResources().getString(resId));
	}

	protected void setRightText(String text) {
		if (hasActionBar()) {
			mRightText.setVisibility(View.VISIBLE);
			mRightText.setText(text);
			mRightBtn.setVisibility(View.GONE);
		}
	}

	protected void setRightBackground(int resId) {
		if (hasActionBar()) {
			mRightText.setVisibility(View.VISIBLE);
			mRightText.setBackgroundResource(resId);
			mRightBtn.setVisibility(View.GONE);
		}
	}

	protected void setRightBtnIcon(int iconResID) {
		setRightBtnIcon(getResources().getDrawable(iconResID));
	}

	public void onLeftClick(View v) {
		finish();
	}

	public void onClickTitle(View v) {
	}

	public void onRightClick(View v) {
	}

}
