package ru.nobird.junction.processing;

/**
 * Created by Owntage on 11/25/2017.
 */

public class BeatGenerator implements Updatable {

    private final long myInterval;
    private final int myOffBeats;
    private final PitchListener myPitchListener;

    private int myBeatCounter = -1;
    private long myLastBeatTimestamp = -1;


    public BeatGenerator(int bpm, int offBeats, PitchListener pitchListener) {
        float f_bpm = (float) bpm;
        myOffBeats = offBeats;
        myPitchListener = pitchListener;

        f_bpm /= 60.0f; //beats per seconds
        f_bpm = 1.0f / f_bpm; //beat period
        f_bpm /= (offBeats + 1); //time between clicks
        myInterval = (long) (f_bpm * 1000.0f);
    }

    @Override
    public void update(long timestampMs) {
        if (myLastBeatTimestamp == -1 || timestampMs - myLastBeatTimestamp > myInterval) {
            myLastBeatTimestamp = timestampMs;
            myBeatCounter = (myBeatCounter + 1) % (myOffBeats + 1);
            if (myBeatCounter == 0) {
                myPitchListener.onStrongBeat(timestampMs);
            } else {
                myPitchListener.onWeakBeat(timestampMs);
            }
        }
    }
}
