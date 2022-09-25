package io.itch.deltabreaker.main;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import io.itch.deltabreaker.gui.MainWindow;

public class Startup {

	public static ArrayList<ColorSet> colorList = new ArrayList<>();

	public static InputListener globalInput;
	
	public static void main(String[] args) {
		try (InputStream file = Startup.class.getResourceAsStream("/color_list.json")) {
			JSONArray array = (JSONArray) new JSONParser().parse(new InputStreamReader(file));
			for (int i = 0; i < array.size(); i++) {
				colorList.add(new ColorSet((String) ((JSONObject) array.get(i)).get("name"),
						(String) ((JSONObject) array.get(i)).get("hex")));
			}
			System.out.println("[Startup]: Colors loaded successfully");
			new MainWindow();
			
			GlobalScreen.registerNativeHook();
			globalInput = new InputListener();
			GlobalScreen.addNativeKeyListener(globalInput);
			GlobalScreen.addNativeMouseMotionListener(globalInput);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "color_list.json was not found!");
		}
	}

}
