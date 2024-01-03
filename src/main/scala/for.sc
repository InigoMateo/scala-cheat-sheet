val numbers = List(1, 2, 3, 4, 5)
val squaredNumbers = for (num <- numbers) yield num * num
// squaredNumbers: List[Int] = List(1, 4, 9, 16, 25)

val numbers = List(1, 2, 3, 4, 5)
val squaredNumbers = for (num <- numbers) yield {num * 2}
// squaredNumbers: List[Int] = List(1, 4, 9, 16, 25)

// list all combinations of numbers x and y where x is drawn from
// 1 to M and y is drawn from 1 to N
//for (x <- 1 to M; y <- 1 to N)
//  yield (x, y)

for (x <- 1 to 3; y <- 1 to 3)
  yield (x, y)

//Other way to indicate a for stament but with flatMap
(1 to M).flatMap(x => (1 to N) map (y => (x, y))))

(1 to 3).flatMap(x => (1 to 3 map (y => (x, y)))