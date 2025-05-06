/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.util;

import java.io.IOException;
import java.util.logging.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author shanuka
 */
public class LoggerUtil {
    
    private static final String LOG_FILE = "logs/inventory-log.txt";
    
    static {
        try {
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("logs"));
            
            Handler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new CustomFormatter());
            fileHandler.setLevel(Level.ALL);
            
            Logger rootLogger = Logger.getLogger("");
            rootLogger.setLevel(Level.ALL);
            
            for (Handler handler: rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }
            
            rootLogger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private LoggerUtil(){}
    
    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }
    
    private static class CustomFormatter extends Formatter {
        private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        @Override
        public String format(LogRecord record) {
            return String.format("%s [%s] %s - %s%n",
                    sdf.format(new Date(record.getMillis())),
                    record.getLevel().getName(),
                    record.getLoggerName(),
                    record.getMessage());
        }
    }
}
