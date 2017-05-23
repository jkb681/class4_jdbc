package kr.co.sist.menu.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import kr.co.sist.menu.view.LoginForm;

public class LoginFormEvt implements ActionListener {

	private LoginForm lf;
	public LoginFormEvt(LoginForm lf) {
		this.lf = lf;

	}// LoginFormEvt 생성자

	private void chkNull() {
		
		String id = lf.getJtfId().getText().trim();
		String pw = new String(lf.getJpfpw().getPassword()).trim();
		if (id.equals("") || pw.equals("")) {
			JOptionPane.showMessageDialog(lf, "아이디와 비밀번호가 없습니다.");
			return;
		} // end if
		boolean flag = false;
			// 관리자 ID : root, //PW:tiger 프라퍼티저에 넣어서 진행해주면 더 좋은 코드가됨
		if (id.equals("root") && pw.equals("tiger")) {
			flag=true;
		}// end if
		lf.setFlag(flag,1);//로그인 결과를 설정
		lf.dispose(); //로그인창을 닫아 다음으로 진행하도록 만든다.
	}// chkNull
	@Override
	public void actionPerformed(ActionEvent ae) {
		// 닫기버튼
		if (ae.getSource() == lf.getJbtClose()) {
			lf.dispose();
		} // end if
			// 로그인버튼
		if (ae.getSource() == lf.getJbtLogin()) {
			chkNull();
		} // end if

		// 비번
		if (ae.getSource() == lf.getJpfpw()) {
			chkNull();
		} // end if

	}// actionPerformed

}// class
