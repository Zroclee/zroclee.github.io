# JAVA学习日志-基础篇

## 环境搭建

1. 安装JDK

确保从[Oracle的官网](https://www.oracle.com/java/technologies/downloads/)下载最新的稳定版JDK，需要注意系统版本和芯片类型。安装完成后，配置环境变量。

2. 安装IDE

IDE有很多种，比如完全免费Eclipse，JetBrains公司的IntelliJ IDEA等。选择一个适合自己，比如我是前端转后端习惯用VSCode，装个插件一样可以学些和开发java。安装完成后，配置环境变量。

## JAVA 基础知识

- Java程序基本结构
- 变量和数据类型
- 整数运算
- 浮点数运算
- 布尔运算
- 字符和字符串
- 数组类型

### 基本结构

```java
/**
 * 可以用来自动创建文档的注释
 */
public class Hello {
    public static void main(String[] args) {
        // 向屏幕输出文本:
        System.out.println("Hello, world!");
        /* 多行注释开始
        注释内容
        注释结束 */
    }
} // class定义结束

```

Java是面向对象的语言，一个程序的基本单位就是class。

类名要求：

- 类名必须以英文字母开头，后接字母，数字和下划线的组合
- 习惯以大写字母开头

其中public是访问修饰符，表示该class是公开的

在class内部，可以定义若干方法（method）：

Java入口程序规定的方法必须是静态方法，方法名必须为main，括号内的参数必须是String数组。

在方法内部，语句才是真正的执行代码。Java的每一行语句必须以分号结束

### 变量和数据类型

在Java中，变量分为两种：基本类型的变量和引用类型的变量。

在Java中，变量必须先定义后使用，在定义变量的时候，可以给它一个初始值。

变量的一个重要特点是可以重新赋值。

基本类型的变量：

- 整数类型（int, short, byte, long）
- 浮点数类型（float, double）
- 布尔类型（boolean）
- 字符类型（char）

引用类型的变量：

- 字符串类型（String）
- 数组类型（数组是引用类型）
- 类类型（自定义的类）

常量：定义变量的时候，如果加上final修饰符，这个变量就变成了常量，不能重新赋值。

var关键字：些时候，类型的名字太长，写起来比较麻烦。可以使用var关键字来定义变量，Java会自动推断变量的类型。

变量的作用范围：定义变量时，要遵循作用域最小化原则，尽量将变量定义在尽可能小的作用域，并且，不要重复使用变量名。

### 整数运算

Java的整数运算遵循四则运算规则，可以使用任意嵌套的小括号。四则运算规则和初等数学一致。

**_ 溢出 _**
特别注意，整数由于存在范围限制，如果计算结果超出了范围，就会产生溢出，而溢出不会出错，却会得到一个奇怪的结果:

```java
// 运算溢出
public class Main {
    public static void main(String[] args) {
        int x = 2147483640;
        int y = 15;
        int sum = x + y;
        System.out.println(sum); // -2147483641
    }
}
```

要解决上面的问题，可以把int换成long类型，由于long可表示的整型范围更大

简写的运算符，即+=，-=，\*=，/=

自增/自减:++，--

**_移位运算_**
整数总是以二进制的形式，整数进行移位运算时，会将二进制表示的位向左或向右移动指定的位数。

左移运算符：<<
右移运算符：>>>
无符号右移运算符：>>>

**_位运算_**
位运算是按位进行与$、或|、非~和异或^的运算。

```java
// 位运算
public class Main {
    public static void main(String[] args) {
        int i = 167776589; // 00001010 00000000 00010001 01001101
        int n = 167776512; // 00001010 00000000 00010001 00000000
                         // & -----------------------------------
                           // 00001010 00000000 00010001 00000000
        System.out.println(i & n); // 167776512
    }
}

```

**_运算优先级_**
在Java的计算表达式中，运算优先级从高到低依次是：

```
()
! ~ ++ --
* / %
+ -
<< >> >>>
&
|
+= -= *= /=
```

记不住也没关系，只需要加括号就可以保证运算的优先级正确。

**_类型自动提升与强制转型_**
在运算过程中，如果参与运算的两个数类型不一致，那么计算结果为较大类型的整型。

也可以将结果强制转型，即将大范围的整数转型为小范围的整数。
但是要注意，超出范围的强制转型会得到错误的结果，原因是转型时，高位字节直接被扔掉，只保留低位字节。

### 浮点数运算

浮点数运算和整数运算相比，只能进行加减乘除这些数值计算，不能做位运算和移位运算。

在计算机中，浮点数虽然表示的范围大，但是，浮点数有个非常重要的特点，就是浮点数常常无法精确表示。

举个例子：

> 浮点数0.1在计算机中就无法精确表示，因为十进制的0.1换算成二进制是一个无限循环小数，很显然，无论使用float还是double，都只能存储一个0.1的近似值。但是，0.5这个浮点数又可以精确地表示。

因此，浮点数运算会产生误差

```java
// 浮点数运算误差
public class Main {
    public static void main(String[] args) {
        double x = 1.0 / 10;
        double y = 1 - 9.0 / 10;
        // 观察x和y是否相等:
        System.out.println(x); // 0.1
        System.out.println(y); // 0.09999999999999998
    }
}

```

浮点数在内存的表示方法和整数比更加复杂。Java的浮点数完全遵循IEEE-754标准，这也是绝大多数计算机平台都支持的浮点数标准表示方法。

比较两个浮点数通常比较它们的差的绝对值是否小于一个特定值

```java
// 比较x和y是否相等，先计算其差的绝对值:
double r = Math.abs(x - y);
// 再判断绝对值是否足够小:
if (r < 0.00001) {
    // 可以认为相等
} else {
    // 不相等
}

```

类型提升
如果参与运算的两个数其中一个是整型，那么整型可以自动提升到浮点型
**_需要特别注意，在一个复杂的四则运算中，两个整数的运算不会出现自动提升的情况。_**

溢出
整数运算在除数为0时会报错，而浮点数运算在除数为0时，不会报错，但会返回几个特殊值：
NaN表示Not a Number
Infinity表示无穷大
-Infinity表示负无穷大

强制转型
可以将浮点数强制转型为整数。在转型时，浮点数的小数部分会被丢掉。如果转型后超过了整型能表示的最大范围，将返回整型的最大值。

### 布尔运算

布尔类型boolean，永远只有true和false两个值。

布尔运算是一种关系运算，包括以下几类：

比较运算符：>，>=，<，<=，==，!=
与运算 &&
或运算 ||
非运算 !

关系运算符的优先级从高到低依次是：

```
!
>，>=，<，<=
==，!=
&&
||
```

**_ 短路运算 _**
布尔运算的一个重要特点是短路运算。如果一个布尔运算的表达式能提前确定结果，则后续的计算不再执行，直接返回结果。

```java
// 短路运算
public class Main {
    public static void main(String[] args) {
        boolean b = 5 < 3;
        boolean result = b && (5 / 0 > 0); // 此处 5 / 0 不会报错
        System.out.println(result);

        boolean result = true || (5 / 0 > 0); // true
        System.out.println(result);
    }
}
```

与运算和或运算是短路运算。

三元运算`b ? x : y`后面的类型必须相同，三元运算也是“短路运算”，只计算x或y。

### 字符和字符串

**_ 在Java中，字符和字符串是两个不同的类型。 _**

字符类型char是基本数据类型，它是character的缩写。代码中用`'`单引号表示，例如：

```java
char c = 'a';
```

将char类型直接赋值给int类型，会自动提升提升为int类型。

```java
int n1 = 'A'; // 字母“A”的Unicodde编码是65
int n2 = '中'; // 汉字“中”的Unicode编码是20013

// 注意是十六进制:
char c3 = '\u0041'; // 'A'，因为十六进制0041 = 十进制65
char c4 = '\u4e2d'; // '中'，因为十六进制4e2d = 十进制20013
```

和char类型不同，字符串类型String是引用类型，我们用双引号"..."表示字符串。一个字符串可以存储0个到任意个字符：

```java
String s = "hello";
String s = "abc\"xyz"; // 包含7个字符: a, b, c, ", x, y, z
String s = "abc\\xyz"; // 包含7个字符: a, b, c, \, x, y, z
```

常见的转义字符包括：

```
\" 表示字符"
\' 表示字符'
\\ 表示字符\
\n 表示换行符
\r 表示回车符
\t 表示Tab
\u#### 表示一个Unicode编码的字符
```

**_字符串连接_**
Java的编译器对字符串做了特殊照顾，可以使用+连接任意字符串和其他数据类型，这样极大地方便了字符串的处理。
如果用+连接字符串和其他数据类型，会将其他数据类型先自动转型为字符串，再连接：

```java
// 字符串连接
public class Main {
    public static void main(String[] args) {
        String s1 = "Hello";
        String s2 = "world";
        String s = s1 + " " + s2 + "!";
        System.out.println(s); // Hello world!

        int age = 25;
        String s = "age is " + age;
        System.out.println(s); // age is 25
    }
}

```

多行字符串
从Java 13开始，字符串可以用"""..."""表示多行字符串（Text Blocks）了

```java
// 多行字符串
public class Main {
    public static void main(String[] args) {
        String s = """
                   SELECT * FROM
                     users
                   WHERE id > 100
                   ORDER BY name DESC
                   """;
        System.out.println(s);

        // 上述多行字符串实际上是5行，在最后一个DESC后面还有一个\n。如果我们不想在字符串末尾加一个\n，就需要这么写：
        String s = """
                   SELECT * FROM
                     users
                   WHERE id > 100
                   ORDER BY name DESC""";
        System.out.println(s);
    }
}

```

**_ 不可变特性 _**

Java的字符串除了是一个引用类型外，还有个重要特点，就是字符串不可变。

```java
// 字符串不可变
public class Main {
    public static void main(String[] args) {
        String s = "hello";
        System.out.println(s); // 显示 hello
        s = "world";
        System.out.println(s); // 显示 world
    }
}
```

观察执行结果，难道字符串s变了吗？其实变的不是字符串，而是变量s的“指向

空值null
引用类型的变量可以指向一个空值null，它表示不存在，即该变量不指向任何对象。
要区分空值null和空字符串""。

### 数组类型

定义一个数组类型的变量，使用数组类型“类型[]”，例如：

```java
int[] arr = new int[3];
```

这表示定义了一个整数数组，数组的长度是3。

java的数组有几个特点：

- 数组所有元素初始化为默认值，整型都是0，浮点型是0.0，布尔型是false；
- 数组一旦创建后，大小就不可改变

```java
// 数组
public class Main {
    public static void main(String[] args) {
        // 5位同学的成绩:
        int[] ns = new int[5];
        ns = new int[] { 68, 79, 91, 85, 62 };
        ns = { 68, 79, 91, 85, 62 };
        System.out.println(ns.length); // 5
        System.out.println(ns[5]); // 索引n不能超出范围
    }
}

```

字符串数组
如果数组元素不是基本类型，而是一个引用类型，那么，修改数组元素会有哪些不同？

```java
// 数组
public class Main {
    public static void main(String[] args) {
        String[] names = {"ABC", "XYZ", "zoo"};
        String s = names[1];
        names[1] = "cat";
        System.out.println(s); // s是"XYZ"还是"cat"?
    }
}
```

数组元素可以是值类型（如int）或引用类型（如String），但数组本身是引用类型；

---

## 流程控制

### 输入和输出

在前面的代码中，我们总是使用`System.out.println()`来向屏幕输出一些内容。

如果输出后不想换行，可以用`System.out.print()`

格式化输出:
Java还提供了格式化输出的功能。为什么要格式化输出？因为计算机表示的数据不一定适合人来阅读

如果要把数据显示成我们期望的格式，就需要使用格式化输出的功能。格式化输出使用System.out.printf()，通过使用占位符%?，printf()可以把后面的参数格式化成指定格式：

```java
// 格式化输出
public class Main {
    public static void main(String[] args) {
        double d = 12900000;
        System.out.println(d); // 1.29E7

        double d = 3.1415926;
        System.out.printf("%.2f\n", d); // 显示两位小数3.14
        System.out.printf("%.4f\n", d); // 显示4位小数3.1416

        int n = 12345000;
        System.out.printf("n=%d, hex=%08x", n, n); // 注意，两个%占位符必须传入两个数
    }
}

```

Java的格式化功能提供了多种占位符，可以把各种数据类型“格式化”成指定的字符串：

|---|---|
|占位符|说明|
|---|---|
|%d|格式化输出整数|
|%x|格式化输出十六进制整数|
|%f|格式化输出浮点数|
|%e|格式化输出科学计数法表示的浮点数|
|%s|格式化字符串|

#### 输入

Java提供Scanner对象来方便输入，读取对应的类型可以使用：scanner.nextLine() / nextInt() / nextDouble() / ...

我们先看一个从控制台读取一个字符串和一个整数的例子：

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // 创建Scanner对象
        System.out.print("Input your name: "); // 打印提示
        String name = scanner.nextLine(); // 读取一行输入并获取字符串
        System.out.print("Input your age: "); // 打印提示
        int age = scanner.nextInt(); // 读取一行输入并获取整数
        System.out.printf("Hi, %s, you are %d\n", name, age); // 格式化输出
        /*
        Hello, world!
        Input your name: 111
        Input your age: 22
        Hi, 111, you are 22
        */
    }
}

