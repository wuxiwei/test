package thread;

import jdk.nashorn.internal.runtime.ListAdapter;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Test2 {

//    public static int a;

//    static class A {
//        int a = 1024 * 1024 * 1024;
//    }

    public static void main(String[] args) {

        ByteBuffer allocate = ByteBuffer.allocate(8 * 1024 * 1024);
        ByteBuffer.allocateDirect(1024 * 1024 * 10);

        while (true) {

        }
//        System.out.println(a);
    }

}
