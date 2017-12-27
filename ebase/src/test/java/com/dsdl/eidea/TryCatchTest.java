package com.dsdl.eidea;

/**
 * @author liudalei
 * @version 1.0.0
 * @date 2017/12/1
 * @description
 */
public class TryCatchTest {
    public static void main(String[] args)
    {
        System.out.println(getName());
    }

    public static Integer getName()
    {
        int x=100;
        int y=1000;
        try
        {
            return x*y;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        finally {
            System.out.println("finally");
        }

    }
}
