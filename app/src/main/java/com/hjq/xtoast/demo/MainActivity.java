package com.hjq.xtoast.demo;

import android.app.Activity;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.hjq.xtoast.XToast;
import com.hjq.xtoast.draggable.MovingDraggable;
import com.hjq.xtoast.draggable.SpringDraggable;

import java.util.List;
import java.util.Locale;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/XToast
 *    time   : 2019/01/04
 *    desc   : Demo 使用案例
 */
 public final class MainActivity extends AppCompatActivity {
    TextView SET;
    Integer text_cnt = 0;
    static XToast XToaststatic=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        udpServerThread = new UdpServerThread(UdpServerPORT);
        udpServerThread.start();
        if(XToaststatic==null) {
                    XToaststatic = new XToast<>(getApplication())
                     .setView(R.layout.toast_hint)
                    .setAnimStyle(android.R.style.Animation_Translucent)
                    .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                    .setText(android.R.id.message, String.valueOf(123))
                      .setOutsideTouchable(true)
                    // 设置窗口背景阴影强度
                     // 设置成可拖拽的
                    .setDraggable(new MovingDraggable())
                    .setOnClickListener(android.R.id.message, new XToast.OnClickListener<TextView>() {

                        @Override
                        public void onClick(XToast<?> toast, TextView view) {
                            toast.cancel();
                        }
                    })
                    .show();
        }
    }

    static final int UdpServerPORT = 12345;
    UdpServerThread udpServerThread;



