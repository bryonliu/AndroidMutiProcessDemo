// AIDLService.aidl
package com.top.bryon.androidmutiprocessdemo.service;

// Declare any non-default types here with import statements
import com.top.bryon.androidmutiprocessdemo.INetCallback;
interface IAIDLService {
  void hello(int index);
  void register(INetCallback callback);
  void unregister(INetCallback callback);
}
