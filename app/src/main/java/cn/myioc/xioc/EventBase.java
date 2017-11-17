package cn.myioc.xioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by luo on 2017/11/17.
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
//    所有点击事件的三要素
public @interface EventBase {

//    设置监听
    String listenerSetter();
//    设置类型
    Class<?> listenerType();
//    设置回调
    String callBack();




}
