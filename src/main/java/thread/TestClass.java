package thread;

public class TestClass {
    private static int m;
    public static int inc() {
        return m+1;
    }

    public static void main(String[] args) {
        System.out.println(inc());
    }
}
