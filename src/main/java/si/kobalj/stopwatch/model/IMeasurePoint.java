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
 * Interface to define required methods for every measure point.
 * @author Jure Kobal
 */
public interface IMeasurePoint {

    /**
     * Name of the measure point.
     *
     * @return Name
     */
    public String getName();

    /**
     * Returns the execution duration for the measure point.
     *
     * @return Time in milliseconds
     */
    public long getDuration();

    /**
     * Returns if the measure point violated the SLA.
     *
     * @return true if SLA is violated, else false
     */
    public boolean isSLAViolation();
}
