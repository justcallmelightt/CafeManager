public class Order {

    private OrderItem item1;
    private OrderItem item2;

    public Order(OrderItem item1, OrderItem item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public int getTotalPrice() {
        return item1.getTotalPrice()
                + item2.getTotalPrice();
    }

    public void showOrder() {
        System.out.println("=== 주문 내역 ===");
        item1.showInfo();
        item2.showInfo();
        System.out.println("총 금액: " + getTotalPrice() + "원");
    }
}