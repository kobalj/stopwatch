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

import java.util.Collection;

/**
 * Definition of methods needed in CStopWatch that are needed for internal work of the system.
 *
 * @author Jure Kobal
 */
public interface IInternalStopWatch {

    /**
     * Return a list of all measure points stored in the system.
     * @return List of IMeasurePoint
     * @see IMeasurePoint
     */
    public Collection<IMeasurePoint> getMeasurePoints();
}
