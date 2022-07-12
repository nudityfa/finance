package com.adminUtil.study;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 * 懒汉模式————用到才初始化
 * @author luojinhua
 * @since 2022/6/24 14:25
 */
public class Singleton2 {

    private static final int tryNum = 50;

    /**
     * volatile 使用，保证了写入了主内存
     */
    private static volatile Singleton2 INSTANCE;

    /**
     * 一定要private
     */
    private Singleton2(){

    }


    public static Singleton2 getInstance(){
        if (INSTANCE == null){
            synchronized (Singleton2.class){
                if (INSTANCE == null){
                    INSTANCE = new Singleton2();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0 ; i < tryNum;i++){
            new Thread(()->
                    System.out.println(Singleton2.getInstance().hashCode())
            ).start();
        }
    }
}
