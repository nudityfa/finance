package com.adminUtil.study;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 * 枚举实现，能防反序列化
 * @author luojinhua
 * @since 2022/6/24 14:40
 */
public enum Singleton4 {
    //
    INSTANCE;
    private static final int tryNum = 50;
    Singleton4() {
    }

    public static void main(String[] args) {
        for (int i = 0 ; i < tryNum;i++){
            new Thread(()->
                    System.out.println(INSTANCE.hashCode())
            ).start();
        }
    }
}
