
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

