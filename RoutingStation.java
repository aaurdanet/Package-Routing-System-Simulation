package project1;

import java.util.Random;

public class RoutingStation implements Runnable {
    private int station_workload;
    private int station_number;
    private Conveyor in_conveyor;
    private Conveyor out_conveyor;

    public RoutingStation(int workload, int station_numb, Conveyor inbound_conveyor, Conveyor outbound_conveyor) {
        station_workload = workload;
        station_number = station_numb;
        in_conveyor = inbound_conveyor;
        out_conveyor = outbound_conveyor;

    }

    //Sleep Thread....Zz
    public void goToSleep() {
        try {
            Random rand = new Random();
            Thread.sleep(rand.nextInt(500));

        } catch (InterruptedException e) {
            System.out.println("Sleep Thread Error:" + e);
        }
    }

    public void doWork() {
        goToSleep();
        System.out.println("\n\033[92m### Routing Station S" + station_number + ": ### CURRENTLY HARD AT WORK MOVING PACKAGES. ###\033[0m\n");
        int i = station_workload;
        while( i!=1){
            i--;
            System.out.println("\033[38;5;173m▨ Routing Station S" + station_number + " Moving Package : " + i + " ▨\033[0m");
        }

        System.out.println("\n\033[92m### Routing Station S" + station_number + ": going offline – work completed! BYE! ###\033[0m\n");

    }


    public void run() {

        System.out.println("Routing Station S" + station_number + ": Has Total Workload of " + station_workload + " Package Groups.");
        System.out.println("\n\033[93m### ROUTING STATION S" + station_number + " Coming Online - Initializing Conveyors ###\033[0m\n");
        System.out.println("Routing Station S" + station_number + ": Input conveyor assigned to conveyor number C" + in_conveyor.conveyorID + ".");
        System.out.println("Routing Station S" + station_number + ": Output conveyor assigned to conveyor number C" + out_conveyor.conveyorID + ".");
        System.out.println("\n\033[92m### Routing Station S" + station_number + " workload set. Station S" + station_number + " has a total of " + station_workload + " to move ###\033[0m\n");
        System.out.println("\n\033[92m### ROUTING STATION S" + station_number + ": ONLINE & READY TO MOVE ###\033[0m\n");


        for (int i = 0; i < station_workload; i++) {
            boolean block_both = false;

            while (!block_both) {
                if (in_conveyor.lockConveyor()) {
                    System.out.println("\nRouting Station S" + station_number + ": Currently holds input conveyor lock C" + in_conveyor.conveyorID + ".");
                    if (out_conveyor.lockConveyor()) {
                        block_both = true;
                        System.out.println("Routing Station S" + station_number + ": Currently holds output conveyor lock C" + out_conveyor.conveyorID + ".");
                            doWork();
                            System.out.println("\033[34mS" + station_number + ": Lock Release Phase.\033[0m");
                            in_conveyor.unlockConveyor();
                            System.out.println("Routing Station S" + station_number + ": Unlocks/releases input conveyor C" + in_conveyor.conveyorID + ".");
                            out_conveyor.unlockConveyor();
                            System.out.println("Routing Station S" + station_number + ": Unlocks/releases output conveyor C" + out_conveyor.conveyorID + ".");
//                            goToSleep();


                    }else {
                        System.out.println("\n\033[91mRouting Station S" + station_number + ": UNABLE TO LOCK OUTPUT CONVEYOR C" + out_conveyor.conveyorID + ".\n" +
                                "SYNCHRONIZATION ISSUE:\n" +
                                "Station S" + out_conveyor.conveyorID + " currently holds the lock on output conveyor C" + out_conveyor.conveyorID + " – Station S" + station_number + " releasing\n" +
                                "lock on input conveyor C" + in_conveyor.conveyorID + "\033[0m\n");
                        in_conveyor.unlockConveyor();
                        goToSleep();
                }
                }

            }

        }


    }
}








