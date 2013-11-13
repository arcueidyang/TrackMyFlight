package gui.canvas;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

public class Console extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4008118987539502745L;

	private static Console myConsole = new Console();
	
	private Console() {
		init();
	}
	
	private void init() {
		final JPopupMenu menu = createPopupMenu(this);
		MouseListener mouseListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getButton() == MouseEvent.BUTTON3) {
					menu.setLocation(arg0.getXOnScreen(), arg0.getYOnScreen());
					menu.setVisible(true);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			    //do nothing
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				//do nothing	
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				//do nothing
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//do nothing
			}
		};
		addMouseListener(mouseListener);
	}
	
	private JPopupMenu createPopupMenu(final JTextArea textArea) {
		final JPopupMenu rootMenu = new JPopupMenu("Right click menu"); 
	    JMenuItem pasteItem = new JMenuItem("Paste");
	    pasteItem.setMnemonic(KeyEvent.VK_P);
	    ActionListener pasteListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				Transferable tran = null;
				
				tran = clipboard.getContents(null);
				
				String content = null;
				
				if(tran != null && tran.isDataFlavorSupported(DataFlavor.stringFlavor)){
				    try {
					     content = (String)tran.getTransferData(DataFlavor.stringFlavor);
					} catch (UnsupportedFlavorException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				textArea.append(content);
				rootMenu.setVisible(false);
			}
	    	
	    };
	    pasteItem.addActionListener(pasteListener);
	    rootMenu.add(pasteItem);
	    
	    JMenuItem clearItem = new JMenuItem("Clear");
	    clearItem.setMnemonic(KeyEvent.VK_C);
	    ActionListener clearListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
	    };
	    clearItem.addActionListener(clearListener);
	    rootMenu.add(clearItem);
	    
	    return rootMenu;
	}
	
	public static Console getInstance() {
		return myConsole;
	}
	
	public void append(String str) {
	    super.append(str);	
	}
	
	public void setText(String str) {
		super.setText(str);
	}
	
}
