public class Main {
    public static void main(String[] args) {

        Drink americano =
                new Drink("아메리카노", 3000, 10);
        Dessert cookie =
                new Dessert("쿠키", 2000, 5);
        OrderItem americanoOrder =
                new OrderItem(americano, 2);
        OrderItem cookieOrder =
                new OrderItem(cookie, 1);
        Order order =
                new Order(americanoOrder, cookieOrder);

        order.showOrder();

        Payment payment =
                new Payment(10000);

        if (payment.canPay(order.getTotalPrice())) {
            System.out.println(
                    "결제가 완료되었습니다."
            );

            System.out.println(
                    "거스름돈: "
                    + payment.calculateChange(order.getTotalPrice())
                    + "원"
            );
        } else {
            System.out.println(
                    "결제 금액이 부족합니다."
            );
        }
    }
}