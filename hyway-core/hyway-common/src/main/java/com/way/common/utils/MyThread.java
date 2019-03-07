package com.way.common.utils;

import java.util.List;

/**
 * @version :1.0
 * @类说明：线程池
 */
public abstract class MyThread implements Runnable {
	/** 线程出错后重新运行次数 */
	private static int errorTimes = 2;

	/** 最多同时执行任务数 */
	private static int maxThread = 10;

	/** 线程使用状态：false未使用、true使用中 */
	private String thread_states = "false";
	


	/** 
	 * 得到空闲线程、并传递参数
	 * @param threads 线程池
	 * @param args 参数 
	 * @return
	 */
	protected static MyThread getMyThread(List<MyThread> threads, Object ... args) {
		for(MyThread t:threads){
			if(t.thread_states.equals("false")){
				t.thread_states = "true";	// 标记使用中
				t.setArgs(args);
				return t;
			}
		}
		try {
			Thread.sleep(1500L);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getMyThread(threads, args);
	}
	
	public void close(){
		this.thread_states = "false";
	}

	/** 执行代码 */
	public abstract void runCode() throws Exception;
	
	/** 设置参数 */
	public abstract void setArgs(Object ... args);;
	
	/** 异常获取、重新执行代码  */
	public void run() {
		try {
			runCode();
			this.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			if(getErrorTimes()>0){
				setErrorTimes(getErrorTimes() - 1); 
				run();
			}
			this.close();
		}
	}	
	// --------------------------------------

	public int getErrorTimes() {
		return errorTimes;
	}

	public void setErrorTimes(int errorTimes) {
		this.errorTimes = errorTimes;
	}

	public static int getMaxThread() {
		return maxThread;
	}

	public static void setMaxThread(int maxThread) {
		MyThread.maxThread = maxThread;
	}

	public String getThread_states() {
		return thread_states;
	}

	public void setThread_states(String threadStates) {
		thread_states = threadStates;
	}

}

