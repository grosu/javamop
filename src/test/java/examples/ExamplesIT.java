// Copyright (c) 2002-2014 JavaMOP Team. All Rights Reserved.
package examples;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * JUnit test case to run through select program examples. Based on examples/run and examples/runall.
 */
@RunWith(Parameterized.class)
public class ExamplesIT {
    
    private final TestHelper helper;
    private final String path;
    
    /**
     * Construct this instance of the parameterized test.
     * @param path The path to the .mop file used in this test.
     */
    public ExamplesIT(String path) {
        this.path = new File(path).getParent();
        helper = new TestHelper(path);
    }
    
    /**
     * Test all the instances of this example. Each example has a _1, _2, and possibly a _3 
     * component. This runs assertions on all the available ones. This function is inspired by the
     * examples/run script.
     */
    @Test
    public void testExample() throws Exception {
        final String testName = path.substring(path.lastIndexOf(File.separator)+1);
        String command = System.getProperty("user.dir") + File.separator + "bin" + File.separator + "javamop";
        if (SystemUtils.IS_OS_WINDOWS) {
            command += ".bat";
        }
        helper.testCommand(null, false, true, command, testName + ".mop");
        
        String classpath = "." + File.pathSeparator + "mop" + File.separator + File.pathSeparator
                + System.getProperty("java.class.path");
        
        String subcasePath = testName + "_";
        for(int i = 1; new File(path + File.separator + subcasePath + i).exists(); i++) {
            String subcasePathI = subcasePath + i;
            String specificClasspath = classpath + File.pathSeparator + subcasePathI + 
                    File.pathSeparator + subcasePathI + File.separator + "mop";
            // AJC has nonzero return codes with just warnings, not errorss.
            helper.testCommand(null, false, true, "java", "-cp", specificClasspath,
                "org.aspectj.tools.ajc.Main", "-1.6", "-d",  subcasePathI, subcasePathI + 
                File.separator + subcasePathI + ".java", testName + "MonitorAspect.aj");
            helper.testCommand(subcasePathI, subcasePathI, false, true, "java", "-cp", specificClasspath,
                subcasePathI);
            helper.deleteFiles(true, subcasePathI + File.separator + subcasePathI + ".actual.err", 
                subcasePathI + File.separator + subcasePathI + ".actual.out");
            helper.deleteFiles(true, subcasePathI + File.separator + subcasePathI + ".class");
            
            String[] classFilePrefix = {
                subcasePathI + File.separator + "mop" + File.separator + testName,
                subcasePathI + File.separator + testName
            };
            for(String prefix : classFilePrefix) {
                helper.deleteFiles(false, prefix + "Monitor.class", prefix + "Monitor_Set.class", 
                    prefix + "MonitorAspect.class", prefix + "RuntimeMonitor.class", 
                    prefix + "SuffixMonitor.class", prefix + "Monitor$IntStack.class",
                    prefix + "SuffixMonitor_Set.class", prefix  + "DisableHolder.class",
                    prefix + "MonitorAspect$" + testName + "_DummyHookThread.class"
                );
                helper.deleteFiles(false, subcasePathI + File.separator + "mop" + File.separator +
                    "I" + testName + "Monitor.class");
            }
        }
        helper.deleteFiles(true, testName + "MonitorAspect.aj");
    }

    /**
     * Run a subset of the examples as tests. These are from the examples/runall script.
     */
    @Parameterized.Parameters(name="{0}")
    public static Collection<Object[]> data() {
        ArrayList<Object[]> data = new ArrayList<Object[]>();
        //from examples/runall.txt
        data.add(new Object[]{"examples" + File.separator + "CFG" + File.separator + "HasNext" + File.separator + "HasNext.mop"});
        data.add(new Object[]{"examples" + File.separator + "CFG" + File.separator + "SafeFile" + File.separator + "SafeFile.mop"});
        data.add(new Object[]{"examples" + File.separator + "CFG" + File.separator + "SafeFileWriter" + File.separator + "SafeFileWriter.mop"});

        data.add(new Object[]{"examples" + File.separator + "ERE" + File.separator + "HasNext" + File.separator + "HasNext.mop"});
        //#SuffixHasNext, hasnext2, hashset, passwordlogic require to change mop/directoy name
        data.add(new Object[]{"examples" + File.separator + "ERE" + File.separator + "SafeSyncCollection" + File.separator + "SafeSyncCollection.mop"});
        data.add(new Object[]{"examples" + File.separator + "ERE" + File.separator + "SafeFileWriter" + File.separator + "SafeFileWriter.mop"});
        data.add(new Object[]{"examples" + File.separator + "ERE" + File.separator + "SafeSyncMap" + File.separator + "SafeSyncMap.mop"});
        //Pending Issue 5
        data.add(new Object[]{"examples" + File.separator + "ERE" + File.separator + "SafeEnum" + File.separator + "SafeEnum.mop"});
        data.add(new Object[]{"examples" + File.separator + "ERE" + File.separator + "UnsafeIterator" + File.separator + "UnsafeIterator.mop"});
        data.add(new Object[]{"examples" + File.separator + "ERE" + File.separator + "UnsafeMapIterator" + File.separator + "UnsafeMapIterator.mop"});

        data.add(new Object[]{"examples" + File.separator + "FSM" + File.separator + "HasNext" + File.separator + "HasNext.mop"});
        data.add(new Object[]{"examples" + File.separator + "FSM" + File.separator + "HasNext2" + File.separator + "HasNext2.mop"});

        //Pending Issue 5
        data.add(new Object[]{"examples" + File.separator + "LTL" + File.separator + "SafeEnum" + File.separator + "SafeEnum.mop"});
        data.add(new Object[]{"examples" + File.separator + "LTL" + File.separator + "HasNext" + File.separator + "HasNext.mop"});
        data.add(new Object[]{"examples" + File.separator + "LTL" + File.separator + "SafeFileWriter" + File.separator + "SafeFileWriter.mop"});
        data.add(new Object[]{"examples" + File.separator + "LTL" + File.separator + "SafeIterator" + File.separator + "SafeIterator.mop"});
        data.add(new Object[]{"examples" + File.separator + "LTL" + File.separator + "SafeMapIterator" + File.separator + "SafeMapIterator.mop"});

        data.add(new Object[]{"examples" + File.separator + "MOPSyntax" + File.separator + "Creation" + File.separator + "Creation.mop"});
        data.add(new Object[]{"examples" + File.separator + "EndProgram" + File.separator + "FileClose" + File.separator + "FileClose.mop"});
        //# Think about sth similar to handle countCond
        return data;
    }
}