package com.dsdl.eidea.util;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by 刘大磊 on 2017/1/5 9:22.
 */
public class DateTimeHelperTest {
    public static class TestSimpleDateFormatThreadSafe extends Thread {
        Set<String> hash=new TreeSet<>();
        @Override
        public void run() {
            while(true) {
                try {
                    this.join(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                System.out.println(this.getName()+":"+ DateTimeHelper.formatDateTime(DateTimeHelper.parseDateTime("2013-05-24 06:02:20")));
            }
        }
    }
    @Test
    public void testMultithreadingFormatDate() {
        System.out.println("开始测试");
        for(int i = 0; i < 30; i++){
            new TestSimpleDateFormatThreadSafe().start();
        }

    }
    @Test
    public void testGetCurrentYear()
    {
        String year=DateTimeHelper.getCurrentYear();
        System.out.println(year);
        System.out.println(DateTimeHelper.getHourMinute(new Date()));
        System.out.println(DateTimeHelper.getCurrentMonth());
        System.out.println(DateTimeHelper.getCurrentDayInMonth());
    }
    public static void main(String[] args)
    {
        for(int i = 0; i < 3; i++){
            new TestSimpleDateFormatThreadSafe().start();
        }

    }
}
