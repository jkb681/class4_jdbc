package kr.co.sist.menu.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.OrderViewForm;
import kr.co.sist.menu.vo.OrderAllVO;

/**
 * @author user
 *
 */
public class OrderViewFormEvt extends WindowAdapter implements ActionListener, Runnable {
	
	private OrderViewForm ovf;

	public OrderViewFormEvt(OrderViewForm ovf) {
		this.ovf = ovf;
		setOrder();
	}// OrderViewFormEvt

	private void setOrder() { // 디비에 주문된 도시락들을 조회하는 메소드
		if (MenuFormEvt.ORDER_SELECT) {
			MenuDAO md = MenuDAO.getInstance();
			try {
				List<OrderAllVO> list = md.selectOrder();
				DefaultTableModel dtm = ovf.getDtmOrder();
				JLabel tempResult = ovf.getOrderResult();
				StringBuilder sbResult = new StringBuilder();
				sbResult.append("주문결과 : ").append(list.size()).append("건, 총 금액 : ");

				Object[] data = new Object[8];
				OrderAllVO oav = null;
				int total = 0;

				dtm.setRowCount(0);
				for (int i = 0; i < list.size(); i++) {
					oav = list.get(i);

					total += oav.getTotalPrice(); // 총주문금액 (주문금액합산)

					data[0] = i + 1;
					data[1] = oav.getOrderNum();
					data[2] = oav.getMeun();
					data[3] = oav.getItem_code();
					data[4] = oav.getName();
					data[5] = oav.getQuan();
					data[6] = oav.getTotalPrice();
					data[7] = oav.getOrderDate();
					dtm.addRow(data);

				} // end for

				sbResult.append(total).append("원");
				tempResult.setText(sbResult.toString());
				if (list.isEmpty()) {
					String[] emptyData = { "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A" };
					dtm.addRow(emptyData);
					JOptionPane.showMessageDialog(ovf, "도시락 주문이 없습니다.^^");
				} // end if
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ovf, "서버에 문제발생, 다시시도바람.");
				e.printStackTrace();
			} // catch
		} // end if

	}// setOrder

	@Override
	public void windowClosing(WindowEvent we) {
		MenuFormEvt.ORDER_SELECT = false;
		ovf.setVisible(false);
	}// windowClosing

	@Override
	public void run() {// 쓰레드를사용하여 추가되면 바로 리스트에 뜨게끔 해주는 동시작업

		while (true) {
			setOrder();// 메소드~

			try {
				Thread.sleep(10000);// 20초마다 한번씩 메소드를 불러주는 코드
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // catch

		} // end while
	}// run

	@Override
	public void actionPerformed(ActionEvent ae) {
		MenuFormEvt.ORDER_SELECT = false;
		ovf.setVisible(false);

	}// actionPerformed

}// class
