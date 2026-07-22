public class Oop {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Student s = new Student();
        s.setName("Zroc");
        s.setAge(18);
        System.out.println(s.getName());
        System.out.println(s.getAge());

        int n = 15;
        s.setAge(n);
        n = 30;
        System.out.println(n);
        System.out.println(s.getAge());

        String[] names = { "ABC", "XYZ", "zoo" };
        System.out.println(names[1]);

        s.setName(names[1]);
        System.out.println(s.getName());
        
        names[1] = "cat";
        System.out.println(names[1]);
        System.out.println(s.getName());
    }
}
class Student {
    private String name;
    private int age;    

    // 构造方法
    public Student() {
        System.out.println("Student constructor");
    }
    // 构造方法2
    public Student(String name, int age) {
        System.out.println("Student constructor with name and age");
        this.name = name;
        this.age = age;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
}


