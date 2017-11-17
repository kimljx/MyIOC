package cn.myioc.xioc;

import android.content.Context;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by luo on 2017/11/17.
 */

public class InjectView {

    public static void inject(Context context) {

        injectContentView(context);
        injectXView(context);

    }

    //绑定控件
    private static void injectXView(Context context) {

        Class<?> clazz = context.getClass();
//        获取所有加上XView注解的字段集合
        Field[] fields = clazz.getDeclaredFields();
        for (Field filed : fields) {
//            获取单个字段的注解信息
            XView xView = filed.getAnnotation(XView.class);
            if (xView == null) {
                continue;
            }
//            获取控件ID
            int viewId = xView.value();
            try {
//              获取方法
                Method findViewById = clazz.getMethod("findViewById", int.class);

//                反射调用函数方法
                View view = (View) findViewById.invoke(context, viewId);
//                开启访问私有属性权限
                filed.setAccessible(true);
//                设置值
                filed.set(context,view);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }


        }

    }


    //绑定ContentView
    private static void injectContentView(Context context) {

//        获取class
        Class<?> clazz = context.getClass();
//        获取注解标识
        ContentView contentView = clazz.getAnnotation(ContentView.class);
//        获取布局ID
        int layoutID = contentView.value();
        try {
//        获取方法
            Method setContentView = clazz.getMethod("setContentView", int.class);
//            反射调用方法函数
            setContentView.invoke(context, layoutID);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }

}
