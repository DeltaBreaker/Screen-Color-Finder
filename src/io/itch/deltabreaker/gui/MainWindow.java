package io.itch.deltabreaker.gui;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jnativehook.keyboard.NativeKeyEvent;

import io.itch.deltabreaker.main.InputListener;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 2278856977040932622L;

	public MainWindow() {
		setTitle("Color Finder");
		setSize(256, 140);
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		if (SystemTray.isSupported()) {
			try {
				PopupMenu popup = new PopupMenu();
				TrayIcon trayIcon = new TrayIcon(ImageIO.read(MainWindow.class.getResourceAsStream("/icon.png")),
						"Color Finder");
				SystemTray tray = SystemTray.getSystemTray();

				MenuItem exit = new MenuItem("Exit");
				exit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});

				MenuItem open = new MenuItem("Open File");
				open.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser file = new JFileChooser();
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Standard Image Files", "jpg",
								"png", "jpeg", "bmp");
						file.setFileFilter(filter);
						int returnVal = file.showOpenDialog(null);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							try {
								File f = file.getSelectedFile();
								new ImageWindow(f.getName(), ImageIO.read(f));
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "There was an error reading this image");
							}
						}
					}
				});

				MenuItem key = new MenuItem("Keybind: " + NativeKeyEvent.getKeyText(InputListener.keyCode));
				key.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						InputListener.readKey = true;
						JOptionPane.showMessageDialog(null, "The next key pressed will set as the active keybind");
					}
				});

				popup.add(open);
				//popup.add(key);
				popup.add(exit);

				trayIcon.setPopupMenu(popup);
				tray.add(trayIcon);

				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				setType(Type.UTILITY);
				setVisible(false);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[Init]: Could not add program to system tray");
			}
		} else {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	}

}
