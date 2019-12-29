package systemTest;

import org.junit.Before;
import org.junit.Test;
import system.*;

import static org.junit.Assert.*;

public class LeafTest {
    FileSystem fs ;
    Space space ;
    @Test
    public void getSize() throws OutOfSpaceException {

        Leaf l = new Leaf("notTested" , 100) ;
      //  assertEquals( 100 , l.size) ;
        assertEquals(l.allocations.length , 100);

    }

//    @Test(expected = NullPointerException.class)
//    public void NullNameTest() throws OutOfSpaceException {
//
//        Leaf l = new Leaf(null  , 100) ;
//
//    }

//    @Test (expected = NullPointerException.class)
//    public void NegativeSizeValueTest() throws OutOfSpaceException {
//        Leaf l = new Leaf("GoodName"  , -1) ;
//    }


    @Before
    public void setUp() throws Exception {
       fs = new FileSystem(100) ;
       space = FileSystem.fileStorage ;
    }
}