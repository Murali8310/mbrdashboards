package com.titan.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

	public static boolean  validation(String s)
	{
 
        // Creating regex pattern by
        // creating object of Pattern class
        Pattern p = Pattern.compile(
            "[^a-z0-9 @.+,-]/_", Pattern.CASE_INSENSITIVE);
 
        // Creating matcher for above pattern on our string
        Matcher m = p.matcher(s);
 
        // Now finding the matches for which
        // let us set a boolean flag and
        // imposing find() method
        boolean res = m.find();
 /*
        if (res)
            System.out.println("String1 contains Special Characters");
        else
            System.out.println("No Special Characters found in String 1");
 */
        return res;     
    }
	
	
	public static List<?> decryptPasswordInList(Object obj) {
		 List<Object> list = new ArrayList<Object>();
		try {
		PasswordUtils passwordUtils = new PasswordUtils();
	   
	    if (obj.getClass().isArray()) {
	        list = Arrays.asList((Object[])obj);
	        String decPass= passwordUtils.decrypt((String)list.get(4));
	      list.set(4, decPass);
	        
	    }
	
	}catch(Exception er)
	{
		er.printStackTrace();
	}
	return list;
}
	}
