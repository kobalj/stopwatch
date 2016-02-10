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

import java.util.ArrayList;
import si.kobalj.stopwatch.model.IInternalStopWatch;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import si.kobalj.stopwatch.model.IMeasurePoint;
import si.kobalj.stopwatch.model.IStopWatch;

/**
 * Class with methods to work with the stopwatch.
 *
 * @author Jure Kobal
 * @author Matej Korošec
 */
public class CStopWatch implements IStopWatch, IInternalStopWatch {

    private final HashMap<String, Long> startTimes;
    private final Map<String, Long> maxAllowedDurations;
    private final Collection<IMeasurePoint> measurePoints;

    protected CStopWatch(CStopWatchBuilder pStopWatchBuilder) {
        this.startTimes = new HashMap<>();
        this.maxAllowedDurations = pStopWatchBuilder.getMaxAllowedDurations();
        this.measurePoints = new ArrayList();
    }

    /**
     * Start measuring duration of the specified measure point.
     *
     * @param pMeasurePointName Name of the measure point
     */
    @Override
    public void start(String pMeasurePointName) {
        this.startTimes.put(pMeasurePointName, System.currentTimeMillis());
    }

    /**
     * Start measuring duration of the global measure point.
     */
    @Override
    public void startGlobal() {
        start(GLOBAL_MARK);
    }

    /**
     * Stop measuring duration of the specified measure point.<br><br>
     *
     * Method get the start time of the specified measure point, the end time and the SLA in case it is defined. With
     * that the CMeasurePoint is being created via CMeasurePointBuilder.
     *
     * @see CMeasurePoint
     * @see CMeasurePoint.CMeasurePointBuilder
     *
     * @param pMeasurePointName Name of the measure point
     */
    @Override
    public void stop(String pMeasurePointName) {
        long maxdurration = (this.maxAllowedDurations.containsKey(pMeasurePointName)) ? this.maxAllowedDurations.get(pMeasurePointName) : -1;
        long startTime = this.startTimes.get(pMeasurePointName);
        CMeasurePoint mp = new CMeasurePoint.CMeasurePointBuilder(pMeasurePointName, startTime, System.currentTimeMillis())
                .setMaxAllowedDuration(maxdurration)
                .build();
        this.measurePoints.add(mp);
    }

    /**
     * Stop measuring duration of the global measure point.
     */
    @Override
    public void stopGlobal() {
        stop(GLOBAL_MARK);
    }

    /**
     * Method returns all stored measure points. Needed for getting the data and any other work that has to be done on
     * the stored measure points
     *
     * @return Collection of measure points.
     */
    @Override
    public Collection<IMeasurePoint> getMeasurePoints() {
        return Collections.unmodifiableCollection(this.measurePoints);
    }
}
