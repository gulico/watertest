package com.example.wxy.watertest10.Bean;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.WindowManager;

import java.text.DecimalFormat;

/**
 * 屏幕分辨率计算工具
 */
public class ResolutionUtil {
    private static volatile ResolutionUtil instance;

    /**
     * UI模版标准宽度
     */
    private static final int STANDARD_SCREEN_WIDTH = 1080;
    /**
     * UI模版标准高度
     */
    private static final int STANDARD_SCREEN_HEIGHT = 1853;

    private boolean isInitial;
    private int screenWidth;
    private int screenHeight;
    /**
     * 垂直方向数据列
     */
    private SparseArray<Integer> verticalPixelList;
    /**
     * 水平方向数据列
     */
    private SparseArray<Integer> horizontalPixelList;

    public ResolutionUtil() {
        isInitial = false;
        verticalPixelList = new SparseArray<>();
        horizontalPixelList = new SparseArray<>();
    }

    /**
     * 获取当前设备屏幕分辨率
     *
     * @param context context
     */
    public void init(Context context) {
        if (!isInitial) {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);

            this.screenWidth = metrics.widthPixels;
            this.screenHeight = metrics.heightPixels;
            isInitial = true;
        }
    }

    public static ResolutionUtil getInstance() {
        if (instance == null) {
            synchronized (ResolutionUtil.class) {
                if (instance == null) {
                    instance = new ResolutionUtil();
                }
            }
        }
        return instance;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int formatHorizontal(int standardValue) {
        int processedValue = horizontalPixelList.get(standardValue, -1);
        if (processedValue == -1) {
            processedValue = Integer.valueOf(getFormatDouble("#0000", standardValue * screenWidth / STANDARD_SCREEN_WIDTH));
            horizontalPixelList.append(standardValue, processedValue);
        }
        return processedValue;
    }

    public int formatVertical(int standardValue) {
        int processedValue = verticalPixelList.get(standardValue, -1);
        if (processedValue == -1) {
            processedValue = Integer.valueOf(getFormatDouble("#0000", standardValue * screenHeight / STANDARD_SCREEN_HEIGHT));
            verticalPixelList.append(standardValue, processedValue);
        }
        return processedValue;
    }

    public static String getFormatDouble(String str, double sum) {
        DecimalFormat df = new DecimalFormat(str);
        return df.format(sum);
    }
}
