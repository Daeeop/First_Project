import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Kiosk {

	private ArrayList<Integer> takeOutList;
	private HashMap<Integer, Drink> drinkList;
	private HashMap<String, Integer> memberList;
	private HashMap<String, Receipt> monthlyReceipt;
	
	private Order order;
	private Admin admin;

    public Kiosk() {
        this.takeOutList = new ArrayList<Integer>();
        this.drinkList = new HashMap<Integer, Drink>();
        this.memberList = new HashMap<String, Integer>();
        this.monthlyReceipt = new HashMap<String, Receipt>();
        this.order = new Order();
        this.admin = new Admin();
        
        this.displayMenu();
    }
    
    
	public void setTakeOutList(ArrayList<Integer> takeOutList) {
		this.takeOutList = takeOutList;
	}

	//////////////////////////////////////////////////////////////////////////
	private void displayMenu() { // 메뉴선택
		do {
			int menu = showMenu();
			if (menu >= 1 && menu <= 4) {
				switch (menu) {
				case 1:
					order();
					break;
				case 2:
					receive();
					break;
				case 3:
					findCoupon();
					break;
				case 4:
					adminAccess();
					break;
				}
			} else {
				System.out.println("올바른 번호를 입력해주세요.");
			}
		} while (true);
	}

	private int showMenu() { // 메뉴
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("-------------------------------------");
		System.out.println("--------------JBK  Cafe--------------");
		System.out.println("-------------------------------------");
		System.out.println("1. 주문하기");
		System.out.println();
		System.out.println("2. 수령하기");
		System.out.println();
		System.out.println("3. 쿠폰조회");
		System.out.println();
		System.out.println("4. 관리자 모드");
		System.out.println();
		System.out.print(">>");

		while (!scanner.hasNextInt()) {
			scanner.next();
			System.out.println("숫자만 입력해주세요.");
			System.out.print(">>");
		}

		int selectmenu = Integer.parseInt(scanner.next());
		return selectmenu;
	}
	
	
	private void order() {
		
		if(!admin.isSales()) {
			System.out.println("아직 영업을 시작하지 않았습니다.");
		}else {
			System.out.println("----------------주문하기----------------");
			admin.menuShow(drinkList);
			order.drinkChoice(this.memberList, this.monthlyReceipt, this.drinkList);
		}
	}
	
	private void receive() {
		Scanner scanner = new Scanner(System.in);
		
		
		if(!admin.isSales()) {
			System.out.println("아직 영업을 시작하지 않았습니다.");
		}else {
			System.out.println("----------------수령하기----------------");
			if(!order.getOrderReceipt().isEmpty()) {
				System.out.println("주문번호를 입력해주세요.");
				System.out.print(">>");
				
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("숫자만 입력해주세요.");
					System.out.print(">>");
				}
				
				int orderNumber = scanner.nextInt();
				int checkReceive = takeOutList.size();
				
				Iterator<Receipt> takeOut = this.order.getOrderReceipt().iterator();
		        
		        while (takeOut.hasNext()) {
		        	
		            Receipt temp = takeOut.next();
		            
		            if(takeOutList.contains(orderNumber)) {
		                System.out.println("이미 수령한 음료입니다.");
		                
		            }else {
		            	
		                if (temp.getOrderNumber() == orderNumber) {
		                    System.out.println("음료가 준비되었습니다. 픽업대에서 음료를 찾아가세요.");
		                    takeOutList.add(orderNumber);
		                    break;
		                }
		            }
		        }//while
			}
		}
	}
	
	private void findCoupon() {
		
		
		if(!admin.isSales()) {
			System.out.println("아직 영업을 시작하지 않았습니다.");
		}else {
			System.out.println("----------------쿠폰조회----------------");
			String phonenumber = "";
			
			if(!memberList.isEmpty()) {
				phonenumber = order.checkCardorPhone(2);
				if(memberList.containsKey(phonenumber)) {
					System.out.println(phonenumber +"님이 보유한 쿠폰은 총 " +memberList.get(phonenumber) +"개 입니다.");
				}else {
					System.out.println("존재하지 않는 번호입니다. 입력하신 번호를 확인해주세요.");
				}
			}else {
				System.out.println("등록된 번호가 없습니다.");
			}
		}
	}
	
	private void adminAccess() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("---------------관리자 모드---------------");
        System.out.println("관리자 모드 접속 비밀번호를 입력해주세요.");
        System.out.print(">>");
        //scanner.nextLine();
        String password = scanner.nextLine();
        
        while(true) {
            if(password.equals(admin.getPassword())) {              
                admin.displayAdmin(drinkList, this.order, this.monthlyReceipt, this.takeOutList);
                break;
            }else {
                
                System.out.println("비밀번호가 일치하지 않습니다.");
                System.out.println("관리자 모드 접속 비밀번호를 입력해주세요.");
                System.out.print(">>");
                password = scanner.nextLine();
            }
        }
    }

	
}
