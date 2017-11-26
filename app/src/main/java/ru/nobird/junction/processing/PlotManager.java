package ru.nobird.junction.processing;

import io.reactivex.subjects.PublishSubject;
import ru.nobird.junction.model.PlotData;
import ru.nobird.junction.model.Vec3f;
import ru.nobird.junction.processing.HistoryManager.HistoryData;
import ru.nobird.junction.sound.MetronomeSounds;

/**
 * Created by Owntage on 11/25/2017.
 */

public class PlotManager {
    private static final long PLOT_UPDATE_PERIOD_MS = 16L;
    private final PublishSubject<PlotData> myTargetSubject;
    private final PitchDetector myPitchDetector;

    private final BeatGenerator beatGenerator;
    private final HistoryManager historyManager = new HistoryManager();
    private final HistoryInterpolator historyInterpolator = new HistoryInterpolator(historyManager);

    private final Object myLock = new Object();

    public PlotManager(PublishSubject<PlotData> targetSubject) {
        myTargetSubject = targetSubject;
        myPitchDetector = new PitchDetector(new PitchListener() {
            @Override
            public void onStrongBeat(long localTimestamp) {
                synchronized (myLock) {
                    //todo: write to history
                }
            }

            @Override
            public void onWeakBeat(long localTimestamp) {
                synchronized (myLock) {
                    //todo: write to history
                }
            }
        });

        beatGenerator = new BeatGenerator(60, 1, new PitchListener(){
            @Override
            public void onStrongBeat(long localTimestamp) {
                synchronized (myLock) {
                    HistoryData data = new HistoryData(localTimestamp, true);
                    historyManager.addToHistory(data);
                    MetronomeSounds.INSTANCE.playStrong();
                }
            }

            @Override
            public void onWeakBeat(long localTimestamp) {
                synchronized (myLock) {
                    HistoryData data = new HistoryData(localTimestamp, false);
                    historyManager.addToHistory(data);
                    MetronomeSounds.INSTANCE.playWeak();
                }
            }
        });
    }

    public SensorDataListener getRealListener() {
        return myPitchDetector.getSensorDataListener();
    }

    public PingListener getPingListener() {
        return myPitchDetector.getPingListener();
    }

    public void update(long currentTimestamp) {
        synchronized (myLock) {
            beatGenerator.update(currentTimestamp);
            historyManager.update(currentTimestamp);
            historyInterpolator.update(currentTimestamp);
            myTargetSubject.onNext(new PlotData(0, historyInterpolator.getMagnitude(), 1));
        }
    }
}
