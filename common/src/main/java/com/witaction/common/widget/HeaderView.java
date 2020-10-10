package com.witaction.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.witaction.common.R;
import com.witaction.common.databinding.ViewHeadViewBinding;
import com.witaction.common.utils.DensityUtils;
import com.witaction.common.utils.GlobalUtil;

public class HeaderView extends RelativeLayout {
    private ViewHeadViewBinding binding;

    private HeaderView.HeaderListener headerListener;


    public HeaderView(Context context) {
        this(context, null);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        binding = ViewHeadViewBinding.inflate(LayoutInflater.from(context), this, true);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HeaderView);

        Drawable background = ta.getDrawable(R.styleable.HeaderView_Background);

        Drawable leftMipmap = ta.getDrawable(R.styleable.HeaderView_LeftImage);
        boolean leftShow = ta.getBoolean(R.styleable.HeaderView_LeftImage_Show, true);

        Drawable rightSecondMipmap = ta.getDrawable(R.styleable.HeaderView_RightSecondImage);
        boolean rightSecondShow = ta.getBoolean(R.styleable.HeaderView_RightSecondImage_Show, false);

        Drawable rightMipmap = ta.getDrawable(R.styleable.HeaderView_RightImage);
        boolean rightShow = ta.getBoolean(R.styleable.HeaderView_RightImage_Show, false);

        String title = ta.getString(R.styleable.HeaderView_Title);
        boolean titleShow = ta.getBoolean(R.styleable.HeaderView_Title_Show, true);
        int titleColor = ta.getColor(R.styleable.HeaderView_TitleColor, ContextCompat.getColor(context, R.color.black));
        float titleSize = ta.getDimension(R.styleable.HeaderView_TitleSize, GlobalUtil.INSTANCE.getDimension(R.dimen.sp_18));

        String subTitle = ta.getString(R.styleable.HeaderView_Sub_Title);
        boolean subTitleShow = ta.getBoolean(R.styleable.HeaderView_Sub_Title_Show, false);
        int subTitleColor = ta.getColor(R.styleable.HeaderView_Sub_TitleColor, ContextCompat.getColor(context, R.color.black));
        float subTitleSize = ta.getDimension(R.styleable.HeaderView_Sub_TitleSize, GlobalUtil.INSTANCE.getDimension(R.dimen.sp_18));

        String rightText = ta.getString(R.styleable.HeaderView_Right_Text);
        boolean rightTextShow = ta.getBoolean(R.styleable.HeaderView_Right_Text_Show, false);
        int rightTextColor = ta.getColor(R.styleable.HeaderView_Right_TextColor, ContextCompat.getColor(context, R.color.black));
        float rightTextSize = ta.getDimension(R.styleable.HeaderView_Right_TextSize, GlobalUtil.INSTANCE.getDimension(R.dimen.sp_16));

        boolean showLineView = ta.getBoolean(R.styleable.HeaderView_ShowLineView, true);

        ta.recycle();

        if (background != null) {
            setBackground(background);
        } else {
            setBackgroundColor(GlobalUtil.INSTANCE.getColor(R.color.white));
        }

        if (leftMipmap != null) {
            binding.ivHeaderLeft.setVisibility(VISIBLE);
            binding.ivHeaderLeft.setImageDrawable(leftMipmap);
        } else {
            binding.ivHeaderLeft.setVisibility(INVISIBLE);
        }
        if (rightMipmap != null) {
            binding.ivHeaderRight.setVisibility(VISIBLE);
            binding.ivHeaderRight.setImageDrawable(rightMipmap);
        } else {
            binding.ivHeaderRight.setVisibility(INVISIBLE);
        }
        if (rightSecondMipmap != null) {
            binding.ivHeaderRightSecond.setVisibility(VISIBLE);
            binding.ivHeaderRightSecond.setImageDrawable(rightSecondMipmap);
        } else {
            binding.ivHeaderRightSecond.setVisibility(INVISIBLE);
        }

        if (showLineView) {
            binding.line.setVisibility(VISIBLE);
        } else {
            binding.line.setVisibility(INVISIBLE);
        }

        binding.ivHeaderLeft.setVisibility(leftShow ? VISIBLE : GONE);
        binding.ivHeaderRightSecond.setVisibility(rightSecondShow ? VISIBLE : GONE);
        binding.ivHeaderRight.setVisibility(rightShow ? VISIBLE : GONE);

        binding.tvHeaderTitle.setText(title);
        binding.tvHeaderTitle.setVisibility(titleShow ? VISIBLE : GONE);
        binding.tvHeaderTitle.setTextColor(titleColor);
        binding.tvHeaderTitle.setTextSize(DensityUtils.INSTANCE.px2sp(titleSize));

        binding.tvHeaderSubTitle.setText(subTitle);
        binding.tvHeaderSubTitle.setVisibility(subTitleShow ? VISIBLE : GONE);
        binding.tvHeaderSubTitle.setTextColor(subTitleColor);
        binding.tvHeaderSubTitle.setTextSize(DensityUtils.INSTANCE.px2sp(subTitleSize));

        binding.tvHeaderRight.setText(rightText);
        binding.tvHeaderRight.setVisibility(rightTextShow ? VISIBLE : GONE);
        binding.tvHeaderRight.setTextColor(rightTextColor);
        binding.tvHeaderRight.setTextSize(DensityUtils.INSTANCE.px2sp(rightTextSize));

        if (Build.VERSION.SDK_INT >= 21) {//5.0以上系统判断
            int[] attrss = {android.R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrss);
            int resourceId = typedArray.getResourceId(0, 0);
            binding.ivHeaderLeft.setBackgroundResource(resourceId);
        }

    }

    public void setTitle(String title) {
        binding.tvHeaderTitle.setText(title);
    }

    public void setSubTitle(String subTitle) {
        binding.tvHeaderSubTitle.setText(subTitle);
    }

    public void setRightText(String rightText) {
        binding.tvHeaderRight.setText(rightText);
    }

    public void setRightVisible(int rightVisible) {
        binding.ivHeaderRight.setVisibility(rightVisible);
    }

    public void setRightSecondVisible(int rightVisible) {
        binding.ivHeaderRightSecond.setVisibility(rightVisible);
    }

    public void setLeftVisible(int leftVisible) {
        binding.ivHeaderLeft.setVisibility(leftVisible);
    }

    public void setHeaderListener(final HeaderView.HeaderListener headerListener) {
        this.headerListener = headerListener;
        binding.ivHeaderLeft.setOnClickListener(headerListener::onLeftClick);
        binding.tvHeaderSubTitle.setOnClickListener(headerListener::onSubTitleClick);
        binding.ivHeaderRight.setOnClickListener(headerListener::onRightClick);

        binding.ivHeaderRightSecond.setOnClickListener(headerListener::onRightSecondClick);
        binding.tvHeaderRight.setOnClickListener(headerListener::onRightTextClick);

    }

    public void setRightTextVisible(boolean visible) {
        binding.tvHeaderRight.setVisibility(visible ? VISIBLE : GONE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int statubarHeight = ImmersionBar.getStatusBarHeight((Activity) getContext());
        setPadding(0, statubarHeight, 0, 0);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, GlobalUtil.INSTANCE.getDimension(R.dimen.dp_48) + statubarHeight);
    }

    public interface HeaderListener {
        void onLeftClick(View view);

        void onSubTitleClick(View view);

        void onRightClick(View view);

        void onRightSecondClick(View view);

        void onRightTextClick(View view);
    }
}

