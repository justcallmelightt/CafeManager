public class Menu {
    private String name;
    private int price;
    private int stock;

    public Menu(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public boolean hasStock(int amount) {
        return stock >= amount;
    }

    public void reduceStock(int amount) {
        if (hasStock(amount)) {
            stock -= amount;
        }
    }

    public void showInfo() {
        System.out.println("메뉴명: " + name);
        System.out.println("가격: " + price + "원");
        System.out.println("재고: " + stock + "개");
    }
}