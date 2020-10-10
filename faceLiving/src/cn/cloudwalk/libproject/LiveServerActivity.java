package cn.cloudwalk.libproject;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.cloudwalk.FaceInterface;

import static cn.cloudwalk.libproject.LiveStartActivity.activity;

public class LiveServerActivity extends Activity {
    public static final String TAG = "LiveServer";
    public static final String FACEDECT_RESULT_ISSUCCESS = "facedect_result_type";
    public static final String FACEDECT_RESULT_FACEIMG = "facedect_result_faceimg";
    public static final String FACEDECT_RESULT_FACEPARAM = "facedect_result_faceparam";

    private ImageView mImgOffine;//前端检测
    private ImageView mImgServer;//后端防hack检测
    private TextView mTvOffine;//前端检测
    private TextView mTvServer;//后端防hack检测
    private Button mBtnOk;

    private boolean isSuccess;

    SoundPool sndPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloudwalk_activity_live_server);
        initView();
        initData();
    }

    private void initData() {
        int streamID;

        sndPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);

        isSuccess = getIntent().getBooleanExtra(LiveServerActivity.FACEDECT_RESULT_ISSUCCESS, isSuccess);


        if (isSuccess) {
            mImgOffine.setImageResource(R.drawable.ico_tick);
            mImgServer.setImageResource(R.drawable.ico_tick);

            mTvOffine.setText(getResources().getText(R.string.facedect_ok_offline));
            mTvOffine.setTextColor(getResources().getColor(R.color.face_result_ok));
            mTvServer.setText(getResources().getText(R.string.facedect_ok_server));
            mTvServer.setTextColor(getResources().getColor(R.color.face_result_ok));
            mBtnOk.setText(getResources().getText(R.string.yes));

            streamID = sndPool.load(this, R.raw.cloudwalk_success, 1);
        } else if (!isSuccess) {
            mImgOffine.setImageResource(R.drawable.ico_tick);
            mImgServer.setImageResource(R.drawable.ico_error);

            mTvOffine.setText(getResources().getText(R.string.facedect_ok_offline));
            mTvOffine.setTextColor(getResources().getColor(R.color.face_result_ok));
            mTvServer.setText(getResources().getText(R.string.facedect_fail_server));
            mTvServer.setTextColor(getResources().getColor(R.color.face_result_fail));
            mBtnOk.setText(getResources().getText(R.string.commit));

            streamID = sndPool.load(this, R.raw.cloudwalk_failed, 1);
        }

        sndPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                int playId = sndPool.play(sampleId, 1.0f, 1.0f, 0, 0, 1.0f);

            }
        });
    }

    /**
     * 初始化布局等参数
     */
    private void initView() {
        mImgOffine = (ImageView) findViewById(R.id.img_offine);
        mImgServer = (ImageView) findViewById(R.id.img_server);
        mTvOffine = (TextView) findViewById(R.id.tv_offine);
        mTvServer = (TextView) findViewById(R.id.tv_server);
        mBtnOk = (Button) findViewById(R.id.live_ok);

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSuccess) {
                    if (Bulider.mResultCallBack != null)
                        Bulider.mResultCallBack.result(true, true, "", 0d, FaceInterface
                                        .CW_LivenessCode.CW_FACE_LIVENESS_FACEDEC_OK,
                                Bulider.bestFaceData, Bulider.clipedBestFaceData, Bulider.liveDatas);
                    if (activity != null) {
                        activity.finish();
                    }
                    finish();
                } else {
                    startActivity(new Intent(LiveServerActivity.this, Bulider.startCls));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        sndPool.stop(1);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        sndPool.setOnLoadCompleteListener(null);
        sndPool.release();
        super.onDestroy();
    }
}
