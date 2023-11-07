
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