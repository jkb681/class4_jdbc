package kr.co.sist.menu.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import kr.co.sist.menu.vo.MenuVO;
import kr.co.sist.menu.vo.OrderAllVO;
import kr.co.sist.menu.vo.OrderVO;

public class MenuDAO {
	private static MenuDAO m_dao;

	private MenuDAO() { // �ܺο��� �����������ϰ� �����̺����� ���������ڸ� ��(�̱�������)

	}// MenuDAO

	public static MenuDAO getInstance() {
		if (m_dao == null) {
			m_dao = new MenuDAO();
		} // end if
		return m_dao;
	}// getInstance

	private Connection getConnection() throws SQLException {
		Connection con = null;
		Properties prop = new Properties();
		try {
			File file = new File("C:/dev/jdbc_git/jdbc_prj/src/kr/co/sist/menu/dao/MenuDAO.java");
			if (file.exists()) {
				prop.load(new FileInputStream(file));
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String id = prop.getProperty("dboid");
				String pw = prop.getProperty("dbopwd");

				try {// ����̹��ε�
					Class.forName(driver);
					con = DriverManager.getConnection(url, id, pw);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} // catch
			} else {
				JOptionPane.showMessageDialog(null, "��θ� Ȯ���ϼ���");
			} // end else if
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // catch
		return con;
		
	}// getConnection

	/**
	 * �Էµ� �޴��� ����� ��ȸ<br>
	 * item_code,img,menu,info,price�� ��ȸ�Ͽ� MenuVO�� �����ϰ� List�� �߰��Ͽ� ��ȯ�ϴ� ���� �Ѵ�.
	 * 
	 * @return : List<MenuVO>
	 * @throws SQLException
	 */
	public List<MenuVO> selectMenuList() throws SQLException {
		List<MenuVO> list = new ArrayList<MenuVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1.����̹��ε�
			// 2.Ŀ�ؼ� ���
			con = getConnection();
			// 3.������ ���� ��ü ���
			String selectMenu = "select item_code,img,menu,info,price from menu";
			pstmt = con.prepareStatement(selectMenu);
			// 4.������ ���� �� ��ü ���
			rs = pstmt.executeQuery();
			MenuVO mv = null;
			while (rs.next()) {
				// ���ͷ� �޾ƿ��� �Ǽ��� ���� ���� �� ����.
				// �̷��� ���°� �������� ����, ���� ������ ���� ���� �� ����.
				mv = new MenuVO();
				mv.setItem_code(rs.getString("item_code"));
				mv.setImg(rs.getString("img"));
				mv.setMenu(rs.getString("menu"));
				mv.setInfo(rs.getString("info"));
				mv.setPrice(rs.getInt("price"));

				list.add(mv);
			} // while
		} finally {
			// 5
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (rs != null) {
				rs.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // finally

		return list;
	}// selectMenuList

	/**
	 * �޴� �ֹ� ����(item_code,name,quan)�� ���̺� �߰��ϴ� ��
	 * 
	 * @param ov
	 * @throws SQLException
	 */
	public void insertOrder(OrderVO ov) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.����̹��ε�
			// 2.Ŀ�ؼ� ���
			con = getConnection();
			// 3. ������������ü���
			String insertOrdering = "insert into ordering(order_num,name,item_code,quan,ipaddr,order_date)"
					+ "values(seq_ordering.nextval,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(insertOrdering);
			// 4. ������ ���� �� ��� ���
			// ���ε庯�� ���־��ֱ�
			pstmt.setString(1, ov.getName());//
			pstmt.setString(2, ov.getItem_code());//
			pstmt.setInt(3, ov.getQuan());// �ֹ���
			pstmt.setString(4, ov.getIpAddr());// �������ּ�

			pstmt.executeUpdate();

		} finally {
			// 5. �������
			if (con != null) {
				con.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		} // finally

	}// insertOrder

	/**
	 * �޴��� ���̺� �߰��ϴ� ��
	 * 
	 * @param mv
	 * @throws SQLException
	 */
	public void insertMenu(MenuVO mv) throws SQLException {// �޴��� �߰����ִ� �޼ҵ�
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.����̹��ε�
			// 2.Ŀ�ؼ� ���
			con = getConnection();
			// 3. ������������ü���
			String insertMenu = "insert into menu(item_code,menu,price,info,img,inputdate)"
					+ "values(menu_num,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(insertMenu);
			// 4. ������ ���� �� ��� ���
			// ���ε� ������ ���ֱ�
			// menu,price,info,img�� ���� ��
			pstmt.setString(1, mv.getMenu());
			pstmt.setInt(2, mv.getPrice());
			pstmt.setString(3, mv.getInfo());
			pstmt.setString(4, mv.getImg());

			// ������ ����
			pstmt.executeUpdate();// �� ����.

		} finally {
			// 5. �������
			if (con != null) {
				con.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		} // finally
	}// insertMenu

	/**
	 * �׳� 1�ñ����� �ֹ������� ��ȸ�ϴ� ��
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<OrderAllVO> selectOrder() throws SQLException {
		List<OrderAllVO> list = new ArrayList<OrderAllVO>();
		// item_code,name,orderName,orderData,quan,price
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1.����̹��ε�
			// 2.Ŀ�ؼ� ���
			con = getConnection();
			// 3. ������������ü���
			StringBuilder selectOrder = new StringBuilder();
			selectOrder.append("select m.item_code, m.menu, o.order_num,o.name, to_char(o.order_date,'hh24:mi:ss') order_date")
					.append(",o.ipaddr, o.quan, (m.price*o.quan) totalprice")
					.append(" from menu m, ordering o")
					.append(" where (o.item_code=m.item_code)")
					.append(" and to_char(order_Date,'hh24')>8 and ")
					.append("to_char(order_Date,'hh24')<13 and ")
					.append("to_char(order_date,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')")
					.append(" order by order_date desc");
			
			pstmt = con.prepareStatement(selectOrder.toString());
			// 4. ������ ���� �� ��� ���

			// ������ ����
			rs = pstmt.executeQuery();

			OrderAllVO oav = null;
			while (rs.next()) {
				// ����Ʈ�� ����� �� ���� �˾ƺ��� ���Ͽ�
				oav = new OrderAllVO();
				oav.setItem_code(rs.getString("item_code"));
				oav.setMeun(rs.getString("menu"));
				oav.setName(rs.getString("name"));
				oav.setOrderDate(rs.getString("order_date"));
				oav.setIpAddr(rs.getString("ipaddr"));
				oav.setQuan(rs.getInt("quan"));
				oav.setTotalPrice(rs.getInt("totalPrice"));
				oav.setOrderNum(rs.getInt("order_num"));

				list.add(oav);// ����Ʈ�� oav�� ���� �����.
			} // while
		} finally {
			// 5. �������
			if (con != null) {
				con.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} // finally
		return list;
	}// selectOrder

	///////////// �����׽�Ʈ �޼ҵ� ����/////////////////
//	public static void main(String[] args) {
//		try {
//			 MenuDAO md = MenuDAO.getInstance();
//			List<OrderAllVO> list = md.selectOrder();
//			System.out.println(list);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} // catch
//	}// main
		///////////// �����׽�Ʈ �޼ҵ� �� /////////////////
}// class

// ���� �޼ҵ忡��

// try {
// System.out.println(MenuDAO.getInstance().getConnection());
// try {
//// �Ǿ��� ���̵�� ,�����Ǹ� ���� �� ����(�������� ��꿡 ���Ƽ� ����Ϳ� �ö�.)
//// �̻���� �ߴ��� ���ߴ����� �˱����Ͽ�
// String ip = Inet4Address.getLocalHost().toString();//�Ǿ��Ǿ��̵��,������
// MenuDAO md = MenuDAO.getInstance();
// OrderVO ov = new OrderVO("Al_00001","�쵿��",ip,1);
// md.insertOrder(ov);
// System.out.println("�ֹ��߰�����");
// } catch (UnknownHostException e) {
// e.printStackTrace();
//
// }//catch
// } catch (SQLException e) {
// e.printStackTrace();
// } //catch
