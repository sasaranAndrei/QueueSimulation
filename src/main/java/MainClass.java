import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainClass {
    public static void main(String[] args) {
        int inputNoOfClients = 0, inputNoOfQueues = 0, inputMaxSimulationTime = 0, inputMinArrivalTime = 0, inputMaxArrivalTime = 0, inputMinServiceTime = 0, inputMaxServiceTime = 0;

        if (args.length != 2){
            System.out.println("Invalid number of arguments.");
            return;
        }
        try {
            FileReader fr = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(fr);
            String textIn;

            if (( textIn = br.readLine()) != null) {
                inputNoOfClients = Integer.parseInt(textIn);

                if ((textIn = br.readLine()) != null) {
                    inputNoOfQueues = Integer.parseInt(textIn);

                    if ((textIn = br.readLine()) != null) {
                        inputMaxSimulationTime = Integer.parseInt(textIn);

                        if ((textIn = br.readLine()) != null) {
                            int indexArrival = textIn.indexOf(",");
                            //System.out.println(index);

                            inputMinArrivalTime = Integer.parseInt(textIn.substring(0, indexArrival));
                            inputMaxArrivalTime = Integer.parseInt(textIn.substring(indexArrival + 1));

                            if ((textIn = br.readLine()) != null) {
                                int indexService = textIn.indexOf(",");

                                inputMinServiceTime = Integer.parseInt(textIn.substring(0, indexService));
                                inputMaxServiceTime = Integer.parseInt(textIn.substring(indexService + 1));

                            } else {
                                System.out.println("INVALID Service Time");
                                //return false;
                            }
                        } else {
                            System.out.println("INVALID Arrival Time");
                            //return false;
                        }
                    }
                    else {
                        System.out.println("INVALID Max Simulation Time");
                        //return false;
                    }
                }
                else {
                    System.out.println("INVALID No Of Queues");
                    //return false;
                }
            }
            else {
                System.out.println("INVALID No Of Clients");
                //return false;
            }

            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("!!! IO EXCEPTION !!!");
            e.printStackTrace();
        }

        Simulation simulation = new Simulation(inputNoOfClients, inputNoOfQueues, inputMaxSimulationTime, inputMinArrivalTime, inputMaxArrivalTime, inputMinServiceTime, inputMaxServiceTime);
        //System.out.println(simulation);
        simulation.run(args[1]);



    }

}
