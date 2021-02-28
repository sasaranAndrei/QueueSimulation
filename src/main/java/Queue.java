import java.util.ArrayList;

public class Queue extends Thread implements Comparable {


    private ArrayList<Client> clientsQueue ;

    //private static int countQueues = 0;

    public Queue() {
        clientsQueue = new ArrayList<Client>();
    }

    public ArrayList<Client> getClientsQueue() {
        return clientsQueue;
    }

    // first client (who is served) on position size - 1
    // last client (who has just arrived) on position 0
    public void addClient (Client client){
        clientsQueue.add(0, client);
    }

    public void removeFirstClient (){
        if (clientsQueue.size() > 0){
            clientsQueue.remove(clientsQueue.size() - 1);
        }
        else {
            System.out.println("Empty queue");
        }
    }

    public Client peek () {
        if (clientsQueue.size() > 0) {
            return clientsQueue.get(clientsQueue.size() - 1);
        } else {
            System.out.println("Empty queue");
            return null;
        }
    }

    int computeServiceTime (){
        int totalServiceTime = 0;
        for (Client client : clientsQueue){
            totalServiceTime += client.getServiceTime();
        }
        return totalServiceTime;
    }


    public void serve () {
        // check if the queue is empty
        if (clientsQueue.size() > 0) {
            Client firstClient = peek();
            // check if the man in front was served
            if (firstClient.getServiceTime() == 1) { //if he was served,
                removeFirstClient();
            }
            else { // decrementing service time
                firstClient.setServiceTime(firstClient.getServiceTime() - 1);
                // and increase the waiting time of all clients
                for (Client client : clientsQueue){
                    client.setWaitingTime(client.getWaitingTime() + 1);
                }
            }
        }

    }

    @Override
    public synchronized void start() {
        serve();
    }

    @Override
    public void run() {
        serve();
    }

    @Override
    public String toString() {
        String result = "";
        for (Client client : clientsQueue){
            result += client;
        }
        return result;
    }

    @Override
    public int compareTo(Object o) {
        Queue comparableQueue = (Queue) o;
        int currentQueueServiceTime = this.computeServiceTime();
        int comparableQueueServiceTime = comparableQueue.computeServiceTime();

        if (currentQueueServiceTime < comparableQueueServiceTime){
            return -1;
        }
        if (currentQueueServiceTime == comparableQueueServiceTime){
            return 0;
        }
        else {
            return 1;
        }
    }


}
