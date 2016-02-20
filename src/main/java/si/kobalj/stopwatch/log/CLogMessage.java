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

import si.kobalj.stopwatch.model.ILogMessage;
import java.util.logging.Level;

/**
 * Implementation of ILogMessage
 * @author Jure Kobal
 */
public class CLogMessage implements ILogMessage {

    private final Level level;
    private final String message;

    public CLogMessage(Level level, String message) {
        this.level = level;
        this.message = message;
    }

    /**
     * Returns the logging level of the message.
     * @return Level
     */
    @Override
    public Level getLevel() {
        return this.level;
    }

    /**
     * Return the generated messge for logging.
     * @return Message containing metrics and environment data.
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
