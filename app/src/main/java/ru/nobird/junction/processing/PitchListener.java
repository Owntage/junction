package ru.nobird.junction.processing;

/**
 * Created by Owntage on 11/25/2017.
 */

public interface PitchListener {
    void onStrongBeat(long localTimestamp);
    void onWeakBeat(long localTimestamp);
}
