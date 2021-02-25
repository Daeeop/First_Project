public class Drink {
    
    private String name;
    private int price;
    
    
    public Drink(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "음료명 : " + name + ", 금액" + price;
    }
    
}
