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
package si.kobalj.stopwatch.model;

/**
 * Interface with methods that need to be implemented in the implementation class for the stopwatch.
 * @author Jure Kobal
 * @author Matej Korošec
 */
public interface IStopWatch {

    public static final String GLOBAL_MARK = "GLOBAL";
    
    /**
     * Start measuring of the global measure point.
     */
    public void startGlobal();
    
    /**
     * Stop measuring of the global measure point.
     */
    public void stopGlobal();
    
    /**
     * Start measuring of the specified measure point.
     * @param pMeasurePointName  Name of the measure point
     */
    public void start(String pMeasurePointName);
    
    /**
     * Stop measuring of the specified measure point.
     * @param pMeasurePointName Name of the measure point
     */
    public void stop(String pMeasurePointName);
}
