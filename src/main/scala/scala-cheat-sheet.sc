def cube (x: Int): Int =
  x * x * x

val cube2 = (x: Int) => x * x * x
val cube2Result = cube2(2)
val cube3: Int => Int = (x: Int) => x * x * x
val cube3Result = cube3(3)

def method1 (a: Int, b: Int, c: Int, cube: (Int) => Int): Int = {
  cube(a) + cube(b) + cube(c)
}

def square (x: Int): Int =
  x * x

def method2 (a: Int, b: Int, square: Int => Int): Int = {
  square(a) + square(b)
}

//Functions as parameters:
def operateOnNumbers(a: Int, b: Int, operation: (Int, Int) => Int): Int = {
  operation(a, b)
}

val addition = (x: Int, y: Int) => x + y
val subtraction = (x: Int, y: Int) => x - y

val result1 = operateOnNumbers(5, 3, addition)       // result1 = 8
val result2 = operateOnNumbers(8, 4, subtraction)    // result2 = 4

//Functions as results:
def mathFunction (operator: String): (Int, Int) => Int = {
  operator match{
    case "add" => (x: Int,y: Int) => x + y
    case "subtract" => (x: Int,y: Int) => x - y
    case _ => (x: Int,y: Int) => 0
  }
}

val add = mathFunction("add")
val subtract = mathFunction("subtract")

val result1 = add(4,2)
val result2 = subtract(4,2)

//Currying examples:
val f: (Int, Int) => Int = (x: Int, y: Int) => x + y
val curryingF: Int => Int => Int = f.curried

val ResultF = f(2,4)
val curry1 = curryingF(2)
val curry2 = curry1(4)