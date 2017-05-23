package kr.co.sist.menu.vo;

/**
 * @author user
 *
 */
public class OrderAllVO {
	private String item_code,meun,name,orderDate,ipAddr;
	private int orderNum,quan,totalPrice;
	public OrderAllVO() {
		
	}//OrderAllVO기본생성자
	public OrderAllVO(String item_code, String meun, String name, String orderDate, String ipAddr, int orderNum,
			int quan, int totalPrice) {
		super();
		this.item_code = item_code;
		this.meun = meun;
		this.name = name;
		this.orderDate = orderDate;
		this.ipAddr = ipAddr;
		this.orderNum = orderNum;
		this.quan = quan;
		this.totalPrice = totalPrice;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public void setMeun(String meun) {
		this.meun = meun;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getItem_code() {
		return item_code;
	}
	public String getMeun() {
		return meun;
	}
	public String getName() {
		return name;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public int getQuan() {
		return quan;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public void setQuan(int quan) {
		this.quan = quan;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getOrderNum() {
		return orderNum;
	}
	
	
	
	
}//class
