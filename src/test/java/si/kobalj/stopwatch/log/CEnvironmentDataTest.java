/*
 * Copyright (C) 2016 Jure Kobal
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

/**
 *
 * @author Jure Kobal
 */
public class CEnvironmentDataTest {

    public CEnvironmentDataTest() {
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
     * Test of addData method, of class CEnvironmentData. Test for when there is no data.
     */
    @Test
    public void testNoData() {
        System.out.println("noData");
        CEnvironmentData instance = new CEnvironmentData();

        String s = instance.getDataAsString();
        assertEquals("Result is not as required", s, "NODATA");
    }

    /**
     * Test of addData method, of class CEnvironmentData. Test for when there is no data.
     */
    @Test
    public void testData() {
        System.out.println("data");
        CEnvironmentData instance = new CEnvironmentData();
        instance.addData("test", "Test");
        instance.addData("key", "value");
        
        
        String s = instance.getDataAsString();
        assertEquals("Result is not as required", s, "test=Test&key=value");
    }
}
