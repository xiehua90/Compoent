//@TargetAnnotationTest.TYPE_USE_A
//@TargetAnnotationTest.PACKAGE_A
package com.example.xh.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class TargetAnnotationTest {
    public static void main(String[] args) {


    }

    @Target(ElementType.TYPE_PARAMETER)
    public @interface TYPE_PARAMETER_A{ }

    /**
     * TYPE_USE 标记的注解可以用于除package的任何声明处
     * */
    @Target(ElementType.TYPE_USE)
    public @interface TYPE_USE_A{ }

    @Target(ElementType.FIELD)
    public @interface FIELD_A{}

    @Target(ElementType.PACKAGE)
    public @interface PACKAGE_A{ }


    @TYPE_USE_A
    public @interface NALL{}


    public class Container<@TYPE_PARAMETER_A T>{

    }

    @TYPE_USE_A
    public class Container2<@TYPE_USE_A T>{
        @TYPE_USE_A Container2(){}

        @TYPE_USE_A T getField(@TYPE_USE_A T a){
            @TYPE_USE_A T b;
            return a;
        }
    }

//    /**
//     * ElementType.TYPE_PARAMETER 只能用于类型参数声明 不能用于类型声明,故此处会报错
//     * */
//    @TYPE_PARAMETER_A
//    public class Container3<@TYPE_PARAMETER_A T>{
//
//        @TYPE_PARAMETER_A
//        Container3(){}
//
//        @TYPE_PARAMETER_A T getField(@TYPE_PARAMETER_A T a){
//            @TYPE_PARAMETER_A T b;
//            return a;
//        }
//    }


    @TYPE_USE_A
    public class Container4{ }


    public class Container5{
        @FIELD_A String a;
    }

    @NALL
    public class Container6{
        @NALL
        Container6(){}

        @NALL
        String getMethod(@NALL String a){
            @NALL String b;

            return a;
        }

    }

//    /**
//     * 如果一个注解没有指定@Target注解，则此注解可以用于除了TYPE_PARAMETER和TYPE_USE以外的任何地方
//     * 此处用在了 TYPE_PARAMETER 故编译不过
//     * */
//    interface Nest<@NALL T>{ }

}
