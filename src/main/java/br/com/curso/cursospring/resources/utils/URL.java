package br.com.curso.cursospring.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static List<Integer> decodeIntList(String lista){
		return Arrays.asList(lista.split(",")).stream().map(id -> Integer.parseInt(id)).collect(Collectors.toList());
	}

}
