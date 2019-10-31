package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = union(s2, s3)
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remvoe the
   * @Ignore annotation.
   */
  @Test def `singleton sets contain or not`: Unit = {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton1")
      assert(contains(s2, 2), "Singleton2")
      assert(!contains(s3, 2), "Singleton3")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  @Test def `intersection contains only common elements`: Unit = {
    new TestSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1), "Inter 1")
      assert(!contains(s, 3), "Inter 2")
      val ss = intersect(s4, s2)
      assert(contains(ss, 2), "Inter 3")
    }
  }

  @Test def `difference contains only remaining elements`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      val ss = diff(s, s2)
      assert(contains(ss, 1), "Diff 1")
      assert(!contains(ss, 2), "Diff 1")
    }
  }

  @Test def `filter retains only correct values`: Unit = {
    new TestSets {
      val s = union(s1, s4)

      val ss = filter(s, s3) 
      assert(contains(ss, 3))
      assert(!contains(ss, 1))
      assert(!contains(ss, 2))
      
      val sax = filter(s, (x: Int) => x>2)
      assert(contains(sax, 3))
      assert(!contains(sax, 1))
      assert(!contains(sax, 2))
    }
  }

  @Test def `forall is pretty good at its job`: Unit = {
    new TestSets {
      val s = union(s1, s4)
      assert(forall(s, (x: Int) => x > 0), "Forall 1")
      assert(!forall(s, (x: Int) => x < 2), "Forall 2")
      assert(forall(s, (x: Int) => x < 5), "Forall 3")
    }
  }

  @Test def `exists works`: Unit = {
    new TestSets {
      val s = union(s1, s4)
      assert(exists(s, (x: Int) => x > 2), "Exist 1")
      assert(!exists(s, (x: Int) => x > 3), "Exist 2")
      assert(exists(s, (x: Int) => x <= 10), "Exist 3")
    }
  }

  @Test def `map is nice` : Unit = {
    new TestSets {
      val s = map(s1, (x: Int) => x + 1)
      assert(contains(s, 2))
      assert(!contains(s, 1))      
    }
  }

  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}

