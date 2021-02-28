public class Client implements Comparable{
    private int id;
    private int arrivalTime;
    private int serviceTime;
    private int waitingTime = 0;

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    @Override
    public String toString() {
        return  "("+id+","+arrivalTime+","+serviceTime+" / wTime = " + waitingTime + ")  ;";
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Client){
            Client comparableObject = (Client) o;
            if (arrivalTime < comparableObject.arrivalTime){ // 1a < 2a
                return -1;
            }
            else {
                if (arrivalTime == comparableObject.arrivalTime){
                    if (serviceTime < comparableObject.serviceTime){ // 1a = 2a si 1s < 2s
                        return -1;
                    }
                    else { // 1a = 2a si 1s > 2s
                        return 1;
                    }
                }
                else { // 1a > 2a
                    return 1;
                }
            }


        }
        else {
            return 0;
        }
    }


    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
}
