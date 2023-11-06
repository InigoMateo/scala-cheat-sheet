# **Scala cheat sheet**

## **Evaluation rules**
* Call by value: evaluates the function arguments before calling the function
* Call by name: evaluates the function first, and then evaluates the arguments if need be
``` scala 
def example = 2      // evaluated when called
val example = 2      // evaluated immediately
lazy val example = 2 // evaluated once when needed

def square(x: Double)    // call by value
def square(x: => Double) // call by name
def myFct(bindings: Int*) =  ...  // bindings is a sequence of int, containing a varying # of arguments (```)
```
## **Higher order functions**
These are functions that take a function as a parameter or return functions.
``` scala
// sum takes a function that takes an integer and returns an integer then
// returns a function that takes two integers and returns an integer
def sum(f: Int => Int): (Int, Int) => Int =
  def sumf(a: Int, b: Int): Int = f(a) + f(b)
  sumf

// same as above. Its type is (Int => Int) => (Int, Int) => Int
def sum(f: Int => Int)(a: Int, b: Int): Int =  ...

// Called like this
sum((x: Int) => x * x * x)          // Anonymous function, i.e. does not have a name
sum(x => x * x * x)                 // Same anonymous function with type inferred

def cube(x: Int) = x * x * x
sum(x => x * x * x)(1, 10) // sum of 1 cubed and 10 cubed
sum(cube)(1, 10)           // same as above  
```
## **Currying**
Converting a function with multiple arguments into a function with a single argument that returns another funtion.
```scala
val f2: (Int, Int) => Int = f // uncurried version (type is (Int, Int) => Int)
val f3: Int => Int => Int = f2.curried // transform it to a curried version (type is Int => Int => Int)
val f4: (Int, Int) => Int = Function.uncurried(f3) // go back to the uncurried version (type is (Int, Int) => Int)
```
## **Classes**
``` scala
class MyClass(x: Int, val y: Int,
                      var z: Int):        // Defines a new type MyClass with a constructor
                                          // x will not be available outside MyClass
                                          // val will generate a getter for y
                                          // var will generate a getter and a setter for z
  require(y > 0, "y must be positive")    // precondition, triggering an IllegalArgumentException if not met
  def this (x: Int) =  ...                // auxiliary constructor
  def nb1 = x                             // public method computed every time it is called
  private def test(a: Int): Int =  ...    // private method
  val nb3 = x + y                         // computed only once
  override def toString =                 // overridden method
      x + ", " + y
end MyClass

new MyClass(1, 2, 3) // creates a new object of type
```


## **FAQs**

### **Difference between overloading and overriding**
"Overriding" and "overloading" are two concepts in object-oriented programming, used to define different behaviors for functions or methods.
* **Overriding**:
  Overriding is a concept associated with inheritance. When a subclass provides a specific implementation of a method that is already defined in its superclass, it's called method overriding. The overridden method in the subclass should have the same name, return type, and parameters (or a covariant return type), and it should be tagged with the override keyword. The overriding method in the subclass completely replaces the inherited method's behavior.
``` scala
class Animal {
  def makeSound(): Unit = {
    println("Animal makes a sound")
  }
}

class Dog extends Animal {
  override def makeSound(): Unit = {
    println("Dog barks")
  }
}

```
* **Overloading**:
  Overloading is a concept that allows you to define multiple methods in the same class with the same name but different parameters (number or types). Overloaded methods have the same name but provide different behavior based on the arguments provided when calling the method. The return type doesn't play a role in overloading.
``` scala
class Calculator {
def add(a: Int, b: Int): Int = a + b

def add(a: Double, b: Double): Double = a + b
}

 ```
These concepts lead into *polymorphism* this main concepts allows you to write code that works with objects of different classes without needing to know the exact class at compile time. It allows objects of different classes to be treated as objects of a common superclass or interface.
### **What is a constructor?**
A constructor in object-oriented programming is a special type of method or function that is used to initialize and set up the initial state of an object when it is created. Constructors are typically associated with classes and are invoked automatically when an instance of a class (an object) is created.In summary, constructors are essential in object-oriented programming because they ensure that objects are properly initialized, encapsulate initialization logic, promote consistency, and simplify the process of creating and using objects.
  ``` scala
class Person(name: String, age: Int) {
  // Constructor code can be placed directly in the class body
  def greet(): Unit = {
    println(s"Hello, my name is $name and I am $age years old.")
  }
}

