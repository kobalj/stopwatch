/*
 * Copyright (C) 2016 Jure Kobal and Matej Korošec
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package si.kobalj.stopwatch.log;

import si.kobalj.stopwatch.model.ILogMessage;
import java.util.logging.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import si.kobalj.stopwatch.CStopWatchFactory;
import si.kobalj.stopwatch.model.IStopWatch;

/**
 *
 * @author Jure Kobal
 */
public class CGenericMetrcicsLoggerTest {

    public CGenericMetrcicsLoggerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Sample implementation of the stopwatch system.
     * Test with no SLA violation and global and one specific measure point. Without environment data.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testMessage() throws Exception {
        System.out.println("testMessage");
        
        CGenericMetrcicsLogger logger = new CGenericMetrcicsLogger.CGenericMetrcicsLoggerBuilder().build();
        IStopWatch pStopWatch = CStopWatchFactory.getStopWatchBuilder().build();
        pStopWatch.startGlobal();

        // System is "doing" some work
        Thread.sleep(100);

        // Simple measure point
        pStopWatch.start("GET_ACC");
        // System is "doing" some work
        Thread.sleep(100);

        pStopWatch.stop("GET_ACC");

        // We stop the global stopwatch
        pStopWatch.stopGlobal();
        
        ILogMessage message = logger.getMessage(pStopWatch, null);
        assertEquals("Level is not as expected.", message.getLevel(), Level.INFO);
        assertTrue(message.getMessage().contains("PERF_LOG:"));
        assertTrue(message.getMessage().contains("GET_ACC"));
        assertFalse(message.getMessage().contains("|| ENV_DATA: NODATA"));
    }
    
    /**
     * Sample implementation of the stopwatch system.
     * Test with no SLA violation and global and one specific measure point. Without environment data.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testMessageSLAViolation() throws Exception {
        System.out.println("testMessageSLAViolation");
        
        CGenericMetrcicsLogger logger = new CGenericMetrcicsLogger.CGenericMetrcicsLoggerBuilder().
                setLogSLAViolationsAsWarning(true).build();
        IStopWatch pStopWatch = CStopWatchFactory.getStopWatchBuilder().setGlobalSLA(10).build();
        pStopWatch.startGlobal();

        // System is "doing" some work
        Thread.sleep(100);

        // Simple measure point
        pStopWatch.start("GET_ACC");
        // System is "doing" some work
        Thread.sleep(100);

        pStopWatch.stop("GET_ACC");

        // We stop the global stopwatch
        pStopWatch.stopGlobal();
        
        ILogMessage message = logger.getMessage(pStopWatch, null);
        assertEquals("Level is not as expected.", message.getLevel(), Level.WARNING);
        assertTrue(message.getMessage().contains("PERF_LOG:"));
        assertTrue(message.getMessage().contains("GET_ACC"));
        assertFalse(message.getMessage().contains("|| ENV_DATA: NODATA"));
    }
    
    /**
     * Sample implementation of the stopwatch system.
     * Test with no SLA violation and global and one specific measure point. With environment data.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testMessageSLAViolationEnvData() throws Exception {
        System.out.println("testMessageSLAViolationEnvData");
        
        CGenericMetrcicsLogger logger = new CGenericMetrcicsLogger.CGenericMetrcicsLoggerBuilder().
                setLogSLAViolationsAsWarning(true).build();
        IStopWatch pStopWatch = CStopWatchFactory.getStopWatchBuilder().setGlobalSLA(10).build();
        pStopWatch.startGlobal();

        // System is "doing" some work
        Thread.sleep(100);

        // Simple measure point
        pStopWatch.start("GET_ACC");
        // System is "doing" some work
        Thread.sleep(100);

        pStopWatch.stop("GET_ACC");

        // We stop the global stopwatch
        pStopWatch.stopGlobal();
        
        CEnvironmentData envData = new CEnvironmentData();
        
        ILogMessage message = logger.getMessage(pStopWatch, envData);
        assertEquals("Level is not as expected.", message.getLevel(), Level.WARNING);
        assertTrue(message.getMessage().contains("PERF_LOG:"));
        assertTrue(message.getMessage().contains("GET_ACC"));
        assertTrue(message.getMessage().contains("|| ENV_DATA: NODATA"));
    }
}
