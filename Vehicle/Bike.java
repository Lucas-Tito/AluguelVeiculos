package Vehicle;

public class Bike extends Vehicle {
  
  public Bike(int id, String name, float dailyRate){

    super(id, name, dailyRate);
    type = "Moto";

  }
  public Bike(int id, String nome, float dailyRate, boolean isRented){
    super(id, nome, dailyRate,isRented);
    type = "Moto";
  }
}