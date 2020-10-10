package cn.cloudwalk.libproject.util;

public class TimeUtil {
static long startTime=0;
public static void timeSpanStart(){
	startTime= System.currentTimeMillis();
}
public static long timeSpanEnd(){
	return System.currentTimeMillis()-startTime;
}
}
