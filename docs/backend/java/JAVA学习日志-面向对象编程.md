# JAVA学习日志-面向对象编程

## 面向对象编程
Java是一种面向对象的编程语言。面向对象编程，英文是Object-Oriented Programming，简称OOP
面向对象编程，是一种通过对象的方式，把现实世界映射到计算机模型的一种编程方法。

在本章中，我们将讨论：

面向对象的基本概念，包括：

类
实例
方法
面向对象的实现方式，包括：

继承
多态
Java语言本身提供的机制，包括：

package
classpath
jar
以及Java标准库提供的核心类，包括：

字符串
包装类型
JavaBean
枚举
常用工具类

### 类
class是一种对象模版，它定义了如何创建实例，因此，class本身就是一种数据类型。
而instance是对象实例，instance是根据class创建的实例，可以创建多个instance，每个instance类型相同，但各自属性可能不相同：

定义class
```java
// 定义一个类
class Person {
    public String name;
    public int age;
}
class Book {
    public String name;
    public String author;
    public String isbn;
    public double price;
}

Person ming = new Person();
ming.name = "Xiao Ming"; // 对字段name赋值
ming.age = 12; // 对字段age赋值
System.out.println(ming.name); // 访问字段name

Person hong = new Person();
hong.name = "Xiao Hong";
hong.age = 15;
```

在OOP中，class和instance是“模版”和“实例”的关系；

定义class就是定义了一种数据类型，对应的instance是这种数据类型的实例；

class定义的field，在每个instance都会拥有各自的field，且互不干扰；

通过new操作符创建新的instance，然后用变量指向它，即可通过变量来引用这个instance；

访问实例字段的方法是变量名.字段名；

指向instance的变量都是引用变量。

### 方法

直接操作field，容易造成逻辑混乱。为了避免外部代码直接去访问field，我们可以用private修饰field，拒绝外部访问，使用方法（method）来让外部代码可以间接修改field，以此来添加约束。

方法可以让外部代码安全地访问实例字段；

方法是一组执行语句，并且可以执行任意逻辑；

方法内部遇到return时返回，void表示不返回任何值（注意和返回null不同）；

外部代码通过public方法操作实例，内部代码可以调用private方法；

```java
// private field
public class Main {
    public static void main(String[] args) {
        Person ming = new Person();
        ming.setName("Xiao Ming"); // 设置name
        ming.setAge(12); // 设置age
        System.out.println(ming.getName() + ", " + ming.getAge());
    }
}

class Person {
    private String name;
    private int age;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 100) {
            throw new IllegalArgumentException("invalid age value");
        }
        this.age = age;
    }
    // 有public方法，自然就有private方法。和private字段一样，private方法不允许外部调用
    private void printName() {
        System.out.println(this.name);
    }
    //  方法可以包含0个或任意个参数。方法参数用于接收传递给方法的变量值。
    public void setNameAndAge(String name, int age) {
        this.age = age;
        this.name = name;
    }
}
```
一个类通过定义方法，就可以给外部代码暴露一些操作的接口，同时，内部自己保证逻辑一致性。

this变量 在方法内部，可以使用一个隐含的变量this，它始终指向当前实例。

参数绑定
调用方把参数传递给实例方法时，调用时传递的值会按参数位置一一绑定。
基本类型参数的传递，是调用方值的复制。双方各自的后续修改，互不影响。

```java
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

```

### 构造方法

创建实例的时候，实际上是通过构造方法来初始化实例的。

构造方法的名称就是类名。构造方法的参数没有限制，在方法内部，也可以编写任意语句。但是，和普通方法相比，构造方法没有返回值（也没有void），调用构造方法，必须用new操作符。

如果一个类没有定义构造方法，编译器会自动为我们生成一个默认构造方法，它没有参数，也没有执行语句

如果我们自定义了一个构造方法，那么，编译器就不再自动创建默认构造方法了。

可以定义多个构造方法，在通过new操作符调用的时候，编译器通过构造方法的参数数量、位置和类型自动区分：

