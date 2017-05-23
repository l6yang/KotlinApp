package com.kotlin.loyal.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.kotlin.loyal.impl.Contact;

import java.lang.ref.WeakReference;

public class LocationService extends Service implements Contact/*, AMapLocationListener*/ {
    private HandlerClass handler;
    private static final int LocatedStart = -100;
    private static final int LocatedStop = -200;
   /* private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();*/

    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        initParam();
        Message message = Message.obtain(handler, LocatedStart);
        message.sendToTarget();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initParam() {
        handler = new HandlerClass(this);
       /* locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(this);*/
    }

    private static class HandlerClass extends Handler {
        private final WeakReference<LocationService> weakReference;

        HandlerClass(LocationService service) {
            weakReference = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LocationService service = weakReference.get();
            switch (msg.what) {
                case LocatedStart:
                    //service.startLocation();
                    break;
                case LocatedStop:
                   /* AMapLocation location = (AMapLocation) msg.obj;
                    Intent intent = new Intent();
                    intent.setAction(Str.service_action_loc);
                    intent.putExtra("city", null == location ? "" : StringUtil.replaceNull(location.getCity()));
                    service.sendBroadcast(intent);
                    service.stopLocation();*/
                    break;
            }
        }
    }

   /* @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation) {
            //解析定位结果
            if (aMapLocation.getErrorCode() == 0)
                System.out.println("result::" + aMapLocation.getCity());
            else System.out.println(aMapLocation.getErrorInfo());
        } else {
            System.out.println("定位失败，loc is null");
        }
        Message message = Message.obtain(handler, LocatedStop, aMapLocation);
        message.sendToTarget();
    }

    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        *//*
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         *//*
        locationOption.setGpsFirst(false);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        // 设置是否单次定位
        locationOption.setOnceLocation(false);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(false);
        //设置是否使用传感器
        locationOption.setSensorEnable(false);
        //设置是否开启wifi扫描，如果设置为false时同时会停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
        locationOption.setInterval(2000);
        // 设置网络请求超时时间
        locationOption.setHttpTimeOut(3000);
    }

    private void startLocation() {
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        if (null != locationClient) {
            locationClient.setLocationOption(locationOption);
            locationClient.startLocation();
        }
    }

    //停止定位
    private void stopLocation() {
        if (null != locationClient)
            locationClient.stopLocation();
    }

    private void destroyLocation() {
        if (null != locationClient) {
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service onDestroy");
        //destroyLocation();
    }
}
