package io.itch.deltabreaker.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import io.itch.deltabreaker.main.ColorSet;
import io.itch.deltabreaker.main.Startup;

public class ColorWindow extends JFrame {

	private static final long serialVersionUID = 7057491744606450113L;

	private int defaultWidth = 400;
	private int defaultHeight = 160;

	private JPanel panel;

	public ColorWindow(int color) {
		this((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff);
	}
	
	public ColorWindow(int red, int green, int blue) {
		setSize(defaultWidth, defaultHeight);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setTitle("Color Finder");

		panel = new JPanel() {
			private static final long serialVersionUID = -1832067852853431128L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.clearRect(0, 0, getWidth() / 2, getHeight());
				g.setColor(new Color(red, green, blue));
				g.fillRect(getWidth() / 2, 0, getWidth() / 2, getHeight());
			}
		};
		panel.setBounds(0, 0, getWidth() - 14, getHeight() - 38);
		
		JTextArea text = new JTextArea();
		text.setBounds(0, 0, panel.getWidth() / 2, panel.getHeight());
		text.setEditable(false);
		text.setBackground(new Color(240, 240, 240));
		
		text.append(" RGB:   " + red + ",   " + green + ",   " + blue);
		text.append("\n             " + (int) ((red / 255.0) * 1000) / 10.0 + "%,   "
				+ (int) ((green / 255.0) * 1000) / 10.0 + "%,   " + (int) ((blue / 255.0) * 1000) / 10.0 + "%");

		byte[] bytes = { (byte) red, (byte) green, (byte) blue };
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X", b));
		}
		text.append("\n\n Hex:   " + sb.toString());

		double ri = red / 255.0;
		double gi = green / 255.0;
		double bi = blue / 255.0;
		double k = 1 - Math.max(ri, Math.max(gi, bi));
		double c = (1 - ri - k) / (1 - k);
		double m = (1 - gi - k) / (1 - k);
		double y = (1 - bi - k) / (1 - k);

		text.append("\n\n CMYK:   " + (int) Math.round(c * 100) + ",   " + (int) Math.round(m * 100) + ",   "
				+ (int) Math.round(y * 100) + ",   " + (int) Math.round(k * 100));

		ColorSet closest = Startup.colorList.get(0);
		int distance = ColorSet.compare(closest.r, closest.g, closest.b, red, green, blue);
		for (ColorSet check : Startup.colorList) {
			int distanceCheck = ColorSet.compare(check.r, check.g, check.b, red, green, blue);
			if (distanceCheck < distance) {
				distance = distanceCheck;
				closest = check;
			}
		}
		
		text.setFont(new Font("Arial", Font.BOLD, 12));
		panel.add(text);

		JButton button = new JButton("Open ColorHexa");
		button.setBounds(5, 95, text.getWidth() - 10, 20);
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.colorhexa.com/" + sb.toString().toLowerCase()));
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error opening website");
				}
			}
		});
		text.add(button);
		
		panel.setLayout(null);
		add(panel);

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				panel.setBounds(0, 0, getWidth() - 16, getHeight() - 39);
				revalidate();
				repaint();
			}
		});
		
		revalidate();
		repaint();

		setVisible(true);
	}

}