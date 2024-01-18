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
*this* references the current object, *assert*(<condition>) issues AssertionError if condition is not met. See scala.Predef for *require*, *assume* and *assert*.
## Operators
The associativity of an operator is determined by its last character: Right-associative if ending with :, Left-associative otherwise. See "FAQs" for more details.
## Class Organization
* Classes and objects are organized in packages (package myPackage).

* They can be referenced through import statements (import myPackage.MyClass, import myPackage.*,
    import myPackage.{MyClass1, MyClass2}, import myPackage.{MyClass1 as A})

* They can also be directly referenced in the code with the fully qualified name (new myPackage.MyClass1)

* All members of packages scala and java.lang as well as all members of the object scala.Predef are automatically imported.

* Traits are similar to Java interfaces, except they can have non-abstract members

## Type parameters
See in FAQs section

## Variance
Variance in Scala, often referred to as type variance, is a concept that deals with how subtyping relationships between classes with type parameters are related. Variance specifies whether the subtyping relationship between two classes should be preserved or reversed when their type parameters are considered. It can be marked as covariant (+T), contravariant (-T), or invariant (T). This annotation influences the subtyping relationship between classes with type parameters.
1. **Covariance:**

A type parameter is marked as covariant using the + symbol. In a covariant type parameter, the subtyping relationship is preserved. This means that if A is a subtype of B, then C[A] is considered a subtype of C[B]. Covariance is suitable for situations where you only produce values of type T.
``` scala
class Box[+T](value: T)
val intBox: Box[Int] = new Box(42)
val numberBox: Box[Number] = intBox // Preserves subtyping relationship
//We can safely assign intBox to a Box[Number] because Int is a subtype of Number
```
2. **Contravariance:**

A type parameter is marked as contravariant using the - symbol. In a contravariant type parameter, the subtyping relationship is reversed. This means that if A is a subtype of B, then C[B] is considered a subtype of C[A]. Contravariance is useful when you only consume values of type T.
```scala
class Consumer[-T]
val intConsumer: Consumer[Int] = new Consumer[Number]
//We can safely assign intConsumer to a Consumer[Number] because Int is a supertype of Number. 
```

3. **Invariance:**

If a type parameter is not marked with any variance annotation, it is considered invariant. Invariance means that there is no subtyping relationship between types with different type parameters. Types with different type parameters are not considered subtypes of each other.
``` scala
class Box[T](value: T)
val intBox: Box[Int] = new Box(42)
// The following line would result in a compilation error:
// val numberBox: Box[Number] = intBox
```
## Pattern Matching
When talking about pattern matching we are talking about "match" Can be classes, object, list... data structures.
```scala
unknownObject match
case MyClass(n) => ...
case MyClass2(a, b) => ...
```
Here more pattern matching examples:
```scala
(someList: List[T]) match
case Nil => ...          // empty list
case x :: Nil => ...     // list with only one element
case List(x) => ...      // same as above
case x :: xs => ...      // a list with at least one element. x is bound to the head,
// xs to the tail. xs could be Nil or some other list.
case 1 :: 2 :: cs => ... // lists that starts with 1 and then 2
case (x, y) :: ps => ... // a list where the head element is a pair
case _ => ...            // default case if none of the above matches
```
## Base Classes: Iterable, Seq, Set & Map
Let's talk about the base traits in the Scala collections hierarchy. The key collection traits are:

1. Iterable:
    * The base trait for all iterable collections.
    * It defines operations for traversing the collection, such as foreach, map, and filter.
    * Subtypes include List, Set, Seq, etc.

2. Seq:
    * The base trait for sequences (ordered collections).
    * It extends Iterable and adds methods for indexing and ordering elements.
    * Subtypes include List, Vector, Array, etc.

3. Set:
    * The base trait for sets (unordered collections with no duplicate elements).
    * It extends Iterable and adds set-specific operations.
    * Subtypes include HashSet, TreeSet, etc.

4. Map:
    * The base trait for maps (collections of key-value pairs).
    * It extends Iterable and adds map-specific operations.
    * Subtypes include HashMap, TreeMap, etc.

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
1. **Immutable by Default**:

Case classes are designed to be immutable by default. This means that once you create an instance of a case class, you cannot modify its fields. This immutability is a fundamental characteristic of case classes and is achieved by declaring fields as val (immutable) rather than var (mutable).

2. **Concise Syntax**:

Case classes have a concise syntax for defining classes. In a case class definition, you don't need to explicitly specify getters, setters, equals, hashCode, and toString methods; they are generated automatically. This concise syntax is particularly useful for defining simple data structures.

3. **Structural Equality:**

Case classes have built-in structural equality, which means that two case class instances with the same field values are considered equal. This is done by automatically generating an equals method that compares the content of the objects, not just their references.

4. **Pattern Matching:**

