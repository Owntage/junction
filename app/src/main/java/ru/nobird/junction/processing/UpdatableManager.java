package ru.nobird.junction.processing;

import java.util.ArrayList;

/**
 * Created by Owntage on 11/25/2017.
 */

public class UpdatableManager {
    public static final long TIMER_PERIOD = 10L;

    private final ArrayList<Updatable> myUpdatables = new ArrayList<>();

    public void update() {
        for (Updatable updatable : myUpdatables) {
            updatable.update(System.currentTimeMillis());
        }
    }

    public void addActor(Updatable updatable) {
        myUpdatables.add(updatable);
    }
}
