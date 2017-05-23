package kr.co.sist.menu.evt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.OrderForm;
import kr.co.sist.menu.vo.OrderVO;

/**
 *
 * @author user
 */
public class OrderFormEvt extends WindowAdapter implements ActionListener {
	private OrderForm of;
	public OrderFormEvt(OrderForm of) {
		this.of = of;
		
	}//OrderFormEvt 생성자
	
	private void setOrder(){	//액션퍼폼드 주문버튼에서 사용할 메소드 생성
		String name = of.getJtfName().getText();
		if(name.length() > 10){
			JOptionPane.showMessageDialog(of, name+"은 10자 이하로 입력해주세요");
			of.getJtfName().setText("");//주문자 이름 초기화
			of.getJtfName().requestFocus();//커서를 맨앞으로~
			return;
		}//end if
		String item_code = of.getJtfItemCode().getText();
		String menu = of.getJtfMenu().getText();
		String ipaddr= "";
			try {
				ipaddr = InetAddress.getLocalHost().toString();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}//end catch
		Integer quan = (Integer)of.getJcbQuan().getSelectedItem();
		String price = of.getJtfPrice().getText();
		
		StringBuilder order = new StringBuilder();
		order.append(name).append("님의 주문사항\n")
		.append("제품명(코드) : ")
		.append(menu).append("(").append(item_code)
		.append(")\n")
		.append("단가 : ").append(price)
		.append("\n 수량 : ").append(quan)
		.append("\n 총 금액 : ").append(Integer.parseInt(price)*quan)
		.append("\n주문자 정보 : ").append(ipaddr)
		.append("\n 주문하시겠습니까?");
		
		int flag = JOptionPane.showConfirmDialog(of, order.toString());
		switch (flag) {
		case JOptionPane.OK_OPTION :
			MenuDAO md = MenuDAO.getInstance();
			OrderVO ov = new OrderVO(item_code,name,ipaddr,quan);
			try {
				md.insertOrder(ov);
				ImageIcon iiDongHa = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/donghaFE.gif");
				JLabel lblThank = new JLabel(iiDongHa);
				JPanel jp = new JPanel();
				jp.setLayout(new BorderLayout());
				jp.add("North", new JLabel("성공적으로 주문되었습니다."));
				
				
				jp.add("Center",lblThank);
				lblThank.setToolTipText("주문해주셔서 대단히 감사합니다");
				
				JOptionPane.showMessageDialog(of, jp);
				of.dispose();
			} catch (SQLException e) {
				e.printStackTrace();
			}//catch
			
		}//switch
		
	} //setOrder
	

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==of.getJbtOrder()){
			setOrder();
		}//end if
		if(ae.getSource()==of.getJbtClose()){
			int selectNum = JOptionPane.showConfirmDialog(of, "주문창을 종료하시겠습니까?");
			switch (selectNum) {
			case JOptionPane.OK_OPTION :
				of.dispose();
			}//end switch
		}//end if
	}//actionPerformed

}//class