//    public Activity getActivity_(UdpServerThread context)
//    {
//        if (context == null)
//        {
//            return null;
//        }
//        else if (context instanceof ContextWrapper)
//        {
//            if (context instanceof Activity)
//            {
//                return (Activity) context;
//            }
//            else
//            {
//                return getActivity_(((ContextWrapper) context).getBaseContext());
//            }
//        }
//
//        return null;
//    }

    public void show1(View v) {
        new XToast<>(this)
                .setDuration(3000)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Translucent)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                .setText(android.R.id.message, "这个动画是不是很骚")
                .show();
    }

    public void show2(View v) {
        new XToast<>(this)
                .setDuration(1000)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Activity)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                .setText(android.R.id.message, "一秒后消失")
                .show();
    }

    public void show3(View v) {
        new XToast<>(this)
                .setDuration(3000)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Dialog)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_warning)
                .setText(android.R.id.message, "是不是感觉很牛逼")
                .setOnToastListener(new XToast.OnToastListener() {

                    @Override
                    public void onShow(XToast<?> toast) {
                        Snackbar.make(getWindow().getDecorView(), "XToast 显示了", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDismiss(XToast<?> toast) {
                        Snackbar.make(getWindow().getDecorView(), "XToast 消失了", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void show4(View v) {
        new XToast<>(this)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Translucent)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                .setText(android.R.id.message, "点我点我点我")
                .setOnClickListener(android.R.id.message, new XToast.OnClickListener<TextView>() {

                    @Override
                    public void onClick(final XToast<?> toast, TextView view) {
                        view.setText("那么听话啊");
                        getWindow().getDecorView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 1000);
                    }
                })
                .show();
    }

    public void show5(View v) {
//        text_cnt++;
        XToast<?> show = new XToast<>(getApplication())
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Translucent)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                .setText(android.R.id.message, "show5")
                // 设置外层是否能被触摸
                .setOutsideTouchable(false)
                // 设置窗口背景阴影强度
                .setBackgroundDimAmount(0.5f)
                // 设置成可拖拽的
                .setDraggable(new MovingDraggable())
                .setOnClickListener(android.R.id.message, new XToast.OnClickListener<TextView>() {

                    @Override
                    public void onClick(XToast<?> toast, TextView view) {
                        toast.cancel();
                    }
                })
                .show();
    }

    final MyHandler handler = new MyHandler();

    public void show6(View v) {
        XXPermissions.with(MainActivity.this)
                .permission(Permission.SYSTEM_ALERT_WINDOW)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> granted, boolean all) {
                        // 传入 Application 表示这个是一个全局的 Toast
                        new XToast<>(getApplication())
                                .setView(R.layout.toast_phone)
                                .setGravity(Gravity.END | Gravity.BOTTOM)
                                .setYOffset(200)
                                // 设置指定的拖拽规则
                                .setDraggable(new SpringDraggable())
                                .setOnClickListener(android.R.id.icon, new XToast.OnClickListener<TextView>() {

                                    @Override
                                    public void onClick(XToast<?> toast, TextView view) {
                                        ToastUtils.show("我被点击了");
                                        Message msg = Message.obtain(handler, 1, 100, 200);
                                        SET=(TextView)findViewById(android.R.id.icon);
                                        msg.sendToTarget();
                                        // 点击后跳转到拨打电话界面
                                        // Intent intent = new Intent(Intent.ACTION_DIAL);
                                        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        // toast.startActivity(intent);
                                        // 安卓 10 在后台跳转 Activity 需要额外适配
                                        // https://developer.android.google.cn/about/versions/10/privacy/changes#background-activity-starts
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onDenied(List<String> denied, boolean never) {
                        new XToast<>(MainActivity.this)
                                .setDuration(1000)
                                .setView(R.layout.toast_hint)
                                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                                .setText(android.R.id.message, "请先授予悬浮窗权限")
                                .show();
                    }
                });
    }

    public void show7(View v) {
        // 将 ToastUtils 中的 View 转移给 XToast 来显示
        new XToast<>(this)
                .setDuration(1000)
                .setView(ToastUtils.getStyle().createView(this))
                .setAnimStyle(android.R.style.Animation_Translucent)
                .setText(android.R.id.message, "就问你溜不溜")
                .setGravity(Gravity.BOTTOM)
                .setYOffset(100)
                .show();
    }


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            text_cnt++;
            String str = null;
            Integer i =0;
            switch (msg.what) {
                case 1:
                    SET=(TextView)findViewById(android.R.id.icon);
                    if(SET!=null)
                    SET.setText(" is");
//                    if(XToaststatic==null) {
//                        XToast<?> XToaststatic = new XToast<>(getApplication())
//                                .setView(R.layout.toast_hint)
//                                .setAnimStyle(android.R.style.Animation_Translucent)
//                                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
//                                .setText(android.R.id.message, String.valueOf(123))
//                                // 设置外层是否能被触摸
//                                .setOutsideTouchable(false)
//                                // 设置窗口背景阴影强度
//                                .setBackgroundDimAmount(0.5f)
//                                // 设置成可拖拽的
//                                .setDraggable(new MovingDraggable())
//                                .setOnClickListener(android.R.id.message, new XToast.OnClickListener<TextView>() {
//
//                                    @Override
//                                    public void onClick(XToast<?> toast, TextView view) {
//                                        toast.cancel();
//                                    }
//                                })
//                                .show();
//                    }
//                    else {
                        if(XToaststatic!=null) {
                        XToaststatic.setText(android.R.id.message, String.valueOf(msg.arg1));
                        if(msg.arg1>200)
                            XToaststatic.setTextColor(android.R.id.message, Color.WHITE);//"#C10808");
                        else
                            XToaststatic.setTextColor(android.R.id.message, Color.RED);//"#C10808");
                        XToaststatic.show();
                    }
                     str = String.format(Locale.CHINA, "hello world arg1 = %d arg2 = %d", msg.arg1, msg.arg2);
                    break;
                case 2:
                    str = String.format(Locale.CHINA, "hi zj arg1 = %d arg2 = %d", msg.arg1, msg.arg2);
                    break;
            }
            Log.e(this.getClass().getName(),"handleMessage: " +"str");
        }
    }


    // @Override
    // protected void onStart() {
    //     udpServerThread = new UdpServerThread(UdpServerPORT);
    //     udpServerThread.start();
    //     super.onStart();
    // }

    // if(udpServerThread != null){
    //     udpServerThread.setRunning(false);
    //     udpServerThread = null;
    // }

    private class UdpServerThread extends Thread{

        int serverPort;
        DatagramSocket socket;

        boolean running;

        public UdpServerThread(int serverPort) {
            super();
            this.serverPort = serverPort;
        }

        public void setRunning(boolean running){
            this.running = running;
        }

        @Override
        public void run() {

            running = true;

            try {
                socket = new DatagramSocket(serverPort);

                Log.e(this.getClass().getName(), "UDP Server is running");

                while(running){
                    byte[] buf = new byte[256];

                    // receive request
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);     //this code block the program flow
                    String data = new String(packet.getData());
                    // send the response to the client at "address" and "port"
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
//                    String addressrp="192.168.0.108";
                    InetAddress addressrp = InetAddress.getByName("192.168.0.108");
                    int portrp=12347;
//                    address.;
                    //updatePrompt("Request from: " + address + ":" + port +" data :"+ data+"\n");

                    String dString = new Date().toString() + "\n"
                            + "Your address " + address + ":" + String.valueOf(port);
                    buf = dString.getBytes();
                    packet = new DatagramPacket(buf, buf.length,addressrp , portrp);
                    socket.send(packet);


                    //                    String line = "12312\r\n123";
//                    String pattern = "(\\d+)(\r\n)(123)";
//
//                    // 创建 Pattern 对象
//                    Pattern r = Pattern.compile(pattern);
//                    Matcher m = r.matcher(line);


                    //                    String s=new String(m.group(1));
//                    if(m.group(1)!=null)
//                    Log.e(this.getClass().getName(), m.group(1));
//                    Integer integer = text_cnt++;

                      int datamessage=99999;/**/
                      int index = data.indexOf("\r\n");
//                    System.out.println(str.substring(0, index));
                    try {
                        datamessage = Integer.parseInt(data.substring(0, index));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

//                    int index=data.indexOf("\r\n");
//                    StringBuffer temp = new StringBuffer(data);
//                    byte[]  databyte = data.getBytes();
//                    temp.setCharAt(index,'\0');
//                    temp.setCharAt(index+1,'\0');
//                    //                    for (int i=0;i<2;i++) {
////                        temp.append(databyte[i]);
////                    }
//                    String sendmessages=temp.toString();
//                    try {
//
//                    datamessage = Integer.parseInt(sendmessages);/**/
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//


                    Message msg = Message.obtain(handler, 1, datamessage, 200);
                    msg.sendToTarget();
                    //
//                    XXPermissions.with(getApplication())
//                            .permission(Permission.SYSTEM_ALERT_WINDOW)
//                            .request(new OnPermissionCallback() {
//                                @Override
//                                public void onGranted(List<String> granted, boolean all) {
//                                    // 传入 Application 表示这个是一个全局的 Toast
//                                    new XToast<>(getApplication())
//                                            .setView(R.layout.toast_phone)
//                                            .setGravity(Gravity.END | Gravity.BOTTOM)
//                                            .setYOffset(200)
//                                            // 设置指定的拖拽规则
//                                            .setDraggable(new SpringDraggable())
//                                            .setOnClickListener(android.R.id.icon, new XToast.OnClickListener<TextView>() {
//
//                                                @Override
//                                                public void onClick(XToast<?> toast, TextView view) {
//                                                    ToastUtils.show("我被点击了");
//                                                    Message msg = Message.obtain(handler, 1, 100, 200);
//                                                    SET=(TextView)findViewById(android.R.id.icon);
//                                                    msg.sendToTarget();
//                                                    // 点击后跳转到拨打电话界面
//                                                    // Intent intent = new Intent(Intent.ACTION_DIAL);
//                                                    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                    // toast.startActivity(intent);
//                                                    // 安卓 10 在后台跳转 Activity 需要额外适配
//                                                    // https://developer.android.google.cn/about/versions/10/privacy/changes#background-activity-starts
//                                                }
//                                            })
//                                            .show();
//                                }
//
//                                @Override
//                                public void onDenied(List<String> denied, boolean never) {
//                                    new XToast<>(MainActivity.this)
//                                            .setDuration(1000)
//                                            .setView(R.layout.toast_hint)
//                                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
//                                            .setText(android.R.id.message, "请先授予悬浮窗权限")
//                                            .show();
//                                }
//                            });



                }

                Log.e(this.getClass().getName(), "UDP Server ended");

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(socket != null){
                    socket.close();
                    Log.e(this.getClass().getName(), "socket.close()");
                }
            }
        }
    }

}