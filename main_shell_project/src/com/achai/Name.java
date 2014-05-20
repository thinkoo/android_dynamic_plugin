package com.achai;

import com.orm.androrm.CharField;
import com.orm.androrm.Model;

public class Name extends Model {
	public CharField mName;
	
	public Name(){
		mName = new CharField();
	}

	
}