Case classes are designed to work seamlessly with pattern matching. Pattern matching is a powerful feature in Scala, and case classes are a natural fit for this because they provide a convenient way to destructure objects in pattern matching expressions.

5. **Copy Method:**

Case classes automatically generate a copy method, which allows you to create a new instance of the case class with some fields updated. This is a useful feature for creating modified copies of objects while keeping most of the original values.

6. **Serialization Support:**

Case classes provide built-in support for serialization and deserialization. This makes them suitable for use in distributed systems, for example, in Akka actors.

7. **Regular Classes:**

Regular classes, on the other hand, do not have the above features by default. You need to manually implement methods like equals, hashCode, toString, and write your own copy methods if needed. Regular classes are typically used for more complex data structures or when mutability is required.

### Class hierarchy. Inheritance and Interfaces.
What is class hierarchy? class hierarchies refer to the organization of classes in a hierarchical structure, where classes inherit properties and behaviors from parent classes, creating a relationship between classes. These hierarchies can be built using inheritance and interfaces.
1. Inheritance: 

Inheritance allows you to create new classes that are based on existing classes. The new class (subclass or derived class) inherits the fields and methods of the existing class (superclass or base class). This forms a parent-child relationship, where the subclass is a specialized version of the superclass. You can use the **extends** keyword to inherit from a class.
``` scala
class Animal(name: String) {
  def speak(): Unit = {
    println(s"$name makes a sound.")
  }
}

class Dog(name: String, breed: String) extends Animal(name) {
  def bark(): Unit = {
    println(s"$name, a $breed dog, barks loudly.")
  }
}

// Create instances of the classes
val genericAnimal = new Animal("Generic Animal")
val dog = new Dog("Buddy", "Golden Retriever")

// Call methods on the instances
genericAnimal.speak()
dog.speak() // Inherited from Animal
dog.bark()
```
2. Interfaces and Traits: 
 
Scala supports both interfaces and traits for building class hierarchies. Interfaces define a contract that implementing classes must adhere to. Traits are similar to interfaces but can also provide concrete implementations. A class can mix in multiple traits using the **with** keyword.
``` scala
// Define traits with some methods
trait Speaker {
  def speak(): Unit
}

trait Singer {
  def sing(): Unit
}

trait Dancer {
  def dance(): Unit
}

// Create a class that implements multiple traits
class Performer(name: String) extends Speaker with Singer with Dancer {
  def speak(): Unit = {
    println(s"$name is speaking.")
  }

  def sing(): Unit = {
    println(s"$name is singing.")
  }

  def dance(): Unit = {
    println(s"$name is dancing.")
  }
}

// Create an instance of the class
val performer = new Performer("Alice")

// Call methods from the implemented traits
performer.speak()
performer.sing()
performer.dance()
```
### Difference between Interface and Trait
In Scala, traits and interfaces are both mechanisms for defining contracts that a class must adhere to. They enable code reuse and multiple inheritance of behavior.
1. Trait:

A trait is a special construct in Scala that is similar to an interface in Java, but it can also contain concrete method implementations. Traits are used to define a set of abstract methods (those that have to be override and define in the subclass) and/or concrete methods (those already define in the trait) that can be mixed into a class.
```scala
trait Speaker {
  //abstract method
  def speak(): Unit
  //concrete method
  def greet(): Unit = {
    println("Hello!")
  }
}

class Person(name: String) extends Speaker {
  def speak(): Unit = {
    println(s"My name is $name.")
  }
}
```
2. Interface:

In Scala, interfaces are similar to traits but are primarily used to define contracts. An interface cannot contain concrete method implementations; it only defines abstract methods that implementing classes must provide. Interfaces in Scala are often referred to as "pure abstract traits."
```scala
trait Speaker {
  def speak(): Unit
}

class Person(name: String) extends Speaker {
  def speak(): Unit = {
    println(s"My name is $name.")
  }
}
```
### Abstract class, what is it?
An abstract class in Scala is a class that cannot be instantiated on its own and is meant to be used as a base class or blueprint for other classes. Abstract classes can contain both abstract (unimplemented) and concrete (implemented) methods.
```scala
abstract class Shape {
  def area: Double // Abstract method
  def perimeter: Double // Abstract method
  def printDetails(): Unit = {
    println(s"Area: $area, Perimeter: $perimeter")
  }
}
//must implement all the abstract method defined in the abstract class
class Circle(radius: Double) extends Shape {
  def area: Double = math.Pi * radius * radius
  def perimeter: Double = 2 * math.Pi * radius
}
//must implement all the abstract method defined in the abstract class
class Rectangle(width: Double, height: Double) extends Shape {
  def area: Double = width * height
  def perimeter: Double = 2 * (width + height)
}
```
### What are the associative left or right property in scala?
1. Left-Associative Operator Example:

