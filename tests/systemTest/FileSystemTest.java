package systemTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import system.BadFileNameException;
import system.FileSystem;
import system.OutOfSpaceException;

import java.nio.file.DirectoryNotEmptyException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FileSystemTest {
   FileSystem fs ;
    String[] name ;
    String[] name2 ;
    String[] name3 ;
    String[] name4 ;
    @Before
    public void setUp() throws Exception {
        fs = new FileSystem(100);

         name = new String[] { "root" , "dirTest1" } ;

         name2 = new String[] { "root" , "dirTest1" , "dirTest2"} ;

         name3 = new String[] { "root" , "dirTest1" , "fileTest1"} ;

         name4 = new String[] { "root" , "dirTest1" , "fileTest2"} ;
    }

    @Test
    public void init()  {
        FileSystem fs  = new FileSystem(100) ;
        assertNotNull(FileSystem.fileStorage);
        assertEquals(FileSystem.fileStorage.countFreeSpace() , 100);

    }

    @Test
    public void dir() throws BadFileNameException {
        String[] name = { "root" , "dirTest1"} ;
        fs.dir(name);
        assertNotNull(fs.DirExists(name));
        String[] name2 =  { "root" , "dirTest1" , "dirTest2"} ;
        fs.dir(name2);
        assertNotNull(fs.DirExists(name2));

    }


    @Test (expected = BadFileNameException.class)
    public void dirBadName() throws BadFileNameException {
        String[] name = { "notRoot" , "NotExists"} ;
        fs.dir(name );
        //Assert.fail("dir");
    }
    @Test (expected = BadFileNameException.class)
    public void dirBadPath() throws BadFileNameException, OutOfSpaceException {
        String[] name = { "root" , "Exists"} ;
        fs.file(name , 10 );
        fs.dir(name );
    }
    @Test
    public void disk() throws OutOfSpaceException, BadFileNameException {

        fs = new FileSystem(10) ;
        //int initFreeSpace = FileSystem.fileStorage.countFreeSpace();
        String[][] disk = fs.disk();
        String[] name = { "root" , "file1"} ;
        fs.file(name , 5 );
        assertEquals( 10 , disk.length);


        disk = fs.disk() ;
        int count = 0 ;
        for (int i = 0; i < disk.length; i++) {

            if (disk[i] == null) {
                continue;
            } else {
                for (int j = 0; j < disk[i].length; j++){
                    if  ( !disk[i][j].equals(name[j]))
                        break ;
                    else if (j == disk[i].length-1)//end
                            count++ ; // another good block
                }


            }
        }
        assertEquals( 5, count); // Expected outcome: 10 disk blocks occupied
    }

    @Test
    public void file() throws BadFileNameException, OutOfSpaceException {
        FileSystem fs = new FileSystem(10) ;
        String[] name = { "root" , "dirTest1"} ;
        fs.dir(name);
        assertNotNull(fs.DirExists(name));
        String[] name2 =  { "root" , "dirTest1" , "fileTest1"} ;
        fs.file(name2 , 5);
        assertNotNull(fs.FileExists(name2));
    }


    @Test (expected = BadFileNameException.class)
    public void fileBadPath() throws BadFileNameException, OutOfSpaceException {
        String[] name = { "notRoot" , "Exists"} ;
        fs.file(name , 10 );
    }

    @Test (expected = BadFileNameException.class)
    public void fileSameNameAsDir() throws BadFileNameException, OutOfSpaceException {
        FileSystem fs = new FileSystem(10) ;
        String[] name = { "root" , "dirTest1"} ;
        fs.dir(name);
        assertNotNull(fs.DirExists(name));
        // dame filr name as dir
        fs.file(name , 5);
    }

    @Test
    public void fileSameNameAsFile() throws BadFileNameException, OutOfSpaceException {
        FileSystem fs = new FileSystem(10) ;
        String[] name = { "root" , "fileTest"} ;
        fs.file(name , 5);
        // dame filr name as dir
        fs.file(name , 7);
        assertNotNull( fs.FileExists(name ));
        assertEquals(fs.fileStorage.countFreeSpace() , 3 );
    }
    @Test (expected = OutOfSpaceException.class)
    public void fileOutOfMemory() throws BadFileNameException, OutOfSpaceException {
        FileSystem fs = new FileSystem(10) ;
        String[] name = { "root" , "fileTest"} ;
        // dame filr name as dir
        fs.file(name , 15);
    }
    @Test
    public void lsdir() throws BadFileNameException, OutOfSpaceException {
        FileSystem fs = makeTestFs() ;
        String[] list = fs.lsdir(name );
        String[] rightAns = { "dirTest2" , "fileTest1" , "fileTest2"} ;
        ArrayList<String> toHelp = new ArrayList();
        for (String s:list
             ) { toHelp.add(s); }

        assertTrue( toHelp.contains(rightAns[0] ) );
        assertTrue( toHelp.contains(rightAns[1] ) );
        assertTrue( toHelp.contains(rightAns[2] ) );

        fs.rmfile(name3);
        list = fs.lsdir(name );
        toHelp = new ArrayList();
        for (String s:list
        ) { toHelp.add(s); }
        //rightAns = new String[]{"dirTest2", "fileTest2"};
        assertTrue( toHelp.contains(rightAns[0] ) );
        assertTrue( toHelp.contains(rightAns[2] ) );
        assertFalse(toHelp.contains(rightAns[1]));

    }

    @Test
    public void rmfile() throws BadFileNameException, OutOfSpaceException {
        FileSystem fs = makeTestFs();
        String[] list = fs.lsdir(name );
        String[] rightAns = { "dirTest2" , "fileTest1" , "fileTest2"} ;
        assertEquals( rightAns[0] , list[0] );
        assertEquals( rightAns[1] , list[1] );
        assertEquals( rightAns[2] , list[2] );
        fs.rmfile(name3);
        list = fs.lsdir(name );
        rightAns = new String[]{"dirTest2", "fileTest2"};
        assertEquals( rightAns[0] , list[0] );
        assertEquals( rightAns[1] , list[1] );
    }

    @Test
    public void rmdirEmpty() throws OutOfSpaceException, BadFileNameException, DirectoryNotEmptyException {
        FileSystem fs = makeTestFs();
        // remove empty dir
        fs.rmdir(name2);
        assertNull(fs.DirExists(name2));
    }

    @Test (expected = DirectoryNotEmptyException.class)
    public void rmdirFull() throws OutOfSpaceException, BadFileNameException, DirectoryNotEmptyException {
        FileSystem fs =makeTestFs();
        // remove empty dir
        fs.rmdir(name);
        assertNull(fs.DirExists(name2));
    }

    @Test
    public void fileExists() throws OutOfSpaceException, BadFileNameException {
        FileSystem fs = makeTestFs() ;
        assertNotNull(fs.FileExists(name3));
        fs.rmfile(name3);
        assertNull(fs.FileExists(name3));
    }

    @Test
    public void dirExists() throws DirectoryNotEmptyException, OutOfSpaceException, BadFileNameException {
        FileSystem fs = makeTestFs() ;
        assertNotNull(fs.DirExists(name2));
        fs.rmdir(name2);
        assertNull(fs.DirExists(name2));
    }


    private FileSystem makeTestFs () throws OutOfSpaceException, BadFileNameException {
        fs= new FileSystem(50) ;
        fs.dir(name);
        assertNotNull(fs.DirExists(name));
        fs.dir(name2);
        fs.file(name3 , 5 );
        fs.file(name4 , 5 );
        return fs ;
    }
}