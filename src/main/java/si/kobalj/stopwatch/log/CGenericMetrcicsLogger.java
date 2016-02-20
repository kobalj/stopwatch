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

import si.kobalj.stopwatch.model.ILogMessage;
import java.util.Collection;
import java.util.logging.Level;
import si.kobalj.stopwatch.model.IInternalStopWatch;
import java.util.logging.Logger;
import si.kobalj.stopwatch.model.IEnvironmentData;
import si.kobalj.stopwatch.model.IMeasurePoint;
import si.kobalj.stopwatch.model.IStopWatch;

/**
 * Implementation of generic logger class to log metrics and environment data.
 *
 * @author Jure Kobal
 */
public class CGenericMetrcicsLogger {

    private static final Logger LOGGER = Logger.getLogger("METRICS");
    private final boolean logSLAViolationsOnly;
    private final boolean logSLAViolationsAsWarning;

    public CGenericMetrcicsLogger(CGenericMetrcicsLoggerBuilder pBuilder) {
        this.logSLAViolationsOnly = pBuilder.logSLAViolationsOnly;
        this.logSLAViolationsAsWarning = pBuilder.logSLAViolationsAsWarning;
    }

    /**
     * Method to log performance metrics.
     *
     * @param pStopWatch Stopwatch with every stored measure point
     */
    public void log(IStopWatch pStopWatch) {
        log(pStopWatch, null);
    }

    /**
     * Method to log performance metrics. In addition to the metrics it requires additional environment data
     * (IEnvironmentData) that contains the parameters used in the execution process the stopwatch is being used in.
     *
     * @param pStopWatch Stopwatch with every stored measure point
     * @param pEnvironmentData Environment data used in execution process
     */
    public void log(IStopWatch pStopWatch, IEnvironmentData pEnvironmentData) {
        ILogMessage message = getMessage(pStopWatch, pEnvironmentData);

        if (message != null) {
            LOGGER.log(message.getLevel(), message.getMessage());
        }
    }

    public ILogMessage getMessage(IStopWatch pStopWatch, IEnvironmentData pEnvironmentData) {
        boolean hasData = false;
        boolean hasSLAViolations = false;

        IInternalStopWatch intStopWatch = (IInternalStopWatch) pStopWatch;
        Collection<IMeasurePoint> measurePoints = intStopWatch.getMeasurePoints();

        StringBuilder sb = new StringBuilder();
        sb.append("PERF_LOG: ");

        boolean isFirst = true;
        for (IMeasurePoint mp : measurePoints) {
            if (mp.isSLAViolation()) {
                hasSLAViolations = true;
            }
            hasData = true;
            if (!isFirst) {
                sb.append(";");
            } else {
                isFirst = false;
            }
            sb.append(mp.getName()).append("=").append(mp.getDuration()).append("ms");
        }

        if (pEnvironmentData != null) {
            sb.append(" || ENV_DATA: ");
            sb.append(pEnvironmentData.getDataAsString());
        }

        Level level = Level.INFO;
        if (logSLAViolationsAsWarning && hasSLAViolations) {
            level = Level.WARNING;
        }

        ILogMessage message = null;
        if (hasData
                || (hasSLAViolations && logSLAViolationsOnly && hasData)) {
            message = new CLogMessage(level, sb.toString());
        }

        return message;
    }

    public static class CGenericMetrcicsLoggerBuilder {

        private boolean logSLAViolationsOnly = false;
        private boolean logSLAViolationsAsWarning = false;

        /**
         * Method to define if we want to store only stopwatches that contain at least one measure point with SLA
         * violation.
         *
         * @param pLogSLAViolationsOnly true if only stopwatches that have measure points with SLA violations should be
         * stored, else false
         * @return This object
         */
        public CGenericMetrcicsLoggerBuilder setLogSLAViolationsOnly(boolean pLogSLAViolationsOnly) {
            this.logSLAViolationsOnly = pLogSLAViolationsOnly;
            return this;
        }

        /**
         * Method to define if we want to log stopwatches that contain at least one measure point with SLA violation as
         * WARRNING and not INFO logging level.
         *
         * @param pLogSLAViolationsAsWarning true if we want to store SLA violations as WARRNING, else false
         * @return This object
         */
        public CGenericMetrcicsLoggerBuilder setLogSLAViolationsAsWarning(boolean pLogSLAViolationsAsWarning) {
            this.logSLAViolationsAsWarning = pLogSLAViolationsAsWarning;
            return this;
        }

        /**
         * Building the logger class.
         *
         * @return Generated CGenericMetrcicsLogger
         */
        public CGenericMetrcicsLogger build() {
            return new CGenericMetrcicsLogger(this);
        }
    }
}
