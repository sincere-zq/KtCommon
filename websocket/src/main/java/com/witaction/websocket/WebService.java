package com.witaction.websocket;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.net.URI;

public class WebService extends Service {
    private static final int HEART_MSG = 1002;
    private final static int GRAY_SERVICE_ID = 1001;
    private final static String GRAY_SERVICE_NAME = "1001";
    private static final long HEART_BEAT_RATE = 20;//每隔10秒进行一次对长连接的心跳检测
    private Handler handler = new WebHandler(this);
    private URI uri;
    public JWebSocketClient client;
    private JWebSocketClientBinder mBinder = new JWebSocketClientBinder();
    private WebMessageHandler messageHandler = new WebMessageHandlerImpl();

    private static class WebHandler extends Handler {
        private WeakReference<WebService> ref;

        public WebHandler(WebService service) {
            this.ref = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WebService service = ref.get();
            if (service == null) {
                return;
            }
            if (msg.what == HEART_MSG) {
                if (service.client != null) {
                    if (service.client.isClosed()) {
//                        LogUtils.i("重连");
                        service.reconnectWs();
                    } else {
//                        LogUtils.i("心跳");
                    }
                } else {
                    //如果client已为空，重新初始化websocket
//                    LogUtils.i("初始化心跳");
                    service.initSocketClient();
                }
                service.interval();
            }
        }
    }

    //灰色保活
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            //适配8.0service
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel mChannel = new NotificationChannel(GRAY_SERVICE_NAME, "保活", NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(mChannel);
                Notification notification = new Notification.Builder(getApplicationContext(), GRAY_SERVICE_NAME).build();
                startForeground(1, notification);
                stopForeground(true);
                stopSelf();
            } else {
                startForeground(GRAY_SERVICE_ID, new Notification());
                stopForeground(true);
                stopSelf();
            }
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

    //用于Activity和service通讯
    public class JWebSocketClientBinder extends Binder {
        public WebService getService() {
            return WebService.this;
        }
    }

    /**
     * 连接websocket
     */
    private void connect() {
        ThreadPoolProxyFactory.getThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 开启重连
     */
    private void reconnectWs() {
        ThreadPoolProxyFactory.getThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //重连
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    public void sendMsg(String msg) {
        if (null != client) {
            Log.e("JWebSocketClientService", "发送的消息：" + msg);
            client.send(msg);
        }
    }

    /**
     * 断开连接
     */
    private void closeConnect() {
        try {
            cancel();
            if (null != client) {
                client.close();
            }
            ThreadPoolProxyFactory.getThreadPoolProxy().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

    /**
     * 初始化websocket连接
     */
    private void initSocketClient() {
//        LocalConfigEntity userEntity = PreferencesManager.getInstance().getLocalConfig();
//        if (userEntity != null) {
//            Uri uriL = Uri.parse(userEntity.getServerIp());
//            String url = MessageFormat.format("ws://{0}/WebSocketApi/Index?user={1}", uriL.getHost() + ":" + uriL.getPort(), userEntity.getAccount());
//            uri = URI.create(url);
//        }
        client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                messageHandler.handleMessage(message);
            }
        };
        connect();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        interval();
        //设置service为前台服务，提高优先级
        if (Build.VERSION.SDK_INT < 25) {
            //Android4.3 - Android7.0，隐藏Notification上的图标
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        } else {
            //适配8.0service
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel mChannel = new NotificationChannel(GRAY_SERVICE_NAME, "保活", NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(mChannel);
                Notification notification = new Notification.Builder(getApplicationContext(), GRAY_SERVICE_NAME).build();
                startForeground(1, notification);
            } else {
                startForeground(GRAY_SERVICE_ID, new Notification());
            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onDestroy() {
        closeConnect();
        super.onDestroy();
    }

    /**
     * 心跳检测
     */
    protected void interval() {
        handler.sendEmptyMessageDelayed(HEART_MSG, HEART_BEAT_RATE * 1000);
    }

    /**
     * 取消订阅
     */
    public void cancel() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private static String getIpHostPort(URI uri) {
        URI effectiveURI = null;

        try {
            // URI(String scheme, String userInfo, String host, int port, String
            // path, String query,String fragment)
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable var4) {
        }

        assert effectiveURI != null;
        return effectiveURI.getHost();
    }
}
