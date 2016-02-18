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

import si.kobalj.stopwatch.CStopWatchFactory;
import si.kobalj.stopwatch.CAccumulatingStopWatch;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import si.kobalj.stopwatch.model.IInternalStopWatch;
import si.kobalj.stopwatch.model.IMeasurePoint;
import si.kobalj.stopwatch.model.IStopWatch;
import si.kobalj.stopwatch.model.IStopWatchBuilder;

/**
 *
 * @author Matej Korošec
 */
public class CAccumulatingStopWatchTest {

    public CAccumulatingStopWatchTest() {
    }

    /**
     * Test of start method, of class CStopWatch. Test for stopwatch factory
     * implementation
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testFactory() throws InterruptedException {
        IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().enable(IStopWatchBuilder.Option.ACCUMULATE_SAME_TAGS).build();
        assertTrue(stopWatch.getClass().isAssignableFrom(CAccumulatingStopWatch.class));
    }

    /**
     * Test of start method, of class CStopWatch. Test for basic stopwatch
     * implementation
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testBasicImpl() throws InterruptedException {
        IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().enable(IStopWatchBuilder.Option.ACCUMULATE_SAME_TAGS).build();

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
     * Test of start method, of class CStopWatch. Test for basic stopwatch
     * implementation
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testAccumulatingImpl() throws InterruptedException {
        IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().enable(IStopWatchBuilder.Option.ACCUMULATE_SAME_TAGS).build();

        stopWatch.start("MARK1");
        Thread.sleep(100);
        stopWatch.stop("MARK1");

        stopWatch.start("MARK1");
        Thread.sleep(100);
        stopWatch.stop("MARK1");

        stopWatch.start("MARK1");
        Thread.sleep(100);
        stopWatch.stop("MARK1");

        IInternalStopWatch internalStopWatch = (IInternalStopWatch) stopWatch;
        Collection<IMeasurePoint> points = internalStopWatch.getMeasurePoints();
        assertTrue("Number of measure points is not right.", points.size() == 1);
        assertTrue(points.iterator().next().getDuration() >= 300);
    }

    /**
     * Test of start method, of class CStopWatch. Test for stopwatch with global
     * SLA.
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testIStopWatchWithGlobalSLA() throws InterruptedException {
        System.out.println("testIStandardStopWatchWithGlobalSLA");

        IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().enable(IStopWatchBuilder.Option.ACCUMULATE_SAME_TAGS).setGlobalSLA(50).build();

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
     * Test of start method, of class CStopWatch. Test for stopwatch with SLA
     * for measurepoint.
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testIStopWatchWithMeasurePointSLA() throws InterruptedException {
        System.out.println("testIStandardStopWatchWithMeasurePointSLA");

        IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().enable(IStopWatchBuilder.Option.ACCUMULATE_SAME_TAGS).setSLA("MARK1", 50).build();

        stopWatch.start("MARK1");
        Thread.sleep(100);
        stopWatch.stop("MARK1");

        IInternalStopWatch internalStopWatch = (IInternalStopWatch) stopWatch;
        Collection<IMeasurePoint> points = internalStopWatch.getMeasurePoints();
        assertTrue("Number of measure points is not right.", points.size() == 1);

        IMeasurePoint mp = points.iterator().next();
        assertTrue("Invalid max duration time status.", mp.isSLAViolation());
    }
    
    /**
     * Test of start method, of class CStopWatch. Test of toString method.
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    @Ignore
    public void testIStopWatch2String() throws InterruptedException {
        System.out.println("testIStandardStopWatchWithMeasurePointSLA");

        IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().enable(IStopWatchBuilder.Option.ACCUMULATE_SAME_TAGS).setSLA("MARK1", 50).build();

        stopWatch.start("MARK1");
        Thread.sleep(100);
        stopWatch.stop("MARK1");

        stopWatch.start("MARK2");
        Thread.sleep(20);
        stopWatch.stop("MARK2");
        
        stopWatch.start("MARK2");
        Thread.sleep(30);
        stopWatch.stop("MARK2");

        String s = stopWatch.toString();
        assertTrue("toString didn't contain expected parameter", s.contains("MARK1:100"));
        assertTrue("toString didn't contain expected parameter", s.contains("MARK2:50"));
        System.out.println("Result is:"+s);
    }
}

