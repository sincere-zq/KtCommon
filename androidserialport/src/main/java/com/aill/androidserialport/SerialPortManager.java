package com.aill.androidserialport;

import android.text.TextUtils;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 串口读卡
 */
public class SerialPortManager implements Runnable {
    private static SerialPortManager instance;
    private FileDescriptor mId;
    private static final String PATH = "/dev/ttyS4";
    private static final int BOUND = 9600;
    private FileInputStream inputStream = null;
    private byte[] revBuf = new byte[512];
    private boolean isStart;
    private String lastCardNo;
    private long lastTime;
    private OnReadCardResultLitener onReadCardResultLitener;

    public void setOnReadCardResultLitener(OnReadCardResultLitener onReadCardResultLitener) {
        this.onReadCardResultLitener = onReadCardResultLitener;
    }

    public interface OnReadCardResultLitener {
        void onReadSuccess(String cardNo);
    }

    private ScheduledExecutorService sExecutor = new ScheduledThreadPoolExecutor(1);

    private void execute(Runnable runnable) {
        sExecutor.execute(runnable);
    }

    private SerialPortManager() {
        openDev();
    }

    public static synchronized SerialPortManager getInstance() {
        if (instance == null) {
            instance = new SerialPortManager();
        }
        return instance;
    }

    /**
     * 打开设备
     */
    private void openDev() {
        mId = SerialPort.open(PATH, BOUND, 0);
        if (mId != null) {
            inputStream = new FileInputStream(mId);
        }
    }

    public void setStart() {
        if (mId != null && !isStart) {
            isStart = true;
            execute(this);
        }
    }

    @Override
    public void run() {
        while (isStart) {
            if (mId != null) {
                try {
                    if (inputStream != null) {
                        int length;
                        if ((length = inputStream.read(revBuf)) != -1) {
                            String cardNo = new String(revBuf, 0, length, "utf-8");
                            handleCardNo(cardNo.substring(1, cardNo.length() - 1));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleCardNo(String cardNo) {
        //去重
        long time = System.currentTimeMillis();
        if (TextUtils.equals(lastCardNo, cardNo)) {
            if (time - lastTime > 5000) {
                lastTime = time;
                lastCardNo = cardNo;
                if (!TextUtils.isEmpty(cardNo) && onReadCardResultLitener != null) {
                    onReadCardResultLitener.onReadSuccess(cardNo);
                }
            }
        } else {
            lastTime = time;
            lastCardNo = cardNo;
            if (!TextUtils.isEmpty(cardNo) && onReadCardResultLitener != null) {
                onReadCardResultLitener.onReadSuccess(cardNo);
            }
        }
    }

    public void destroy() {
        isStart = false;
        if (mId != null) {
            try {
                inputStream.close();
                inputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
