package com.exam.cn.baselibrary.bindview;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by admin on 2017/7/22.
 *
 * Description : @interface 就是注解 Annotation
 */

@Target(ElementType.METHOD)  //代表 Annotation 的位置 ；FIELD属性，METHOD方法，TYPE类，CONSTRUCTOR构造函数
@Retention(RetentionPolicy.RUNTIME)  //什么时候生效 ；CLASS 编译时，RUNTIME 运行时，SOURCE源码资源时
public @interface CheckNet {
}
