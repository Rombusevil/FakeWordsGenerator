package com.rombus.evilbones.fwg;

import java.util.Random;

/**
 * 
 */

/**
 * @author rombus
 *
 * 04/06/2016 17:48:02
 */
public class Utils {
	public static final char[] VOWELS_ARR = new char[]{'a','e','i','o','u'};
	public static final Random ran = new Random();
	
	public static boolean isVowel(char c){
		for (char ch : VOWELS_ARR) {
			if(c == ch) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isVowel(String s){
		// I only care if the last char is vowel
		return isVowel(s.charAt(s.length()-1));
	}
	
	public static char getRandomVowel(){
		int r = Utils.ran.nextInt(VOWELS_ARR.length);
		return VOWELS_ARR[r];
	}

}
