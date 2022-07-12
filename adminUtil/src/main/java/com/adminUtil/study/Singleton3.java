package com.adminUtil.study;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 * 懒汉模式————完美写法
 * @author luojinhua
 * @since 2022/6/24 14:25
 */
public class Singleton3 {

    private static final int tryNum = 50;

    /**
     * 一定要private
     */
    private Singleton3(){

    }

    private static class Sg{
        private static final  Singleton3 INSTANCE = new Singleton3();
    }

    public static Singleton3 getInstance(){
        return Sg.INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0 ; i < tryNum;i++){
            new Thread(()->
                    System.out.println(Singleton3.getInstance().hashCode())
            ).start();
        }
    }
}
