package ru.nobird.junction.processing;

import ru.nobird.junction.model.Vec3f;

/**
 * Created by Owntage on 11/25/2017.
 */

public interface SensorDataListener {
    void onLinearAcceleration(Vec3f acceleration, long timestamp);
    void onAngularAcceleration(Vec3f acceleration, long timestamp);
}
