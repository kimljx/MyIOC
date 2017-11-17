package cn.myioc.xioc;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by luo on 2017/11/17.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnClickListener"
        ,listenerType = View.OnClickListener.class
        ,callBack = "onClick")
public @interface OnClick {
    int[] value() default -1;

}
