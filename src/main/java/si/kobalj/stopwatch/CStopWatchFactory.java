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

import si.kobalj.stopwatch.model.IStopWatchBuilder;

/**
 * Wraper class for generating new CStopWatchBuilder.
 * @author Jure Kobal
 * @author Matej Korošec
 */
public class CStopWatchFactory {

    public static IStopWatchBuilder getStopWatchBuilder() {
        return new CStopWatchBuilder();
    }
}
