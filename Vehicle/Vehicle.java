package Vehicle;

public abstract class Vehicle {
  private int id;
  private String name;
  protected String type;
  private float dailyRate;
  private boolean isRented;

  Vehicle(int id, String name, float dailyRate){
    this.id = id;
    this.name = name;
    this.dailyRate = dailyRate;
    this.isRented = false;
  }
  
  //constructor used by fileDataToArray() to put data from txt to array
  Vehicle(int id, String name, float dailyRate, boolean isRented){
    this.id = id;
    this.name = name;
    this.dailyRate = dailyRate;
    this.isRented = isRented;
  }

  public int getId(){
    return id;
  }

  public void setId(int idToSet){
    this.id = idToSet;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public float getDailyRate(){
    return dailyRate;
  }

  public void setDailyRate(float dailyRate){
    this.dailyRate = dailyRate;
  }

  public String getType(){
    return type;
  }

  public boolean getIsRented(){
    return isRented;
  }

  public void setIsRented(boolean estado){
    this.isRented = estado;
  }

  public String toString(){

    return "-" + id +" ["+"type: "+type+ " | name: " + name + " | dailyRate: " + dailyRate +"]";

  }
}