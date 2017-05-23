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

	private MenuDAO() { // 외부에서 접근하지못하게 프라이빗으로 접근지정자를 줌(싱글턴패턴)

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

				try {// 드라이버로딩
					Class.forName(driver);
					con = DriverManager.getConnection(url, id, pw);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} // catch
			} else {
				JOptionPane.showMessageDialog(null, "경로를 확인하세요");
			} // end else if
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // catch
		return con;
		
	}// getConnection

	/**
	 * 입력된 메뉴의 목록을 조회<br>
	 * item_code,img,menu,info,price를 조회하여 MenuVO에 저장하고 List에 추가하여 반환하는 일을 한다.
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
			// 1.드라이버로딩
			// 2.커넥션 얻기
			con = getConnection();
			// 3.쿼리문 생성 객체 얻기
			String selectMenu = "select item_code,img,menu,info,price from menu";
			pstmt = con.prepareStatement(selectMenu);
			// 4.쿼리문 실행 후 객체 얻기
			rs = pstmt.executeQuery();
			MenuVO mv = null;
			while (rs.next()) {
				// 셋터로 받아오면 실수를 많이 줄일 수 있음.
				// 이렇게 쓰는게 가독성이 좋음, 값의 꼬임을 많이 줄일 수 있음.
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
	 * 메뉴 주문 정보(item_code,name,quan)를 테이블에 추가하는 일
	 * 
	 * @param ov
	 * @throws SQLException
	 */
	public void insertOrder(OrderVO ov) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.드라이버로딩
			// 2.커넥션 얻기
			con = getConnection();
			// 3. 쿼리문생성객체얻기
			String insertOrdering = "insert into ordering(order_num,name,item_code,quan,ipaddr,order_date)"
					+ "values(seq_ordering.nextval,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(insertOrdering);
			// 4. 쿼리문 실행 후 결과 얻기
			// 바인드변수 값넣어주기
			pstmt.setString(1, ov.getName());//
			pstmt.setString(2, ov.getItem_code());//
			pstmt.setInt(3, ov.getQuan());// 주문수
			pstmt.setString(4, ov.getIpAddr());// 아이피주소

			pstmt.executeUpdate();

		} finally {
			// 5. 연결끊기
			if (con != null) {
				con.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		} // finally

	}// insertOrder

	/**
	 * 메뉴를 테이블에 추가하는 일
	 * 
	 * @param mv
	 * @throws SQLException
	 */
	public void insertMenu(MenuVO mv) throws SQLException {// 메뉴를 추가해주는 메소드
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.드라이버로딩
			// 2.커넥션 얻기
			con = getConnection();
			// 3. 쿼리문생성객체얻기
			String insertMenu = "insert into menu(item_code,menu,price,info,img,inputdate)"
					+ "values(menu_num,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(insertMenu);
			// 4. 쿼리문 실행 후 결과 얻기
			// 바인드 변수에 값넣기
			// menu,price,info,img에 들어가는 값
			pstmt.setString(1, mv.getMenu());
			pstmt.setInt(2, mv.getPrice());
			pstmt.setString(3, mv.getInfo());
			pstmt.setString(4, mv.getImg());

			// 쿼리문 실행
			pstmt.executeUpdate();// 값 변경.

		} finally {
			// 5. 연결끊기
			if (con != null) {
				con.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		} // finally
	}// insertMenu

	/**
	 * 그날 1시까지의 주문정보를 조회하는 일
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
			// 1.드라이버로딩
			// 2.커넥션 얻기
			con = getConnection();
			// 3. 쿼리문생성객체얻기
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
			// 4. 쿼리문 실행 후 결과 얻기

			// 쿼리문 실행
			rs = pstmt.executeQuery();

			OrderAllVO oav = null;
			while (rs.next()) {
				// 리스트를 찍었을 때 값을 알아보기 위하여
				oav = new OrderAllVO();
				oav.setItem_code(rs.getString("item_code"));
				oav.setMeun(rs.getString("menu"));
				oav.setName(rs.getString("name"));
				oav.setOrderDate(rs.getString("order_date"));
				oav.setIpAddr(rs.getString("ipaddr"));
				oav.setQuan(rs.getInt("quan"));
				oav.setTotalPrice(rs.getInt("totalPrice"));
				oav.setOrderNum(rs.getInt("order_num"));

				list.add(oav);// 리스트에 oav의 값을 담아줌.
			} // while
		} finally {
			// 5. 연결끊기
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

	///////////// 단위테스트 메소드 시작/////////////////
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
		///////////// 단위테스트 메소드 끝 /////////////////
}// class

// 메인 메소드에서

// try {
// System.out.println(MenuDAO.getInstance().getConnection());
// try {
//// 피씨의 아이디와 ,아이피를 얻을 수 있음(추적가능 허브에 남아서 라우터에 올라감.)
//// 이사람이 했는지 안했는지를 알기위하여
// String ip = Inet4Address.getLocalHost().toString();//피씨의아이디와,아이피
// MenuDAO md = MenuDAO.getInstance();
// OrderVO ov = new OrderVO("Al_00001","우동하",ip,1);
// md.insertOrder(ov);
// System.out.println("주문추가성공");
// } catch (UnknownHostException e) {
// e.printStackTrace();
//
// }//catch
// } catch (SQLException e) {
// e.printStackTrace();
// } //catch
