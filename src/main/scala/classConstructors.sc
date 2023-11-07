class animal{

  var familyType: String = ""

  //create a constructor if it is not define in the class
  def this(familyType: String) = {
    this() //calls the primary constructor
    this.familyType = familyType
  }

  override def toString: String = s"animal: $familyType"
}

class specialCreatures (name: String){

  var hasWings : Boolean = false

  //create an auxiliary constructor
  def this(name: String, hasWings: Boolean) = {
    this(name)
    this.hasWings = hasWings
  }

  override def toString: String = s"special creature: $name"
}

class mammal extends animal {

  var name: String = ""

  def this(name: String) = {
    this() //calls the primary constructor
    this.name = name
  }

  override def toString: String = s"mammal: $name"
}

val dog = new mammal("kuke")
println(dog.toString)

object dragon {
  def main(args: Array[String]): Unit = {
    val animal1 = new animal("reptile")
    println(animal1)

    val dragon1 = new specialCreatures("Voromir")
    val dragon2 = new specialCreatures("Kelebrion", true)


  }
}

println(dragon.toString)
println(dragon.toString)


