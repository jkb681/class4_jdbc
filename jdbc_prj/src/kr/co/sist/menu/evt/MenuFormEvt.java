package kr.co.sist.menu.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.LoginForm;
import kr.co.sist.menu.view.MenuAddForm;
import kr.co.sist.menu.view.MenuForm;
import kr.co.sist.menu.view.OrderForm;
import kr.co.sist.menu.view.OrderViewForm;
import kr.co.sist.menu.vo.MenuVO;

/**
 * 
 * @author user
 */
public class MenuFormEvt extends MouseAdapter implements ActionListener {
	private MenuForm mf; // has a관계
	private MenuDAO m_dao;
	private OrderViewForm ovf;
	private MenuAddForm maf;
	
	public static boolean ORDER_SELECT ;
	
	public MenuFormEvt(MenuForm mf) {
		this.mf = mf;
		m_dao = MenuDAO.getInstance();

		// 메뉴를 조회하여 설정한다.
		setMenu(); // method 사용

	}// 생성자 MenuFormEvt

	public void setMenu() { //디자인을 갱신하는 메소드
		try {
			List<MenuVO> lstMenu = m_dao.selectMenuList();
			Object[] rowMenu = new Object[6];
			DefaultTableModel dtmMenu = mf.getDtmMenu();

			// 번호,이미지,메뉴번호,메뉴이름,설명,가격을 넣는 코드
			MenuVO mv = null;
			
			// setRowCount로 초기화된 후  추가된 데이터가 선택이 안되는 error 발생하면
			// setNumRows(행수) method를 사용하여 데이터를 추가하면 선택이 안되는 문제가 해결됨.
			dtmMenu.setRowCount(0); //중복값을 막아주는것(중복메뉴가 있으면 초기화해줌.)
			
			for (int i = 0; i < lstMenu.size(); i++) {
				mv = lstMenu.get(i);
				// 한행에 레코드가 만들어지는것.
				rowMenu[0] = i + 1; // 번호
				rowMenu[1] = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/s_" + mv.getImg()); // 이미지
				rowMenu[2] = mv.getItem_code(); // 메뉴번호
				rowMenu[3] = mv.getMenu(); // 메뉴이름
				rowMenu[4] = mv.getInfo();// 메뉴설명
				rowMenu[5] = mv.getPrice();// 메뉴가격

				dtmMenu.addRow(rowMenu);
				
			} // end for

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(mf, "죄송합니다 메뉴를 불러올 수 없습니다.");
			e.printStackTrace();
		} // catch
	}// setMenu

	@Override
	public void mouseClicked(MouseEvent me) {
		// 마우스가 더블 클릭 되었을 때
		if (me.getClickCount() == 2) { // 클릭횟수가 두번이라면
			//도시락 주문은 1시까지만 가능합니다.
			Calendar cal = Calendar.getInstance();
//			cal.get(cal.HOUR_OF_DAY); //지금이 몇시인지 알려주는 것
			if(cal.get(cal.HOUR_OF_DAY)>12){
				JOptionPane.showMessageDialog(mf, "1시이후에는 주문이 되지 않습니다.");
				return;
			}//end if
			
			
			JTable temp = mf.getJtMenu(); // menuform에 있는 jtmenu메소드

			// menu에는 내가 더블클릭한 행의 메뉴이름이 들어감
			// selectedRow는 내가 더블클릭한 행의 메뉴이름이 들어간다.
			int selectedRow = temp.getSelectedRow();

			// 코드의 가독성을 쉽게 하기 위하여 VO사용
			// 그행의 무엇을 번호,이미지 등등 아무거나 더블클릭해도 그행의 메뉴이름이 나오게 되어 있음.
			MenuVO mv = new MenuVO();
			mv.setImg(((ImageIcon) temp.getValueAt(selectedRow, 1)).toString());
			mv.setItem_code((String) temp.getValueAt(selectedRow, 2));
			mv.setMenu((String) temp.getValueAt(selectedRow, 3));
			mv.setInfo((String) temp.getValueAt(selectedRow, 4));
			mv.setPrice((Integer) temp.getValueAt(selectedRow, 5));

			int flag = JOptionPane.showConfirmDialog(mf, mv.getMenu() + "를 주문하시겠습니까?");
			switch (flag) {
			case JOptionPane.OK_OPTION:
				new OrderForm(mf, mv);
			}// end switch
		} // end if

	}// mouseClicked

	@Override
	public void actionPerformed(ActionEvent ae) {
		// 어떤 객체가 이벤트 작업을하더라도 일단 로그인 작업을 해줘야함.
		LoginForm lf = new LoginForm(mf);
		try {
			if(lf.getFlag()){
				JOptionPane.showMessageDialog(mf, "로그인 성공 ~");
				if (ae.getSource() == mf.getJbtMenuAdd()) {
					if(maf ==  null){
						maf=new MenuAddForm(mf,this);
					}else {
						maf.setVisible(true);
						
					}//end else
				} // end if

				if (ae.getSource() == mf.getJbtOrderList()) {
					if(ovf == null){//ovf가 널인경우에만 창을 띄움.
					ovf = new OrderViewForm(mf);
					}else{
						ORDER_SELECT= true;
						ovf.setVisible(true);
					}//end if
				} // end if

			} // end if
		} catch (LoginException le) {
			JOptionPane.showMessageDialog(mf, "아이디나 비밀번호를 확인하세요");
		}//catch 
	}// actionPerformed

}// class
