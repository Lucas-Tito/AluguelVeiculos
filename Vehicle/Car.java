package Vehicle;

public class Car extends Vehicle {
  
  public Car(int id, String name, float dailyRate){

    super(id,name, dailyRate);
    type = "Carro";
    
  }
  public Car(int id, String name, float dailyRate, boolean isRented){

    super(id,name, dailyRate, isRented);
    type = "Carro";

  }
}