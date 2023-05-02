package com.learning.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @Author PYB
 * @Date 2023/4/30 18:45
 * @Version 1.0
 */
public class RedPacketUtil {
    public static List<Integer> divideRedPacket(Integer totalMoney, Integer peopleNum) {
        List<Integer> redPacket = new ArrayList<>();

        for (int i = peopleNum; i > 1; i--) {
            int region = (totalMoney/peopleNum)*2 ;
            Random random = new Random();
            int i1 = random.nextInt(region) +1;
            redPacket.add(i1);
            totalMoney  -= i1;
        }
        redPacket.add(totalMoney);
        return redPacket;
    }

    public static void main(String[] args) {
        for(int k = 0; k < 100; k++) {
            List<Integer> integers = divideRedPacket(100, 10);
            int i = 1;
            for (Integer integer : integers) {
                System.out.println("第"+i++ +"个红包："+integer);
            }
            integers.stream().reduce(Integer::sum).ifPresent(System.out::println);
        }


    }
}
