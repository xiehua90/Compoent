#include <jni.h>
#include <string>

//
// Created by A18060 on 2019/1/30.
//




extern "C" JNIEXPORT jstring
JNICALL Java_com_example_thinkdo_adater_Test_sayHello
  (JNIEnv *env, jobject){
    std::string hello = "Hello from C++";
    return env-> NewStringUTF(hello.c_str());
  }