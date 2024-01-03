
//Now we are gonna see the options 'require', 'assert' and 'assume'
class house (var squareMeters: Int, direction: String):
  assert (squareMeters >= 0, "The house must have positive meters"){}

class Car(var carName: String, var cc: Int):
require(carName != null && carName != "", "Name cannot be null or empty"){
  override toString: String ={
    println(s"Car: $name and $cc")
  }
}

val car1 = new Car("Ford", 4500)
println(car1.toString)

//end keyword to mark the end of a class
class MyClass(a: Int, b: String):
  def myMethod(name: String): Unit =
    println(s"Hello $name")
  end myMethod
end MyClass

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

//Contravariance
class Animal
class Dog extends Animal

trait Consumer[-T] {
  def consume(value: T): Unit
}

class AnimalConsumer extends Consumer[Animal] {
  def consume(value: Animal): Unit = println(s"Consuming animal: $value")
}

val animalConsumer: Consumer[Animal] = new AnimalConsumer

val dogConsumer: Consumer[Dog] = animalConsumer // Valid due to contravariance
dogConsumer.consume(new Dog)
animalConsumer.consume(new Animal)


//Can i instantiate a trait
trait A
trait B {
  def method(): Unit= println("hello")
}
//So basically what is happening here is that the "new A{}" creates an anonymous class "A" that extends trait A
val traitA1 = new A {}
//Then traitA1 and traitA2 are objects.
val traitA2 = new B {}


//Can i use a trait in scala as a referenced type when declaring an object, like here:
class Abc

trait Interface[T]{def hello():Unit={println("hello")}}

class B extends Interface[Abc]{def break():Unit={println("end")}}

val AbcObject: Interface[Abc] = new B
AbcObject.hello()
//You can see we can't use the methods defined in class B, we can access both if we declare AbcObject as type B.
//AbcObject.break()
val DefObject = new B
DefObject.hello()
DefObject.break()


//This is an example comparably to base classes as Seq, Map, etc.
trait MyTrait {
  def hello(): Unit
}

object MyTrait {
  def staticMethod(): Unit = {
    println("This is a static method in the companion object of MyTrait")
  }
}

class MyClass extends MyTrait {
  def hello(): Unit = {
    println("Hello from MyClass!")
  }
}

object Main extends App {
  val myObject: MyTrait = new MyClass
  myObject.hello()

  // Accessing the static method from the companion object
  MyTrait.staticMethod()
}

val hwlo = Seq.hashCode()


