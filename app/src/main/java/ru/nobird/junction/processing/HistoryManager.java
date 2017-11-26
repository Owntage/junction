package ru.nobird.junction.processing;

import java.util.LinkedList;

/**
 * Created by Owntage on 11/25/2017.
 */

public class HistoryManager implements Updatable {
    public static final long HISTORY_LENGTH_MS = 15_000;

    private final LinkedList<HistoryData> myHistory = new LinkedList<>();

    public static class HistoryData {
        public final long timestamp;
        public final boolean strong;

        public HistoryData(long timestamp, boolean strong) {
            this.timestamp = timestamp;
            this.strong = strong;
        }
    }

    public void addToHistory(HistoryData data) {
        myHistory.addLast(data);
    }

    public boolean hasOlderThan(long timestampMs) {
        if (myHistory.size() == 0) return false;
        return myHistory.getFirst().timestamp < timestampMs;
    }

    public boolean hasNewerThan(long timestampMs) {
        if (myHistory.size() == 0) return false;
        return myHistory.getLast().timestamp > timestampMs;
    }

    public HistoryData getInRange(long timestampMs, long delta) {
        for (HistoryData data : myHistory) {
            if (Math.abs(data.timestamp - timestampMs) <= delta) {
                return data;
            }
        }
        return null;
    }

    public float countStatistic(HistoryManager idealHistory) {
        int idealSize = idealHistory.myHistory.size();
        int matchCounter = 0;
        for (HistoryData idealData : idealHistory.myHistory) {
            if (getInRange(idealData.timestamp, 300) != null) {
                matchCounter++;
            }
        }
        if (matchCounter == 0) return 1.0f;
        return 1.0f - (float) matchCounter / (float) idealSize;
    }

    public HistoryData getNewerThan(long timestampMs) {
        HistoryData bestMatch = null;
        for (HistoryData data : myHistory) {
            if (data.timestamp >= timestampMs) {
                if (bestMatch == null) bestMatch = data;
                if (data.timestamp < bestMatch.timestamp) {
                    bestMatch = data;
                }
            }
        }
        if (bestMatch == null) {
            bestMatch = myHistory.size() > 0 ? myHistory.getLast() : new HistoryData(0, false);
        }
        return bestMatch;
    }

    public HistoryData getOlderThan(long timestampMs) {
        HistoryData bestMatch = null;
        for (HistoryData data : myHistory) {
            if (data.timestamp <= timestampMs) {
                if (bestMatch == null) bestMatch = data;
                if (data.timestamp > bestMatch.timestamp) {
                    bestMatch = data;
                }
            }
        }
        if (bestMatch == null) {
            bestMatch = myHistory.size() > 0 ? myHistory.getFirst() : new HistoryData(0, false);
        }
        return bestMatch;
    }

    @Override
    public void update(long timestampMs) {
        if (myHistory.size() == 0) return;
        if (timestampMs - myHistory.getFirst().timestamp > HISTORY_LENGTH_MS) {
            myHistory.pollFirst();
        }
    }
}
