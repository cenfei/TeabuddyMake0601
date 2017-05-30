package com.taomake.teabuddy.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.taomake.teabuddy.R;
import com.taomake.teabuddy.object.DbRecordInfoObj;
import com.taomake.teabuddy.util.MyStringUtils;
import com.taomake.teabuddy.util.Util;

import java.util.ArrayList;
import java.util.List;

import quinticble.QuinticBleAPISdkBase;
import quinticble.QuinticCallbackTea;
import quinticble.QuinticCommon;
import quinticble.QuinticDeviceFactoryTea;
import quinticble.QuinticDeviceTea;
import quinticble.QuinticException;

/**
 * Created by foxcen on 16/4/14.
 */
public class Volume_Popwindow {

    private Integer chooseType = 1;//1代表刷刷钱包
    PopupWindow window;
     CallBackPayWindow callBackPayWindow;

    Activity activity;
    View relView;
    View view;
    /**
     * 显示popupWindow
     */
    public void showPopwindow(final Activity activity, View relView,String blindDeviceId,CallBackPayWindow callBackPayWindow) {
        // 利用layoutInflater获得View
        this.callBackPayWindow=callBackPayWindow;
        context = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         view = inflater.inflate(R.layout.cm_volume_popwindow, null);
this.relView=relView;
        this.activity=activity;

this.blindDeviceId=blindDeviceId;
//        getTheVoiceRecordListInfo(unionid,voiceid);
        initUi(activity, view, relView);

        connectFindDevice(context);
    }

    public void initColor() {

//        choose_photo_lib.setTextColor(context.getResources().getColor(R.color.line_hot_all));
//
//        open_camara.setTextColor(context.getResources().getColor(R.color.line_hot_all));
//
//        choose_photo_src.setTextColor(context.getResources().getColor(R.color.line_hot_all));
    }

    private Activity context;
//    TextView choose_photo_src, choose_photo_lib, open_camara;
private List<DbRecordInfoObj> dbRecordInfoObjList=new ArrayList<DbRecordInfoObj>();
    GridView gridview;
    /**
     * @param view
     */
    SeekBar    seekbar_first,seekbar_second;
    public void initUi(final Activity activity, View view, View relView) {



//

        window = new PopupWindow(view,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

            seekbar_first = (SeekBar) view.findViewById(R.id.seekbar_first);

        seekbar_first.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //监听点击时
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("xiaobing", "开始");
            }

            //监听滑动时
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("xiaobing", "变化" + progress);
            }

            //监听停止时
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("xiaobing", "结束");
                first_value=seekBar.getProgress();
                setFirstVolume(context);
            }
        });

            seekbar_second = (SeekBar) view.findViewById(R.id.seekbar_second);

        seekbar_second.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //监听点击时
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("xiaobing", "开始");
            }

            //监听滑动时
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("xiaobing", "变化" + progress);
            }

            //监听停止时
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("xiaobing", "结束");
                second_value=seekBar.getProgress();
                setSecondVolume(context);

            }
        });

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
//        window.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.rounded_choose));
        window.setBackgroundDrawable( dw);


        // 设置popWindow的显示和消失动画
     //   window.setAnimationStyle(R.style.mypopwindow_anim_style);
//        backgroundAlpha(0.5f, activity);

//        relView.getLocationOnScreen(location);
//        window.setAnimationStyle(R.style.mypopwindow_anim_style);  //设置动画
//这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
//        window.showAtLocation(relView, Gravity.NO_GRAVITY, (location[0] +relView.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight+10);
//        window.showAsDropDown(relView, 0,
//                0);

        // 在底部显示
        if(Util.checkDeviceHasNavigationBar(context)){
            window.showAtLocation(relView,
                    Gravity.CENTER, 0, 0);
            setBackgroundAlpha(activity,0.5f);
        }else{
            window.showAtLocation(relView,
                    Gravity.CENTER, 0, 0);
            backgroundAlpha(0.5f, activity);

        }


        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
                closePopupWindow(activity);
            }
        });
    }
    /***
     * 获取PopupWindow实例
     */
