package com.agro.org.n3r.idworker;

public class Test {

	public static void main(String[] args) {

		for (int i = 0 ; i < 1000 ; i ++) {
			Sid sid = new Sid();
			System.out.println(sid.nextShort());
		}
	}

}
