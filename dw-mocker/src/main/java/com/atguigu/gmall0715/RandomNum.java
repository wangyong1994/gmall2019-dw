package com.atguigu.gmall0715;

import java.util.Random;

/**
 * Created by WangYong
 */
public class RandomNum {
    public static final  int getRandInt(int fromNum,int toNum){
        return   fromNum+ new Random().nextInt(toNum-fromNum+1);
    }

}
