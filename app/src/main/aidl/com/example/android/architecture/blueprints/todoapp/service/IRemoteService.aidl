// IRemoteService.aidl
package com.example.android.architecture.blueprints.todoapp.service;

// Declare any non-default types here with import statements

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String sayHello();

    int add(int arg1, int arg2);
}