The + operator for addition is a left-associative operator. When you use it in a chain, the expressions are evaluated from left to right.
``` scala
val result = 1 + 2 + 3
```
In this example, the left-associative behavior means that the expression is evaluated as follows:

    1 + 2 is evaluated first, resulting in 3.
    Then, 3 + 3 is evaluated, resulting in the final value of 6.

So, result will be 6.

Right-Associative Operator Example:

The :: operator for adding an element to the beginning of a list is right-associative. When you use it in a chain, the expressions are evaluated from right to left.
``` scala
val myList = 1 :: 2 :: 3 :: Nil
```
In this example, the right-associative behavior means that the expression is evaluated as follows:

    3 :: Nil is evaluated first, resulting in a new list containing only the element 3.
    Then, 2 :: (the result of the previous step) is evaluated, resulting in a new list containing 2 and 3.
    Finally, 1 :: (the result of the previous step) is evaluated, resulting in the final list List(1, 2, 3).
So, myList will be List(1, 2, 3).

### What are type parameters?
Type parameters in Scala are a powerful feature of the language that allows you to create generic classes and methods, making your code more flexible and reusable. Type parameters, also known as generics, enable you to define classes and methods that can work with different types while providing type safety (which means that the compiler ensures that you use the correct data types and prevents type-related errors at compile time, by inferring the type).
``` scala
class Box[T](value: T) {
  def getValue: T = value
}

def findMax[T](elements: Seq[T])(implicit ord: Ordering[T]): Option[T] = {
  if (elements.isEmpty) None
  else Some(elements.max)
}

val intBox = new Box(42) // A generic class instance with type parameter Int
val stringBox = new Box("Hello") // A generic class instance with type parameter String

val numbers = Seq(3, 7, 1, 5, 9)
val result1 = findMax(numbers) // Calls the generic method with type parameter Int

val words = Seq("apple", "banana", "cherry")
val result2 = findMax(words) // Calls the generic method with type parameter String
```
It is possible to restrict the type being used:
``` scala
    def myFct[T <: TopLevel](arg: T): T = ... // T must derive from TopLevel or be TopLevel
    def myFct[T >: Level1](arg: T): T = ...   // T must be a supertype of Level1
    def myFct[T >: Level1 <: TopLevel](arg: T): T = ...
```
### What is a singleton object and a companion object in scala?
**Singleton Object**: In Scala, a singleton object is an object that is defined using the object keyword rather than the class keyword. A singleton object is a single instance of its own type and is created lazily when it is first accessed. It serves as a container for methods and values that are not associated with instances of a class but are still part of the program logic.
```scala
object MySingleton {
  def greet(): Unit = {
    println("Hello from the singleton object!")
  }
}
```
In this example, MySingleton is a singleton object with a method greet. You can call this method without creating an instance of the object:
```scala
MySingleton.greet()
```
**Companion Object**: A companion object in Scala is a singleton object that is defined in the same file as a class and has the same name as the class. The class and its companion object must be defined in the same source file. They have access to each other's private members.
Companion objects are often used for:
* Factory Methods: Creating instances of a class with more descriptive or convenient methods.
* Shared Logic: Placing methods and values that are not instance-specific but are related to the class.
```scala
class MyClass {
  private val secretValue = 42
}

object MyClass {
  def revealSecret(instance: MyClass): Unit = {
    println(s"The secret value is: ${instance.secretValue}")
  }
}
```
### What is the factory method in companion objects?
```scala
class Dog(val name: String, val age: Int)

object Dog {
  // Factory method to create a Dog with default age
  def createDogWithName(name: String): Dog = {
    new Dog(name, age = 2) // Default age is 2
  }

  // Factory method to create a Dog with specified age
  def createDogWithNameAndAge(name: String, age: Int): Dog = {
    new Dog(name, age)
  }
}

object FactoryMethodExample extends App {
  // Using the factory methods to create Dog instances
  val dog1 = Dog.createDogWithName("Buddy")
  val dog2 = Dog.createDogWithNameAndAge("Max", 3)

  // Outputting information about the dogs
  println(s"Dog 1: ${dog1.name}, Age: ${dog1.age}")
  println(s"Dog 2: ${dog2.name}, Age: ${dog2.age}")
}
```
### Scala data types:
Scala has all the same data types as Java, with the same memory footprint and precision. Following is the table giving details about all the data types available in Scala 
1. Byte: 8 bit signed value. Range from -128 to 127
2. Short: 16 bit signed value. Range -32768 to 32767
3. Int: 32 bit signed value. Range -2147483648 to 2147483647
4. Long: 64 bit signed value. -9223372036854775808 to 9223372036854775807
5. Float: 32 bit IEEE 754 single-precision float
6. Double: 64 bit IEEE 754 double-precision float
7. Char: 16 bit unsigned Unicode character. Range from U+0000 to U+FFFF
8. String: A sequence of Chars
9. Boolean: Either the literal true or the literal false
10. Unit: Corresponds to no value
11. Null: null or empty reference
12. Nothing: The subtype of every other type; includes no values. It is commonly used to represent the result of operations that result in an exception or do not return a value.
```scala
def fail(message: String): Nothing = throw new RuntimeException(message)
```
13. Any: The supertype of any type; any object is of type Any. Bear in mind non-null values.
14. AnyRef: is the root class of all reference types. All types except the value types descend from this class.

