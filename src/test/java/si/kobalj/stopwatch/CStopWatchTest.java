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
package si.kobalj.stopwatch;

import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import si.kobalj.stopwatch.model.IInternalStopWatch;
import si.kobalj.stopwatch.model.IMeasurePoint;
import si.kobalj.stopwatch.model.IStopWatch;

/**
 *
 * @author Jure Kobal
 */
public class CStopWatchTest {
    
    public CStopWatchTest() {
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
     * Test of start method, of class CStopWatch.
     * Test for stopwatch implementation
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testIStopWatch() throws InterruptedException {
        System.out.println("testIStopWatch");
        
        IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().build();
        
        stopWatch.start("MARK1");
        Thread.sleep(100); 
        stopWatch.stop("MARK1");
        
        stopWatch.start("MARK2");
        Thread.sleep(100); 
        stopWatch.stop("MARK2");
        
        IInternalStopWatch internalStopWatch = (IInternalStopWatch) stopWatch;
        Collection<IMeasurePoint> points = internalStopWatch.getMeasurePoints();
        assertTrue("Number of measure points is not right.", points.size() == 2);
    }
    
    /**
     * Test of start method, of class CStopWatch.
     * Test for stopwatch with global SLA.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testIStopWatchWithGlobalSLA() throws InterruptedException {
        System.out.println("testIStandardStopWatchWithGlobalSLA");
        
        IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().setGlobalSLA(50).build();
        
        stopWatch.startGlobal();
        Thread.sleep(100); 
        stopWatch.stopGlobal();
        
        IInternalStopWatch internalStopWatch = (IInternalStopWatch) stopWatch;
        Collection<IMeasurePoint> points = internalStopWatch.getMeasurePoints();
        assertTrue("Number of measure points is not right.", points.size() == 1);
        
        IMeasurePoint mp = points.iterator().next();
        assertTrue("Invalid max duration time status.", mp.isSLAViolation());
    }
    
    /**
     * Test of start method, of class CStopWatch.
     * Test for stopwatch with SLA for measurepoint.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testIStopWatchWithMeasurePointSLA() throws InterruptedException {
        System.out.println("testIStandardStopWatchWithMeasurePointSLA");
        
        IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().setSLA("MARK1", 50).build();
        
        stopWatch.start("MARK1");
        Thread.sleep(100); 
        stopWatch.stop("MARK1");
        
        IInternalStopWatch internalStopWatch = (IInternalStopWatch) stopWatch;
        Collection<IMeasurePoint> points = internalStopWatch.getMeasurePoints();
        assertTrue("Number of measure points is not right.", points.size() == 1);
        
        IMeasurePoint mp = points.iterator().next();
        assertTrue("Invalid max duration time status.", mp.isSLAViolation());
    }
}
