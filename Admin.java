import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Admin {
	
	private String password;
	private boolean sales;

	public String getPassword() {
		return password;
	}

	public boolean isSales() {
		return sales;
	}

	////////////////////////////////////////////////
	public Admin(){
		this.password = "0000";
		sales = false;
	}
////////////////////////////////////////////////
	public void displayAdmin(HashMap<Integer, Drink> drinkList, Order order, HashMap<String, Receipt> monthlyReceipt, ArrayList<Integer> takeOutList) { // 메뉴선택
		do {
			int menu = showAdmin();
			if (menu >= 1 && menu <= 7) {
				switch (menu) {
				case 1:
					storeOpen(drinkList);
					break;
				case 2:
					storeClose(order, takeOutList);
					break;
				case 3:
					menuManage(drinkList);
					break;
				case 4:
					makeReport(monthlyReceipt);
					break;
				case 5:
					changePassword();
					break;
				case 6:
					System.out.println("관리자 모드를 종료합니다.");
					return;
				case 7:
					System.out.println("키오스크를 종료합니다.");
					System.exit(0);
				}
			} else {
				System.out.println("올바른 번호를 입력해주세요.");
			}
		} while (true);
	}

	private int showAdmin() { // 메뉴
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("-------------------------------------");
		System.out.println("-----------------관리자----------------");
		System.out.println("-------------------------------------");
		System.out.println("1. 영업시작");
		System.out.println();
		System.out.println("2. 영업종료");
		System.out.println();
		System.out.println("3. 메뉴관리");
		System.out.println();
		System.out.println("4. 매출분석");
		System.out.println();
		System.out.println("5. 비밀번호 변경");
		System.out.println();
		System.out.println("6. 관리자 종료");
		System.out.println();
		System.out.println("7. 키오스크 종료");
		System.out.println();
		System.out.print(">>");

		while (!scanner.hasNextInt()) {
			scanner.next();
			System.out.println("잘못 입력하였습니다. 숫자만 입력해주세요.");
			System.out.print(">>");
		}

		int selectmenu = Integer.parseInt(scanner.next());
		return selectmenu;
	}
	//////////////////////////////////////////////////////////
	//영업시작
	private void storeOpen(HashMap<Integer,Drink> drinkList) {
		if(drinkList.isEmpty()){
			System.out.println("판매할 수 있는 음료가 없습니다. 영업을 시작하기 전에 음료를 추가해주세요.");
		}else if(sales == true){
			System.out.println("이미 영업을 시작했습니다.");
		}else {
			System.out.println("영업을 시작합니다.");
			sales = true;
			return;
		}
	}
	
	//영업종료
	private void storeClose(Order order, ArrayList<Integer> takeOutList) {
		Scanner scanner = new Scanner(System.in);
		
		if(sales) {
			System.out.println("정말로 영업을 끝내시겠습니까?");
			System.out.println("1. 영업종료 2. 영업계속");
			System.out.print(">>");
			
			while(!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력하였습니다. 숫자만 입력해주세요.");
				System.out.print(">>");
			}
			
			int choice = scanner.nextInt();
			
			if(choice == 1) {
				ArrayList<Receipt> initOrderReceipt = new ArrayList<Receipt>();
                order.setOrderCount(0);
                order.setOrderReceipt(initOrderReceipt);
                
                takeOutList.clear();
                
                System.out.println("영업을 종료합니다.");
                sales = false;
				
			}else  if(choice == 2) {
				System.out.println("영업를 계속합니다.");
			}else {
				System.out.println("올바른 번호를 입력해주세요.");
			}
		}else {
			System.out.println("아직 영업이 시작되지 않았습니다.");
		}
	}

	//////////////////////////////////////////////////////////
	private void menuManage(HashMap<Integer,Drink> drinkList) { //메뉴관리
		do {
			int menu = showManage();
			if (menu >= 1 && menu <= 4) {
				switch (menu) {
				case 1:
					menuAdd(drinkList);
					break;
				case 2:
					menuRemove(drinkList);
					break;
				case 3:
					menuEdit(drinkList);
					break;
				case 4 :
					System.out.println("메뉴 관리 종료");
					return;
				}
			} else {
				System.out.println("올바른 번호를 입력해주세요.");
			}
		} while (true);
	}

	private int showManage() { //메뉴
		Scanner scanner = new Scanner(System.in);

		System.out.println("----------------메뉴관리----------------");
		System.out.println("1. 메뉴 추가");
	    System.out.println("2. 메뉴 제거");
	    System.out.println("3. 메뉴 수정");
	    System.out.println("4. 메뉴 관리 종료");
		System.out.print(">>");
		
		while(!scanner.hasNextInt()) {
			scanner.next();
			System.out.println("잘못 입력하였습니다. 숫자만 입력해주세요.");
			System.out.print(">>");
		}
		
		int selectmenu = Integer.parseInt(scanner.next());
		return selectmenu;
	}
	
	//Kiosk order에서 사용
	public void menuShow(HashMap<Integer, Drink> drinkList) {
		System.out.println("-----------------메뉴------------------");
		if(!drinkList.isEmpty()) {
			for(int number : drinkList.keySet()) {
				System.out.printf("(%d)  %-15s %-5s원\n", number, drinkList.get(number).getName() ,drinkList.get(number).getPrice());
			}
		}else {
			System.out.println("등록된 음료가 없습니다.");
		}
	}
	
	//메뉴 추가
	private HashMap<Integer, Drink> menuAdd(HashMap<Integer, Drink> drinkList) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("----------------메뉴 추가---------------");
        System.out.println("음료 이름을 입력해주세요.");
        System.out.print(">>");
        String name = scanner.nextLine();
        
        System.out.println("음료 가격을 입력해주세요.");
        System.out.print(">>");
        
        while(!scanner.hasNextInt()) {
			scanner.next();
			System.out.println("잘못 입력하였습니다. 숫자만 입력해주세요.");
			System.out.print(">>");
		}
        int price = scanner.nextInt();
        
        // drink 객체 생성
        Drink drink = new Drink(name, price);
        
        System.out.println("1. 자동으로 메뉴 추가 2. 위치를 지정해 추가");
        System.out.print(">>");
        
        while(!scanner.hasNextInt()) {
			scanner.next();
			System.out.println("잘못 입력하였습니다. 숫자만 입력해주세요.");
			System.out.print(">>");
		}
        
        int addCase = scanner.nextInt();
        int tempSize = drinkList.size();
        
        if (addCase == 1 || addCase ==2) {
            if (addCase == 2) {
                System.out.println("몇번 음료 뒤에 추가하겠습니까? (0번을 입력하면 1번에 추가됩니다.)");
                System.out.print(">>");
                
                while(!scanner.hasNextInt()) {
    				scanner.next();
    				System.out.println("잘못 입력하였습니다. 숫자만 입력해주세요.");
    				System.out.print(">>");
    			}
                
                int position = scanner.nextInt();
                
                while(position > tempSize || drinkList.isEmpty() || position < 0) {
                    System.out.println("아직 등록된 음료가 없거나 존재하지 않는 번호입니다.");
                    System.out.println("몇번 음료 뒤에 추가하겠습니까? (0번을 입력하면 1번에 추가됩니다.)");
                    System.out.print(">>");
                    
                    while(!scanner.hasNextInt()) {
        				scanner.next();
        				System.out.println("잘못 입력하였습니다. 숫자만 입력해주세요.");
        				System.out.print(">>");
        			}
                    
                    position = scanner.nextInt();
                }

                for (int i = 1; i < tempSize - position + 2; i++) {
                    Drink tempDrink = drinkList.get((tempSize + 1 - i));
                    drinkList.put(tempSize + 2 - i, tempDrink);
                }
                
                drinkList.put(position+1, drink);
                
            } else if (addCase == 1) {
            	
                drinkList.put(drinkList.size() + 1, drink);
            }
        }else {
        	System.out.println("올바른 번호를 입력해주세요.");
        }
        menuShow(drinkList);
		return drinkList;
    }
	
	//메뉴 수정
	private HashMap<Integer,Drink> menuEdit(HashMap<Integer,Drink> drinkList) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("----------------메뉴 수정---------------");
		menuShow(drinkList);
		if(!drinkList.isEmpty()) {
			System.out.println("수정할 음료번호를 입력해주세요.");
			System.out.print(">>");
			
			while(!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력하였습니다. 숫자만 입력해주세요.");
				System.out.print(">>");
			}
			
			int drinkNumber = scanner.nextInt();
			scanner.nextLine();
			
			if (drinkList.containsKey(drinkNumber)) {
				
				System.out.println("음료 이름을 입력해주세요.");
				System.out.print(">>");
				String name = scanner.nextLine();
				
				System.out.println("음료 가격을 입력해주세요."); //문자열 넣으면 다시받도록 수정
				System.out.print(">>");
				
				while(!scanner.hasNextInt()) {
    				scanner.next();
    				System.out.println("잘못 입력하였습니다. 숫자만 입력해주세요.");
    				System.out.print(">>");
    			}
				int price = scanner.nextInt();

				Drink editDrink = new Drink(name, price);
				drinkList.put(drinkNumber, editDrink);

			} else {
				System.out.println("존재하지 않는 음료 번호입니다.");
			}
		}
		
		return drinkList;
	}
	

	private HashMap<Integer,Drink> menuRemove(HashMap<Integer,Drink> drinkList) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("----------------메뉴 삭제---------------");
		menuShow(drinkList);
		
		if(!drinkList.isEmpty()) {
			System.out.println("삭제할 음료번호를 입력해주세요.");
			System.out.print(">>");
			int drinkNumber = scanner.nextInt();
			
			if(drinkList.containsKey(drinkNumber)) {
				System.out.println("정말로 '"+ drinkList.get(drinkNumber).getName() +"' 음료를 삭제하시겠습니까?");
				System.out.println("1-네, 2-아니오");
				System.out.print(">>");
				
				while(!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력하였습니다. 숫자만 입력해주세요.");
					System.out.print(">>");
				}
				
				int choice = scanner.nextInt();
				
				if(choice == 1) {
					
					for (int i = drinkNumber; i < drinkList.size(); i++) {
	                    Drink tempDrink = drinkList.get((i+1));
	                    drinkList.put(i, tempDrink);
	                }
					drinkList.remove(drinkList.size());
					
					System.out.println("음료를 삭제했습니다.");
					
				}else if(choice == 2) {
					System.out.println("음료 삭제를 취소합니다.");
				}else {
					System.out.println("번호를 잘못 선택하였습니다.");
				}
				
				menuShow(drinkList);
				
			}else {
				System.out.println("존재하지 않는 음료 번호입니다.");
			}
		}
		
		return drinkList;
	}
	
	public void makeReport(HashMap<String, Receipt> monthlyReceipt) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("-----------------매출분석---------------");
		if(!monthlyReceipt.isEmpty()) {
			
			System.out.println("원하는 일자를 입력하세요. (ex.210101)");
			System.out.print(">>");
			
			String searchDate = scanner.nextLine();
			String checkDate = searchDate + "1";
			
			int dailyTotalPrice = 0;
			int dailyTotalAmount = 0;
			int dailyPayCard = 0;
			int dailyPayCoupon = 0;
			
			if(monthlyReceipt.containsKey(checkDate)) {
				
				HashMap<String, Receipt> tempDailyReceipt = new  HashMap<String, Receipt>();
				ArrayList<Cart> tempCartList = new ArrayList<Cart>();
				
				for(String key : monthlyReceipt.keySet()) {
					if(key.contains(searchDate)) {
						tempDailyReceipt.put(key, monthlyReceipt.get(key));
					}
				}
				
				if(!tempDailyReceipt.isEmpty()) {
					
					for(String key : tempDailyReceipt.keySet()) {
						dailyTotalPrice += tempDailyReceipt.get(key).getTotalPrice();
						
						if(tempDailyReceipt.get(key).getPayment().equals("카드결제")) {
							dailyPayCard++;
						}else {
							dailyPayCoupon++;
						}
						
					}
				}
				System.out.println("*"+searchDate+" 일자 매출분석");
				System.out.println("*총 주문건수 : " +tempDailyReceipt.size() +"건");
				System.out.println("*총 매출    : " +dailyTotalPrice + "원");
				System.out.println("*카드결제 횟수: " +dailyPayCard + "회");
				System.out.println("*쿠폰결제 횟수: " +dailyPayCoupon + "회");
				
				
			}else {
				System.out.println("입력하신 날짜에 매출 내역은 없습니다.");
				
			}
		}else {
			System.out.println("아직 매출내역이 없습니다.");
		}
		
	}

	private void changePassword() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("---------------비밀번호 변경-------------");
		do {
			System.out.println("현재 비밀번호를 입력해주세요.");
			System.out.print(">>");
			String checkPassword = scanner.next();
			
			if (this.password.equals(checkPassword)) {
				System.out.println("변경할 비밀번호를 입력해주세요.");
				System.out.print(">>");
				String newPassword = scanner.next();
				this.password = newPassword;
				System.out.println("비밀번호가 " + newPassword + "로 변경되었습니다.");
				break;
			} else {
				System.out.println("비밀번호가 올바르지 않습니다.");
			}
		} while (true);
	}
	
}
