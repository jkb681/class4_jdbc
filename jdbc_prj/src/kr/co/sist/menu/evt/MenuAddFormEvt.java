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
		FileDialog fdImg = new FileDialog(maf, "���ö� �̹��� ����", FileDialog.LOAD);
		fdImg.setVisible(true);

		String path = fdImg.getDirectory();
		String file = fdImg.getFile();

		if (file != null) { // ���࿡ ������ ���õǸ�!
			String validFile = "jpg,gif,png,bmp"; // Ȯ���ڸ� ����ذ�
			if (!validFile.contains(file.substring(file.lastIndexOf(".") + 1))) {
				// ������ ���Ͽ� .�ڿ� jpg,gif,png,bmp�� �ƴ϶��
				JOptionPane.showMessageDialog(maf, "�̹��� ������ �ƴմϴ� ~");
				return;
			} // end if
			ImageIcon temp = new ImageIcon(path + file); // ������ ���+�����̸�
			maf.getJlPreview().setIcon(temp);
		} // end if
	}// end setImg

	private void addFormClose(){
	
		//��� �Է�â �ʱ�ȭ.
		String path = "C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/default.jpg";
		ImageIcon icon = new ImageIcon(path);
		maf.getJlPreview().setIcon(icon);
		maf.getJtfMenu().setText("");
		maf.getJtfPrice().setText("");
		maf.getJtaMenuInfo().setText("");

		maf.setVisible(true);// �Է�â �����
	}//addFormClose
	
	private void addMenu() {
		// �̹����� C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/�� ����
		// �̹����� ���
		// �޴��̸�,����,����
		ImageIcon icon = (ImageIcon) maf.getJlPreview().getIcon(); // ������ ���
																	// ���������ڵ�
		File file = new File(icon.toString()); // ��θ� ���Ͽ� �����
		String tempFile = file.getName();
		// ���࿡ ������ �̸��� defauult.jpg�� ���ٸ� �⺻�̹����� �������ؼ� ������� ����
		if (tempFile.equals("defauult.jpg")) {
			JOptionPane.showMessageDialog(maf, "�⺻�̹����� ����� �Ұ����մϴ� ~");
			return;
		} // end if
		File sFile = new File(file.getParent() + "/s_" + tempFile); // ������ ��ο�
																	// /s_�� ����
																	// ������ �ִ°�.
		if (!sFile.exists()) { // ���ϸ� �տ� /s_�� �ٴ� ������ ���ٸ�
			JOptionPane.showMessageDialog(maf, "�޴� ���ý� �߰��Ǵ� ������ s_" + tempFile + "�� �ʿ��մϴ�.");
			return;
		} // end if

		// ������ �����ġ�� �̵���Ŵ(����),
		// ������ ��ġ�� ������ �����ش� ��ġ�� �ƴ϶��
		// ������ �Ѵ�.
		if (!file.getParent().equals("C:\\dev\\workspace\\jdbc_prj\\src\\kr\\co\\sist\\menu\\img")) {

			try {
				// �������� ����
				FileInputStream fis = new FileInputStream(file);
				FileOutputStream fos = new FileOutputStream(
						"C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/" + file.getName());
				
				byte[] temp = new byte[512];
				int readData = 0;

				try {
					while ((readData = fis.read(temp)) != -1) {
						//�о ������� �����Ϳ��� ù����� ũ�⸸ŭ �д´�.
						fos.write(temp, 0, readData);
					}//end while
					fos.flush();
					
					if(fis != null){fis.close();}//end if
					if(fos != null){fos.close();}//end if
					////////////////////////////////////////////////////////////////////////////
					// �޴����� ���� ����
					fis = new FileInputStream(file.getParent()+"/s_"+file.getName());//s_�� ���� ���ϸ��� �������
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
			//�޴�VO�� ���ֱ�
			mv.setImg(tempFile);
			mv.setMenu(menu);
			mv.setPrice(Integer.parseInt(price));
			mv.setInfo(info);
			
			md.insertMenu(mv);
			//���� �߰� �� �޴� ����Ʈ�� ����
			mfe.setMenu();
			//�����ڿ��� ó�� ����� �����ش�.
			
			JOptionPane.showMessageDialog(maf, menu+"�� ���������� �߰��Ǿ����ϴ�.");
			
			addFormClose();
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(maf, "������ ���ڸ� �Է� �����մϴ�.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(maf, "���� �߻� ;;;");
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
