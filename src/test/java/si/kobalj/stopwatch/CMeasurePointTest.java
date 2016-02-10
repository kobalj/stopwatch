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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jure Kobal
 */
public class CMeasurePointTest {
    
    public CMeasurePointTest() {
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
     * Test of getDuration method, of class CMeasurePoint.
     */
    @Test
    public void testGetDuration() {
        System.out.println("getDuration");
        CMeasurePoint instance = new CMeasurePoint.CMeasurePointBuilder("test", 100, 110).build();
        long expResult = 10L;
        long result = instance.getDuration();
        assertEquals("Calculated duration if wrong.", expResult, result);
    }

    /**
     * Test of isMaxAllowedDurationViolation method, of class CMeasurePoint.
     * We test when the duration ia longer then the max allowed duration.
     */
    @Test
    public void testIsMaxAllowedDurationViolationTrue() {
        System.out.println("isMaxAllowedDurationViolationTrue");
        CMeasurePoint instance = new CMeasurePoint.CMeasurePointBuilder("test", 100, 110).setMaxAllowedDuration(5).build();
        boolean expResult = true;
        boolean result = instance.isSLAViolation();
        assertEquals("Result is not right. Expected violation,", expResult, result);
    }
    
    /**
     * Test of isMaxAllowedDurationViolation method, of class CMeasurePoint.
     * We test when the duration is inside max allowed duration.
     */
    @Test
    public void testIsMaxAllowedDurationViolationFalse_1() {
        System.out.println("isMaxAllowedDurationViolationFalse_1");
        CMeasurePoint instance = new CMeasurePoint.CMeasurePointBuilder("test", 100, 110).setMaxAllowedDuration(11).build();
        boolean expResult = false;
        boolean result = instance.isSLAViolation();
        assertEquals("Result is not right. No violation expected,", expResult, result);
    }
    
    /**
     * Test of isMaxAllowedDurationViolation method, of class CMeasurePoint.
     * We test when the duration is same as the max allowed one. In this case it should still be ok.
     */
    @Test
    public void testIsMaxAllowedDurationViolationFalse_2() {
        System.out.println("isMaxAllowedDurationViolationFalse_2");
        CMeasurePoint instance = new CMeasurePoint.CMeasurePointBuilder("test", 100, 110).setMaxAllowedDuration(10).build();
        boolean expResult = false;
        boolean result = instance.isSLAViolation();
        assertEquals("Result is not right. No violation expected,", expResult, result);
    }
}
