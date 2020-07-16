package thread;

public class Test2 {

//    public static int a;
static int v;
    static {
        int a = 3;
        v = a;
    }
    public static void main(String[] args) {

        System.out.println(v == 0);
//        System.out.println(a);
    }

}
