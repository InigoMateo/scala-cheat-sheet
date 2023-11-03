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
### **WWhat is **