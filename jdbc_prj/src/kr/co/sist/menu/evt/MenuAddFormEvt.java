package kr.co.sist.menu.evt;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.MenuAddForm;
import kr.co.sist.menu.vo.MenuVO;

/**
 * @author user
 *
 */
public class MenuAddFormEvt extends WindowAdapter implements ActionListener {
	private MenuAddForm maf;
	private MenuFormEvt mfe;

	public MenuAddFormEvt(MenuAddForm maf, MenuFormEvt mfe) {
		this.maf = maf;
		this.mfe = mfe;

	}// MenuAddFormEvt

	@Override
	public void windowClosing(WindowEvent e) {
		maf.setVisible(false);

	}// windowClosing

	public void setImg() {
		FileDialog fdImg = new FileDialog(maf, "도시락 이미지 선택", FileDialog.LOAD);
		fdImg.setVisible(true);

		String path = fdImg.getDirectory();
		String file = fdImg.getFile();

		if (file != null) { // 만약에 파일이 선택되면!
			String validFile = "jpg,gif,png,bmp"; // 확장자를 골라준것
			if (!validFile.contains(file.substring(file.lastIndexOf(".") + 1))) {
				// 선택한 파일에 .뒤에 jpg,gif,png,bmp가 아니라면
				JOptionPane.showMessageDialog(maf, "이미지 파일이 아닙니다 ~");
				return;
			} // end if
			ImageIcon temp = new ImageIcon(path + file); // 템프에 경로+파일이름
			maf.getJlPreview().setIcon(temp);
		} // end if
	}// end setImg

	private void addFormClose(){
	
		//모든 입력창 초기화.
		String path = "C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/default.jpg";
		ImageIcon icon = new ImageIcon(path);
		maf.getJlPreview().setIcon(icon);
		maf.getJtfMenu().setText("");
		maf.getJtfPrice().setText("");
		maf.getJtaMenuInfo().setText("");

		maf.setVisible(true);// 입력창 숨기기
	}//addFormClose
	
	private void addMenu() {
		// 이미지는 C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/에 복사
		// 이미지의 경로
		// 메뉴이름,가격,설명
		ImageIcon icon = (ImageIcon) maf.getJlPreview().getIcon(); // 아이콘 경로
																	// 가져오는코드
		File file = new File(icon.toString()); // 경로를 파일에 담아줌
		String tempFile = file.getName();
		// 만약에 가져온 이름이 defauult.jpg와 같다면 기본이미지로 생각을해서 등록하지 않음
		if (tempFile.equals("defauult.jpg")) {
			JOptionPane.showMessageDialog(maf, "기본이미지는 등록이 불가능합니다 ~");
			return;
		} // end if
		File sFile = new File(file.getParent() + "/s_" + tempFile); // 선택한 경로에
																	// /s_가 붙은
																	// 파일이 있는가.
		if (!sFile.exists()) { // 파일명 앞에 /s_가 붙는 파일이 없다면
			JOptionPane.showMessageDialog(maf, "메뉴 선택시 추가되는 파일인 s_" + tempFile + "이 필요합니다.");
			return;
		} // end if

		// 파일을 사용위치에 이동시킴(복붙),
		// 선택한 위치가 파일을 보여준느 위치가 아니라면
		// 복붙을 한다.
		if (!file.getParent().equals("C:\\dev\\workspace\\jdbc_prj\\src\\kr\\co\\sist\\menu\\img")) {

			try {
				// 원본파일 복붙
				FileInputStream fis = new FileInputStream(file);
				FileOutputStream fos = new FileOutputStream(
						"C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/" + file.getName());
				
				byte[] temp = new byte[512];
				int readData = 0;

				try {
					while ((readData = fis.read(temp)) != -1) {
						//읽어서 저장들인 데이터에서 첫방부터 크기만큼 읽는다.
						fos.write(temp, 0, readData);
					}//end while
					fos.flush();
					
					if(fis != null){fis.close();}//end if
					if(fos != null){fos.close();}//end if
					////////////////////////////////////////////////////////////////////////////
					// 메뉴선택 파일 복붙
					fis = new FileInputStream(file.getParent()+"/s_"+file.getName());//s_가 붙은 파일명을 가지고옴
					fos = new FileOutputStream("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/s_" + file.getName());
					
					while( (readData = fis.read() ) != -1){
						fos.write(readData);
					}//end while
					
					fos.flush();
					
					if(fis != null){fis.close();}//end if
					if(fos != null){fos.close();}//end if
					
					
				} catch (FileNotFoundException fne) {
					fne.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} //end catch

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} // end catch

		} // end if

		String menu = maf.getJtfMenu().getText();
		String price = maf.getJtfPrice().getText();
		String info = maf.getJtaMenuInfo().getText();
		
		MenuDAO md = MenuDAO.getInstance();
		MenuVO mv = new MenuVO();
		try {
			//메뉴VO에 값넣기
			mv.setImg(tempFile);
			mv.setMenu(menu);
			mv.setPrice(Integer.parseInt(price));
			mv.setInfo(info);
			
			md.insertMenu(mv);
			//정상 추가 후 메뉴 리스트를 갱신
			mfe.setMenu();
			//관리자에게 처리 결과를 보여준다.
			
			JOptionPane.showMessageDialog(maf, menu+"가 정상적으로 추가되었습니다.");
			
			addFormClose();
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(maf, "가격은 숫자만 입력 가능합니다.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(maf, "문제 발생 ;;;");
			e.printStackTrace();
		} //end catch

	}// addMenu

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == maf.getJbtAdd()) {
			addMenu();

		} // end if
		if (ae.getSource() == maf.getJbtClose()) {
			 addFormClose();
		}

		if (ae.getSource() == maf.getJbtImage()) {
			setImg();
		} // end if

	}// actionPerformed

}// class