```

### if条件判断

在Java程序中，如果要根据条件来决定是否执行某一段代码，就需要if语句。
根据if的计算结果（true还是false），JVM决定是否执行if语句块（即花括号{}包含的所有语句）。

```java
// 条件判断
public class Main {
    public static void main(String[] args) {
        int n = 70;
        if (n>=90) {
            System.out.println("优秀");
            System.out.println("恭喜你");
        } else if (n >= 60) {
            System.out.println("及格了");
        } else {
            System.out.println("不及格了");
        }
        // 当if语句块只有一行语句时，可以省略花括号{}：
        // 但是，省略花括号并不总是一个好主意 假设某个时候，突然想给if语句块增加一条语句时
        // 由于使用缩进格式，很容易把两行语句都看成if语句的执行块，但实际上只有第一行语句是if的执行块。
        // !!! 不推荐
        // if (n < 60) System.out.println("不及格了");

        System.out.println("END");
    }
}

```

##### 判断引用类型相等

判断值类型的变量是否相等，可以使用==运算符。但是，判断引用类型的变量是否相等，需要使用equals()方法。

```java
// 条件判断
public class Main {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "HELLO".toLowerCase();
        System.out.println(s1);
        System.out.println(s2);
        if (s1.equals(s2)) {
            System.out.println("s1 equals s2");
        } else {
            System.out.println("s1 not equals s2");
        }

