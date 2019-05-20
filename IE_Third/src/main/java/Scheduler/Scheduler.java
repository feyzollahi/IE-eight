package Scheduler;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    public Scheduler(){
        runTask();
    }
    public void runTask() {

        Calendar calendar = Calendar.getInstance();





        Timer time = new Timer(); // Instantiate Timer Object

        // Start running the task on Monday at 15:40:00, period is set to 8 hours
        // if you want to run the task immediately, set the 2nd parameter to 0

        time.schedule(new CustomTask(), calendar.getTime(), TimeUnit.MINUTES.toMillis(5));
        time.schedule(new CheckWinningBidTask(), calendar.getTime(), TimeUnit.MINUTES.toMillis(1));

    }
}
