package kr.co.sist.menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.evt.MenuFormEvt;

/**
 * 탭(메뉴선택,메뉴추가,주문현황)을 가지고 사용자에게 보여주는 form(화면)
 * @author user
 */
@SuppressWarnings("serial")
public class MenuForm extends JFrame {

	private JTable jtMenu;
	private DefaultTableModel dtmMenu;
	private JTabbedPane jtpTab; //탭을 만들어주는 컴포넌트
	
	private JButton jbtOrderList;
	private JButton jbtMenuAdd;


	
	
	public MenuForm(){
		super("안도시락 - 주문시스템");
	
		String[] columnNames={"번호","이미지","메뉴번호","메뉴이름","메뉴설명","가격"};
		String[][] data={};
		//배열을 넣어줌
		dtmMenu = new DefaultTableModel(data,columnNames){

			//어나니머스 이너클래스 (테이블의 편집을 막기위하여 사용)
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
			
		}; //DefaultTableModel
		jtMenu = new JTable(dtmMenu){//j테이블에 배열들의 값을 넣어줌.
			
			//컬럼에 이미지를 넣기위하여 사용하는 메소드
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}//getColumnClass
			
		}; //jtMenu
		
		//컬럼을 선택하여 움직이지 못하도록  막아줌. 마우스로 이동불가능해짐(편집불가능함)
		jtMenu.getTableHeader().setReorderingAllowed(false);
		//컬럼의 높이 설정
		jtMenu.setRowHeight(100);
		//컬럼의 넓이 설정
		jtMenu.getColumnModel().getColumn(0).setPreferredWidth(40);//번호
		jtMenu.getColumnModel().getColumn(1).setPreferredWidth(150);//이미지
		jtMenu.getColumnModel().getColumn(2).setPreferredWidth(80);//메뉴번호
		jtMenu.getColumnModel().getColumn(3).setPreferredWidth(120);//메뉴이름
		jtMenu.getColumnModel().getColumn(4).setPreferredWidth(470);//메뉴설명
		jtMenu.getColumnModel().getColumn(5).setPreferredWidth(40);//가격
		
		JScrollPane jspMenu = new JScrollPane(jtMenu); //j테이블에 스크롤바를 만듬
		// 이미지파일 넣기
		ImageIcon logo = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/main_logo.png");
		
		JLabel jlLogo = new JLabel(logo); //j라벨에 이미지를 넣어줌.
		
		jlLogo.setToolTipText("까다로운 고객님의 입맛을 정확하게 맞추는 안도시락!!"); //????
		
		
		JPanel jpMenu = new JPanel(); //패널을 만들어서
		jpMenu.setLayout(new BorderLayout());//j테이블을 보더레이아웃으로 만들어줌
		
		// j테이블에  메뉴배치
		jpMenu.add("North", jlLogo); 
		jpMenu.add("Center", jspMenu); 
		
		jpMenu.setBorder(new TitledBorder("안 도시락의 최고의 메뉴")); //j테이블에 타이틀을 입력.
		
		ImageIcon iiInfo = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/donghaFE.gif");
		JLabel lblInfo = new JLabel(iiInfo);
		String msg = "고객의 요구에 신속하고 철저하게 응답할 수 있는\n"
				+ "(주)동하 는 2017년 급조하여 설립된 이래 \n "
				+ "최고의 도시락을 제공하기 위해 임(동하)직원(기준)이 "
				+ "분열된 마음으로 최선을 다할것 같으냐?";
		JPanel jpComInfo = new JPanel();
		jpComInfo.add(lblInfo);
		jpComInfo.add(new JTextArea(msg));
		
		jbtOrderList = new JButton("주문 조회");
		jbtMenuAdd = new JButton("메뉴 추가");
		
		JPanel jpBtn = new JPanel();
		jpBtn.setLayout(new FlowLayout(FlowLayout.TRAILING));
		jpBtn.add(jbtOrderList);
		jpBtn.add(jbtMenuAdd);
		
		jtpTab = new JTabbedPane();
		jtpTab.add("메뉴",jpMenu);
		jtpTab.addTab("안도시락은요?", jpComInfo);
		
		//j테이블에 배치
		add("North", jpBtn);
		add("Center",jtpTab);
	
		jpMenu.setBackground(Color.WHITE);//j테이블색변경
		
		MenuFormEvt mfe = new MenuFormEvt(this);
		jtMenu.addMouseListener(mfe);
		jbtMenuAdd.addActionListener(mfe);
		jbtOrderList.addActionListener(mfe);
		
		
		// 프로그램을 종료하기위하여
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we){
				dispose();
				System.exit(0);
			}//windowClosing
		});//WindowAdapter
		
		setBounds(10,10,900,600); //윈도우창 크기 설정
		setVisible(true);//가시화
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창나가기
		
		
	}//생성자

//getter 	
	
	
	public JTable getJtMenu() {
		return jtMenu;
	}
	
	public JButton getJbtOrderList() {
		return jbtOrderList;
	}

	public JButton getJbtMenuAdd() {
		return jbtMenuAdd;
	}

	public DefaultTableModel getDtmMenu() {
		return dtmMenu;
	}

	
}//class


