//If you want to add setters you must have var variables define in your constructor either primary or auxiliary
class Person(var name: String, var age: Int) {

  private var nationality: String = "" // Make nationality private

  // Auxiliary constructor
  def this(name: String, age: Int, nationality: String) = {
    this(name, age) // Call the primary constructor with default values
    this.nationality = nationality
  }

  // Getter for nationality
  def getNationality: String = nationality

  // Setter for nationality
  def setNationality(newNationality: String): Unit = {
    nationality = newNationality
  }

  // Getter for name
  def getName: String = name

  //Setter for the name
  def setName(newName: String): Unit ={
    name = newName
  }

  // Getter for age
  def getAge: Int = age

  //Setter for age
  def setAge(newAge: Int): Unit ={
    age = newAge
  }

  override def toString: String = s"Person: $name, $age"
  def toString(name:String, age:Int, nationality:String): String = s"Person: $name, $age, $nationality"
}

object Josh{
  val josh = new Person("Josh", 27)
}

object Monica{
  val monica = new Person("Monica", 31, "Moldava")
}

println(Josh.josh.toString)
println(Monica.monica.toString)
println(Monica.monica.toString("Monica", 31, "Moldava"))

//Checking how the getters work
println(Josh.josh.getNationality)
println(Monica.monica.getNationality)

println(Josh.josh.getName)
println(Monica.monica.getName)

println(Josh.josh.getAge)
println(Monica.monica.getAge)

//Now let's try how the setters work
Josh.josh.setAge(28)
Josh.josh.setName("John McGloin")
Josh.josh.setNationality("Irish")

println(Josh.josh.toString)

Monica.monica.setName("Monica Lewosky")
Monica.monica.setAge(32)
Monica.monica.setNationality("Spanish")

println(Monica.monica.toString)
