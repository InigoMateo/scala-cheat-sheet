// Function that may or may not return a value
def findValueByKey(key: String): Option[String] = {
  // Logic to find the value, return Some(value) if found, None otherwise
  // For demonstration purposes, let's just return Some or None based on a condition
  if (key == "found") Some("Value found!") else None
}

// Using the Option returned by the function
val result: Option[String] = findValueByKey("found")

result match {
  case Some(value) => println(s"Value is: $value")
  case None        => println("No value found")
}

val myMap = Map("a" -> 42, "b" -> 43)

def getMapValue(s: String): String =
  myMap.get(s) match{
case Some(nb) => "Value found: " + nb
case None => "No value found"
}

getMapValue("a")  // "Value found: 42"
getMapValue("c")  // "No value found"

def getMapValue(s: String): String =
  myMap.get(s).map("Value found: " + _).getOrElse("No value found")

getMapValue("a")

//Pattern Matching in Anonymous Functions
val options: List[Option[Char]] = Some('a') :: None :: Some('b') :: Nil

val pairs: List[Option[Char]] = Some('a')::Some('z')::Some('c')::Nil
val chars: List[Char]= pairs.map(p => p match {
  case Some(ch) => ch
  case None => 'z'
})
//Instead of p => p match { case ... }, you can simply write { case ...}, so the above example becomes more concise:
val chars: List[Char]= pairs.map {
  case Some(ch) => ch
  case None => 'z'
}