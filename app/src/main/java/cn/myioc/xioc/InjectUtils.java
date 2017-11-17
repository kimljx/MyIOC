package cn.myioc.xioc;

import android.content.Context;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Created by luo on 2017/11/17.
 */

public class InjectUtils {

    public static void inject(Context context) {

        injectContentView(context);
        injectXView(context);

        injectEvent(context);

    }


//  绑定事件
    private static void injectEvent(Context context) {
//          获取所有的使用了注解的事件
        Class<?> clazz = context.getClass();
        Method[] methods = clazz.getMethods();
        //遍历所有方法
        for (Method method :methods) {
            //拿到方法上的所有的注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {

                Class<?> annotionType = annotation.annotationType();
                //拿到注解上的注解
                EventBase evebtBase = annotionType.getAnnotation(EventBase.class);
                if (evebtBase == null) {
                    continue;
                }
//        获取事件三要素，通过反射完成事件注入  设置事件的监听方法
                String setEventListener = evebtBase.listenerSetter();
//            监听事件类型
                Class<?> listenerType = evebtBase.listenerType();
//            回调函数
                String callBack = evebtBase.callBack();

                try {
                    //拿到Onclick注解中的value方法
                    Method value = annotionType.getDeclaredMethod("value");
                    //取出所有的viewId
                    int[] viewIds = (int[]) value.invoke(annotation);

//                    通过ListenerInvocation设置代理
                    ListenerInvocation listenerInvocation = new ListenerInvocation(context);
                    listenerInvocation.addMethod(callBack,method);
                    Object listener = Proxy.newProxyInstance(
                            listenerType.getClassLoader(),
                            new Class<?>[]{listenerType}, listenerInvocation);

//                拿到控件对象  设置事件
                    for (int id : viewIds) {
                        Method findViewById = clazz.getMethod("findViewById", int.class);
                        View view = (View) findViewById.invoke(context, id);
                        if (view == null) {
                            continue;
                        }
                        Method setEventListener1 = view.getClass()
                                .getMethod(setEventListener, listenerType);

                        setEventListener1.invoke(view,listener);

                    }


                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }
        }


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
