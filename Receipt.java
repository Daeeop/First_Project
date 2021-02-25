import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Receipt {
    
    private int orderNumber;
    private ArrayList<Cart> cartList;
    private String payment;
    private int totalPrice;
    private String paymentNum;
    
    private Calendar calendar;
    private SimpleDateFormat nowDate;
    private SimpleDateFormat nowTime;
    
    private String receiptDate;
    private String receiptTime;

    public Receipt(int orderNumber, ArrayList<Cart> cartList, String payment, int totalPrice, String paymentNum) {
        super();
        this.orderNumber = orderNumber;
        this.cartList = cartList;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.paymentNum = paymentNum;
        
        this.calendar = Calendar.getInstance();
        this.nowDate = new SimpleDateFormat("yyyy년 MM월 dd일");
        this.nowTime = new SimpleDateFormat("HH시mm분ss초");
        
        this.receiptDate = nowDate.format(calendar.getTime());
        this.receiptTime = nowTime.format(calendar.getTime());
        
    }

    public int getOrderNumber() {
        return orderNumber;
    }
    
    public ArrayList<Cart> getCartList() {
		return cartList;
	}

	public int getTotalPrice() {
		return totalPrice;
	}
	
	public String getPayment() {
		return payment;
	}
	
	public String getPaymentNum() {
		return paymentNum;
	}

	public String getReceiptDate() {
		return receiptDate;
	}
	
	public String getReceiptTime() {
		return receiptTime;
	}
	
	@Override
    public String toString() {
        return "Receipt [orderNumber=" + orderNumber + ", cartList=" + cartList + ", payment=" + payment
                + ", totalPrice=" + totalPrice + ", paymentNum=" + paymentNum + ", receiptDate=" + receiptDate
                + ", receiptTime=" + receiptTime + "]";
    }

}
