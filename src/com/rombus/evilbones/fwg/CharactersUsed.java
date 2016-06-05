package com.rombus.evilbones.fwg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author rombus
 *
 * 04/06/2016 11:14:15
 */
public class CharactersUsed {
	private int uncompressedStrLen;
	private String userString;
	private char[] plainChars;
	private int desiredLength;
	private Map<Character, Float> chars;

	public CharactersUsed(String text, int desiredLength){
		this.uncompressedStrLen = text.length();
		this.desiredLength = desiredLength;
		this.plainChars = new char[this.uncompressedStrLen];
		this.userString = text;
		this.chars = new HashMap<Character, Float>();
		
		storeCharsAndProbability();
		storePlainChars();
	}
	
	private void storeCharsAndProbability(){
		int len = userString.length();
		
		// Fill the array with the keys and occurrence count
		for (int i = 0; i<len; i++){
			char c = userString.charAt(i);
			
			float count = 1;
			if(chars.containsKey(c)){
				count = chars.get(c) + 1;
			}
			chars.put(c, count);
		}
		
		// Replace occurrence count with ocurrence percent
		updateOcurrencePercent(chars);
	}
	
	private void updateOcurrencePercent(Map<Character, Float> letters){
		Iterator<Character> it = letters.keySet().iterator();
		
		while(it.hasNext()){
			Character key = it.next();			
			float percent = (letters.get(key)/ this.uncompressedStrLen);// * 100); No lo multiplico por 100, me sirve que esté entre 0 y 1
			
			letters.put(key, percent);
		}
	}
	
	/**
	 * Guarda en un arreglo de chars todos los caracteres disponibles,
	 * con su cantidad de ocurrencias correspondiente.
	 */
	private void storePlainChars(){		
		Iterator<Character> it = chars.keySet().iterator();
		int i = 0, len = plainChars.length;
		resetPlainCharArray();
		
		while(it.hasNext() && i < len){
			Character key = it.next();
			
			float probability = chars.get(key);
			int cant = (int)Math.ceil(probability * desiredLength);
			
			for(int j=0; j<cant && i<len; j++){
				plainChars[i] = key;
				i++;
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void resetPlainCharArray(){
		for (char c: plainChars) {
			c = '\0';
		}
	}
	
	// External API
	public Map<Character, Float> getData(){
		return this.chars;
	}
	public char[] getPlainChars(){
		return this.plainChars;
	}
	
	
	// Debug methods
	public void printPlainChars(){
		System.out.println(new String(this.plainChars));
	}
	
	public void printData(Map<Character, Integer> chars){
		Iterator<Character> it = chars.keySet().iterator();
		System.out.println("Data:");
		while(it.hasNext()){
			Character key = it.next();
			
			System.out.println("\t"+key+" "+chars.get(key));
		}
	}
}
