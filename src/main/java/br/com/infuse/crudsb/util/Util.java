package br.com.infuse.crudsb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static Date converterStringParaData(String dataString) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            return formato.parse(dataString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
