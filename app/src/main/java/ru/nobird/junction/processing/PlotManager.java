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
    private final HistoryManager idealHistoryManager = new HistoryManager();
    private final HistoryManager actualHistoryManager = new HistoryManager();
    private final HistoryInterpolator idealHistoryInterpolator = new HistoryInterpolator(idealHistoryManager);
    private final HistoryInterpolator actualHistoryInterpolator = new HistoryInterpolator(actualHistoryManager);


    private final Object myLock = new Object();

    public PlotManager(PublishSubject<PlotData> targetSubject) {
        myTargetSubject = targetSubject;
        myPitchDetector = new PitchDetector(new PitchListener() {
            @Override
            public void onStrongBeat(long localTimestamp) {
                synchronized (myLock) {
                    //todo: write to history
                    actualHistoryManager.addToHistory(new HistoryData(localTimestamp, true));
                }
            }

            @Override
            public void onWeakBeat(long localTimestamp) {
                synchronized (myLock) {
                    //todo: write to history
                    actualHistoryManager.addToHistory(new HistoryData(localTimestamp, false));
                }
            }
        });

        beatGenerator = new BeatGenerator(30, 1, new PitchListener(){
            @Override
            public void onStrongBeat(long localTimestamp) {
                synchronized (myLock) {
                    HistoryData data = new HistoryData(localTimestamp, true);
                    idealHistoryManager.addToHistory(data);
                    MetronomeSounds.INSTANCE.playStrong();
                }
            }

            @Override
            public void onWeakBeat(long localTimestamp) {
                synchronized (myLock) {
                    HistoryData data = new HistoryData(localTimestamp, false);
                    idealHistoryManager.addToHistory(data);
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
            actualHistoryManager.update(currentTimestamp);
            idealHistoryManager.update(currentTimestamp);
            actualHistoryInterpolator.update(currentTimestamp);
            idealHistoryInterpolator.update(currentTimestamp);
            float statistic1 = actualHistoryManager.countStatistic(idealHistoryManager);
            float statistic2 = idealHistoryManager.countStatistic(actualHistoryManager);
            myTargetSubject.onNext(new PlotData(
                    actualHistoryInterpolator.getMagnitude(), idealHistoryInterpolator.getMagnitude(),
                    1.0f - Math.sqrt(statistic1 * statistic2)));
        }
    }
}
