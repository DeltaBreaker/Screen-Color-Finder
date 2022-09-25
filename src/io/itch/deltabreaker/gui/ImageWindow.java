package io.itch.deltabreaker.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class ImageWindow extends JFrame implements Runnable, MouseInputListener, MouseMotionListener {

	private static final long serialVersionUID = 6492814933575796973L;

	private BufferedImage image;
	private JPanel panel;

	public ImageWindow(String name, BufferedImage image) {
		this.image = image;
		double resizeWidth = image.getWidth();
		double resizeHeight = image.getHeight();
		while (resizeWidth > 1280 || resizeHeight > 720) {
			resizeWidth *= 0.9;
			resizeHeight *= 0.9;
		}
		setSize((int) resizeWidth, (int) resizeHeight);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		panel = new JPanel() {
			private static final long serialVersionUID = -1832067852853431128L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.clearRect(0, 0, getWidth(), getHeight());
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};
		panel.setLayout(null);
		add(panel);

		addMouseListener(this);
		addMouseMotionListener(this);

		setVisible(true);
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (isVisible()) {
			try {
				panel.setBounds(0, 0, getWidth() - 16, getHeight() - 39);
				revalidate();
				repaint();
				Thread.sleep(16L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int posX = e.getX() - 8;
		int posY = e.getY() - 31;

		double xRatio = (double) panel.getWidth() / image.getWidth();
		double yRatio = (double) panel.getHeight() / image.getHeight();

		int adjustedX = (int) (posX / xRatio);
		int adjustedY = (int) (posY / yRatio);
		
		int color = image.getRGB(adjustedX, adjustedY);
		int r = (color >> 16) & 0xff;
		int g = (color >> 8) & 0xff;
		int b = color & 0xff;
		
		new ColorWindow(r, g, b);
	}

}