```java
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name) {
        this(name, 18); // 调用另一个构造方法Person(String, int)
    }

    public Person() {
        this("Unnamed"); // 调用另一个构造方法Person(String)
    }
}

```

实例在创建时通过new操作符会调用其对应的构造方法，构造方法用于初始化实例；

没有定义构造方法时，编译器会自动创建一个默认的无参数构造方法；

可以定义多个构造方法，编译器根据参数自动判断；

可以在一个构造方法内部调用另一个构造方法，便于代码复用。

### 方法重载

在一个类中，我们可以定义多个方法。如果有一系列方法，它们的功能都是类似的，只有参数有所不同,称为方法重载.

注意：方法重载的返回值类型通常都是相同的。

### 继承

继承是面向对象编程中非常强大的一种机制，它首先可以复用代码。当我们让Student从Person继承时，Student就获得了Person的所有功能，我们只需要为Student编写新增的功能。

为了让子类可以访问父类的字段，我们需要把private改为protected。用protected修饰的字段可以被子类访问，把字段和方法的访问权限控制在继承树内部。

阻止继承
正常情况下，只要某个class没有final修饰符，那么任何类都可以从该class继承。
从Java 15开始，允许使用sealed修饰class，并通过permits明确写出能够从该class继承的子类名称。

继承是面向对象编程的一种强大的代码复用方式；

Java只允许单继承，所有类最终的根类是Object；

protected允许子类访问父类的字段和方法；

子类的构造方法可以通过super()调用父类的构造方法；

可以安全地向上转型为更抽象的类型；

可以强制向下转型，最好借助instanceof判断；

子类和父类的关系是is，has关系不能用继承。

### 多态
在继承关系中，子类如果定义了一个与父类方法签名完全相同的方法，被称为覆写（Override）。

多态是指，针对某个类型的方法调用，其真正执行的方法取决于运行时期实际类型的方法。

Java的实例方法调用是基于运行时的实际类型的动态调用，而非变量的声明类型。

子类可以覆写父类的方法（Override），覆写在子类中改变了父类方法的行为；

Java的方法调用总是作用于运行期对象的实际类型，这种行为称为多态；

final修饰符有多种作用：

final修饰的方法可以阻止被覆写；
final修饰的class可以阻止被继承；
final修饰的field必须在创建对象时初始化，随后不可修改。

### 抽象类

如果一个class定义了方法，但没有具体执行代码，这个方法就是抽象方法，抽象方法用abstract修饰。

因为无法执行抽象方法，因此这个类也必须申明为抽象类（abstract class）。

使用abstract修饰的类就是抽象类。我们无法实例化一个抽象类：

因为抽象类本身被设计成只能用于被继承，因此，抽象类可以强迫子类实现其定义的抽象方法，否则编译会报错。因此，抽象方法实际上相当于定义了“规范”。

通过abstract定义的方法是抽象方法，它只有定义，没有实现。抽象方法定义了子类必须实现的接口规范；

定义了抽象方法的class必须被定义为抽象类，从抽象类继承的子类必须实现抽象方法；

如果不实现抽象方法，则该子类仍是一个抽象类；

面向抽象编程使得调用者只关心抽象方法的定义，不关心子类的具体实现。

### 接口

所谓interface，就是比抽象类还要抽象的纯抽象接口，因为它连字段都不能有。因为接口定义的所有方法默认都是public abstract的，所以这两个修饰符不需要写出来（写不写效果都一样）。


实现类可以不必覆写default方法。default方法的目的是，当我们需要给接口新增一个方法时，会涉及到修改全部子类。如果新增的是default方法，那么子类就不必全部修改，只需要在需要覆写的地方去覆写新增方法。

default方法和抽象类的普通方法是有所不同的。因为interface没有字段，default方法无法访问字段，而抽象类的普通方法可以访问实例字段。

Java的接口（interface）定义了纯抽象规范，一个类可以实现多个接口；

接口也是数据类型，适用于向上转型和向下转型；

接口的所有方法都是抽象方法，接口不能定义实例字段；

接口可以定义default方法（JDK>=1.8）。



