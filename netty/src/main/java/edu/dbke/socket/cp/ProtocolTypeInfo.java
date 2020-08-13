package edu.dbke.socket.cp;

import java.util.HashMap;

public class ProtocolTypeInfo extends ProtocolType {

	private static final HashMap<Short, String> map = buildMap();

	private static final HashMap<Short, String> buildMap() {
		HashMap<Short, String> map = new HashMap<Short, String>();

		map.put(HANDSET_LOCATION_UPDATE, "Handset location update");
		map.put(HANDSET_ONLINE_LIST, "Handset online list");
		map.put(HANDSET_ONLINE, "Handset location online");
		map.put(HANDSET_OFFLINE, "Handset location offline");
		map.put(HANDSET_MONITOR, "Handset location monitor request");
		map.put(HANDSET_LOCATION_MONITOR, "Handset location monitor infomation");

		return map;
	}

	public static String code2name(Short code) {
		return map.get(code);
	}

	public static Short name2code(String name) {
		for (Short code : map.keySet()) {
			if (map.get(code).equalsIgnoreCase(name)) {
				return code;
			}
		}
		return null;
	}

	public static boolean containsCode(Byte code) {
		return map.containsKey(code);
	}

	public ProtocolTypeInfo() {
		super();
	}

}