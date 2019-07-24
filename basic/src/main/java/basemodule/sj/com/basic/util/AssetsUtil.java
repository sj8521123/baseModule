package basemodule.sj.com.basic.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class AssetsUtil {
  public static String replaceAssetsFileContext(Context context,String fileName,String desText,String text){
    try {
      InputStream inputStream = context.getAssets().open(fileName);
      return getString(inputStream).replace(desText, text);
    }catch (IOException e1) {
      e1.printStackTrace();
    }
    return text;
  }
  public static String getString(InputStream inputStream) {
    InputStreamReader inputStreamReader = null;
    try {
      inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }
    BufferedReader reader = new BufferedReader(inputStreamReader);
    StringBuffer sb = new StringBuffer("");
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }
}
