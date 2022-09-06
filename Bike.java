class Bike extends Vehicle {
  
  Bike(int id, String name, float dailyRate){

    super(id, name, dailyRate);
    type = "Moto";

  }
  Bike(int id, String nome, float dailyRate, boolean alugado){
    super(id, nome, dailyRate,alugado);
    type = "Moto";
  }
}