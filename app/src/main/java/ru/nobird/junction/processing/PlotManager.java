package ru.nobird.junction.processing;

import io.reactivex.subjects.PublishSubject;
import ru.nobird.junction.model.PlotData;
import ru.nobird.junction.model.Vec3d;

/**
 * Created by Owntage on 11/25/2017.
 */

public class PlotManager {
    private static final long PLOT_UPDATE_PERIOD_MS = 16L;
    private final PublishSubject<PlotData> myTargetSubject;

    public PlotManager(PublishSubject<PlotData> targetSubject) {
        myTargetSubject = targetSubject;
    }

    public SensorDataListener getRealListener() {
        return new SensorDataListener() {
            @Override
            public void onLinearAcceleration(Vec3d acceleration, long timestamp) {
                //todo: finish
            }

            @Override
            public void onAngularAcceleration(Vec3d acceleration, long timestamp) {
                //todo: finish
            }
        };
    }

    public SensorDataListener getIdealListener() {
        return new SensorDataListener() {

            @Override
            public void onLinearAcceleration(Vec3d acceleration, long timestamp) {
                //todo: finish
            }

            @Override
            public void onAngularAcceleration(Vec3d acceleration, long timestamp) {
                //todo: finish
            }
        };
    }

    public void update(long currentTimestamp) {
        //todo:
    }
}
