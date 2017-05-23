package kr.co.sist.menu.view;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.menu.evt.OrderFormEvt;
import kr.co.sist.menu.vo.MenuVO;

/**
 * �޴������� �����׸�(ū�̹���,�޴��ڵ�,�޴��̸�,�޴�����,���� ��), 
 * �ֹ�����(����, �� ����, �ֹ���)�� �Է��ϴ� form(ȭ��)
 * @author user
 */
@SuppressWarnings("serial")
public class OrderForm extends JDialog {

	private JTextField jtfItemCode,jtfMenu,jtfPrice,jtfName;
	private JComboBox<Integer> jcbQuan;
	private JButton jbtOrder,jbtClose;
	
	
	public OrderForm(JFrame jf, MenuVO mv) {//MenuVO�� ����ö��� �־����� �˰�����.
		//�ֹ��ý��� â���� ����Ŭ���� ���� ������ 
		//���ö� �ֹ�â�� �ڽ�â�� ����鼭 ���ö� �ֹ� â�� ����
			super(jf,"���ö� �ֹ�â",true); // �ڽ�â�� �������� �θ�â�� ������ ���� ��޷� ����.
			
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder("���ֹ�")); //Ÿ��Ʋ�ټ���
			panel.setLayout(null);//������ġ�� �ϱ����Ͽ� �������
			//��üȭ
			jtfItemCode = new JTextField();
			jtfMenu = new JTextField();
			jtfPrice = new JTextField();
			jtfName = new JTextField(10);
			
			//�޴��ڵ�,�޴���,������ �б�����(�����Ұ�)
			jtfItemCode.setEditable(false);
			jtfMenu.setEditable(false);
			jtfPrice.setEditable(false);
			
			Integer[] num = {1,2,3,4,5,6,7,8,9,10}; //���ö��� 1~10������ ��ų �� ����
			//2�����迭�� �־��ֱ� ���Ͽ� ���
			DefaultComboBoxModel<Integer> dcmb = new DefaultComboBoxModel<Integer>(num);
			
			jcbQuan = new JComboBox<Integer>(dcmb); //��� �޺��ڽ��� ������ 1~10�߿� �ϳ����ð����ϰ� �������.
			jbtOrder = new JButton("�ֹ�");
			jbtClose = new JButton("�ݱ�");
			
			File file = new File(mv.getImg());//�̹����� ���ԵǾ� �ִ� �����
			//getParent�θ��� ��θ� �ҷ��ְ� getName�� ������ �̸��� �ҷ��´� 
			//��ο� /�� ���̰� �׵ڿ� 2���ڸ� ¥�� �����̸��� �ҷ��ش�.
			ImageIcon ii = new ImageIcon(file.getParent()+"/"+file.getName().substring(2)); 
			JLabel jlimg = new JLabel(ii); //�󺧿� �̹��� �־���
			jlimg.setToolTipText("��� �̹����� �ߺ��̹Ƿ� ���� ��ǰ���� ���̰� �������� �ֽ��ϴ�.");
			
			
			JPanel jpLbl = new JPanel();
			//��ǰ�̹��� ���� ����
			jpLbl.setLayout(new GridLayout(4, 1));
			jpLbl.add(new JLabel("���ö���  : "));
			jpLbl.add(new JLabel("��ǰ�ڵ�  : "));
			jpLbl.add(new JLabel("��ǰ����  : "));
			jpLbl.add(new JLabel("��ǰ����  : "));
			
			JPanel jtTxt = new JPanel();
			jtTxt.setLayout(new GridLayout(3, 1));
			
			//j�󺧿��� ���ö���,�ڵ�,����,����� ��� ��������.
			jtTxt.add(jtfMenu);
			jtTxt.add(jtfItemCode);
			jtTxt.add(jtfPrice);
			
		
			
			//�Է��� ���� �޾ƿ�.
			jtfMenu.setText(mv.getMenu());
			jtfItemCode.setText(mv.getItem_code());
			jtfPrice.setText(String.valueOf(mv.getPrice()));
			
			JTextArea jtfInfo = new JTextArea(mv.getInfo());
			jtfInfo.setEditable(false); //�����Ұ�
			jtfInfo.setLineWrap(true);
			jtfInfo.setWrapStyleWord(true);
			JScrollPane jspInfo = new JScrollPane(jtfInfo);
			
			ImageIcon iiAd = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/ad.gif");//�̹����ֱ�
			JLabel jlAd = new JLabel(iiAd);
			
			JPanel jpOrder = new JPanel();
			jpOrder.setBorder(new TitledBorder("�ֹ�����"));
			jpOrder.add(new JLabel("�ֹ��� : " ));
			jpOrder.add(jtfName);
			jpOrder.add(new JLabel("���� : " ));
			jpOrder.add(jcbQuan);
			
			JPanel jpBtn = new JPanel(); 
			jpBtn.add(jbtOrder);
			jpBtn.add(jbtClose);
			
			
			
			//��ġ��ġ, ũ�� ���� : ���� 244
			jpBtn.setBounds(360,330,300,40);
			jpOrder.setBounds(280, 250, 300, 60);
			jlAd.setBounds(10, 250,200,90);
			jlimg.setBounds(10, 20, 244, 220);
			jpLbl.setBounds(280,20,70,100);
			jtTxt.setBounds(350,20,230,80);
			jspInfo.setBounds(350,100,230,100);
			
			//��ġ
			
			panel.add(jpBtn);
			panel.add(jpOrder);
			panel.add(jlAd);
			panel.add(jlimg);
			panel.add(jpLbl);
			panel.add(jtTxt);
			panel.add(jspInfo);
			add("Center",panel);
//			pack(); // ������ȿ� �� �ִ� ������Ʈ ũ�⿡ �°� ũ�⸦ �ڵ����� ���� (�ڵ���ġ������ ��밡��)
			
			// �̺�Ʈ ���
			OrderFormEvt ofe = new OrderFormEvt(this);
			jbtOrder.addActionListener(ofe);
			jbtClose.addActionListener(ofe);
			
			
			setResizable(false);//���ö� â�� ũ�� ����Ұ���	
			setBounds(jf.getX()+160, jf.getY()+120, 600,410);//�θ�â�ȿ��� �ڽ�â�� �߰Բ� ũ�� ����
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//
			
			jtfName.requestFocus();
	}// �����ִ� ������

////////////////////////getter
	public JTextField getJtfItemCode() {
		return jtfItemCode;
	}


	public JTextField getJtfMenu() {
		return jtfMenu;
	}


	public JTextField getJtfPrice() {
		return jtfPrice;
	}


	public JTextField getJtfName() {
		return jtfName;
	}


	public JComboBox<Integer> getJcbQuan() {
		return jcbQuan;
	}


	public JButton getJbtOrder() {
		return jbtOrder;
	}


	public JButton getJbtClose() {
		return jbtClose;
	}

}//class �⺻�����ڰ� �ִ� ����� ������ ��������.
