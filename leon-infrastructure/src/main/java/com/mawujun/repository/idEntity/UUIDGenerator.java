package com.mawujun.repository.idEntity;

import java.util.UUID;

public class UUIDGenerator  {
	
	public static String generate(){
		return UUID.randomUUID().toString();
	}
}
