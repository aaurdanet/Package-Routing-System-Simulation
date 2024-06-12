/*
Name: Antonio Urdaneta
*/


package project1;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        ArrayList<Integer> instructions = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("C:/Users/anton/IdeaProjects/PackageSys/out/production/PackageSys/project1/config.txt"));
            String line = reader.readLine();

            while (line != null) {
                instructions.add(Integer.parseInt(line));
                // read next line
                line = reader.readLine();
            }

            int MAX_SIZE = instructions.get(0);
            int[] thread_workload_array = new int[MAX_SIZE];
            int[] conveyor_number = new int[MAX_SIZE];
            ExecutorService executor = Executors.newFixedThreadPool(MAX_SIZE);

            for (int i = 1; i < instructions.size(); i++) {
                //filling thread array with number of packets per index = stations
                thread_workload_array[i - 1] = instructions.get(i);
                //filling conveyor array with conveyors#
                conveyor_number[i - 1] = i - 1;
            }
            reader.close();
            System.out.println("\033[34m\nPacket Management Simulation Project:\033[0m\n");
            System.out.println(
                    "\n" +
                            "\033[34m  ___         _                   ___        _ _ _ _          ___ _           _      _           \n" +
                            " | _ \\__ _ __| |____ _ __ _ ___  | __|_ _ __(_) (_) |_ _  _  / __(_)_ __ _  _| |__ _| |_ ___ _ _ \n" +
                            " |  _/ _` / _| / / _` / _` / -_) | _/ _` / _| | | |  _| || | \\__ \\ | '  \\ || | / _` |  _/ _ \\ '_|\n" +
                            " |_| \\__,_\\__|_\\_\\__,_\\__, \\___| |_|\\__,_\\__|_|_|_|\\__|\\_, | |___/_|_|_|_\\_,_|_\\__,_|\\__\\___/_|  \n" +
                            "                      |___/                            |__/                                      \n" +
                            "\033[0m\n\n"
            );
            System.out.println("The parameters for this simulation are:\n");

            int conveyors_stations_number = MAX_SIZE;


            RoutingStation threads[] = new RoutingStation[conveyors_stations_number];
            Conveyor conveyors[] = new Conveyor[conveyors_stations_number];

            //Creates conveyor object array
            for (int i = 0; i < conveyors_stations_number; i++) {
                conveyors[i] = new Conveyor((conveyor_number[i]));
            }

            //Creates Station Threads and puts all the objects in the array
            for (int i = 0; i < conveyors_stations_number; i++) {
                int out_conveyor = output_conveyor_finder(conveyors_stations_number, conveyor_number[i]);
                threads[i] = new RoutingStation(thread_workload_array[i], conveyor_number[i], conveyors[i], conveyors[out_conveyor]);

            }

            try {
                //Starts each thread using executor
                for (int j = 0; j < conveyors_stations_number; j++) {
                    executor.execute(threads[j]);
                }
            } catch (Exception e) {
                System.out.println("Thread creation error: " + e.getMessage());
                e.printStackTrace();
            }

            executor.shutdown();

        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

    }

    // Finds output conveyor
    public static int output_conveyor_finder(int conveyor_station_number, int curr_station_number) {
        int input_conveyor = curr_station_number;
        int output_conveyor = (input_conveyor - 1 + conveyor_station_number) % conveyor_station_number;
        return output_conveyor;

    }
}