        // 同时为了避免变量为null时的运行异常，利可以用短路运算符&&
        if (s1 != null && s2 != null && s1.equals(s2)) {
            System.out.println("s1 equals s2");
        } else {
            System.out.println("s1 not equals s2");
        }
    }
}

```

- if ... else可以做条件判断，else是可选的；

- 不推荐省略花括号{}；

- 多个if ... else串联要特别注意判断顺序；

- 要注意if的边界条件；

- 要注意浮点数判断相等不能直接用==运算符；

- 引用类型判断内容相等要使用equals()，注意避免NullPointerException。

### switch多重选择

switch语句根据switch (表达式)计算的结果，跳转到匹配的case结果，然后继续执行后续语句，直到遇到break结束执行。

```java
// switch
public class Main {
    public static void main(String[] args) {
        int option = 1;
        switch (option) {
        case 1:
            System.out.println("Selected 1");
            break;
        case 2:
            System.out.println("Selected 2");
            break;
        case 3:
            System.out.println("Selected 3");
            break;
        }
        // 如果有几个case语句执行的是同一组语句块
        case 4:
        case 5:
            System.out.println("Selected 4, 5");
            break;
        default:
            System.out.println("Invalid option");
            break;
    }
}
```

使用switch时，注意case语句并没有花括号{}，而且，case语句具有***“穿透性”***，漏写break将导致意想不到的结果：判断命中后，后续的case语句内的代码也会执行。

#### switch 表达式

使用switch时，如果遗漏了break，就会造成严重的逻辑错误，而且不易在源代码中发现错误。从***Java 14***开始，switch语句升级为更简洁的表达式语法，使用类似模式匹配（Pattern Matching）的方法，保证只有一种路径会被执行，并且不需要break语句：

```java

