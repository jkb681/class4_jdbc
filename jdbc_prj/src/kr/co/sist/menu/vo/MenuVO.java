package kr.co.sist.menu.vo;

/**
 * @author user
 *
 */
public class MenuVO {
	private String item_code,img,menu,info; //이미지는 파일명만 가져와서 나중에 경로를 설정해줌.
	private int price;
	public MenuVO() {
		
		
	}//MenuVO 기본생성자
	
	public MenuVO(String item_code, String img, String menu, String info, int price) {
		super();
		this.item_code = item_code;
		this.img = img;
		this.menu = menu;
		this.info = info;
		this.price = price;
	}//MenuVO 인자 있는 생성자

	
	
	
	
	//////////////////// get,setter
	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}//class
