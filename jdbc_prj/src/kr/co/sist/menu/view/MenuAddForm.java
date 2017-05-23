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
 * 메뉴를 추가하기 위한 폼
 * @author user
 */
public class MenuAddForm extends JDialog {
	private MenuForm mf;
	
	private JTextField jtfMenu,jtfPrice;
	private JTextArea jtaMenuInfo;
	private JButton jbtAdd,jbtClose,jbtImage;
	private JLabel jlPreview;
	
	public MenuAddForm(MenuForm mf,MenuFormEvt mfe) {
		super(mf," 안도시락 메뉴 추가  ",true);
		this.mf = mf;
		//244,220
		jtfMenu = new JTextField();
		jtfPrice = new JTextField();
		jtaMenuInfo = new JTextArea();
		jbtImage = new JButton("이미지 선택");
		jbtAdd = new JButton("메뉴추가");
		jbtClose = new JButton("닫기");
		
		
		//홈페이지에서 이미지를 가져와서 넣어줌.
		ImageIcon noImage =  null;
//		try {
		noImage = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/default.jpg");
//			noImage = new ImageIcon(new URL("http://sist.co.kr/default.jpg"));//URL을가져와서 사용가능하기도함
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} //catch
		setLayout(null);  //수동배치
		jlPreview = new JLabel(noImage);
		jlPreview.setBorder(new TitledBorder(" 도시락 이미지 "));
		
		JPanel panelLbl = new JPanel();
		panelLbl.setLayout(new GridLayout(3, 1));
		panelLbl.add(new JLabel("제품명 : "));
		panelLbl.add(new JLabel("가  격  : "));
		panelLbl.add(new JLabel("제품설명 : "));
		
		JPanel panelText = new JPanel();
		panelText.setLayout(new GridLayout(2, 1));
		panelText.add(jtfMenu);
		panelText.add(jtfPrice);
		
		jtfMenu.setToolTipText("도시락의 이름을 입력합니다");
		jtfPrice.setToolTipText("도시락의 가격을 입력합니다");
		
//		jtaMenuInfo.setBorder(new LineBorder(Color.darkGray)); //라인보더는 표 처럼 줄을 쳐줌.
		JScrollPane jspMenuInfo = new JScrollPane(jtaMenuInfo);
		
		jtaMenuInfo.setWrapStyleWord(true); //단어단위로 줄을바꿈
		jtaMenuInfo.setLineWrap(true); //줄바꿈
		
		
		JPanel panelBtn = new JPanel();
		panelBtn.add(jbtAdd);
		panelBtn.add(jbtClose);
	
		
		//크기설정
		panelBtn.setBounds(310,150,190,35);
		jbtImage.setBounds(45,250,150,30);
		jspMenuInfo.setBounds(330,90,150,60);
		panelText.setBounds(330,20,150,60);
		panelLbl.setBounds(260,20,70,90);
		jlPreview.setBounds(10,20,244,220);
	
		
		//마지막 배치
		add(panelBtn);
		add(jbtImage);
		add(jspMenuInfo);
		add(jlPreview);
		add(panelLbl);
		add(panelText);
		
		MenuAddFormEvt mafe = new MenuAddFormEvt(this,mfe);
		addWindowListener(mafe);//이벤트등록
		jbtAdd.addActionListener(mafe);
		jbtClose.addActionListener(mafe);
		jbtImage.addActionListener(mafe);
	
		
		
		setBounds(mf.getX()+100,mf.getY()+200,500,350);
		setVisible(true);
		
		
		
		
		
	}//MenuAddForm생성자 ~

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
