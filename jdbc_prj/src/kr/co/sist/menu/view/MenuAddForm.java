package kr.co.sist.menu.view;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.menu.evt.MenuAddFormEvt;
import kr.co.sist.menu.evt.MenuFormEvt;

/**
 * �޴��� �߰��ϱ� ���� ��
 * @author user
 */
public class MenuAddForm extends JDialog {
	private MenuForm mf;
	
	private JTextField jtfMenu,jtfPrice;
	private JTextArea jtaMenuInfo;
	private JButton jbtAdd,jbtClose,jbtImage;
	private JLabel jlPreview;
	
	public MenuAddForm(MenuForm mf,MenuFormEvt mfe) {
		super(mf," �ȵ��ö� �޴� �߰�  ",true);
		this.mf = mf;
		//244,220
		jtfMenu = new JTextField();
		jtfPrice = new JTextField();
		jtaMenuInfo = new JTextArea();
		jbtImage = new JButton("�̹��� ����");
		jbtAdd = new JButton("�޴��߰�");
		jbtClose = new JButton("�ݱ�");
		
		
		//Ȩ���������� �̹����� �����ͼ� �־���.
		ImageIcon noImage =  null;
//		try {
		noImage = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/default.jpg");
//			noImage = new ImageIcon(new URL("http://sist.co.kr/default.jpg"));//URL�������ͼ� ��밡���ϱ⵵��
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} //catch
		setLayout(null);  //������ġ
		jlPreview = new JLabel(noImage);
		jlPreview.setBorder(new TitledBorder(" ���ö� �̹��� "));
		
		JPanel panelLbl = new JPanel();
		panelLbl.setLayout(new GridLayout(3, 1));
		panelLbl.add(new JLabel("��ǰ�� : "));
		panelLbl.add(new JLabel("��  ��  : "));
		panelLbl.add(new JLabel("��ǰ���� : "));
		
		JPanel panelText = new JPanel();
		panelText.setLayout(new GridLayout(2, 1));
		panelText.add(jtfMenu);
		panelText.add(jtfPrice);
		
		jtfMenu.setToolTipText("���ö��� �̸��� �Է��մϴ�");
		jtfPrice.setToolTipText("���ö��� ������ �Է��մϴ�");
		
//		jtaMenuInfo.setBorder(new LineBorder(Color.darkGray)); //���κ����� ǥ ó�� ���� ����.
		JScrollPane jspMenuInfo = new JScrollPane(jtaMenuInfo);
		
		jtaMenuInfo.setWrapStyleWord(true); //�ܾ������ �����ٲ�
		jtaMenuInfo.setLineWrap(true); //�ٹٲ�
		
		
		JPanel panelBtn = new JPanel();
		panelBtn.add(jbtAdd);
		panelBtn.add(jbtClose);
	
		
		//ũ�⼳��
		panelBtn.setBounds(310,150,190,35);
		jbtImage.setBounds(45,250,150,30);
		jspMenuInfo.setBounds(330,90,150,60);
		panelText.setBounds(330,20,150,60);
		panelLbl.setBounds(260,20,70,90);
		jlPreview.setBounds(10,20,244,220);
	
		
		//������ ��ġ
		add(panelBtn);
		add(jbtImage);
		add(jspMenuInfo);
		add(jlPreview);
		add(panelLbl);
		add(panelText);
		
		MenuAddFormEvt mafe = new MenuAddFormEvt(this,mfe);
		addWindowListener(mafe);//�̺�Ʈ���
		jbtAdd.addActionListener(mafe);
		jbtClose.addActionListener(mafe);
		jbtImage.addActionListener(mafe);
	
		
		
		setBounds(mf.getX()+100,mf.getY()+200,500,350);
		setVisible(true);
		
		
		
		
		
	}//MenuAddForm������ ~

	public MenuForm getMf() {
		return mf;
	}

	public JTextField getJtfMenu() {
		return jtfMenu;
	}

	public JTextField getJtfPrice() {
		return jtfPrice;
	}

	public JTextArea getJtaMenuInfo() {
		return jtaMenuInfo;
	}

	public JButton getJbtAdd() {
		return jbtAdd;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}

	public JButton getJbtImage() {
		return jbtImage;
	}

	public JLabel getJlPreview() {
		return jlPreview;
	}

}//class
