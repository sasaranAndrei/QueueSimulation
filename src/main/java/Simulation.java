import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Simulation  {
    private int noOfClients;
    private int noOfQueues;
    private int maxSimulationTime;

    private int minArrivalTime;
    private int maxArrivalTime;
    private int minServiceTime;
    private int maxServiceTime;

    private int time;
    private ArrayList<Client> clients;
    private ArrayList<Queue> queues;

    private ArrayList<Client> nowInQueues = new ArrayList<Client>();



    public Simulation(int noOfClients, int noOfQueues, int maxSimulationTime, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime) {
        this.noOfClients = noOfClients;
        this.noOfQueues = noOfQueues;
        this.maxSimulationTime = maxSimulationTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;

        this.time = 0;
        // generam clientii
        clients = new ArrayList<Client>(noOfClients);
        Random randomGenerator = new Random();
        int arrivalTime = -1, serviceTime = -1;
        for (int i = 1; i <= noOfClients; i++) {
            arrivalTime = randomGenerator.nextInt((maxArrivalTime - minArrivalTime + 1)) + minArrivalTime; // returns number beetween [0..M-m] + m => [m..M]
            serviceTime = randomGenerator.nextInt((maxServiceTime - minServiceTime + 1)) + minServiceTime;

            Client newClient = new Client(i, arrivalTime, serviceTime);
            clients.add(newClient);
        }
        Collections.sort(clients);
        // si cozile
        queues = new ArrayList<Queue>();
        for (int i = 0; i < this.noOfQueues; i++){
            queues.add(new Queue());
        }
    }


    @Override
    public String toString() {
        String result = "";
        result += "Clients : " + noOfClients + "\n";
        result += "Queues : " + noOfQueues + "\n";
        result += "MAX Simulation : " + maxSimulationTime + "\n";
        result += "MIN Arrival Time : " + minArrivalTime + "\n";
        result += "MAX Arrival Time : " + maxArrivalTime + "\n";
        result += "MIN Service Time : " + minServiceTime + "\n";
        result += "MAX Service Time : " + maxServiceTime + "\n";
        result += "Clients:\n";
        for (Client client : clients){
            result += client.toString() + "\n";
        }
        result += "\n";
        return result;
    }

    private Queue returnBestQueue (ArrayList<Queue> queues){
        if (queues.size() == 0){
            System.out.println("nu exista cozi wtf");
            return null;
        }
        Queue bestQueue = queues.get(0);
        for (Queue queue : queues){
            if (queue.compareTo(bestQueue) < 0){
                bestQueue = queue;
            }
        }
        return bestQueue;
    }



    public boolean simulationIsOver (){
        if (time > maxSimulationTime){
            return true;
        }
        return false;
    }

    public void prepareQueues() {
        if (simulationIsOver()) {
            System.out.println("Simulation is over");
            return;
        }

        for (Client client : clients){
            if (client.getArrivalTime() == time){
                Queue bestQueue = returnBestQueue(queues);
                bestQueue.addClient(client);
                nowInQueues.add(client);
            }
        }

        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (Queue queue : queues){
            if (queue.getClientsQueue().size() > 0){ // if queue is not empty
                Thread thread = new Thread(queue);
                threads.add(thread);
                thread.start();
            }
        }

        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        time++;
    }

    public String simulationStep (){
        String result = "";
        result = result + "Time : " + time + "\n";
        result = result + "Waiting clients : ";

        ArrayList<Client> waitingList = (ArrayList<Client>)clients.clone();
        waitingList.removeAll(nowInQueues);

        for (Client client : waitingList){
            result += client;
        }
        result += "\n";
        int i = 1;
        for (Queue queue : queues){
            String q = "Queue " + i + ": ";
            if (queue.getClientsQueue().size() == 0){
                q += "CLOSED\n";
            }
            else {
                q += queue + "\n";

            }
            result += q;
            i++;
        }
        result += "---------------------\n";
        return result;
    }

    public double computeAverageWaitingTime (){
        double result = 0;
        for (Client client : clients){
            result += client.getWaitingTime();
        }
        result /= clients.size();
        return result;
    }

    public void run (String outputFileName){

        try {
            FileWriter fw = new FileWriter(outputFileName);
            PrintWriter pw = new PrintWriter(fw);

            //System.out.println(simulationStep());
            pw.println(this.toString());

            while (simulationIsOver() == false){
                prepareQueues();
                //System.out.println(simulationStep());
                pw.println(simulationStep());
            }

            //System.out.println("Average : " + computeAverageWaitingTime());
            pw.println("Average waiting time : " + computeAverageWaitingTime());

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


}


        /* // asta ceva ceva mere
        Queue q1 = new Queue(simulation.noOfClients);
        Queue q2 = new Queue(simulation.noOfClients);

        simulation.queues.add(q1);
        simulation.queues.add(q2);
        ArrayList<Client> noMoreWaiting = new ArrayList<>();

        while (time < simulation.maxSimulationTime){
            for (Iterator<Client> i = simulation.clients.iterator(); i.hasNext();) {
                Client client = i.next();
                if (client.getArrivalTime() == time) {
                    putClientInBestQueue(client, simulation.queues);
                    noMoreWaiting.add(client);
                    //System.out.println(client);
                    //simulation.clients.remove(client);
                    //simulation.printWaitingList();
                }
            }
            System.out.println("to remove at : " + time);
            System.out.println(noMoreWaiting);
            simulation.clients.removeAll(noMoreWaiting);

            // print queue
            for (Queue queue : simulation.queues){
                System.out.println("Queue :");
                System.out.println(queue.getClientsQueue());
            }
            // print waiting list
            System.out.println("Waiting list:");
            System.out.println(simulation.clients);
            System.out.println();
            time++;
        }
        */

//ArrayList<Client> clientsToPutInQueues = new ArrayList<Client>();

    /*
        while (simulation.timeIsOver() == false && simulation.waitingQueueisEmpty() == false) {
            /////////// nebunia cu threaduri

            ArrayList<Thread> multipleQueues = new ArrayList<>(simulation.noOfQueues);
            for (int i = 0; i < multipleQueues.size(); i++) {
                multipleQueues.set(i, new Thread(simulation.queues.get(i)));
                multipleQueues.get(i).start();
            }

            for (Thread queue : multipleQueues){
                try {
                    queue.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ///////////
            // in momentu asta ar trebui sa sortez cozile ca sa fie cele mai pont primele.
            /////

            //int coadaCurentaInCareLBag = 0;

            for (Iterator<Client> i = simulation.clients.iterator(); i.hasNext();) {
                Client client = i.next();
                if (client.getArrivalTime() == time) {
                    putClientInBestQueue(client, simulation.queues);
                    simulation.clients.remove(client);
                    // il bag in coada  : coadaCurentaInCareLBag
                    // si incrementez coadaCurentaInCareLbag
                }
            }


            time++;

        } */


        /*

        System.out.println("Waiting list:");
        simulation.printWaitingList();
        System.out.println("-------");
        time++;

        */




//simulation.queues

