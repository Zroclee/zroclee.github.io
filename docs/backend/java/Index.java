import java.util.Scanner;

public class Index {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        // testClass();
        // testOperator();
        // testArray();
        // testInput();
        // testIf();
        // testBMI(70.0, 1.75);
        // testSwitch();
        testLoop();
    }

    /**
     * 基础数据类型
     * 整点类型： byte, short, int, long
     * 浮点类型： float, double
     * 字符类型： char
     * 布尔类型： boolean
     * 引用数据类型： class, interface, array
     */

    /**
     * 整点类型： byte, short, int, long
     * ┌───┐
     * byte │ │
     * └───┘
     * ┌───┬───┐
     * short │ │ │
     * └───┴───┘
     * ┌───┬───┬───┬───┐
     * int │ │ │ │ │
     * └───┴───┴───┴───┘
     * ┌───┬───┬───┬───┬───┬───┬───┬───┐
     * long │ │ │ │ │ │ │ │ │
     * └───┴───┴───┴───┴───┴───┴───┴───┘
     * ┌───┬───┬───┬───┐
     * float │ │ │ │ │
     * └───┴───┴───┴───┘
     * ┌───┬───┬───┬───┬───┬───┬───┬───┐
     * double │ │ │ │ │ │ │ │ │
     * └───┴───┴───┴───┴───┴───┴───┴───┘
     * ┌───┬───┐
     * char │ │ │
     * └───┴───┘
     * 
     * byte：-128 ~ 127
     * short: -32768 ~ 32767
     * int: -2147483648 ~ 2147483647
     * long: -9223372036854775808 ~ 9223372036854775807
     * 
     */
    public static void testClass() {
        int x = 10;
        int i = -11111;
        int i2 = 1_000_000_000;
        System.out.println(x);
        System.out.println(i);
        System.out.println(i2);

        long n = 9223372036854775800L; // long型的结尾需要加L
        long n2 = 100; // 没有加L，此处900为int，但int类型可以赋值给long
        System.out.println(n);
        System.out.println(n2);

        // 浮点数可表示的范围非常大，float类型可最大表示3.4x1038，而double类型可最大表示1.79x10308。
        float f1 = 3.14f;
        float f2 = 3.14e38f;
        System.out.println(f1);
        System.out.println(f2);
        double d = 1.78e38;
        double d2 = -1.78e38;
        System.out.println(d);
        System.out.println(d2);

        // Java语言对布尔类型的存储并没有做规定，因为理论上存储布尔类型只需要1 bit，但是通常JVM内部会把boolean表示为4字节整数。
        boolean b = true;
        boolean b2 = false;
        boolean b3 = 5 > 3;
        int age = 15;
        boolean isAdult = age >= 18;
        System.out.println(isAdult);
        System.out.println(b);
        System.out.println(b2);
        System.out.println(b3);

        // 注意char类型使用单引号'，且仅有一个字符，要和双引号"的字符串类型区分开。
        char c = 'a';
        char c2 = '中';
        System.out.println(c);
        System.out.println(c2);

        // 除了上述基本类型的变量，剩下的都是引用类型。例如，引用类型最常用的就是String字符串：
        String s = "Hello, world!";
        System.out.println(s);

        // 常量 为了和变量区分开来，根据习惯，常量名通常全部大写。
        final double PI = 3.14;
        double radius = 5;
        double area = PI * radius * radius;
        System.out.println(area);

        // var 关键字 编译器会根据赋值语句自动推断出变量sb的类型是StringBuilder
        var y = 10;
        System.out.println(y);

        // 变量的作用范围 作用域 在Java中，多行语句用{ ... }括起来

        /*
         * Java提供了两种变量类型：基本类型和引用类型
         * 
         * 基本类型包括整型，浮点型，布尔型，字符型。
         * 
         * 变量可重新赋值，等号是赋值语句，不是数学意义的等号。
         * 
         * 常量在初始化后不可重新赋值，使用常量便于理解程序意图。
         * 
         * 
         */
    }

    /**
     * 运算
     * 
     * 整数运算遵循四则运算规则
     * 
     * 要特别注意，整数由于存在范围限制，
     * 如果计算结果超出了范围，就会产生溢出，而溢出不会出错，却会得到一个奇怪的结果：
     * -2147483641
     * long来替换int
     */
    public static void testOperator() {
        int a = 10 + 10 * 2;
        int b = 21474836;
        int c = a + b;
        System.out.println(c);
        int i = 100;
        i += 100;
        i++;
        i -= 100;
        i--;
        System.out.println(i);

        // 移位运算
        // 整数总是以二进制的形式表示
        // 移位运算就是 把二进制数向左或向右移动指定的位数
        // 如int类型的 7 的二进制0000000 0000000 0000000 00000111 = 7
        // 左移1位 00000000 00000000 00000000 00001110 = 14
        int n = 7;
        a = n << 1;
        System.out.println(a);
        // 右移一位 00000000 00000000 00000000 00000011 = 3
        b = n >> 1;
        System.out.println(b);

        // 位运算
        // 与运算的规则是，必须两个数同时为1，结果才为1，否则为0
        // 或运算的规则是，只要任意一个为1，结果就为1
        // 非运算的规则是，0和1互换
        // 异或运算的规则是，如果两个数不同，结果为1，否则为0：
        int n1 = 1 & 1;
        int n2 = 0 | 1;
        int n3 = ~1;
        int n4 = 1 ^ 0;
        System.out.println(n1);
        System.out.println(n2);
        System.out.println(n3);
        System.out.println(n4);

        // 在运算中类型自动提升与强制转型
        // 如果参与运算的两个数类型不一致，那么计算结果为较大类型的整型。
        // 也可以将结果强制转型，即将大范围的整数转型为小范围的整数。
        i = 12345;
        short s = (short) i; // 12345
        System.out.println(s);
        // 要注意，超出范围的强制转型会得到错误的结果，原因是转型时，int的两个高位字节直接被扔掉，仅保留了低位的两个字节

        // ---- 浮点计算 ----
        // 浮点数运算和整数运算相比，只能进行加减乘除这些数值计算，不能做位运算和移位运算。
        double d = 1.0 / 10;
        double d2 = 1 - 9.0 / 10;
        System.out.println(d);
        System.out.println(d2);

    }

    /*
     * 计算前N个自然数的和可以根据公式：
     * (1+N)×N/2
     * 请根据公式计算前N个自然数的和：
     */
    public static void testSum() {
        int N = 10;
        int sum = (1 + N) * N / 2;
        System.out.println(sum);
    }

    // 数组类型
    public static void testArray() {
        int[] arr = { 1, 2, 3, 4, 5 };
        System.out.println(arr[0]);

        // 测试引用类型变化的影响
        String[] names = { "ABC", "XYZ", "zoo" };
        String s = names[1];
        names[1] = "cat";
        System.out.println(s); // s是"XYZ"还是"cat"?
    }

    public static void testInput() {
        Scanner scanner = new Scanner(System.in); // 创建Scanner对象
        System.out.print("Input your name: "); // 打印提示
        String name = scanner.nextLine(); // 读取一行输入并获取字符串
        System.out.print("Input your age: "); // 打印提示
        int age = scanner.nextInt(); // 读取一行输入并获取整数
        System.out.printf("Hi, %s, you are %d\n", name, age); // 格式化输出
    }

    public static void testIf() {
        int n = 40;
        if (n < 60) System.out.println("不及格了");
        if (n >= 60) {
            System.out.println("及格了");
            System.out.println("恭喜你");
        }
        System.out.println("END");
    }
    // 计算体质指数BMI
    public static void testBMI(double weight, double height) {
        // 计算体质指数BMI
        double bmi = weight / height / height;
        if (bmi < 18.5) System.out.println("体重过轻");
        if (bmi >= 18.5 && bmi < 25) System.out.println("体重正常");
        if (bmi >= 25 && bmi < 28) System.out.println("体重过重");
        if (bmi >= 28 && bmi <32) System.out.println("体重肥胖");
        if (bmi >= 32) System.out.println("体重肥胖严重");
        System.out.println("Your BMI is: " + bmi);
    }

    public static void testSwitch() {
        String fruit = "apple";
        switch (fruit) {
            case "apple" -> System.out.println("I like apple");
            case "orange" -> System.out.println("I like orange");
            case "banana" -> {
                System.out.println("I like banana");
                System.out.println("Good choice!");
            }
            default -> System.out.println("I don't like any fruit");
        }
    }

    public static void testLoop() {
        int sum = 0;
		int m = 20;
		int n = 100;
		// 使用while计算M+...+N:
		while (m<=n) {
			sum += m;
			m++;
		}
		System.out.println(sum);

        // do while
        int sum2 = 0;
        int m2 = 20;
		int n2 = 100;
        do {
            sum2 += m2;
            m2++;
        } while (m2 <= n2);
        System.out.println(sum2);

        // for
        int[] ns = { 1, 4, 9, 16, 25 };
        for (int i = 0; i < ns.length; i++) {
            System.out.println(ns[i]);
        }
        int sum3 = 0;
        for (int n1 : ns) {
            // System.out.println(n1);
            sum3 += n1;
        }
        System.out.println(sum3);

        // 计算PI
        // 4*(1/3-1/5+1/7-1/9+...)
        double pi = 0;
        for (int i = 0; i < 100_0000_00; i++) {
            pi = i == 0 ? 1 : i%2 == 1 ? pi - 1.0 / (i*2+1) : pi + 1.0 / (i*2+1);
        }
        System.out.println(pi * 4);

    }   

}
