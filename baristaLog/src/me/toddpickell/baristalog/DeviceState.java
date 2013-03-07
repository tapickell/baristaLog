package me.toddpickell.baristalog;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class DeviceState extends Activity {
	// needs to extend activity to have access to getSharedPreferences()
	
	
	private String device_type;
	private List<String> deviceNames;
	private List<String> subTitles;
	private List<Integer> subTimes;
	private SharedPreferences device;
	private String aero = "areopress";
	private String chem = "chemex";
	private String clev = "clever";
	private String espr = "espresso";
	private String fren = "french press";
	private String pour = "pour over";
	private Integer pre, bloom, brew, total;
	private String firstSub, secondSub, thirdSub;
	
	
	
	
	public DeviceState(String device_type) {
		super();
		this.device_type = device_type;
		deviceNames.add(aero);
		deviceNames.add(chem);
		deviceNames.add(clev);
		deviceNames.add(espr);
		deviceNames.add(fren);
		deviceNames.add(pour);
		initDeviceWithName();
	}
	
	private void initDeviceWithName() {
		if (deviceNames.contains(device_type)) {
			// then device name is in list for shared preferences
			device = getSharedPreferences(device_type, MODE_PRIVATE);
			if (device.contains("pre")) {
				// then file is already in place
				getDeviceSettingsFromFile();
				
			} else {
				// file is not setup with data
				setupNewDevice();
			}
			
			// now load settings into Lists
			addDeviceSettingsToLists();
		}
	}
	
	private void addDeviceSettingsToLists() {
		
		if (pre != null) {
			subTimes.add(pre);
		}
		if (bloom != null) {
			subTimes.add(bloom);
		}
		if (brew != null) {
			subTimes.add(brew);
		}
		if (total != null) {
			subTimes.add(total);
		}
		if (!firstSub.isEmpty()) {
			subTitles.add(firstSub);
		}
		if (!secondSub.isEmpty()) {
			subTitles.add(secondSub);
		}
		if (!thirdSub.isEmpty()) {
			subTitles.add(thirdSub);
		}
		
	}

	private void getDeviceSettingsFromFile() {
	
		if (device.contains("pre")) {
			pre = device.getInt("pre", 0);
		}
		if (device.contains("bloom")) {
			bloom = device.getInt("bloom", 0);
		}
		if (device.contains("brew")) {
			brew = device.getInt("brew", 0);
		}
		if (device.contains("total")) {
			total = device.getInt("total", 0);
		}
		if (device.contains("firstSub")) {
			firstSub = device.getString("firstSub", "");
		}
		if (device.contains("secondSub")) {
			secondSub = device.getString("secondSub", "");
		}
		if (device.contains("thirdSub")) {
			thirdSub = device.getString("thirdSub", "");
		}
	}

	private void setupNewDevice() {
		SharedPreferences.Editor editor = device.edit();		
		
		if (device_type.equals(chem) || device_type.equals(clev)) {
			//setup object for chemex or clever
			pre = 30;
			bloom = 30;
			brew = 180;
			total = 240;
			firstSub = "Pre";
			secondSub = "Bloom";
			thirdSub = "Brew";
			
		} else if (device_type.equals(fren)) {
			//setup object for french press
			pre = 30;
			bloom = 180;
			brew = 30;
			total = 240;
			firstSub = "Bloom";
			secondSub = "Brew";
			thirdSub = "Plunge";
			
		} else if (device_type.equals(pour)) {
			//setup object for pour over
			pre = 30;
			bloom = 30;
			brew = 90;
			total = 150;
			firstSub = "Pre";
			secondSub = "Bloom";
			thirdSub = "Brew";
			
		} else if (device_type.equals(aero)) {
			//setup for aeropress
			pre = 10;
			bloom = 20;
			brew = 0;
			total = 30;
			firstSub = "Steep";
			secondSub = "Plunge";
			thirdSub = "";
			
		} else {
			//setup for espresso
			pre = 0;
			bloom = 0;
			brew = 0;
			total = 0;
			firstSub = "";
			secondSub = "";
			thirdSub = "";
		}
		
		if (!pre.equals(0)) {
			editor.putInt("pre", pre);
		}
		if (!bloom.equals(0)) {
			editor.putInt("bloom", bloom);
		}
		if (!brew.equals(0)) {
			editor.putInt("brew", brew);
		}
		if (!total.equals(0)) {
			editor.putInt("total", total);
		}
		if (!firstSub.equals("")) {
			editor.putString("firstSub", firstSub);
		}
		if (!secondSub.equals("")) {
			editor.putString("secondSub", secondSub);
		}
		if (!thirdSub.equals("")) {
			editor.putString("thirdSub", thirdSub);
		}
		editor.commit();
	}
	
	
	
	public String popSubTitle() {
		String temp = "";
		if (!subTitles.isEmpty()) {
			temp = subTitles.get(0);
			subTitles.remove(0);
		} 
		return temp;
	}

	public Integer popSubTime() {
		Integer temp = 0;
		if (!subTimes.isEmpty()) {
			temp = subTimes.get(0);
			subTimes.remove(0);
		}
		return temp;		
	}
}