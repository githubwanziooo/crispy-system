package com.example.windows.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //implements实现接口
    //当一个活动去绑定服务了之后，就可以调用该服务里的Binder提供的方法了
    //add  binder
    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection(){
        //首先创建一个ServiceConnection的匿名类，
        // 在里面重写了onServiceDisconnected()和onServiceConnected()方法，这两个方法分别会在活动与服务成功绑定以及解除绑定的时候调用
        //onServiceConnected()方法中通过向下转型得到了DownloadBinder实例，这样可以在活动中根据具体得场景来调用DownloadBinder
        //中的任何方法public()方法，即实现了指挥服务干什么服务就干什么的功能。
        //onServiceConnected()方法中调用了DownloadBinder的startDownload()和getProgress()方法
        @Override
        public void onServiceDisconnected(ComponentName name){
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }
    };
    //end
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在onCreate()方法中分别获取Start Service按钮和Stop Service按钮的实例，并为他们注册点击事件
        Button startService = (Button)findViewById(R.id.start_service);
        Button stopService = (Button)findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

       //add bind
        Button bindService = (Button)findViewById(R.id.bind_service);
        Button unbindService = ( Button)findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        //end

        //add intentService
        /*Button startIntentService = ( Button)findViewById(R.id.start_intent_service);
        startIntentService.setOnClickListener(this);*/
        //end
    }
    @Override
    public void onClick(View v){//点击事件里分别构建出了Intent对象，
        // 调用startService()方法来启动MyService服务和调用stopService()方法来停止MyService服务
        //startService()和stopService()方法都是定义在Context类中的，所以在活动里可以直接调用这两个方法。
        //这里完全使用活动来决定服务何时停止，如果没有点击Stop Service按钮，服务就会一直处于运行状态。
        //但服务可以自己停止下来，只需在MyService的任何一个位置调用stopSelf()方法就可以让这个服务停止下来
        switch(v.getId()){
            case R.id.start_service:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);//启动服务
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this ,MyService.class);
                stopService(stopIntent);//停止服务
                break;
            //add bind
            case R.id.bind_service:
                //在该按钮点击事件中实现绑定，构建一个Intent对象，然后调用bindService()方法将MainActivity和MyService进行绑定
                //bindService()方法接收3个参数，第一个参数刚构建出的Intent对象，第二个参数是前面创建出的ServiceConnection的实例，
                //第三个参数是一个标识为，这里传入BIND_AUTO_CREATE表示活动和服务进行绑定后自动创建服务，会使得MyServicedan
                // 中的onCreate()方法得到执行，但onStartCommand()方法不会执行
                Intent bindIntent = new Intent(this,MyService.class);//将活动与服务进行绑定
                bindService(bindIntent,connection,BIND_AUTO_CREATE);//参数：绑定，连接，创建服务
                break;
            case R.id.unbind_service:
                //解除绑定按钮，实现解除活动和服务之间的绑定
                unbindService(connection);
                break;
            //end
            //add intentservice
           /* case R.id.start_intent_service:
                Log.d("MainActivity","Thread id is"+Thread.currentThread().getId());
                Intent intentService = new Intent(this,MyIntentService.class);
                startService(intentService);
                break;*/
                //end
            default:
                break;
        }
    }
}
