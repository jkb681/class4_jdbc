package kr.co.sist.menu.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import kr.co.sist.menu.view.LoginForm;

public class LoginFormEvt implements ActionListener {

	private LoginForm lf;
	public LoginFormEvt(LoginForm lf) {
		this.lf = lf;

	}// LoginFormEvt ������

	private void chkNull() {
		
		String id = lf.getJtfId().getText().trim();
		String pw = new String(lf.getJpfpw().getPassword()).trim();
		if (id.equals("") || pw.equals("")) {
			JOptionPane.showMessageDialog(lf, "���̵�� ��й�ȣ�� �����ϴ�.");
			return;
		} // end if
		boolean flag = false;
			// ������ ID : root, //PW:tiger ������Ƽ���� �־ �������ָ� �� ���� �ڵ尡��
		if (id.equals("root") && pw.equals("tiger")) {
			flag=true;
		}// end if
		lf.setFlag(flag,1);//�α��� ����� ����
		lf.dispose(); //�α���â�� �ݾ� �������� �����ϵ��� �����.
	}// chkNull
	@Override
	public void actionPerformed(ActionEvent ae) {
		// �ݱ��ư
		if (ae.getSource() == lf.getJbtClose()) {
			lf.dispose();
		} // end if
			// �α��ι�ư
		if (ae.getSource() == lf.getJbtLogin()) {
			chkNull();
		} // end if

		// ���
		if (ae.getSource() == lf.getJpfpw()) {
			chkNull();
		} // end if

	}// actionPerformed

}// class
