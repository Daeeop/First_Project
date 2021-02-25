import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order {

    private ArrayList<Cart> cartList;
    private ArrayList<Receipt> dailyOrderReceipt;

    private int orderCount;
    
    private String receiptDate;
    private String monthlyReceiptDate;
    
    private String monthlyReceiptNumber;
    private Calendar calendar;
    private SimpleDateFormat today;
    
    
    
    //생성자
    public Order() {
        this.cartList = new ArrayList<Cart>();
        this.dailyOrderReceipt = new ArrayList<Receipt>();
        this.orderCount = 0;
        
        this.calendar = Calendar.getInstance();
        this.today = new SimpleDateFormat("yyMMdd");
        this.monthlyReceiptDate = today.format(calendar.getTime());
    }
    
    //Getter Setter
    public ArrayList<Receipt> getOrderReceipt() {
		return dailyOrderReceipt;
	}
	public void setOrderReceipt(ArrayList<Receipt> orderReceipt) {
		dailyOrderReceipt = orderReceipt;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	
    //Kiosk receive에서 사용
    //카드번호 (1), 휴대폰 번호 (2) 체크 기능
    public String checkCardorPhone(int number) {
        Scanner scanner = new Scanner(System.in);
        String temp = "";
        String regex = "";
        
        do {
        switch(number) {
        case 1:
            System.out.print("카드 번호 입력 : ");
            temp = scanner.nextLine();
            regex = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$";
            
            break;
        case 2:
            System.out.print("휴대폰 번호 입력 : ");
            temp = scanner.nextLine();
            regex = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
            break;
        }
        
        if (!temp.matches(regex)) {
            System.out.println("잘못 입력하셨습니다. 형식에 맞춰 다시 입력해주세요.");
            System.out.println("카드번호(0000-0000-0000-0000), 휴대폰 번호(010-1234-1234)");
            continue;
        }
        
        } while(!temp.matches(regex));
        return temp;
    }
    
	//메뉴 선택
    private int choiceMenuNumber(int min, int max) {
        Scanner scan = new Scanner(System.in);
        int resultChoice = 0;
        try {
            String tempChoice = scan.nextLine();
            int tempResult = Integer.parseInt(tempChoice);

            if (tempResult < min || tempResult > max) {
                throw new Exception();
            } else {
                resultChoice = tempResult;
            }
        } catch (Exception e) {
        	System.out.println("잘못 입력하였습니다. 확인 후 다시 입력해주세요.");
        }
        return resultChoice;
    }
    
    //장바구니 총 금액
    private int orderTotalPrice() {
        int tempPrice = 0;

        for(int i = 0; i < this.cartList.size(); i++) {
            Cart cartTemp = this.cartList.get(i);
            tempPrice += (cartTemp.getDrink().getPrice() * cartTemp.getAmount());
        }

        return tempPrice;
    }
    
    //장바구니 총 수량
    private int orderTotalAmount() {
        int tempAmount = 0;

        for(int i = 0; i < this.cartList.size(); i++) {
            Cart cartTemp = this.cartList.get(i);
            tempAmount += cartTemp.getAmount();
        }

        return tempAmount;
    }
    
    //음료 선택
    public void drinkChoice(HashMap<String, Integer> memberList, HashMap<String, Receipt> monthlyReceipt, HashMap<Integer, Drink> drinkList) {

        int userDrinkChoice = 0;
        int userAmountChoice = 0;
        
        do {
            System.out.println("음료를 선택해주세요.");
            System.out.print(">>");
            userDrinkChoice = choiceMenuNumber(1, drinkList.size());
        } while(userDrinkChoice == 0);
        
        do {
            System.out.println("수량을 입력해주세요. (최대 50잔까지 가능합니다.)");
            System.out.print(">>");
            userAmountChoice = choiceMenuNumber(1, 50);
        } while(userAmountChoice == 0);
                
        if(drinkList.containsKey(userDrinkChoice)) {
            Cart tempCart = new Cart(drinkList.get(userDrinkChoice), userAmountChoice);
            cartList.add(tempCart);
            
            int userChoice = 0;
            
            showCart();
            if(!cartList.isEmpty()) {
                do {
                    System.out.println("음료를 더 담으시겠습니까?");
                    System.out.println("1. 네 2. 아니오");
                    System.out.print(">>");
                    userChoice = choiceMenuNumber(1, 2);
                } while(userChoice == 0);

                switch(userChoice) {
                case 1:
                    drinkChoice(memberList, monthlyReceipt, drinkList);
                    break;
                case 2:
                    System.out.println("음료선택을 완료합니다.");
                    choicePayment(memberList, monthlyReceipt, drinkList);
                }
            }
            
        }
        
    }
    
    //카드결제
    private void payCard(HashMap<String, Integer> memberList, HashMap<String, Receipt> monthlyReceipt) {

        String tempCardNum = checkCardorPhone(1);

        int orderTotalAmount = orderTotalAmount();
        int orderTotalPrice = orderTotalPrice();
        
        Receipt tempReceipt = new Receipt(++this.orderCount, this.cartList, "카드결제", orderTotalPrice, tempCardNum);
        this.dailyOrderReceipt.add(tempReceipt);
        
        this.monthlyReceiptNumber = this.monthlyReceiptDate + Integer.toString(this.orderCount);
        monthlyReceipt.put(this.monthlyReceiptNumber, tempReceipt);
        
        printReceipt((this.orderCount-1));

        System.out.println("주문이 완료되었습니다.");

        stackCoupon(orderTotalAmount, memberList);

    }
    
    //쿠폰결제
    private void payCoupon(HashMap<String, Integer> memberList, HashMap<String, Receipt> monthlyReceipt) { 
        String tempPhoneNum = checkCardorPhone(2);
        int userChoice = 0;
        int orderTotalAmount = orderTotalAmount();
        int orderTotalPrice = orderTotalPrice();

        if(!memberList.containsKey(tempPhoneNum)) {
            
            do {
            	System.out.println("등록되지 않은 정보입니다. 카드 결제로 전환하겠습니까?");
                System.out.println("1. 네 2. 아니오");
                System.out.print(">>");
                userChoice = choiceMenuNumber(1, 2);
            } while(userChoice == 0);
            switch(userChoice) {
            case 1:
            	payCard(memberList, monthlyReceipt);
                break;
            case 2:
            	payCoupon(memberList,monthlyReceipt);
                return;
            }
          
        } else {
            System.out.println("*현재 쿠폰 개수 : " + memberList.get(tempPhoneNum));

            if(memberList.get(tempPhoneNum) < (orderTotalAmount * 10)) {
                System.out.println("보유 쿠폰 개수가 부족하여 카드 결제로 전환합니다.");
                payCard(memberList, monthlyReceipt);
                return;
            } else {

                do {
                    System.out.println("쿠폰을 사용하시겠습니까? (쿠폰사용 시 1잔당 쿠폰 10개가 차감됩니다.)");
                    System.out.println("1. 네 2. 아니오");
                    System.out.print(">>");
                    userChoice = choiceMenuNumber(1, 2);
                } while(userChoice == 0);

                switch(userChoice) {
                case 1:
                    memberList.put(tempPhoneNum, memberList.get(tempPhoneNum) - (orderTotalAmount * 10));
                    Receipt tempReceipt = new Receipt(++this.orderCount, this.cartList, "쿠폰결제", orderTotalPrice, tempPhoneNum);
                    
                    this.dailyOrderReceipt.add(tempReceipt);
                    this.monthlyReceiptNumber = this.monthlyReceiptDate + Integer.toString(this.orderCount);
                    monthlyReceipt.put(this.monthlyReceiptNumber, tempReceipt);
                    if(!dailyOrderReceipt.isEmpty()) {
                    	printReceipt((this.orderCount-1));
                    }
                    System.out.println("주문이 완료되었습니다." + "(남은 쿠폰 : " +memberList.get(tempPhoneNum) +"개)");
                    break;
                case 2:
                    System.out.println("쿠폰을 사용을 취소하여 카드결제로 전환합니다.");
                    payCard(memberList, monthlyReceipt);
                    return;
                }

            }

        }

    } // payCoupon();

    //결제수단 선택
    private void choicePayment(HashMap<String, Integer> memberList, HashMap<String, Receipt> monthlyReceipt, HashMap<Integer, Drink> drinkList) {
        
        if (!checkCart()) {
            System.out.println("장바구니가 비어 주문할 상품이 없습니다.");
            drinkChoice(memberList, monthlyReceipt, drinkList);
            return;
        }
        
        int userChoice = 0;
        do {
            System.out.println("결제 수단을 선택해주세요.");
            System.out.println("1. 카드");
            System.out.println("2. 쿠폰");
            System.out.println("3. 음료 추가선택");
            System.out.println("4. 주문취소");
            System.out.print(">>");
            userChoice = choiceMenuNumber(1, 4);
        } while(userChoice == 0);
        
        switch(userChoice) {
        case 1: payCard(memberList, monthlyReceipt); clearCart(); break;
        case 2: payCoupon(memberList, monthlyReceipt); clearCart(); break;
        case 3: drinkChoice(memberList, monthlyReceipt, drinkList); break;
        case 4: System.out.println("주문을 취소합니다."); clearCart(); return;
        }

    }

    //쿠폰 적립
    private void stackCoupon(int checkAmount, HashMap<String, Integer> memberList) {
        Scanner scan = new Scanner(System.in);
        
        int userChoice = 0;
        
        do {
        System.out.println("쿠폰 적립 하시겠습니까?");
        System.out.println("1. 네 2. 아니오");
        System.out.print(">>");
        userChoice = choiceMenuNumber(1, 2);
        } while(userChoice == 0);
        
        String userPhoneNum = "";

        if (userChoice == 1) {
            userPhoneNum = checkCardorPhone(2);          
            
            if (!memberList.containsKey(userPhoneNum)) {
                memberList.put(userPhoneNum, checkAmount);
            } else {
                memberList.put(userPhoneNum, (memberList.get(userPhoneNum)+checkAmount));
            }
            System.out.println("쿠폰 적립이 완료되었습니다.");

        } else {
            System.out.println("쿠폰이 적립되지 않았습니다.");
            return;
        }

    } // stackCoupon
    
    //카트 체크
    private boolean checkCart() {
        boolean temp = false;
        if (!this.cartList.isEmpty()) {
            temp = true;
        }
        return temp;
    }
    
    //카트 보기
	private void showCart() {
		System.out.println("-------------------------------------");
        System.out.println("상품명\t            단가         수량      ");
        
    	for(int i = 0; i < this.cartList.size(); i++) {
            Cart cartTemp = this.cartList.get(i);
            System.out.printf("%-16s %-12d %-7d\n", cartTemp.getDrink().getName(),cartTemp.getDrink().getPrice() , cartTemp.getAmount());
    	}
    	
    	System.out.println("-------------------------------------");
    }
    
    //카트 비우기
    private void clearCart() {
        this.cartList.clear();
    }
    
    //카드번호, 휴대폰번호 마스킹
    private String maskingNum(Receipt receipt) {
        String cardNum = receipt.getPaymentNum();
        StringBuilder builder = new StringBuilder();
        char[] charPaymentNum = cardNum.toCharArray();
        
        switch(charPaymentNum.length) {
        case 19: // 길이가 19면 카드번호
            for(int i = 0; i < charPaymentNum.length; i++) {
                builder.append(i >= 5 && i <= 13 && !(i == 9) ? "*" : charPaymentNum[i]);            
            }
            break;
        case 13: // 길이가 13이면 휴대폰번호
            for(int i = 0; i < charPaymentNum.length; i++) {
                builder.append(i >= 4 && i <= 7 ? "*" : charPaymentNum[i]);            
            }
            break;
        }
        return builder.toString();
    }
    
	// 영수증 출력
	private void printReceipt(int orderNum) {

		PrintWriter pw = null;

		String slash = File.separator;
		String path = System.getProperty("user.home") + slash + "Desktop" + slash + "1조_JBK_Cafe" + slash
				+ this.dailyOrderReceipt.get(orderNum).getReceiptDate() + slash;
		File file = new File(path);

		Receipt tempReceipt = this.dailyOrderReceipt.get(orderNum);

		try {

			file.mkdirs();
			pw = new PrintWriter(
					new FileWriter(path + tempReceipt.getReceiptTime() + "_" + this.orderCount + "_" + "receipt.txt"));
			pw.println("*****************************************");
			pw.println("*               JBK  Cafe               *");
			pw.println("*****************************************");
			pw.printf("\n\t\t주문번호 : %03d\n\n", this.orderCount);
			pw.println("-----------------------------------------");
			pw.println("\t상품명\t     단가    수량      금액");
			pw.println("-----------------------------------------");

			Iterator<Cart> cartIterator = this.cartList.iterator();

			while (cartIterator.hasNext()) {
				Cart temp = cartIterator.next();
				pw.printf("%s\n", temp.getDrink().getName());
				pw.printf("\t\t %7d원   %02d  %7d원\n", temp.getDrink().getPrice(), temp.getAmount(),
						(temp.getAmount() * temp.getDrink().getPrice()));
			}

			pw.println("-----------------------------------------");
			pw.printf("  TOTAL :   \t\t   %10d원\n", orderTotalPrice());
			pw.println("-----------------------------------------");
			pw.println("결제 일시 : " + tempReceipt.getReceiptDate() + " " + tempReceipt.getReceiptTime());
			String temp1 = this.dailyOrderReceipt.get(orderNum).getPayment();
			pw.println("결제 수단 : " + temp1);
			String temp2 = maskingNum(tempReceipt);
			pw.println("결제 정보 : " + temp2);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	} // printReceipt
    
}