All the data types listed above are objects. There are no primitive types like in Java. This means that you can call methods on an Int, Long, etc.

### Value types and Reference types:
In Scala, there is a distinction between reference types and value types. However, unlike some other languages like Java, Scala does not expose this distinction directly to the programmer in the syntax. Instead, Scala has a unified type system where everything is an object, and primitive types are represented as objects.

Here's a brief overview:

1. Value Types:
In Scala, types such as Int, Long, Double, Char, Boolean, etc., are considered value types.
Despite these types having a syntax similar to primitive types in other languages, they are implemented as objects in Scala.
Scala provides a mechanism called "value classes" to enable efficient representation of certain value types.

2. Reference Types:
Reference types in Scala are classes, traits, and objects that you define.
Classes in Scala can be instantiated using the new keyword, and they are treated as reference types.

### Option
Option is a data type in Scala. It is a container type that represents an optional value. An Option can either be Some(value), where value is present, or None, indicating the absence of a value. The Option type is part of Scala's standard library and is commonly used to handle scenarios where a value may or may not be available.

Here's a brief explanation of Option:

- Some(value):
    Represents the presence of a value.
    value is the actual value that is present.
    Example: Some("Hello, Scala!")

- None:
    Represents the absence of a value.
    Example: None

Pattern matching can also be used for Option values. Some
functions (like Map.get) return a value of type Option[T] which
is either a value of type Some[T] or the value None

### Nil, Set.empty, Map.empty & None
It represents the empty list, which is a special case of a linked list.

In addition to lists, Nil is not typically used with other collection types. For example, when working with other collection types like Set or Map, you would typically use the appropriate empty instance for that collection type.

Here are a few examples:

1. List with Nil:
```scala
val myList: List[Int] = 1 :: 2 :: 3 :: Nil
```
2. Set with empty Set:
```scala
val mySet: Set[Int] = Set.empty
```
3. Map with empty Map:
```scala
val myMap: Map[String, Int] = Map.empty
```
4. Option with None:
```scala
    val myOption: Option[String] = None
```
While Nil is specific to lists, the concept of representing an empty collection with a specific instance is common across various collection types in Scala. Each collection type typically has its own way of representing an empty instance (e.g., Set.empty, Map.empty, None for Option).

### Ordered and Unordered collections
- Ordered Collections:

An ordered collection is one in which the order of elements is explicitly defined and maintained.
Operations like map, filter, and foreach maintain the order of elements.
Examples of ordered collections include List, Seq, and Array.

- Unordered Collections:

An unordered collection does not guarantee any specific order of its elements.
Operations like map, filter, and foreach do not necessarily maintain the order of elements.
Examples of unordered collections include Set and Map.

### Inmutable and mutable collections
- Immutable Collections:

Immutable collections cannot be modified after they are created.
Operations that appear to modify the collection return a new collection with the desired changes.
Examples of immutable collections include List, Set, and Map (when created using the default immutable implementations).

- Mutable Collections:

Mutable collections can be modified in-place after creation.
Operations can directly change the contents of the collection.
Examples of mutable collections include ArrayBuffer, HashMap, and ListBuffer.

### What is a tuple in scala?
In Scala, a tuple is an ordered collection of elements. Tuples are a type of ordered collection, and they are immutable, meaning their elements cannot be changed after creation. Tuples are particularly useful when you want to group together a fixed number of items with different types.

### Control Flow statements in scala
Here are examples of if, for, while, do while, and a similar construct for match in Scala (Scala doesn't have a switch statement like Java):

- if Statement:
```    scala
val x = 10

if (x > 5) {
println("x is greater than 5")
} else {
println("x is not greater than 5")
}
```

- for Loop:
```scala
val numbers = List(1, 2, 3, 4, 5)

for (num <- numbers) {
println(num)
}
```

- while Loop:
```scala
var i = 0

while (i < 5) {
println(i)
i += 1
}
```

- do while Loop:
```scala
var j = 0

do {
println(j)
j += 1
} while (j < 5)
```
- match Expression (Similar to switch in other languages):
``` scala
val day = "Monday"

val result = day match {
case "Monday" => "Start of the week"
case "Friday" => "End of the week"
case _ => "Some other day"
}

println(result)
