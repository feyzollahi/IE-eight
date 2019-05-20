package Scheduler;

import model.Exceptions.DupEndorse;
import model.Repo.GetRepo;

import java.util.TimerTask;

public class CustomTask extends TimerTask {

    public CustomTask(){

        //Constructor

    }

    public void run() {
        try {

            GetRepo.setRepo();
            GetRepo.print("run!!!!!!!");

        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        } catch (DupEndorse dupEndorse) {
            dupEndorse.printStackTrace();
        }
    }
}