接口的静态字段
因为interface是一个纯抽象类，所以它不能定义实例字段。但是，interface是可以有静态字段的，并且静态字段必须为final类型：

### 静态字段和静态方法
在一个class中定义的字段，我们称之为实例字段。实例字段的特点是，每个实例都有独立的字段，各个实例的同名字段互不影响。

还有一种字段，是用static修饰的字段，称为静态字段：static field。

实例字段在每个实例中都有自己的一个独立“空间”，但是静态字段只有一个共享“空间”，所有实例都会共享该字段。


在Java程序中，实例对象并没有静态字段。在代码中，实例对象能访问静态字段只是因为编译器可以根据实例类型自动转换为类名.静态字段来访问静态对象。因此，不推荐用实例变量.静态字段去访问静态字段。


有静态字段，就有静态方法。用static修饰的方法称为静态方法。静态方法通过类直接调用。
静态方法属于class而不属于实例，因此，静态方法内部，无法访问this变量，也无法访问实例字段，它只能访问静态字段。



静态字段属于所有实例“共享”的字段，实际上是属于class的字段；

调用静态方法不需要实例，无法访问this，但可以访问静态字段和其他静态方法；

静态方法常用于工具类和辅助方法。

### 包
Java定义了一种名字空间，称之为包：package。一个类总是属于某个包，类名（比如Person）只是一个简写，真正的完整类名是包名.类名。
包作用域：
位于同一个包的类，可以访问包作用域的字段和方法。不用public、protected、private修饰的字段和方法就是包作用域。

Java内建的package机制是为了避免class命名冲突；

JDK的核心类使用java.lang包，编译器会自动导入；

JDK的其它常用类定义在java.util.*，java.math.*，java.text.*，……；

包名推荐使用倒置的域名，例如org.apache。


### 作用域
Java内建的访问权限包括public、protected、private和package权限；

Java在方法内部定义的变量是局部变量，局部变量的作用域从变量声明开始，到一个块结束；

final修饰符不是访问权限，它可以修饰class、field和method；

一个.java文件只能包含一个public类，但可以包含多个非public类。

如果不确定是否需要public，就不声明为public，即尽可能少地暴露对外的字段和方法。

把方法定义为package权限有助于测试，因为测试类和被测试类只要位于同一个package，测试代码就可以访问被测试类的package权限方法。

一个.java文件只能包含一个public类，但可以包含多个非public类。如果有public类，文件名必须和public类的名字相同。

### 内部类

Java的内部类可分为Inner Class、Anonymous Class和Static Nested Class三种；

Inner Class和Anonymous Class本质上是相同的，都必须依附于Outer Class的实例，即隐含地持有Outer.this实例，并拥有Outer Class的private访问权限；

Static Nested Class是独立类，但拥有Outer Class的private访问权限。

### classpath和jar
classpath是JVM用到的一个环境变量，它用来指示JVM如何搜索class。
JVM通过环境变量classpath决定搜索class的路径和顺序；

强烈建议不要设置系统环境变量classpath，建议始终通过-cp命令传入；

jar包本质上是zip格式，相当于目录，可以包含很多.class文件，方便下载和使用；

MANIFEST.MF文件可以提供jar包的信息，如Main-Class，这样可以直接运行jar包。

### class版本
我们通常说的Java 8，Java 11，Java 17，是指JDK的版本，也就是JVM的版本。
每个版本的JVM，它能执行的class文件版本也不同。例如，Java 11对应的class文件版本是55，而Java 17对应的class文件版本是61。

在编写源代码的时候，我们通常会预设一个源码的版本。在编译的时候，如果用--source或--release指定源码版本，则使用指定的源码版本检查语法。

版本的JDK可编译输出低版本兼容的class文件，但需注意，低版本的JDK可能不存在高版本JDK添加的类和方法，导致运行时报错。

运行时使用哪个JDK版本，编译时就尽量使用同一版本的JDK编译源码。

### 模块
从Java 9开始，JDK又引入了模块（Module）。

Java 9引入的模块目的是为了管理依赖；

使用模块可以按需打包JRE；

使用模块对类的访问权限有了进一步限制。
