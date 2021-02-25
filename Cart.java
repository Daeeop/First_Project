
public class Cart {
    
    private Drink drink;
    private int amount;
    
    
    public Cart(Drink drink, int amount) {
        this.drink = drink;
        this.amount = amount;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public Drink getDrink() {
        return drink;
    }


    @Override
    public String toString() {
        return "Cart [drink=" + drink + ", amount=" + amount + "]";
    }
    
    
}
