package systemTest;

import org.junit.Before;
import org.junit.Test;
import system.Tree;

import static org.junit.Assert.*;

public class TreeTest {

    private Tree testTree;



    @Test
    public void initTree() {
        Tree t = new Tree( "test") ;
        assertNotNull(t.children);

    }

    @Before
    public void setUp() throws Exception {
        testTree = new Tree("root") ;

    }

    @Test
    public void checkTreeStructure() {



        testTree.GetChildByName("test1");
        testTree.GetChildByName("test2");
        testTree.GetChildByName("test3");

        Tree test1 , test2 , test3 , test12 ;
        //first child
        assertNotNull( test1 = testTree.GetChildByName("test1"));
        assertEquals( test1.getPath()[0] ,"test1" );

        assertNotNull( test2 = testTree.GetChildByName("test2"));
        assertEquals( test2.getPath()[0] ,"test2" );

        //child of a child
        test1.GetChildByName("test1.2");
        assertNotNull( test12 = test1.GetChildByName("test1.2"));
        assertEquals( test12.getPath()[0] ,"test1" );
        assertEquals( test12.getPath()[1] ,"test1.2" );
        

    }

    @Test
    public void getChildByName() {
        Tree test1 = testTree.GetChildByName("test1");
        Tree test2 = testTree.GetChildByName("test2");
        Tree test3 = testTree.GetChildByName("test3");
        assertTrue(test2.parent.equals(testTree));
        assertTrue(testTree.children.containsKey("test2"));

    }
}