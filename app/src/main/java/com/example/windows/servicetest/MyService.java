package com.example.windows.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {//MyService是继承自Service类的，说明这是一个服务
    // add onBind()方法
    // 创建一个Binder对象来对下载功能进行管理
    private DownloadBinder mBinder = new DownloadBinder();
    //新建一个DownloadBinder类，并让他继承自Binder,
    // 然后在内部开始下载以及查看下载进度的方法(仅仅只是两个模拟方法)
    class  DownloadBinder extends Binder {//定义Binder接口
        public void startDownload(){
            Log.d("MyService","startDownload executed");
        }
        public int getProgress(){
            Log.d("MyService","getProgress executed");
            return 0;
        }
    }
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {//onBind()方法是Service中唯一的抽象方法，必须在子类里实现
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }
    //end

    //重写了onCreate()、onStartCommand()、onDestroy()三个方法
    @Override
    public void onCreate(){//在服务创建的时候调用
        super.onCreate();
        Log.d("MyService","onCreate executed");
        //add通知
        /*Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent ,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        //startForeground()将一个Service放到前台执行使其不会被Android内存管理而摧毁
        //方法接受两个参数，第一个参数是通知的id，类似于notify()方法的第一个参数，第二个参数是构建出的Notification对象。
        startForeground(1,notification);*/
        //end
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //会在每次服务启动的时候调用
        //一般情况下，一旦服务启动就立刻去执行某个动作，就可以将逻辑写在该方法里
        Log.d("MyService","onStartCommand executed");

        //add intentService
       /* new Thread(new Runnable(){
            @Override
            public void run(){
                      stopSelf();
            }
        }).start();*/
        //end
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy(){//会在服务销毁的时候调用，当服务销毁时，该方法在回收那些不再使用的资源
        super.onDestroy();
        Log.d("MyService","onDestroy executed");
    }
}
