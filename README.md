Package Routing System Simulation
Project Overview

This project simulates a package routing system for an automated package shipping operation using multi-threaded programming in Java. The main objective is to manage and synchronize multiple threads that handle package routing across various stations and conveyors.
Project Structure

Packages and Classes:

    project1.Main: Reads configuration data, initializes conveyors and routing stations, and manages thread execution.
    project1.Conveyor: Models a conveyor belt with locking mechanisms to control access.
    project1.RoutingStation: Represents a routing station that moves packages between conveyors, implementing the Runnable interface for multi-threading.

Main Class

Package: project1

Main Class: Main
Description

The Main class reads configuration data from a file, initializes conveyor and station objects, and uses a thread pool to manage the execution of these stations.
Key Components

    Configuration File Reading: Reads parameters from a file located at C:/Users/anton/IdeaProjects/PackageSys/out/production/PackageSys/project1/config.txt.
    Initialization:
        Reads the maximum number of stations (MAX_SIZE).
        Initializes thread_workload_array to hold the number of packets each station will handle.
        Initializes conveyor_number to identify each conveyor.
    Thread Pool: Uses ExecutorService to manage a fixed thread pool for executing station tasks.
    Output Conveyor Finder: Determines the output conveyor for each station using the output_conveyor_finder method.
    Station and Conveyor Initialization: Creates arrays of RoutingStation and Conveyor objects to represent the system's stations and conveyors, respectively.

Conveyor Class

Package: project1
Description

The Conveyor class models a conveyor belt in the package routing system. Each conveyor has a unique ID and uses a Lock to manage access, ensuring that multiple threads can interact with it in a thread-safe manner.
Key Components

    Fields:
        conveyorID: An integer representing the unique identifier of the conveyor.
        access_lock: A ReentrantLock object used to control access to the conveyor.

    Constructor:
        Conveyor(int conveyorID): Initializes the conveyorID and the access_lock.

    Methods:
        boolean lockConveyor(): Tries to acquire the lock on the conveyor. Returns true if the lock is acquired successfully, otherwise returns false. This method uses a timeout to prevent indefinite blocking.
        void unlockConveyor(): Releases the lock on the conveyor.

RoutingStation Class

Package: project1
Description

The RoutingStation class represents a station in the package routing system. Each station is responsible for moving a set number of packages between an input conveyor and an output conveyor. The class implements the Runnable interface, allowing each station to run as a separate thread.
Key Components

    Fields:
        station_workload: An integer representing the total number of package groups the station needs to move.
        station_number: An integer representing the unique identifier of the station.
        in_conveyor: A Conveyor object representing the input conveyor.
        out_conveyor: A Conveyor object representing the output conveyor.

    Constructor:
        RoutingStation(int workload, int station_numb, Conveyor inbound_conveyor, Conveyor outbound_conveyor): Initializes the station's workload, station number, and references to the input and output conveyors.

    Methods:
        void goToSleep(): Puts the thread to sleep for a random period up to 500 milliseconds.
        void doWork(): Simulates the station performing work by decrementing the workload and printing the current status.
        void run(): Contains the main logic for the station's operation, including acquiring locks on the conveyors, performing work, and releasing the locks.
