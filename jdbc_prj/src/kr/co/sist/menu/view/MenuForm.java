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
 * ��(�޴�����,�޴��߰�,�ֹ���Ȳ)�� ������ ����ڿ��� �����ִ� form(ȭ��)
 * @author user
 */
@SuppressWarnings("serial")
public class MenuForm extends JFrame {

	private JTable jtMenu;
	private DefaultTableModel dtmMenu;
	private JTabbedPane jtpTab; //���� ������ִ� ������Ʈ
	
	private JButton jbtOrderList;
	private JButton jbtMenuAdd;


	
	
	public MenuForm(){
		super("�ȵ��ö� - �ֹ��ý���");
	
		String[] columnNames={"��ȣ","�̹���","�޴���ȣ","�޴��̸�","�޴�����","����"};
		String[][] data={};
		//�迭�� �־���
		dtmMenu = new DefaultTableModel(data,columnNames){

			//��ϸӽ� �̳�Ŭ���� (���̺��� ������ �������Ͽ� ���)
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
			
		}; //DefaultTableModel
		jtMenu = new JTable(dtmMenu){//j���̺� �迭���� ���� �־���.
			
			//�÷��� �̹����� �ֱ����Ͽ� ����ϴ� �޼ҵ�
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}//getColumnClass
			
		}; //jtMenu
		
		//�÷��� �����Ͽ� �������� ���ϵ���  ������. ���콺�� �̵��Ұ�������(�����Ұ�����)
		jtMenu.getTableHeader().setReorderingAllowed(false);
		//�÷��� ���� ����
		jtMenu.setRowHeight(100);
		//�÷��� ���� ����
		jtMenu.getColumnModel().getColumn(0).setPreferredWidth(40);//��ȣ
		jtMenu.getColumnModel().getColumn(1).setPreferredWidth(150);//�̹���
		jtMenu.getColumnModel().getColumn(2).setPreferredWidth(80);//�޴���ȣ
		jtMenu.getColumnModel().getColumn(3).setPreferredWidth(120);//�޴��̸�
		jtMenu.getColumnModel().getColumn(4).setPreferredWidth(470);//�޴�����
		jtMenu.getColumnModel().getColumn(5).setPreferredWidth(40);//����
		
		JScrollPane jspMenu = new JScrollPane(jtMenu); //j���̺� ��ũ�ѹٸ� ����
		// �̹������� �ֱ�
		ImageIcon logo = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/main_logo.png");
		
		JLabel jlLogo = new JLabel(logo); //j�󺧿� �̹����� �־���.
		
		jlLogo.setToolTipText("��ٷο� ������ �Ը��� ��Ȯ�ϰ� ���ߴ� �ȵ��ö�!!"); //????
		
		
		JPanel jpMenu = new JPanel(); //�г��� ����
		jpMenu.setLayout(new BorderLayout());//j���̺��� �������̾ƿ����� �������
		
		// j���̺�  �޴���ġ
		jpMenu.add("North", jlLogo); 
		jpMenu.add("Center", jspMenu); 
		
		jpMenu.setBorder(new TitledBorder("�� ���ö��� �ְ��� �޴�")); //j���̺� Ÿ��Ʋ�� �Է�.
		
		ImageIcon iiInfo = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/donghaFE.gif");
		JLabel lblInfo = new JLabel(iiInfo);
		String msg = "���� �䱸�� �ż��ϰ� ö���ϰ� ������ �� �ִ�\n"
				+ "(��)���� �� 2017�� �����Ͽ� ������ �̷� \n "
				+ "�ְ��� ���ö��� �����ϱ� ���� ��(����)����(����)�� "
				+ "�п��� �������� �ּ��� ���Ұ� ������?";
		JPanel jpComInfo = new JPanel();
		jpComInfo.add(lblInfo);
		jpComInfo.add(new JTextArea(msg));
		
		jbtOrderList = new JButton("�ֹ� ��ȸ");
		jbtMenuAdd = new JButton("�޴� �߰�");
		
		JPanel jpBtn = new JPanel();
		jpBtn.setLayout(new FlowLayout(FlowLayout.TRAILING));
		jpBtn.add(jbtOrderList);
		jpBtn.add(jbtMenuAdd);
		
		jtpTab = new JTabbedPane();
		jtpTab.add("�޴�",jpMenu);
		jtpTab.addTab("�ȵ��ö�����?", jpComInfo);
		
		//j���̺� ��ġ
		add("North", jpBtn);
		add("Center",jtpTab);
	
		jpMenu.setBackground(Color.WHITE);//j���̺������
		
		MenuFormEvt mfe = new MenuFormEvt(this);
		jtMenu.addMouseListener(mfe);
		jbtMenuAdd.addActionListener(mfe);
		jbtOrderList.addActionListener(mfe);
		
		
		// ���α׷��� �����ϱ����Ͽ�
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we){
				dispose();
				System.exit(0);
			}//windowClosing
		});//WindowAdapter
		
		setBounds(10,10,900,600); //������â ũ�� ����
		setVisible(true);//����ȭ
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //â������
		
		
	}//������

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