// switch
public class Main {
    public static void main(String[] args) {
        String fruit = "apple";
        switch (fruit) {
        case "apple" -> System.out.println("Selected apple");
        case "pear" -> System.out.println("Selected pear");
        case "mango" -> {
            System.out.println("Selected mango");
            System.out.println("Good choice!");
        }
        default -> System.out.println("No fruit selected");
        }

        // 使用新的switch语法，不但不需要break，还可以直接返回值。
        // 同时如果需要复杂的语句，同时又要返回值，用yield返回一个值作为switch语句的返回值
        int option = 2;
        int result = switch (option) {
            case 1 -> 100;
            case 2 -> 200;
            case 3 -> 300;
            case 4 -> {
                System.out.println("Selected 4");
                yield 400;
            }
            default -> 0;
        };
        System.out.println(result);
    }
}
```

- switch语句可以做多重选择，然后执行匹配的case语句后续代码；

- switch的计算结果必须是整型、字符串或枚举类型；

- 注意：千万不要漏写break，建议打开fall-through警告；

- 总是写上default，建议打开missing default警告；

- 从Java 14开始，switch语句正式升级为表达式，不再需要break，并且允许使用yield返回值。

---

### 循环语句

#### while循环

Java提供的while条件循环

while循环在每次循环开始前，首先判断条件是否成立。如果计算结果为true，就把循环体内的语句执行一遍，如果计算结果为false，那就直接跳到while循环的结束。

注意到while循环是先判断循环条件，再循环，因此，有可能一次循环都不做。

```java
// while
public class Main {
    public static void main(String[] args) {
        int sum = 0;
		int m = 20;
		int n = 100;
		// 使用while计算M+...+N:
		while (m<=n) {
			sum += m;
			m++;
		}
		System.out.println(sum);
    }
}

