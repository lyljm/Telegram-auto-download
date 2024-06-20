package org.downloader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.downloader.utils.Config;
import org.downloader.utils.Rwfile;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws InterruptedException {
        for(int i=0;i<100;i++){
//            System.out.print("\r\033[K");
////            System.out.print("\033[0;0H");
            System.out.print("\nProgress: "+i+"% ");
//            System.out.flush();
//            Thread.sleep(100);
        }
    }

    public void testRead(){
        String fileJson="D:\\Product\\java\\Tmp\\java\\exercise\\downerTele\\tdl.json";
        String fileSerial="D:\\Product\\java\\Tmp\\java\\exercise\\downerTele\\serial.txt";
        String json = Rwfile.readAll(fileJson);
        System.out.println(json);
        Rwfile.write(fileSerial,10L);
        String serial = Rwfile.readAll(fileSerial);
        System.out.println(Long.parseLong(serial));

        System.out.println(Config.downCmd);
        System.out.println(Config.jsonCmd);
        long l = Long.parseLong("0");
        System.out.println(l);
    }
}
