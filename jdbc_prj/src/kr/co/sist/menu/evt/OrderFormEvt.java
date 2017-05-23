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
		
	}//OrderFormEvt ������
	
	private void setOrder(){	//�׼������� �ֹ���ư���� ����� �޼ҵ� ����
		String name = of.getJtfName().getText();
		if(name.length() > 10){
			JOptionPane.showMessageDialog(of, name+"�� 10�� ���Ϸ� �Է����ּ���");
			of.getJtfName().setText("");//�ֹ��� �̸� �ʱ�ȭ
			of.getJtfName().requestFocus();//Ŀ���� �Ǿ�����~
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
		order.append(name).append("���� �ֹ�����\n")
		.append("��ǰ��(�ڵ�) : ")
		.append(menu).append("(").append(item_code)
		.append(")\n")
		.append("�ܰ� : ").append(price)
		.append("\n ���� : ").append(quan)
		.append("\n �� �ݾ� : ").append(Integer.parseInt(price)*quan)
		.append("\n�ֹ��� ���� : ").append(ipaddr)
		.append("\n �ֹ��Ͻðڽ��ϱ�?");
		
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
				jp.add("North", new JLabel("���������� �ֹ��Ǿ����ϴ�."));
				
				
				jp.add("Center",lblThank);
				lblThank.setToolTipText("�ֹ����ּż� ����� �����մϴ�");
				
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
			int selectNum = JOptionPane.showConfirmDialog(of, "�ֹ�â�� �����Ͻðڽ��ϱ�?");
			switch (selectNum) {
			case JOptionPane.OK_OPTION :
				of.dispose();
			}//end switch
		}//end if
	}//actionPerformed

}//class
