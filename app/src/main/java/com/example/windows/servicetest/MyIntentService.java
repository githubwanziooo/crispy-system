package com.example.windows.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by WINDOWS on 2018/7/20.
 */

public class MyIntentService extends IntentService {
    public MyIntentService(){//无参构造函数
        super("MyIntentService");//调用父类的有参构造函数
    }
    @Override
    protected  void onHandleIntent(Intent intent){//在子类中实现onHandleIntent()这个抽象方法
        Log.d("MyIntentService","Thread id is"+ Thread.currentThread().getId());
    }
    //打印当前线程id。
    //根据IntentService的特性，这个服务在运行结束后应该是会自动停止的，
    //所以又重写了onDestroy()方法，
    @Override
    public void onDestroy(){
        super.onDestroy();//super调用父类的方法
        Log.d("MyIntentService","onDestroy executed");
    }
}
