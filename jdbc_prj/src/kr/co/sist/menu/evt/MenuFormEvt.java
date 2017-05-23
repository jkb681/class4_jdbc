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
	private MenuForm mf; // has a����
	private MenuDAO m_dao;
	private OrderViewForm ovf;
	private MenuAddForm maf;
	
	public static boolean ORDER_SELECT ;
	
	public MenuFormEvt(MenuForm mf) {
		this.mf = mf;
		m_dao = MenuDAO.getInstance();

		// �޴��� ��ȸ�Ͽ� �����Ѵ�.
		setMenu(); // method ���

	}// ������ MenuFormEvt

	public void setMenu() { //�������� �����ϴ� �޼ҵ�
		try {
			List<MenuVO> lstMenu = m_dao.selectMenuList();
			Object[] rowMenu = new Object[6];
			DefaultTableModel dtmMenu = mf.getDtmMenu();

			// ��ȣ,�̹���,�޴���ȣ,�޴��̸�,����,������ �ִ� �ڵ�
			MenuVO mv = null;
			
			// setRowCount�� �ʱ�ȭ�� ��  �߰��� �����Ͱ� ������ �ȵǴ� error �߻��ϸ�
			// setNumRows(���) method�� ����Ͽ� �����͸� �߰��ϸ� ������ �ȵǴ� ������ �ذ��.
			dtmMenu.setRowCount(0); //�ߺ����� �����ִ°�(�ߺ��޴��� ������ �ʱ�ȭ����.)
			
			for (int i = 0; i < lstMenu.size(); i++) {
				mv = lstMenu.get(i);
				// ���࿡ ���ڵ尡 ��������°�.
				rowMenu[0] = i + 1; // ��ȣ
				rowMenu[1] = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/s_" + mv.getImg()); // �̹���
				rowMenu[2] = mv.getItem_code(); // �޴���ȣ
				rowMenu[3] = mv.getMenu(); // �޴��̸�
				rowMenu[4] = mv.getInfo();// �޴�����
				rowMenu[5] = mv.getPrice();// �޴�����

				dtmMenu.addRow(rowMenu);
				
			} // end for

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(mf, "�˼��մϴ� �޴��� �ҷ��� �� �����ϴ�.");
			e.printStackTrace();
		} // catch
	}// setMenu

	@Override
	public void mouseClicked(MouseEvent me) {
		// ���콺�� ���� Ŭ�� �Ǿ��� ��
		if (me.getClickCount() == 2) { // Ŭ��Ƚ���� �ι��̶��
			//���ö� �ֹ��� 1�ñ����� �����մϴ�.
			Calendar cal = Calendar.getInstance();
//			cal.get(cal.HOUR_OF_DAY); //������ ������� �˷��ִ� ��
			if(cal.get(cal.HOUR_OF_DAY)>12){
				JOptionPane.showMessageDialog(mf, "1�����Ŀ��� �ֹ��� ���� �ʽ��ϴ�.");
				return;
			}//end if
			
			
			JTable temp = mf.getJtMenu(); // menuform�� �ִ� jtmenu�޼ҵ�

			// menu���� ���� ����Ŭ���� ���� �޴��̸��� ��
			// selectedRow�� ���� ����Ŭ���� ���� �޴��̸��� ����.
			int selectedRow = temp.getSelectedRow();

			// �ڵ��� �������� ���� �ϱ� ���Ͽ� VO���
			// ������ ������ ��ȣ,�̹��� ��� �ƹ��ų� ����Ŭ���ص� ������ �޴��̸��� ������ �Ǿ� ����.
			MenuVO mv = new MenuVO();
			mv.setImg(((ImageIcon) temp.getValueAt(selectedRow, 1)).toString());
			mv.setItem_code((String) temp.getValueAt(selectedRow, 2));
			mv.setMenu((String) temp.getValueAt(selectedRow, 3));
			mv.setInfo((String) temp.getValueAt(selectedRow, 4));
			mv.setPrice((Integer) temp.getValueAt(selectedRow, 5));

			int flag = JOptionPane.showConfirmDialog(mf, mv.getMenu() + "�� �ֹ��Ͻðڽ��ϱ�?");
			switch (flag) {
			case JOptionPane.OK_OPTION:
				new OrderForm(mf, mv);
			}// end switch
		} // end if

	}// mouseClicked

	@Override
	public void actionPerformed(ActionEvent ae) {
		// � ��ü�� �̺�Ʈ �۾����ϴ��� �ϴ� �α��� �۾��� �������.
		LoginForm lf = new LoginForm(mf);
		try {
			if(lf.getFlag()){
				JOptionPane.showMessageDialog(mf, "�α��� ���� ~");
				if (ae.getSource() == mf.getJbtMenuAdd()) {
					if(maf ==  null){
						maf=new MenuAddForm(mf,this);
					}else {
						maf.setVisible(true);
						
					}//end else
				} // end if

				if (ae.getSource() == mf.getJbtOrderList()) {
					if(ovf == null){//ovf�� ���ΰ�쿡�� â�� ���.
					ovf = new OrderViewForm(mf);
					}else{
						ORDER_SELECT= true;
						ovf.setVisible(true);
					}//end if
				} // end if

			} // end if
		} catch (LoginException le) {
			JOptionPane.showMessageDialog(mf, "���̵� ��й�ȣ�� Ȯ���ϼ���");
		}//catch 
	}// actionPerformed

}// class
