package systemTest;

import org.junit.Before;
import org.junit.Test;
import system.*;

import static org.junit.Assert.*;

public class SpaceTest {
    Space s ;
    FileSystem fs ;

    @Before
    public void setUp() throws Exception {
        fs = new FileSystem(50);
        s = FileSystem.fileStorage ; //get the file system space
    }

    @Test ( expected = NullPointerException.class)
    public void allocNullLeaf() throws NullPointerException, OutOfSpaceException {
        s.Alloc(10 , null);
    }




//    @Test ( expected = OutOfSpaceException.class)
//    public void allocOutOfMemoryCheck() throws OutOfSpaceException, BadFileNameException {
//        String[] strings = {"root" , "testAllocate"};
//        fs.file( strings , 5);
//        Leaf file = fs.FileExists(strings);
//        s.Alloc(1000 , file ); //maybe can be done better
//    }


    //check !!!!!!!!! **************
//    @Test(expected = NullPointerException.class)
//    public void allocNegativeSpaceTest() {
//        Space st = new Space (-100) ;
//
//    }
    //check !!!!!!!!! ************** which exeption needed ?
//    @Test (expected = NullPointerException.class)
//    public void allocNegative() throws OutOfSpaceException {
//        s.Alloc( -100 , new Leaf ("sdf" , 10));
//    }



    /** check space before and after dealloc
     * @throws OutOfSpaceException
     * @throws BadFileNameException
     */
    @Test
    public void dealloc() throws OutOfSpaceException, BadFileNameException {
       //adding file and removing it
        int freeSpace = s.countFreeSpace(); // before
        String[] strings = {"root" , "testDealloc"};
        fs.file( strings , 5);
        Leaf file = fs.FileExists(strings);
        FileSystem.fileStorage.Dealloc(file);
        assertEquals( freeSpace , s.countFreeSpace());
        assertNull(  fs.FileExists(strings));
        Leaf [] localBlocks = s.getAlloc();
        for (int i = 0; i < file.allocations.length; i++) {
            assertNull(localBlocks[file.allocations[i]]);
            //record new free block
        }
        assertFalse(file.parent.children.containsKey("testDealloc"));

    }


    @Test ( expected = NullPointerException.class)
    public void deallocNullInput() {
        FileSystem.fileStorage.Dealloc(null);
    }


    @Test
    public void countFreeSpace() throws OutOfSpaceException, BadFileNameException {
        //init space
        int size  = 100 ;
        Space newSpace = new Space(size) ;
        assertEquals(newSpace.countFreeSpace() , size) ;

        //add space check
        int beforeFreeSpace = s.countFreeSpace(); // before
        String[] strings = {"root" , "test"};
        fs.file( strings , 5);
        assertEquals(beforeFreeSpace - 5 , s.countFreeSpace()) ;
    }

    @Test
    public void getAlloc() {
        Space sp = new Space(100 );
        assertNotNull(sp.getAlloc()) ;
        assertEquals(sp.getAlloc().length , 100 );

    }


    @Test
    public void init() {
        Space test = new Space(100) ;
        assertEquals(test.getAlloc().length ,100 ) ;
        assertEquals(test.countFreeSpace() , 100);

    }


}