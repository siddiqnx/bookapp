package bookapp.util;

import java.io.*;
import java.util.*;

import bookapp.Config;

public class Command {
  public static String START_POSTGRES_CMD = "net start " + Config.POSTGRES_PROCESS_NAME;
  public static String START_SERVER_CMD = "cmd /C \"set CATALINA_HOME=D:\\Work\\BookApp\\server\\apache-tomcat-9.0.43&& .\\apache-tomcat-9.0.43\\bin\\catalina.bat run\"";

  public static String execCmd(String cmd) {
    String result = null;
    try {
      Process p = Runtime.getRuntime().exec(cmd);
      InputStream inputStream = p.getInputStream();
      Scanner s = new Scanner(inputStream).useDelimiter("\\A");
      p.waitFor();
      result = s.hasNext() ? s.next() : null;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return result;
  }
}