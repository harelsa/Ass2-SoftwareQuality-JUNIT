import org.junit.Before;
import org.junit.Test;
import system.Leaf;
import system.OutOfSpaceException;
import system.Space;

import static org.junit.Assert.*;

public class SpaceTest {
    Space s ;

    @Test
    public void allocOutOfSpace() throws OutOfSpaceException {
        s.Alloc(1000 , new Leaf("test" , 20));
    }

    @Test
    public void allocNegativeSize() {
        
    }

    @Test
    public void  allocNullLeaf() {
        
        
    }

    @Test
    public void dealloc() {
    }

    @Test
    public void countFreeSpace() {
    }

    @Test
    public void getAlloc() {
    }

    @Before
    public void setUp() throws Exception {
         s = new Space (100) ;
    }


    @Test(expected = NullPointerException.class)
    public void NegativeSpaceTest() {
        Space st = new Space (-100) ;

    }


}