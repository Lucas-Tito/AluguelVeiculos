package Client;

public class Account {
  private float balance;

  public Account(){
    balance = 0;
  }

  public void setBalance(float valueToSet){
      this.balance += valueToSet;
  }

  public float getBalance(){
    return balance;
  }

public String toString(){
  return ""+balance;
}
}