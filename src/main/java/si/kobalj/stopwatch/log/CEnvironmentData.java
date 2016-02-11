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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import si.kobalj.stopwatch.model.IEnvironmentData;

/**
 * Class implementing IEnviromentData which purpose is to store environment data that was used in the 
 * process where the measurement was done.
 * @author Jure Kobal
 */
public class CEnvironmentData implements IEnvironmentData {

    private final Map<String, String> values;

    public CEnvironmentData() {
        this.values = new HashMap<>();
    }

    /**
     * Add another key/value pair to the environment data. 
     * @param pKey Name of the parameter
     * @param pValue Value of the parameter (the object needs to implement toString method)
     */
    public void addData(String pKey, Object pValue) {
        this.values.put(pKey, pValue.toString());
    }

    /**
     * Return the parameters and values defined for the environment.<br>
     * The returned values are represented in a key/value way.<br><br>
     *
     * The structure of the data is:<br>
     * key1=value1&amp;key2=value2<br><br>
     *
     * The seperator between every key/value pair is &amp; and key/value are sepperated by =.
     *
     * @return String containing all defined parameteres in a key/value structure.
     */
    @Override
    public String getDataAsString() {
        if (!this.values.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = this.values.entrySet().iterator();
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            while (iterator.hasNext()) {
                if (!isFirst) {
                    sb.append("&");
                } else {
                    isFirst = false;
                }
                Map.Entry<String, String> entry = iterator.next();
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
            return sb.toString();
        }
        return "NODATA";
    }
}
