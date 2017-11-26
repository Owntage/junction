package ru.nobird.junction.processing;

import io.reactivex.subjects.PublishSubject;
import ru.nobird.junction.model.PlotData;
import ru.nobird.junction.model.Vec3f;

/**
 * Created by Owntage on 11/25/2017.
 */

public class PlotManager {
    private static final long PLOT_UPDATE_PERIOD_MS = 16L;
    private final PublishSubject<PlotData> myTargetSubject;
    private final PingListener myPingListener;

    public PlotManager(PublishSubject<PlotData> targetSubject, PingListener pingListener) {
        myTargetSubject = targetSubject;
        myPingListener = pingListener;
    }

    public SensorDataListener getRealListener() {
        return new SensorDataListener() {
            @Override
            public void onLinearAcceleration(Vec3f acceleration, long timestamp) {
                //todo: finish
            }

            @Override
            public void onAngularAcceleration(Vec3f acceleration, long timestamp) {
                //todo: finish
            }
        };
    }

    public SensorDataListener getIdealListener() {
        return new SensorDataListener() {

            @Override
            public void onLinearAcceleration(Vec3f acceleration, long timestamp) {
                //todo: finish
            }

            @Override
            public void onAngularAcceleration(Vec3f acceleration, long timestamp) {
                //todo: finish
            }
        };
    }

    public void update(long currentTimestamp) {
        //todo:
    }
}
