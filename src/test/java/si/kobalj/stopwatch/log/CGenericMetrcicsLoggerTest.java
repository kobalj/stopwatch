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
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testLogCase() throws Exception {
        System.out.println("testLogCase");

        CGenericMetrcicsLogger logger = new CGenericMetrcicsLogger.CGenericMetrcicsLoggerBuilder().build();
        IStopWatch pStopWatch = CStopWatchFactory.getStopWatchBuilder().setGlobalSLA(10000).build();

        // We start the global stop watch
        pStopWatch.startGlobal();

        // System is "doing" some work
        Thread.sleep(100);

        // Simple measure point
        pStopWatch.start("GET_ACC");
        // System is "doing" some work
        Thread.sleep(100);

        pStopWatch.stop("GET_ACC");

        // Measure point with sub measure point
        pStopWatch.start("CALC_CHRG");
        // System is "doing" some work
        Thread.sleep(200);

        // Sub measure point
        pStopWatch.start("GET_CHRG");
        // System is "doing" some work
        Thread.sleep(150);

        pStopWatch.stop("GET_CHRG");

        pStopWatch.stop("CALC_CHRG");

        // WE stop the global stopwatch
        pStopWatch.stopGlobal();

        CEnvironmentData envData = new CEnvironmentData();
        envData.addData("id", "U1212");
        envData.addData("val", 123);
        
        logger.log(pStopWatch, envData);

        assertTrue(true);
    }
}
