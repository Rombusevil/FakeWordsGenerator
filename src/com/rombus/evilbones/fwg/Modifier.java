package com.rombus.evilbones.fwg;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rombus
 *
 * 04/06/2016 11:01:49
 */
public class Modifier {
	private Map<Character, String[]> digraphs;	
	
	public Modifier(){
		this.digraphs = new HashMap<Character, String[]>();
		
		// Digraphs English
		digraphs.put('c', new String[]{"ch"});
		digraphs.put('g', new String[]{"gh"});
		digraphs.put('k', new String[]{"kn"});
		digraphs.put('n', new String[]{"ng"});
		digraphs.put('p', new String[]{"ph"});
		digraphs.put('s', new String[]{"sh"});
		digraphs.put('t', new String[]{"tch", "th"});
		digraphs.put('w', new String[]{"wh","wr"});
		digraphs.put('q', new String[]{"que","qui","c"});
		
		// Digraphs Spanish
		digraphs.put('l', new String[]{"ll"});
		digraphs.put('r', new String[]{"rr"});
		
		// Digraphs Norwegian
//		digraphs.put('a', new String[]{"ai","au","æi"});
//		digraphs.put('e', new String[]{"ei","eg"});		
//		digraphs.put('s', new String[]{"sk","skj","sj"});
//		digraphs.put('k', new String[]{"kj","ki","ky"});
	}
	
	
	public String transformChar(char c){
		String digs[] = digraphs.get(c);
		
		if(digs != null){			
			int len = digs.length;
			int max = len +1;			
			int r = Utils.ran.nextInt(max + 1); // Number between 0 and len+1 (len+1 represents don't use digraph)
			
			// Si es Q obligo a que use un digraph
			if(r >= len && c == 'q'){
				r = Utils.ran.nextInt(max - 1);				
			}
			
			if(r < len){
				return digs[r];
			}
		}
		
		return ""+c;	// Untouched, just returned as a String
	}

}
