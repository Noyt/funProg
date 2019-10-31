package interpreter
import Lisp._
object Main {
  import java.io.{BufferedReader, InputStreamReader}
  val in = new BufferedReader(new InputStreamReader(System.in))

  def main(args: Array[String]): Unit = {
    // TODO: Should we implement exit procedure?
    print("lisp> ")
    val r = in.readLine
    println(evaluate(r))
  }
}

object LispCode {
    // TODO: implement the function `reverse` in Lisp.
  // From a list (a, b, c, d) it should compute (d, c, b, a)
  // Write it as a String, and test it in your REPL
  val reverse = """
  def (reverse L acc)
    (if (null? L) 
        acc 
        (reverse (cdr L) (cons (car L) acc))
        )
  """

    // TODO: implement the function `differences` in Lisp.
  // From a list (a, b, c, d ...) it should compute (a, b-a, c-b, d-c ...)
  // You might find useful to define an inner loop def
  val differences = """
  def (differences L)
    (def (loop K prev acc) 
      (if (null? K)
        acc
        (loop (cdr K) (car K) (cons (- (car K) prev) acc))
      )
      (reverse (loop L 0 nil) nil)
    ) 
  """
  val rebuildList = """
  def (rebuildList L)
    (def (loop K prev acc)
      (if (null? K)
        acc
        (loop (cdr K) (+ prev (car K)) (cons (+ prev (car K)) acc))
      )
      (reverse (loop L 0 nil) nil)
    )    
  """

  val withDifferences: String => String =
    (code: String) => "(" + reverse + " (" + differences + " (" + rebuildList + " " + code + ")))"
}
