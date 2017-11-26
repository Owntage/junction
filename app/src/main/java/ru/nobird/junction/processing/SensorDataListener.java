package ru.nobird.junction.processing;

import ru.nobird.junction.model.Vec3d;

/**
 * Created by Owntage on 11/25/2017.
 */

public interface SensorDataListener {
    void onLinearAcceleration(Vec3d acceleration, long timestamp);
    void onAngularAcceleration(Vec3d acceleration, long timestamp);
}
