package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = oneOf(
    const(empty),
    for {
      k <- arbitrary[Int]
      h <- oneOf(const(empty), genHeap)
    } yield insert(k, h)
  )

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
  findMin(h) == a
  }

  property("min2") = forAll { (a: Int, b: Int) =>
    val h = insert(b, insert(a, empty))
  findMin(h) == Math.min(a, b) 
  }

  property("deleteMin") = forAll { a: Int => 
    val h = insert(a, empty)
  deleteMin(h) == empty
  }

  property("sorted") = forAll { (h: H) =>
    isSorted(produceSeq(h, List[Int]()))
  }

  def produceSeq(h: H, ls: List[Int]): List[Int] = h match {
    case empty => ls.reverse
    case _ => {
      val min = findMin(h)
      produceSeq(deleteMin(h), min :: ls)
    }
  }

  def isSorted(ls: List[Int]): Boolean = ls match {
    case Nil => true
    case x::Nil => true
    case (x :: y :: ls) => 
      if(x > y) false
      else isSorted(y :: ls)
  }
  
  property("minMeld") = forAll { (h1: H, h2: H) =>
    val meldMin = findMin(meld(h1, h2))
    val h1Min = findMin(h1)
    val h2Min = findMin(h2)

    meldMin == Math.min(h1Min, h2Min)
  }
}
