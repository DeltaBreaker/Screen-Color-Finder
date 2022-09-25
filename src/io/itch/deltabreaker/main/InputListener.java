package io.itch.deltabreaker.main;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import io.itch.deltabreaker.gui.ColorWindow;

public class InputListener implements NativeKeyListener, NativeMouseMotionListener {

	public static Point location = new Point(0, 0);
	public static int keyCode = 3666;
	public static boolean readKey = false;

	public Robot robot;

	public GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public int width = gd.getDisplayMode().getWidth();
	public int height = gd.getDisplayMode().getHeight();

	public InputListener() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	@Override 
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		if (readKey) {
			readKey = false;
			keyCode = arg0.getKeyCode();
		} else if (arg0.getKeyCode() == keyCode) {
			Rectangle screenRect = new Rectangle(0, 0, width, height);
			BufferedImage capture = robot.createScreenCapture(screenRect);

			new ColorWindow(capture.getRGB((int) location.getX(), (int) location.getY()));
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {

	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {

	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		location = arg0.getPoint();
	}

}
