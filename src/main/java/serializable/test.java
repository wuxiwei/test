package serializable;

import java.io.*;

public class test {

    public static void main(String[] args) throws Exception {

//        Student stu = new Student();
//        stu.setId(1);
//        stu.setName("Tom");
//        stu.setAge(26);
////        stu.setScore(9000.0);
////        serlizableObject(stu); // 序列化
//        Student student = tranferSerObject();
//        System.out.println(student);

        String s4 = new StringBuilder("ab").append("c").toString();
        System.out.println(s4 == s4.intern());

    }

    // 序列化对象
    public static void serlizableObject(Student stu) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("D:/stu.txt"));
        ObjectOutputStream ois = new ObjectOutputStream(fos);
        ois.writeObject(stu);
        ois.flush();
        ois.close();
    }

    // 反序列化对象
    public static Student tranferSerObject() throws Exception {
        FileInputStream fis = new FileInputStream(new File("D:/stu.txt"));
        @SuppressWarnings("resource")
        ObjectInputStream ois = new ObjectInputStream(fis);
        return (Student) ois.readObject();
    }

}
