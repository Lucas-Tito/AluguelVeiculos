import Client.Client;

import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Controller controller = new Controller();
    controller.fileDataToMemory();

    while (true) {
        String line = scanner.nextLine();
        System.out.println("$" + line);
        String ui[] = line.split(" ");
        if (line.equals("end")) {
          break;
        } else if (ui[0].equals("addClient")) {//name, age, driver license type
          controller.addClient(new Client(ui[1], Integer.parseInt(ui[2]), ui[3]));
        } else if (ui[0].equals("removeClient")){// name
          controller.removeClient(ui[1]);
        } else if (ui[0].equals("listClients")) {
          controller.listClients();
        } else if (ui[0].equals("updateClient")){
          //g.editClient();
        } else if (ui[0].equals("listVehicles")){
          controller.listVehicles();
        } else if (ui[0].equals("removeVehicle")) {//id
          controller.removeVehicle(Integer.parseInt(ui[1]));
        } else if (ui[0].equals("addVehicle")){//vehicle type, name, daily rate
          controller.addVehicle(ui[1],ui[2],Float.parseFloat(ui[3]));
        } else if (ui[0].equals("updateVehicle")) {
          //controller.updateVehicle();
        } else if (ui[0].equals("rent")) {// name and vehicle id
          controller.rent(ui[1], Integer.parseInt(ui[2]), false);
        } else if (ui[0].equals("deposit")){// name e value
          controller.deposit(ui[1],Float.parseFloat(ui[2]));
        }else if (ui[0].equals("dailyRate")){
          controller.dailyRateUpdate();
        }else if (ui[0].equals("returnVehicle")){// name
          controller.returnVehicle(ui[1]);
        }else if (ui[0].equals("tow")){// name
          controller.tow(ui[1]);
        }else if (ui[0].equals("clientsRenting")){
          controller.listClientsRenting();
        }        
        else {
          System.out.println("fail: command not found");
        }
    }
    scanner.close();
  }

}