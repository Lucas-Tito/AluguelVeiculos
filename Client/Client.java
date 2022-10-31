package Client;

public class Client {
  private String name;
  private int age;
  private String LicenseType;
  private Account account;
  private int rentedVehicle;   //receives the id of rented vehicle, if none was rented the attribute receives -1
  private boolean hasInsurance;
                          

  public Client(String name, int age, String type){
    this.name = name;
    this.age = age;
    this.LicenseType = type;
    this.account = new Account();
    this.rentedVehicle = -1;
  }

  //constructor used by fileDataToArray() to put data from txt to array
  public Client(String name, int age, String type, int carId, boolean hasInsurance, float balance){
    this.name = name;
    this.age = age;
    this.LicenseType = type;
    this.account = new Account();
    this.account.setBalance(balance);
    this.rentedVehicle = carId;
    this.hasInsurance = hasInsurance;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public int getAge(){
    return age;
  }

  public void setAge(int age){
    this.age = age;
  } 

  public String getLicenseType(){
    return LicenseType;
  }

  public void setLicenseType(String licenseType){
    this.LicenseType = licenseType;
  }
  
  public Account getAccount(){
    return account;
  }

  public int getRentedVehicle(){
    return rentedVehicle;
  }

  public void setRentedVehicleId(int id){
    this.rentedVehicle = id;
  }

  public boolean getHasInsurance(){
    return hasInsurance;
  }
  
  public void setHasInsurance(boolean state){
    this.hasInsurance = state;
  }

  public String toString(){

    String show = "-" + name +" ["+ "age: " + age +"| license: "+ LicenseType + "| balance: " + account + " Insurance: ";

    if(hasInsurance)
      show += "yes]";
    else
      show+= "no]";

    return show;
    
  }
  
}