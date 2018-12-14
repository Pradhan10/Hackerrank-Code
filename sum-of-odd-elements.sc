Problem available at : https://www.hackerrank.com/challenges/fp-sum-of-odd-elements/problem

Solution : 

def sum(xs: List[Int]): Int = {
  
  def inner(xs: List[Int], accum: Int): Int = {
    xs match {
      case x :: tail => inner(tail, accum + x)
      case Nil => accum
    }
  }
  inner(xs, 0)
}

def f(arr:List[Int]):Int = {
  val odds = arr.filter(_ %2 != 0)
  val sumOfOdds = sum(odds)
  sumOfOdds
}
