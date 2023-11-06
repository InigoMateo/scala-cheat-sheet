case class Person(name: String,
                  var age: Int,
                  nationality: String,
                  profession: String)

val john = new Person("John", 23, "Spanish", "Big Data Engineer")

println(john.toString)

println(john.age)

john.age = john.age + 1
println(john.age
)