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
 * Interface for the stopwatch builder.
 *
 * @author Jure Kobal
 * @author Matej Korošec
 */
public interface IStopWatchBuilder {

    enum Option {
        ACCUMULATE_SAME_TAGS
    }

    /**
     * Define the maximum duration for the global measure point.
     *
     * @param pMaxExecutionTime Maximum allowed execution time
     * @return Builder object
     */
    public IStopWatchBuilder setGlobalSLA(long pMaxExecutionTime);

    /**
     * Define the maximum duration for the specified measure point.
     *
     * @param pMeasurePointName Name of the measure point
     * @param pMaxExecutionTime Maximum allowed execution time
     * @return Builder object
     */
    public IStopWatchBuilder setSLA(String pMeasurePointName, long pMaxExecutionTime);

    public IStopWatchBuilder enable(Option option);
    
    /**
     * Build the stopwatch implementation
     *
     * @return Stopwatch implementation
     */
    public IStopWatch build();
}
