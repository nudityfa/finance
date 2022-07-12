package com.adminUtil.study;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 * 饿汉模式————一开始直接初始化
 * @author luojinhua
 * @since 2022/6/24 14:25
 */
public class Singleton1 {

    private static final int tryNum = 50;

    private static final Singleton1 INSTANCE =  new Singleton1();

    /**
     * 一定要private
     */
    private Singleton1(){

    }

    public static Singleton1 getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0 ; i < tryNum;i++){
            new Thread(()->
                    System.out.println(Singleton1.getInstance().hashCode())
            ).start();
        }
    }
}
