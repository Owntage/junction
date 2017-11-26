package ru.nobird.junction.processing;

import ru.nobird.junction.model.Vec3f;

/**
 * Created by Owntage on 11/25/2017.
 */

public class PitchDetector {
    private static final float G = 9.81f;
    private static final long DETECTION_BLOCK_TIME_MS = 100;
    private static final float LINEAR_STRONG_BEAT = 17.0f;
    private static final float LINEAR_WEAK_BEAT = 8.0f;

    private final PitchListener myPitchListener;

    private long myFirstLinearTimestamp;
    private long myAngularTimestamp;
    private long myPreviousAsyncCallTimestamp = -1;
    private long myPing;

    public PitchDetector(PitchListener pitchListener) {
        myPitchListener = pitchListener;
    }

    public SensorDataListener getSensorDataListener() {
        return new SensorDataListener() {
            @Override
            public void onLinearAcceleration(Vec3f acceleration, long timestamp) {
                float relAcc = Math.abs(G - countMagnitude(acceleration));
                boolean isStrongPitch = relAcc > LINEAR_STRONG_BEAT;
                boolean isWeakPitch = relAcc > LINEAR_WEAK_BEAT;

                long localTimestamp = getCurTime() - myPing;
                if ((isStrongPitch || isWeakPitch) &&
                        ((localTimestamp - myPreviousAsyncCallTimestamp > DETECTION_BLOCK_TIME_MS) ||
                        myPreviousAsyncCallTimestamp == -1)) {
                    if (isStrongPitch) {
                        myPitchListener.onStrongBeat(localTimestamp);
                    } else {
                        myPitchListener.onWeakBeat(localTimestamp);
                    }
                    myPreviousAsyncCallTimestamp = localTimestamp;
                }

            }

            @Override
            public void onAngularAcceleration(Vec3f acceleration, long timestamp) {
                //todo: use angular acceleration too
            }
        };
    }

    public PingListener getPingListener() {
        return new PingListener() {
            @Override
            public void onPingChanged(long newPing) {
                myPing = (myPing + newPing) / 2;
            }
        };
    }

    private float countMagnitude(Vec3f vec) {
        return (float) Math.sqrt(
                vec.getX() * vec.getX() +
                vec.getY() * vec.getY() +
                vec.getZ() * vec.getZ());
    }

    private long getCurTime() {
        return System.currentTimeMillis();
    }
}
