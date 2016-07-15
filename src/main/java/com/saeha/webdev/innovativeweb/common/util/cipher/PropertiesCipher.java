package com.saeha.webdev.innovativeweb.common.util.cipher;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * properties에서 사용할 정보 암호화기
 * @author Pure
 *
 */
public class PropertiesCipher {
	public static void main(String[] args) {
		String algorithm = "PBEWithMD5AndDES";
		String password = "BRACE_PASS";
		String input = "";
		
		if(args.length == 3){
			algorithm = args[0];
			password = args[1];
			input = args[2];
		}else if(args.length == 1){
			input = args[0];
		}else{
			System.err.println("args = input or algorithm password input");
			return;
		}
		
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm(algorithm);
		encryptor.setPassword(password);
		String encodeInput = encryptor.encrypt(input);
		
		System.out.println("== Arguments ==");
		System.out.println("algorithm: " + algorithm);
		System.out.println("password: " + password);
		System.out.println("input: " + input);
		System.out.println("");
		System.out.println("== Output ==");
		System.out.println(encodeInput);
		
		String decodeInput = encryptor.decrypt(encodeInput);
		
		System.out.println("Test decrypt... " + decodeInput);
	}
}
