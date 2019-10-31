package recfun

object Main {
  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      //TODO check invalid cood
      if(c == 0 || c == r) 1 else pascal(c-1, r-1) + pascal(c, r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      
      def balCheck(acc: Int, xs: List[Char]): Boolean = {
        if(acc < 0) false
        else if(xs.isEmpty) {
          acc == 0
        }
        else {
          if(xs.head == '(') balCheck(acc + 1, xs.tail) 
          else if(xs.head == ')') balCheck(acc - 1, xs.tail) 
          else balCheck(acc, xs.tail)
          }
      }

      balCheck(0, chars)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
    
      def count(acc: Int, xs: List[Int]): Int = {

        if(xs.isEmpty) 0
        else if(acc == 0) 1
        else if(acc < 0) 0
        else count(acc-xs.head, xs) + count(acc, xs.tail)
      }

      count(money, coins)
    }
}