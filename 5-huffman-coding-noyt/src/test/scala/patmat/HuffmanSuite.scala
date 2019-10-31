package patmat

import org.junit._
import org.junit.Assert.assertEquals

class HuffmanSuite {
  import Huffman._

  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
    val exTree = createCodeTree(List('b', 'a', 'a', 'a', 'c', 'a', 'e', 'a', 'a', 'g', 'a', 'b', 'a', 'b', 'd', 'f', 'h'))
  }


  @Test def `weight of a larger tree`: Unit =
    new TestTrees {
      assertEquals(5, weight(t1))
    }

  @Test def `chars of a larger tree`: Unit =
    new TestTrees {
      assertEquals(List('a','b','d'), chars(t2))
    }

  @Test def `string2chars hello world`: Unit =
    assertEquals(List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'), string2Chars("hello, world"))

  @Test def `times works fine`: Unit =
    assertEquals(List(('a', 3), ('b', 1), ('c', 4), ('d', 2)), times(List('a','c','c','b','a','c','d','a','c','d')))

  @Test def `slowerTimes works fine`: Unit =
    assertEquals(List(('a', 3), ('b', 1), ('c', 4), ('d', 2)), slowerTimes(List('a','c','c','b','a','c','d','a','c','d')))

  @Test def `make ordered leaf list for some frequency table`: Unit =
    assertEquals(List(Leaf('e',1), Leaf('t',2), Leaf('x',3)), makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))))

  @Test def `singleton says true for one`: Unit =
    assert(singleton(List(Leaf('a', 2))))
  
  @Test def `singleton says false for two and 0`: Unit = {
    assert(!singleton(List(Leaf('a', 2), Leaf('b', 3))))
    assert(!singleton(List())));
  }

  @Test def `combine of some leaf list`: Unit = {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assertEquals(List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)), combine(leaflist))
  }

  @Test def `Scala is indeed glorious (until test)`: Unit = {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    val leaflist2 = List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4))
    val leaflist3 = List(Fork(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), List('e', 't', 'x'),7))
    assertEquals(leaflist3, until(singleton, combine)(leaflist))
  }

  @Ignore
  @Test def `createCodeTree works fine`: Unit = {
    new TestTrees {
      val lA = Leaf('a', 8)
      val lB = Leaf('b', 3)
      val lC = Leaf('c', 1)
      val lD = Leaf('d', 1)
      val lE = Leaf('e', 1)
      val lF = Leaf('f', 1)
      val lG = Leaf('g', 1)
      val lH = Leaf('h', 1)

      val fCD = Fork(lC, lD, List('c', 'd'), 2)
      val fEF = Fork(lE, lF, List('e', 'f'), 2)
      val fGH = Fork(lG, lH, List('g', 'h'), 2)

      val fBCD = Fork(lB, fCD, List('b', 'c', 'd'), 5)
      val fEFGH = Fork(fEF, fGH, List('e', 'f', 'g', 'h'), 4)

      val fBCDEFGH = Fork(fBCD, fEFGH, List('b', 'c', 'd', 'e', 'f', 'g', 'h'), 9)

      val fABCDEFGH = Fork(lA, fBCDEFGH, List('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'), 17)

      assertEquals(fABCDEFGH, exTree)
    }
  }

  @Ignore
  @Test def `createCodeTree works fine restricted`: Unit = {
    val lA = Leaf('a', 8)
    val lB = Leaf('b', 3)
    val lC = Leaf('c', 1)
    val lD = Leaf('d', 1)
    val lE = Leaf('e', 1)
    val lF = Leaf('f', 1)
    val lG = Leaf('g', 1)
    val lH = Leaf('h', 1)

    val fCD = Fork(lC, lD, List('c', 'd'), 2)
    val fEF = Fork(lE, lF, List('e', 'f'), 2)
    val fGH = Fork(lG, lH, List('g', 'h'), 2)

    val fBCD = Fork(lB, fCD, List('b', 'c', 'd'), 5)
    
    assertEquals(fBCD, createCodeTree(List('b', 'c', 'b', 'b', 'd')))
  }
  @Test def `secret message`: Unit = {
    assertEquals(List('h', 'u', 'f', 'f', 'm', 'a', 'n', 'e', 's', 't', 'c', 'o', 'o', 'l'), decodedSecret)
  }

  @Test def `decode and encode a very short text should be identity`: Unit =
    new TestTrees {
      assertEquals("ab".toList, decode(t1, encode(t1)("ab".toList)))
    }

  @Test def `decode and encode a bit longer text should be identity`: Unit =
    new TestTrees {
      assertEquals("abddefghdgeafh".toList, decode(exTree, encode(exTree)("abddefghdgeafh".toList)))
    }

  @Test def `decode and encode using codeTables`: Unit =
    new TestTrees {
      assertEquals("abddefghdgeafh".toList, decode(exTree, quickEncode(exTree)("abddefghdgeafh".toList)))
    }

  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