//    private void getPopupWindow() {
//
//        if (null != window) {
//            closePopupWindow();
//            return;
//        } else {
//            initPopuptWindow();
//        }
//    }

    /**
     * 关闭窗口
     */
    public void closePopupWindow(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 1f;
        activity.getWindow().setAttributes(params);

        if (window != null) {
            window.dismiss();
            window = null;
            System.out.println("popWindow消失 ...");

        }
    }

    public interface CallBackPayWindow {

        void handleCallBackDbSelect(String recorddir);

//        void handleCallBackPayWindowFromLib();

//        void handleDialogResultCancle();

    }

    public static void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
    /**
     * 设置页面的透明度
     * @param bgAlpha 1表示不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }



    //************************蓝牙操作*********************************

    /**
     * 功能：查找设备
     */
    private String blindDeviceId;
    private QuinticDeviceTea resultDeviceAll;
    private Integer countError = 0;
    FoxProgressbarInterface foxProgressbarInterface;
    public void connectFindDevice(final Context context) {
        foxProgressbarInterface = new FoxProgressbarInterface();

        foxProgressbarInterface.startProgressBar(context, "蓝牙读取中...");

        if (MyStringUtils.isNotNullAndEmpty(QuinticBleAPISdkBase.resultDevice)) {
            resultDeviceAll = QuinticBleAPISdkBase.resultDevice;
            // ************处理动作
            getbatteryLevel(context);
        } else {
        QuinticDeviceFactoryTea quinticDeviceFactory = QuinticBleAPISdkBase
                .getInstanceFactory(context);

        quinticDeviceFactory.findDevice(blindDeviceId,
                new QuinticCallbackTea<QuinticDeviceTea>() {

                    @Override
                    public void onComplete(final QuinticDeviceTea resultDevice) {
                        super.onComplete(resultDevice);
                        new Handler(context.getMainLooper())
                                .post(new Runnable() {
                                    @Override
                                    public void run() {
                                        resultDeviceAll = resultDevice;
                                        QuinticBleAPISdkBase.resultDevice = resultDeviceAll;
                                        // ************处理动作
                                        getbatteryLevel(context);

                                    }
                                });
                    }

                    @Override
                    public void onError(final QuinticException ex) {
                        new Handler(context.getMainLooper())
                                .post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (countError < 1) {
                                            Log.d("connectFindDevice ex",
                                                    ex.getCode()
                                                            + ""
                                                            + ex.getMessage());
                                            connectFindDevice(context);
                                            countError++;
                                        } else {
//                                            unconnectUi();
                                            // *****************连接失败
//                                                Util.Toast(context,
//                                                        "");
                                        }
                                    }
                                });
                    }
                });
        }
    }



    public void connectSendCodeFailUi(String msg){

        if(foxProgressbarInterface!=null)
        foxProgressbarInterface.stopProgressBar();

    }

    public void connectSendCodeSuccesslUi(){

        foxProgressbarInterface.stopProgressBar();

    }
    int first_value=0;
    int second_value=0;

    public void getbatteryLevel(final Context context) {
        if (resultDeviceAll == null) return;
        String code = "EA0D";
final String msg="泡茶音乐查询失败";
        resultDeviceAll.sendCommonCode(code, new QuinticCallbackTea<String>() {
            @Override
            public void onError(QuinticException ex) {
                super.onError(ex);
                connectSendCodeFailUi(msg);

            }

            @Override
            public void onComplete(final String result) {
                super.onComplete(result);
                if (result == null) {
                    connectSendCodeFailUi(msg);

                    return;
                }
                new Handler(context.getMainLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
//BACK 0A 04 01 53result
                                String trimResult = result.replace(" ", "");
                                if (trimResult.contains("ea0d01")) {

                                    byte[] data = QuinticCommon.stringToBytes(trimResult);
                                     first_value = QuinticCommon.unsignedByteToInt(data[3]);
                                    first_value=first_value-14*16;

                                    Log.d("当前音量", first_value + "");

                                    seekbar_first.setProgress(first_value * 100 / 15);

                                    getSecond(context);
//                                    getTeaStatus();
                                } else {
                                    connectSendCodeFailUi(msg);
                                }

                            }
                        });

            }
        });

    }


    public void getSecond(final Context context) {
        if (resultDeviceAll == null) return;
        String code = "EA03";
        final String msg="提示音乐查询失败";
        resultDeviceAll.sendCommonCode(code, new QuinticCallbackTea<String>() {
            @Override
            public void onError(QuinticException ex) {
                super.onError(ex);
                connectSendCodeFailUi(msg);

            }

            @Override
            public void onComplete(final String result) {
                super.onComplete(result);
                if (result == null) {
                    connectSendCodeFailUi(msg);

                    return;
                }
                new Handler( context.getMainLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
//BACK 0A 04 01 53result
                                String trimResult = result.replace(" ", "");
                                if (trimResult.contains("ea0301")) {
                                    byte[] data = QuinticCommon.stringToBytes(trimResult);
                                      second_value = QuinticCommon.unsignedByteToInt(data[3]);

                                    second_value=second_value-14*16;

                                    Log.d("当前音量", second_value + "");

                                    seekbar_second.setProgress(second_value * 100 / 15);

                                    connectSendCodeSuccesslUi();
//                                    getTeaStatus();
                                } else {
                                    connectSendCodeFailUi(msg);
                                }

                            }
                        });

            }
        });

    }


    public void setFirstVolume(final Context context) {
        if (resultDeviceAll == null) return;
        String code = "EB12";
      int   first_value0=first_value*15/100+14*16;

        code=code+  QuinticCommon.unsignedIntToHexString(first_value0);

        final String msg="设置泡茶音乐音量失败";
        resultDeviceAll.sendCommonCode(code, new QuinticCallbackTea<String>() {
            @Override
            public void onError(QuinticException ex) {
                super.onError(ex);
                connectSendCodeFailUi(msg);

            }

            @Override
            public void onComplete(final String result) {
                super.onComplete(result);
                if (result == null) {
                    connectSendCodeFailUi(msg);

                    return;
                }
                new Handler(context.getMainLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
//BACK 0A 04 01 53result
                                String trimResult = result.replace(" ", "");
                                if (trimResult.contains("eb1200")) {

                                    connectSendCodeSuccesslUi();
//                                    getSecond(context);
//                                    getTeaStatus();
                                } else {
                                    connectSendCodeFailUi(msg);
                                }

                            }
                        });

            }
        });

    }

    public void setSecondVolume(final Context context) {
        if (resultDeviceAll == null) return;
        String code = "EB08";
        int   second_value0=second_value*15/100+14*16;

        code=code+  QuinticCommon.unsignedIntToHexString(second_value0);

        final String msg="设置提示音量失败";
        resultDeviceAll.sendCommonCode(code, new QuinticCallbackTea<String>() {
            @Override
            public void onError(QuinticException ex) {
                super.onError(ex);
                connectSendCodeFailUi(msg);

            }

            @Override
            public void onComplete(final String result) {
                super.onComplete(result);
                if (result == null) {
                    connectSendCodeFailUi(msg);

                    return;
                }
                new Handler(context.getMainLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
//BACK 0A 04 01 53result
                                String trimResult = result.replace(" ", "");
                                if (trimResult.contains("eb0800")) {

                                    connectSendCodeSuccesslUi();
//                                    getSecond(context);
//                                    getTeaStatus();
                                } else {
                                    connectSendCodeFailUi(msg);
                                }

                            }
                        });

            }
        });

    }

}

