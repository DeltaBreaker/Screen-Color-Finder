package io.itch.deltabreaker.main;

public class ColorSet {

	public String name, hex;
	public int r, g, b;

	public ColorSet(String name, String hex) {
		this.name = name;
		this.hex = hex;
		int color = Integer.parseInt(hex, 16);
		r = (color >> 16) & 0xFF;
		g = (color >> 8) & 0xFF;
		b = color & 0xFF;
	}

	public static int compare(int r1, int g1, int b1, int r2, int g2, int b2) {
		return Math.abs(r1 * r1 - r2 * r2) + Math.abs(g1 * g1 - g2 * g2) + Math.abs(b1 * b1 - b2 * b2);
	}

}
