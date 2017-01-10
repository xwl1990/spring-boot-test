package com.ck.util;

import java.util.UUID;

public class StringTool {

    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    }
    
    static int _i = 10;
    public synchronized static String getOrderID() {
        if(_i>99){
             _i=10;
        }
        return TimeUtils.getFormatCurrentTime(TimeUtils.DATE_FORMAT_0)+(_i++);
    }
    
    public static void main(String[] args) {
        for (int i = 0; i <500; i++) {
            System.out.println(getOrderID()+"  "+ getUUID());
        }
    }
}

	