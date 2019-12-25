import org.junit.Test;
import system.BadFileNameException;
import system.Leaf;
import system.OutOfSpaceException;

import static org.junit.Assert.*;

public class LeafTest {
    @Test
    public void getSize() throws OutOfSpaceException {
        Leaf l = new Leaf("notTested" , 100) ;
        assertEquals( 100 , l.size) ;

    }

    @Test(expected = NullPointerException.class)
    public void NullNameTest() throws OutOfSpaceException {

        Leaf l = new Leaf(null  , 100) ;

    }

    @Test (expected = NullPointerException.class)
    public void NegativeSizeValueTest() throws OutOfSpaceException {
        Leaf l = new Leaf("GoodName"  , -1) ;
    }
}