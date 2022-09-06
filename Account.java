class Account {
  private float balance;

  public Account(){
    balance = 0;
  }

  public void setSaldo(float valueToSet){
      this.balance += valueToSet;
  }

  public float getSaldo(){
    return balance;
  }

public String toString(){
  return ""+balance;
}
}