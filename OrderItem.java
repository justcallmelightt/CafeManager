public class OrderItem {
    private Menu menu;
    private int quantity;

    public OrderItem(Menu menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return menu.getPrice() * quantity;
    }

    public void showInfo() {
        System.out.println(
                menu.getName() + " "
                + quantity + "개 / "
                + getTotalPrice() + "원"
        );
    }
}