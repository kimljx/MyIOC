package cn.myioc.xioc;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by luo on 2017/11/24.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnLongClickListener"
        ,listenerType = View.OnLongClickListener.class
        ,callBack = "onLongClick")
public @interface OnLongClick {
    int[] value() default -1;


}
