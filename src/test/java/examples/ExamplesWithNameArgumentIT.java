package examples;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

import java.io.File;

/**
 * Test javamop with -n argument is able to generate .aj file successfully.
 */

public class ExamplesWithNameArgumentIT {
    public  final String RVM_DIR = "rvm";
    public  final String basePath = "examples" + File.separator + "agent" + File.separator;
    private final String hasNextMopFile = RVM_DIR + File.separator + "HasNext.mop";
    private final String hasNextPath = basePath + "HasNext";
    private final String pathToMany = basePath + "many";

    private TestHelper helper;

    @Test
    public void testExampleWithArgs() throws Exception{
        helper = new TestHelper(hasNextPath + File.separator + hasNextMopFile);
        String command = System.getProperty("user.dir") + File.separator + "bin" + File.separator + "javamop";
        if (SystemUtils.IS_OS_WINDOWS) {
            command += ".bat";
        }
        try {
            helper.testCommand(null, false, true, command, "HasNext.mop" + " -n test");
        } finally {
            helper.deleteFiles(true, "testMonitorAspect.aj");
        }
    }

    @Test
    public void testExampleWithMultipleProperties() throws Exception{
        helper = new TestHelper(pathToMany + File.separator + RVM_DIR);
        String command = System.getProperty("user.dir") + File.separator + "bin" + File.separator + "javamop";
        if (SystemUtils.IS_OS_WINDOWS) {
            command += ".bat";
        }
        try {
            helper.testCommand(null, true, true, command, "rvm" + " -n testMany -s");
        } finally {
            helper.deleteFiles(true,
                    RVM_DIR+File.separator+"cfg"+File.separator+"testManyMonitorAspect.aj",
                    RVM_DIR+File.separator+"ere"+File.separator+"testManyMonitorAspect.aj",
                    RVM_DIR+File.separator+"ltl"+File.separator+"testManyMonitorAspect.aj");
        }
    }
}
