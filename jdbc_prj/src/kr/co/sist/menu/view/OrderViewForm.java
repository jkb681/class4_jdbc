package kr.co.sist.menu.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.evt.MenuFormEvt;
import kr.co.sist.menu.evt.OrderViewFormEvt;

/**
 * 실행되는 날짜의 12시까지의 주문현황을 30초 단위로 갱신하여 보여주는 폼(화면)
 * 30초단위로 갱신해야하기 때문에 쓰레드를 써야함
 * @author user
 */
@SuppressWarnings("serial")
public class OrderViewForm extends JDialog {
	private MenuForm mf;
	private JTable jtOrder;
	private DefaultTableModel dtmOrder;
	private JButton btnClose;
	private JLabel orderResult;
	public OrderViewForm(MenuForm mf) {
		super(mf,"오늘의 주문리스트",false);
		this.mf = mf; //has a 관계 설정
		
		//2차원 배열생성 디폴트테이블 모델에 넣을 컬럼명과,값
		String [] columnNames={"번호","주문번호","도시락명","도시락코드","주문자","수량","가격","주문일시"};
		String [][] data = {};

		dtmOrder = new DefaultTableModel(data, columnNames){

			@Override//디폴트 테이블모델에 어나니머스 이너클래스
			public boolean isCellEditable(int row, int column) {
				//사용자가 수정할 수 없게 만들어줌.
				return false;
			}};
		orderResult = new JLabel("주문결과");
		jtOrder = new JTable(dtmOrder);
		btnClose = new JButton("닫기");
		
		
		JScrollPane jspOrder = new JScrollPane(jtOrder);
		jspOrder.setBorder(new TitledBorder("주문현황"));
		JPanel jpBtn = new JPanel();
		jpBtn.setLayout(new FlowLayout(FlowLayout.TRAILING)); // ??상수????
		jpBtn.add(orderResult);
		jpBtn.add(btnClose);
		
		//컬럼명을 선택했을 시 움직이지 못하게 막아준다.
		jtOrder.getTableHeader().setReorderingAllowed(false);
		//컬럼의 크기 설정
		jtOrder.getColumnModel().getColumn(0).setPreferredWidth(20);
		jtOrder.getColumnModel().getColumn(1).setPreferredWidth(50);
		jtOrder.getColumnModel().getColumn(2).setPreferredWidth(140);
		jtOrder.getColumnModel().getColumn(3).setPreferredWidth(60);
		jtOrder.getColumnModel().getColumn(4).setPreferredWidth(100);
		jtOrder.getColumnModel().getColumn(5).setPreferredWidth(60);
		jtOrder.getColumnModel().getColumn(6).setPreferredWidth(100);
		jtOrder.getColumnModel().getColumn(7).setPreferredWidth(100);
		
		
		
		add("Center",jspOrder);
		add("South",jpBtn);
		
		//이벤트등록
		OrderViewFormEvt ovfe = new OrderViewFormEvt(this);

		//Threa를 실행하여 지정한 시간(20초)에 DB의 조회를 
		//수행하여 주문사항을 최신으로 유지한다.
		MenuFormEvt.ORDER_SELECT = true;
		Thread threadOrder = new Thread(ovfe);
		threadOrder.start(); //런메소드를 불러온것.
		
		addWindowListener(ovfe);
		btnClose.addActionListener(ovfe);
		
		
		setBounds(mf.getX()+100,mf.getY()+100,700,400);//창의크기
		setVisible(true);//가시화
		setResizable(false); //크기수정불가
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 창나가기
	}//OrderViewForm
	
public JLabel getOrderResult() {
		return orderResult;
	}

	///////////////////getter
	public JTable getJtOrder() {
		return jtOrder;
	}
	public DefaultTableModel getDtmOrder() {
		return dtmOrder;
	}
	
	
	
}//class