```

编写循环时要注意循环条件，并避免死循环。

#### do while循环

在Java中，while循环是先判断循环条件，再执行循环。而另一种do while循环则是先执行循环，再判断条件，条件满足时继续循环，条件不满足时退出。

可见，do while循环会至少循环一次

```java
// do-while
public class Main {
    public static void main(String[] args) {
        int sum = 0;
        int n = 1;
        do {
            sum = sum + n;
            n ++;
        } while (n <= 100);
        System.out.println(sum);
    }
}
```

#### for循环

Java使用最广泛的还是for循环。
for循环的功能非常强大，它使用计数器实现循环。for循环会先初始化计数器，然后，在每次循环前检测循环条件，在每次循环后更新计数器。计数器变量通常命名为i。

```java
// for
public class Main {
    public static void main(String[] args) {
        int sum = 0;
        for (int i=1; i<=100; i++) {
            sum = sum + i;
        }
        System.out.println(sum);

        // for循环还可以缺少初始化语句、循环条件和每次循环更新语句，例如：
        // 不设置结束条件:
        for (int i=0; ; i++) {
            ...
        }
        // 不设置结束条件和更新语句:
        for (int i=0; ;) {
            ...
        }
        // 什么都不设置:
        for (;;) {
            ...
        }

        // 求PI的近似值:
        double pi = 0;
        for (int i = 0; i < 100_0000_00; i++) {
            pi = i == 0 ? 1 : i%2 == 1 ? pi - 1.0 / (i*2+1) : pi + 1.0 / (i*2+1);
        }
        System.out.println(pi * 4);
    }
}

```

for each循环
for循环经常用来遍历数组，因为通过计数器可以根据索引来访问数组的每个元素：
但是，很多时候，我们实际上真正想要访问的是数组每个元素的值。Java还提供了另一种for each循环，它可以更简单地遍历数组：

```java
// for-each
public class Main {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        for (int number : numbers) {
            System.out.println(number);
        }
    }
}
```

for循环通过计数器可以实现复杂循环；

for each循环可以直接遍历数组的每个元素；

最佳实践：计数器变量定义在for循环内部，循环体内部不修改计数器；

---

### break 和 continue
无论是while循环还是for循环，有两个特别的语句可以使用，就是break语句和continue语句。
#### break
在循环过程中，可以使用break语句跳出当前循环。
```java
// break
public class Main {
    public static void main(String[] args) {
        int sum = 0;
        for (int i=1; ; i++) {
            sum = sum + i;
            if (i == 100) {
                break;
            }
        }
        System.out.println(sum);
    }
}
```
#### continue
在循环过程中，可以使用continue语句跳过当前循环，继续下一次循环。
```java
// continue
public class Main {
    public static void main(String[] args) {
        int sum = 0;
        for (int i=1; i<=10; i++) {
            System.out.println("begin i = " + i);
            if (i % 2 == 0) {
                continue; // continue语句会结束本次循环
            }
            sum = sum + i;
            System.out.println("end i = " + i);
        }
        System.out.println(sum); // 25
    }
}

```

break语句可以跳出当前循环；

break语句通常配合if，在满足条件时提前结束整个循环；

break语句总是跳出最近的一层循环；

continue语句可以提前结束本次循环；

continue语句通常配合if，在满足条件时提前结束本次循环。

## 数组操作
常用的数组操作有：
遍历；
排序。
以及多维数组的概念。

### 遍历数组

