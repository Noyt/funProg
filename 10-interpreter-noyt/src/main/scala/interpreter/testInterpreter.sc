// Note: "Run this worksheet" does not recompile other files in this project,
// You should run "~compile" in sbt to automatically recompile the project.

import interpreter._
import Lisp._

val code1 =
"""
(* (+ 3 3) 7)
"""


val code2 =
"""
(def factorial (lambda (x)
  (if  (= x 0) 1 (* x (factorial (- x 1)))))
(factorial 6))
"""

val expr2 = "((lambda(x) (+ x 1)) 41)"
def successor = 
"""
(def (succ x) (+ x 1) (succ 5))
"""

def myDef2 =
"""
(def (resul x y) (+ x y) (resul 4 5))
"""

def successorFull = 
"""
(def succ (lambda (x) (+ x 1)) (succ 5))
"""

def case_thing = 
"""
(case 4
        (1 1)
        (2 4)
        (3 9)
        (else -1)
  )
"""

def case_simple =
"""
(case 1 (1 3))
"""

val and_check = 
"""
(and 1 1)
"""

def factor_rec =
"""
(def fact (lambda (n)
  ((lambda (fact)
    (fact fact n))
   (lambda (ft k)
    (if (= k 1)
        1
        (* k (ft ft (- k 1)))))))
        (fact 6))
"""

def simple =
"""
(cons (car (cons 1 nil)) nil)
"""

def reverse =
"""
(def (reverse L acc)
    (if (null? L) 
      (acc) 
      (reverse (cdr (L)) (+ (car (L)) acc)))
    (reverse (a b c d) nil)
    )
"""

val inLine = """
  (def (reverse L acc) 
  (if (null? L) 
    acc 
    (reverse (cdr L) (cons (car L) acc))) 
  
  (reverse (cons 1 (cons 2 (cons 3 nil))) nil))
"""

def differences = """
  (def (differences L) 
    (def (loop K prev acc) 
      (if (null? K)
        acc
        (loop (cdr K) (car K) (cons (- (car K) prev) acc))
      )
      (loop L 0 nil)
    ) 
  (differences (cons 1 (cons 2 (cons 3 nil)))))
"""

def rebuilList = """
  (def (rebuildList L)
    (def (loop K prev acc)
      (if (null? K)
        acc
        (loop (cdr K) (+ prev (car K)) (cons (+ prev (car K)) acc))
      )
      (loop L 0 nil)
    )
  (rebuildList (cons 1 (cons 1 (cons 1 nil)))))
"""
val truc = """def (reverse L acc)""" 
// Uncomment to enable tracing of each evaluation step
// trace = true
// Display the parser output
println(string2lisp(truc))
println(string2lisp(case_thing))
//println(string2lisp(inLine))

// Run the interpreter
//println(evaluate(successorFull))
println(evaluate(successor))
println(evaluate(myDef2))
//println(evaluate(and_check))
//println(evaluate(case_thing))

//println(evaluate(code2))
//println(evaluate(factor_rec))
println(evaluate(simple))


println(evaluate(inLine))
println(evaluate(differences))
println(evaluate(rebuilList))








































