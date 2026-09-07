public class Payment {
    private int paymentAmount;

    public Payment(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public boolean canPay(int totalPrice) {
        return paymentAmount >= totalPrice;
    }

    public int calculateChange(int totalPrice) {
        return paymentAmount - totalPrice;
    }
}