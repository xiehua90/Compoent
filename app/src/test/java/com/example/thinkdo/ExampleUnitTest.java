package com.example.thinkdo;


import com.example.thinkdo.model.dragger.CoffeeMakerModule;
import com.example.thinkdo.model.dragger.CoffeeShopComponent;
import com.example.thinkdo.model.dragger.DaggerCoffeeShopComponent;
import com.example.thinkdo.model.dragger.MachineMaker;
import com.example.thinkdo.model.dragger.mvc.DaggerXComponent;
import com.example.thinkdo.model.dragger.mvc.XActivity;
import com.example.thinkdo.model.dragger.mvc.XModule;
import com.example.thinkdo.model.dragger.zoo.DaggerZooComponent;
import com.example.thinkdo.model.dragger.zoo.ZooComponent;
import com.example.thinkdo.model.dragger.zoo.ZooModule;

import org.junit.Assert;
import org.junit.Test;


import java.util.Arrays;
import java.util.List;
import java.util.Observable;


public class ExampleUnitTest {

    @Test
    public void zooDaggerTest() {
//        ZooComponent ZooComponent = DaggerZooComponent.builder().zooModule(new ZooModule()).build();
//        ZooComponent.maker().toString();
//        ZooComponent.maker().toString();
//        DaggerZooComponent.builder().zooModule(new ZooModule()).build().maker().toString();

//        ZooModule zooModule = new ZooModule();
//        List<String> list = Arrays.asList("Lion", "Tiger", " Monkey");
//        for (String name : list) {
//            zooModule.setName(name);
//            DaggerZooComponent.builder().zooModule(zooModule).build().maker().toString();
//        }
    }

    @Test
    public void CoffeeTest() {
        CoffeeShopComponent shopComponent = DaggerCoffeeShopComponent.builder().coffeeMakerModule(new CoffeeMakerModule()).build();
        shopComponent.maker().makeCoffee();
        shopComponent.maker().makeCoffee();
    }
    @Test
    public void abc() {
        for (int i = 0; i < 10; i--) {
            int tem = set(i);
            int ori = reset(tem);
            System.out.println(String.format("i = %d, set= %d, reset=%d", i, tem, ori));

        }
        Assert.assertNotNull("Hello");
    }

    int set(int value) {
        return value | 0x01;
    }

    int reset(int value) {
	return value & 0xfffffffe;
	}


    @Test
    public void zooTest(){
        String str  = DaggerZooComponent.builder().build().maker().toString();
        Assert.assertNotNull(str);

        str = DaggerZooComponent.create().maker().toString();
        Assert.assertNotNull(str);
    }

    @Test
    public void mvcTest(){
        XActivity activity = new XActivity();
        XActivity activity1 = new XActivity();
        DaggerXComponent.builder().view(activity1).build().inject(activity);


        System.out.print(activity.presenter.mActivity.toString());
        String str = activity.presenter.mActivity.toString();
        Assert.assertNotNull(activity.coffeeMaker);
        Assert.assertNotEquals(activity.toString(), activity.presenter.mActivity.toString());
    }
}