object Person {
  def main(args: Array[String]): Unit = {
    // Creating an instance of the Person class using the primary constructor
    val person = new Person("Bob", 25)

    // Calling the greet method to introduce the person
    person.greet()
  }
}
```
### **What is an auxiliary constructor?**
In Scala, an auxiliary constructor, also known as a secondary constructor, is a constructor in a class that complements the primary constructor. Auxiliary constructors allow you to provide alternative ways to initialize an object of the class with different sets of parameters.
``` scala
class Person {
  var name: String = ""
  var age: Int = 0

  def this(name: String) {
    this() // Calls the primary constructor
    this.name = name
  }

  def this(name: String, age: Int) {
    this(name) // Calls the previous auxiliary constructor
    this.age = age
  }

  override def toString: String = s"Name: $name, Age: $age"
}

object Main {
  def main(args: Array[String]): Unit = {
    val person1 = new Person("Alice")
    val person2 = new Person("Bob", 30)

    println(person1) // Output: Name: Alice, Age: 0
    println(person2) // Output: Name: Bob, Age: 30
  }
}
```
### **What are the getter and the setters in a class?**
In object-oriented programming, "getters" and "setters" are methods used to access and modify the attributes or fields (variables) of a class, respectively. Getters are used to retrieve the values of class attributes, while setters are used to change or update those values. They are also known as accessor methods and mutator methods, respectively.
* **Getters:**
Getters are methods that provide access to the private or protected attributes of a class.
``` scala
class Person {
  private var name: String = ""

  def getName: String = name // Getter method for 'name' attribute
}
```
* **Setters:**
Setters are methods that allow you to modify the values of private or protected attributes of a class.
``` scala
class Person {
  private var name: String = ""

  def setName(newName: String): Unit = {
    name = newName // Setter method for 'name' attribute
  }
}
```
### **What is the use of the keywords "this" and "super"?**
The "this" and "super" keywords are used in class and method definitions to refer to specific objects or to access methods or attributes in the context of inheritance.
* **This:**
  In Java and similar languages, "this" refers to the current instance of the class in which it is used. It is typically used to access instance variables or call instance methods within a class. "this" is often used to disambiguate between instance variables and method parameters when they have the same name.
``` java
public class MyClass {
    private int value;

    public MyClass(int value) {
        this.value = value; // "this" is used to refer to the instance variable
    }
}
```
* **Super:**
"super" is used to refer to the immediate parent class (superclass) of the current class in the context of inheritance. It is often used to call a method or access an attribute defined in the parent class.
``` java
class Parent {
    void display() {
        System.out.println("This is the parent class.");
    }
}

class Child extends Parent {
    void display() {
        super.display(); // Calls the "display" method in the parent class
        System.out.println("This is the child class.");
    }
}
```
### Difference between case class and class:
In Scala, both regular classes and case classes are used to define data structures and create objects. Case classes are a specific type of class in Scala optimized for modeling immutable data and working well with pattern matching. They are typically used for simpler data structures, such as representing records or simple entities, where concise syntax and built-in functionality like structural equality and pattern matching support are beneficial.
1. Immutable by Default:

Case classes are designed to be immutable by default. This means that once you create an instance of a case class, you cannot modify its fields. This immutability is a fundamental characteristic of case classes and is achieved by declaring fields as val (immutable) rather than var (mutable).
2. Concise Syntax:

Case classes have a concise syntax for defining classes. In a case class definition, you don't need to explicitly specify getters, setters, equals, hashCode, and toString methods; they are generated automatically. This concise syntax is particularly useful for defining simple data structures.
3. Structural Equality:

Case classes have built-in structural equality, which means that two case class instances with the same field values are considered equal. This is done by automatically generating an equals method that compares the content of the objects, not just their references.
4. Pattern Matching:

Case classes are designed to work seamlessly with pattern matching. Pattern matching is a powerful feature in Scala, and case classes are a natural fit for this because they provide a convenient way to destructure objects in pattern matching expressions.
5. Copy Method:

Case classes automatically generate a copy method, which allows you to create a new instance of the case class with some fields updated. This is a useful feature for creating modified copies of objects while keeping most of the original values.
6. Serialization Support:

Case classes provide built-in support for serialization and deserialization. This makes them suitable for use in distributed systems, for example, in Akka actors.
7. Regular Classes:

Regular classes, on the other hand, do not have the above features by default. You need to manually implement methods like equals, hashCode, toString, and write your own copy methods if needed. Regular classes are typically used for more complex data structures or when mutability is required.
