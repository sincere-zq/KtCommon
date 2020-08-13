package edu.dbke.socket.cp.util;

import java.util.HashSet;
import java.util.Set;

import edu.dbke.socket.cp.Packet;


/**
 * 包扫描工具
 */
public class PacketScanUtil {
	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		//		System.out.println(PacketScanUtil.getPacketClasses("edu.dbke.edu.dbke.socket.cp.socket.cp.voip"));
		System.out.println("scan use time:" + (System.currentTimeMillis() - begin));
	}

	/**
	 *获取所有的packet
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<Class<? extends Packet<?>>> getPacketClasses(String packageName) {
		Set<Class<? extends Packet<?>>> clazzReturn = new HashSet<Class<? extends Packet<?>>>();
		for (Class<?> clazz : ClassScanUtil.getClasses(packageName)) {
			if (ClassScanUtil.isClass(clazz, "class edu.dbke.edu.dbke.socket.cp.socket.cp.Packet")) {
				clazzReturn.add((Class<? extends Packet<?>>) clazz);
			}
		}
		return clazzReturn;
	}
}
