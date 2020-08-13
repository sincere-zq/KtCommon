package edu.dbke.socket.cp.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import edu.dbke.socket.cp.Packet;


public class PacketCheckUitl {

	private static Object[] tem;

	public static void main(String[] args) throws Exception {
		Set<Class<? extends Packet<?>>> classSet = PacketScanUtil.getPacketClasses("edu.dbke.edu.dbke.socket.cp.socket.cp");
		Iterator<Class<? extends Packet<?>>> it = classSet.iterator();
		while (it.hasNext()) {
			Class<? extends Packet<?>> c = it.next();
			try {
				if (!c.getName().endsWith("Packet") || "edu.dbke.edu.dbke.socket.cp.socket.cp.Packet".equals(c.getName())) {
					continue;
				}

				Object packet1 = fromClass(c);
				Object packet2 = fromClass(c);

				setData((Packet<?>) packet1);
				byte[] b1 = ((Packet<?>) packet1).writeByteObject();
				((Packet<?>) packet2).readByteObject(b1);
				byte[] b2 = ((Packet<?>) packet2).writeByteObject();

				cmpData(packet1, packet2);
				System.out.println("ByteBuffer比较：" + cmpByteBuffer(b1, b2));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static boolean cmpByteBuffer(byte[] b1, byte[] b2) {
		if (b1.length != b2.length)
			return false;
		for (int i = 0; i < b1.length || i < b2.length; ++i) {
			if (b1[i] != b2[i]) {
				return false;
			}
		}
		return true;
	}

	public static Object fromClass(Class<?> c) {
		Object objectCopy = null;

		try {
			objectCopy = c.getConstructor(new Class[] {}).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectCopy;
	}

	public static void cmpData(Object p1, Object p2) throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 获得对象的类型
		Class<?> classType = p1.getClass();
		System.out.println("对象的类型是：" + classType.toString());
		classType = p2.getClass();
		// 获得对象的所有属性
		java.lang.reflect.Field[] fields = classType.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) { // 获取数组中对应的属性
			Field field = fields[i];
			String fieldName = field.getName();
			String stringLetter = fieldName.substring(0, 1).toUpperCase();

			// 获得相应属性的getXXX和setXXX方法名称
			String getName = "get" + stringLetter + fieldName.substring(1);
			String setName = "set" + stringLetter + fieldName.substring(1);

			// 获取相应的方法
			Method getMethod = null;
			try {
				getMethod = classType.getMethod(getName);
				Object value1 = field.get(p1);//getMethod.invoke(p1, new Object[] {});
				Object value2 = field.get(p2);//getMethod.invoke(p2, new Object[] {});
				boolean b = true;
				if (value1 instanceof byte[]) {
					byte[] tmp = (byte[]) value1;
					byte[] tmp2 = (byte[]) value2;
					for (int j = 0; j < tmp2.length; j++) {
						if (tmp[j] != tmp2[j]) {
							b = false;
							break;
						}
					}
				} else {
					b = (value1.equals(value2));
				}
				if (b) {
					System.out.println("数据" + field.getType() + "一致 : " + b);
				} else {
					System.err.println("数据" + field.getType() + "一致 : " + b);
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}

	public static void setData(Packet<?> p) throws IllegalArgumentException, SecurityException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 获得对象的类型
		Class<? extends Packet<?>> classType = (Class<? extends Packet<?>>) p.getClass();
		// 获得对象的所有属性
		java.lang.reflect.Field[] fields = classType.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) { // 获取数组中对应的属性
			Field field = fields[i];
			String fieldName = field.getName();
			String stringLetter = fieldName.substring(0, 1).toUpperCase();

			// 获得相应属性的getXXX和setXXX方法名
			String setName = "set" + stringLetter + fieldName.substring(1);

			// 获取相应的方法
			Method setMethod = null;
			try {
				//setMethod = classType.getMethod(setName, new Class[] { field.getType() });
				Object o = generateData(field);
				//setMethod.invoke(p, new Object[] { o });
				field.set(p, o);
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}

	public static Object generateData(Field field) throws ClassNotFoundException {

		if (field.getType().toString().equals("int")) {
			return 12;
		} else if (field.getType().toString().equals("long")) {
			return 1516L;
		} else if (field.getType().toString().equals("double")) {
			return 15.16D;
		} else if (field.getType().toString().equals("byte")) {
			return (byte) 5;
		} else if (field.getType().toString().equals("short")) {
			return (short) 15;
		} else if (field.getType().equals(Class.forName("java.lang.String"))) {
			return "1516";
		} else if (field.getType().equals(Class.forName("java.util.Date"))) {
			return new Date();
		}
		return new byte[] { 1, 2 };
	}

}
