package ru.nobird.junction.processing;

/**
 * Created by Owntage on 11/25/2017.
 */

public class HistoryInterpolator implements Updatable {
    private static final long LATENCY_MS = 200;
    private static final long HOLD_MS = 200;
    private static final float F_HOLD_MS = 200.0f;
    private static final float STRONG_MAGNITUDE = 1.0f;
    private static final float WEAK_MAGNITUDE = 0.5f;

    private float myTargetMagnitude = 0;
    private float myCurrentMagnitude = 0;

    private final HistoryManager myHistoryManager;



    public HistoryInterpolator(HistoryManager historyManager) {
        myHistoryManager = historyManager;
    }

    public float getMagnitude() {
        return myCurrentMagnitude;
    }

    @Override
    public void update(long timestampMs) {

        long historyTimestamp = timestampMs - LATENCY_MS;
        HistoryManager.HistoryData foundData = myHistoryManager.getNewerThan(historyTimestamp);
        long dt = foundData.timestamp - historyTimestamp;
        float currentMagRatio = (float) dt;
        currentMagRatio = Math.abs(currentMagRatio);
        currentMagRatio /= F_HOLD_MS;
        currentMagRatio = Math.min(0.5f, 1.0f - currentMagRatio);

        myCurrentMagnitude = myCurrentMagnitude * currentMagRatio +
                myTargetMagnitude * (1.0f - currentMagRatio);
    }
}
