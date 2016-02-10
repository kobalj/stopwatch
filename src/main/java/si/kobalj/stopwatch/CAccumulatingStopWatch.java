package si.kobalj.stopwatch;

import si.kobalj.stopwatch.model.IInternalStopWatch;
import si.kobalj.stopwatch.model.IMeasurePoint;
import si.kobalj.stopwatch.model.IStopWatch;
import static si.kobalj.stopwatch.model.IStopWatch.GLOBAL_MARK;
import si.kobalj.stopwatch.model.IStopWatchBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Matej Korošec
 */
public class CAccumulatingStopWatch implements IStopWatch, IInternalStopWatch {

    private final ConcurrentHashMap<String, Long> startTimes;
    private final Map<String, Long> maxAllowedDurations;
    private final ConcurrentHashMap<String, Long> elapsedTime;
    private final Collection<IStopWatchBuilder.Option> options;

    protected CAccumulatingStopWatch(CStopWatchBuilder pStopWatchBuilder) {
        this.options = pStopWatchBuilder.getOptions();
        this.startTimes = new ConcurrentHashMap<>();
        this.maxAllowedDurations = pStopWatchBuilder.getMaxAllowedDurations();
        this.elapsedTime = new ConcurrentHashMap<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        boolean first = true;
        for (Entry<String, Long> p : elapsedTime.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(';');
            }
            sb.append(p.getKey()).append(':').append(p.getValue());
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public void start(String pMeasurePointName) {
        this.startTimes.put(pMeasurePointName, System.currentTimeMillis());
    }

    @Override
    public void startGlobal() {
        start(GLOBAL_MARK);
    }

    @Override
    public void stop(String pMeasurePointName) {
        final long startTime = this.startTimes.get(pMeasurePointName);
        final long elapsed = System.currentTimeMillis() - startTime;

        elapsedTime.compute(pMeasurePointName, (String t, Long u) -> {
            if (u == null) {
                return elapsed;
            } else {
                return elapsed + u;
            }
        });
    }

    @Override
    public void stopGlobal() {
        stop(GLOBAL_MARK);
    }

    @Override
    public Collection<IMeasurePoint> getMeasurePoints() {
        Collection<IMeasurePoint> result = new ArrayList<>(elapsedTime.size());
        for (Entry<String, Long> p : elapsedTime.entrySet()) {
            long maxdurration = (this.maxAllowedDurations.containsKey(p.getKey())) ? this.maxAllowedDurations.get(p.getKey()) : -1;
            CMeasurePoint mp = new CMeasurePoint.CMeasurePointBuilder(p.getKey(), p.getValue())
                    .setMaxAllowedDuration(maxdurration)
                    .build();

            result.add(mp);
        }
        return result;
    }

}
