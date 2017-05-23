package kr.co.sist.menu.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.menu.evt.LoginFormEvt;

@SuppressWarnings("serial")
public class LoginForm extends JDialog {
	private JTextField jtfId;
	private JPasswordField jpfpw;
	private JButton jbtLogin,jbtClose;
	private MenuForm mf;
	private LoginFormEvt lfe;
	public LoginForm(MenuForm mf){
		super(mf,"�ȵ��ö� ������ �α���",true);
		this.mf=mf;
		
		ImageIcon icon = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/login_bg.jpg");
		JLabel backgroundImg = new JLabel(icon);
		
		setLayout(null);
		
		backgroundImg.setBounds(0, 0, 500, 441);
		
		JPanel panleLogin = new JPanel();
		panleLogin.setLayout(new GridLayout(3,1));
		panleLogin.setBorder(new TitledBorder("�α���"));
		
		jtfId = new JTextField();
		jtfId.setBorder(new TitledBorder("���̵�"));
	
		jpfpw = new JPasswordField();
		jpfpw.setBorder(new TitledBorder("��й�ȣ"));
		
		jbtLogin= new JButton("�α���");
		jbtClose = new JButton("�ݱ�");
		JPanel panelBtn = new JPanel();
		panelBtn.add(jbtLogin);
		panelBtn.add(jbtClose);
		
		panleLogin.add(jtfId);
		panleLogin.add(jpfpw);
		panleLogin.add(panelBtn);
		
		panleLogin.setBounds(320,240,160,150);
		panleLogin.setBackground(Color.WHITE);
		panelBtn.setBackground(Color.WHITE);
		add(panleLogin);
		add(backgroundImg);
		
		//�̺�Ʈ ���
		lfe = new LoginFormEvt(this);
		jpfpw.addActionListener(lfe);
		jbtLogin.addActionListener(lfe);
		jbtClose.addActionListener(lfe);
		
		setBounds(mf.getX()+100,mf.getY()+50,500,441);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}//LoginForm
//////////////////getter///////////////////
	public JPasswordField getJpfpw() {
		return jpfpw;
	}

	public JButton getJbtLogin() {
		return jbtLogin;
	}

	public JTextField getJtfId() {
		return jtfId;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}
	//////////	LoginFormEvt���� �α��ΰ���� ����////////////////////////
	private boolean flag;
	private int cnt;
	public void setFlag(boolean flag,int cnt){
		this.cnt=cnt;
		this.flag=flag;
	}//setFlag
	//MenuFormEvt���� �α��� ����� ����.
	public boolean getFlag()throws LoginException{
		//�α����� �����Ͽ����� ���̵� ��й�ȣ�� Ʋ���� 
		//LoginException ���� �߻�
		if(cnt == 1 && !flag){
			throw new LoginException();
		}//end if
		//�α����� �������� �ʰ� �ݱ⸦ Ŭ���ϸ� ������ flase�� ��ȯ
		return flag;
	}//logResult
	
}//class
