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

import si.kobalj.stopwatch.model.IMeasurePoint;

/**
 * Default implementation of IMeasurePoint.
 *
 * @author Jure Kobal
 */
public class CMeasurePoint implements IMeasurePoint {

    private final String name;
    private final long duration;
    private final long maxAllowedDuration;

    private CMeasurePoint(CMeasurePointBuilder pBuilder) {
        this.name = pBuilder.name;
        this.maxAllowedDuration = pBuilder.maxAllowedDuration;
        this.duration = pBuilder.duration;
    }

    /**
     * Returns the name of the measure point.
     *
     * @return Name of measure point.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the execution duration of the measure point.
     *
     * @return Time in milliseconds
     */
    @Override
    public long getDuration() {
        return this.duration;
    }

    /**
     * Returns status of SLA violation for the measure point.<br><br>
     *
     * Violation is calculated via the maximum allowed duration set in the builder and the measure point execution
     * duration.
     *
     * @return true if the measure point violated the SLA, else false.
     */
    @Override
    public boolean isSLAViolation() {
        return (maxAllowedDuration != -1) && this.duration > this.maxAllowedDuration;
    }

    /**
     * Builder class for CMeasurePoint.
     */
    public static class CMeasurePointBuilder {

        private final String name;
        private final long duration;
        private long maxAllowedDuration = -1;

        /**
         * Constructor with required parameters to generate the Builder.
         *
         * @param name Name of measure point.
         * @param startTime Start time of the execution.
         * @param endTime End time of the execution.
         */
        public CMeasurePointBuilder(String name, long startTime, long endTime) {
            this.name = name;
            this.duration = endTime - startTime;
        }

        /**
         * Constructor with required parameters to generate the Builder.
         * @param name Name of measure point.
         * @param duration Duration of execution
         */
        public CMeasurePointBuilder(String name, long duration) {
            this.name = name;
            this.duration = duration;
        }

        /**
         * Sets the max allowed duration. Only when we want to have the measure point checking for SLA violation.
         *
         * @param pMaxAllowedDuration Maximum duration in milliseconds.
         * @return This object
         */
        public CMeasurePointBuilder setMaxAllowedDuration(long pMaxAllowedDuration) {
            this.maxAllowedDuration = pMaxAllowedDuration;
            return this;
        }

        /**
         * Generates the CMeasurePoint object.
         *
         * @return Measure point
         * @see CMeasurePoint
         */
        public CMeasurePoint build() {
            return new CMeasurePoint(this);
        }
    }
}
