package cn.hsd.student.message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Qxx_BaseActivity extends Activity {
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
	}

	public void startActivity(Class<?> paramClass) {
		startActivity(new Intent(this, paramClass));
	}
}
