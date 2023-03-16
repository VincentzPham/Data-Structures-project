import java.util.Scanner;
import java.util.Set;

import Linkedpack.PC;
import Linkedpack.PCqueue;

import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;

public class App {
public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    Map<String, PCqueue<PC>> queueMap = new HashMap<>();
    queueMap.put("pc1", new PCqueue<PC>());
    queueMap.put("pc2", new PCqueue<PC>());
    queueMap.put("pc3", new PCqueue<PC>());
    queueMap.put("pc4", new PCqueue<PC>());

    Map<PCqueue<PC>, Integer> sourcePortMap = new HashMap<>();
        Random rand = new Random();
        Set<Integer> usedPorts = new HashSet<>(); // keep track of used ports

        // assign unique random port numbers to each PC queue
        for (PCqueue<PC> queue : queueMap.values()) {
            int port;
            do {
                port = rand.nextInt(65535);
            } while (usedPorts.contains(port));
            usedPorts.add(port);
            sourcePortMap.put(queue, port);
        }

    long startTime = System.currentTimeMillis(); //record the start time //mehods of calling current time in millisecond

    PCqueue<PC> selectedQueue = null;

    String selection;
    do {
        System.out.println("\n----Message Management System----");
        System.out.println("List of pcs and their source ports:");
        queueMap.forEach((name, queue) -> {
            int sourcePort = sourcePortMap.getOrDefault(queue, -1);
            System.out.println(name + ": " + (sourcePort == -1 ? "No default source port set" : sourcePort));
        });
        System.out.println("1. Choose a pc\n2. Add a message\n" 
                            + "3. Show all messages in the pc\n4. Send messages to pcs\n" 
                            + "5. Exit application");
        System.out.print("Please enter your selection (1 -> 5): ");
        selection = input.nextLine();

        String queueName = "";
        switch(selection) {
        case "1":
            System.out.println("\nChoose a pc (pc1 -> pc4): ");
            
            queueName = input.nextLine();
            System.out.println("Choosing pc: " + queueName);
            selectedQueue = queueMap.get(queueName);
            if (selectedQueue == null) {
                System.out.println("Invalid queue name.");
            }
            break;
        case "2":
            if (selectedQueue != null) {
                System.out.println("\nInput your message's information here: ");
                int sourcePort = sourcePortMap.getOrDefault(selectedQueue, -1);
                if (sourcePort == -1) {
                    System.out.println("No default source port set for this queue.");
                    break;
                }
                try {
                System.out.print("\nInput destination port: ");
                int dp = input.nextInt();
                System.out.print("\nInput aknowledgement number: ");
                int an = input.nextInt();
                System.out.print("\nInput message: ");
                input.nextLine();
                String ms = input.nextLine();
                if (ms.trim().isEmpty()) {
                    System.out.println("Error: Message cannot be empty.");
                    break;
                }
                int sn = PC.letterCount(ms);
                PC message = new PC( sourcePort, dp, sn, an, ms);
                System.out.print("Adding message: " + message + "\n");
                selectedQueue.offer(message);
                } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.nextLine();
                }
            } else {
                System.out.println("Please choose a pc first.");
            }
            break;
        case "4":
        if (selectedQueue != null) {
            try {
            selectedQueue.transferMessages(selectedQueue, sourcePortMap, queueMap);
            } catch (Exception e) {
                System.out.println("An error occurred while transferring messages: " + e.getMessage());
            }
        } else {
            System.out.println("Please choose a pc first.");
        }
        break;
        case "3":
            if (selectedQueue != null) {
                System.out.println("\n\n SHOW THIS PS'S MESSAGES TO SEND AND RECIEVED MESSAGES");
                selectedQueue.showQueue();
            } else {
                System.out.println("Please choose a pc first.");
            }
            break;
        case "5":
            System.out.println("\nGoodbye.\n");
            break;
        default:
            System.out.println("\nYour selection is invalid\n");
            break;
        }
        long endTime = System.currentTimeMillis(); // record the end time 
        double time = (endTime - startTime)/10000.0; System.out.println("Runtime in seconds: "+time);
    } while(!selection.equals("5"));
}
}