package com.pattern.proxy.observer_proxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/21  15:38
 * @Version V1.0
 */
public class Subject {
  private   int   state;
  public List<Observer> observerList= new ArrayList<>();

public   void   attach(Observer  observer){
    observerList.add(observer);
}

public   void  setState(int state){
    this.state=state;
}

    public   int  getState(){
        return this.state;
    }


public   void   notifyAllObserver(){


    for (Observer observer : observerList) {
        observer.update();
    }
}
}
