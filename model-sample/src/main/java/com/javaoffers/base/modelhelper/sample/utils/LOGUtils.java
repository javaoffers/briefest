/**   
 * Copyright © 2019 mh615 Info.  All rights reserved.
 * 
 * 功能描述：
 * @Package: com.mh.others.utils.log
 * @author: cmj   
 * @date: 2019-1-18 上午10:27:07 
 */
package com.javaoffers.base.modelhelper.sample.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * Copyright: Copyright (c) 2019 LanRu-Caifu
 * 
 * @ClassName: LOGUtils.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: 曹明杰
 * @date: 2019-1-18 上午10:27:07 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2019-1-18     cmj           v1.0.0               修改原因
 */
public class LOGUtils {
	public static final Level DEBUG = Level.DEBUG;

	public static final Level INFO = Level.INFO;

	public static final Level WARN = Level.WARN;

	public static final Level ERROR = Level.ERROR;

	public static final Level ALARM = Level.FATAL;
	
	static  Logger logger = Logger.getLogger("");
	
	public static void printLog(String log,Level level){
	    
		if(level.equals(INFO)){
			logger.info(log);
		}
		if(level.equals(DEBUG)){
			logger.log(DEBUG, log);
		}
		if(level.equals(WARN)){
			logger.log(WARN, log);
		}
		if(level.equals(ERROR)){
			logger.log(ERROR, log);
		}
		if(level.equals(ALARM)){
			logger.log(ALARM, log);
		}
	}
	

   public static void printLog(Object log){
	   printLog( log+"",INFO);
	}


	public static void logError(String log) {
		printLog(log,ERROR);
	}

	public static void logInfo(String log) {
		printLog(log,INFO);
	}
}
