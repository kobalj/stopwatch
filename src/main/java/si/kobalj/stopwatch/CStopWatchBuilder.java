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

import si.kobalj.stopwatch.model.IStopWatch;
import si.kobalj.stopwatch.model.IStopWatchBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Matej Korošec
 * @author Jure Kobal
 */
public class CStopWatchBuilder implements IStopWatchBuilder {

    private final Map<String, Long> maxAllowedDurations;
    private final Set<IStopWatchBuilder.Option> options;

    public CStopWatchBuilder() {
        this.maxAllowedDurations = new HashMap<>();
        this.options = new TreeSet<>();
    }

    /**
     * Method to set the maximum duration time for the global measure point. Needed to check if the measure point is
     * violation the defined SLA.
     *
     * @param pMaxAllowedDurationTime Maximum execution time before it is SLA violation
     * @return This object
     */
    @Override
    public IStopWatchBuilder setGlobalSLA(long pMaxAllowedDurationTime) {
        this.maxAllowedDurations.put(IStopWatch.GLOBAL_MARK, pMaxAllowedDurationTime);
        return this;
    }

    /**
     * Method to set the maximum duration time for the specified measure point. Needed to check if the measure point is
     * violation the defined SLA.
     *
     * @param pMeasurePointName Name of the measure point
     * @param pMaxExecutionTime Maximum execution time before it is SLA violation
     * @return This object
     */
    @Override
    public IStopWatchBuilder setSLA(String pMeasurePointName, long pMaxExecutionTime) {
        this.maxAllowedDurations.put(pMeasurePointName, pMaxExecutionTime);
        return this;
    }

    /**
     * Builds the required IStopWatch.
     *
     * @return CStopWatch with defined parameters
     */
    @Override
    public IStopWatch build() {
        if (options.contains(IStopWatchBuilder.Option.ACCUMULATE_SAME_TAGS)) {
            return new CAccumulatingStopWatch(this);
        } else {
            return new CStopWatch(this);
        }
    }

    @Override
    public IStopWatchBuilder enable(IStopWatchBuilder.Option option) {
        options.add(option);
        return this;
    }

    public Map<String, Long> getMaxAllowedDurations() {
        return maxAllowedDurations;
    }

    public Set<Option> getOptions() {
        return options;
    }

}
