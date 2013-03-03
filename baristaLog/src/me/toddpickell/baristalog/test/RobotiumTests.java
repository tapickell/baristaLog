package me.toddpickell.baristalog.test;

import static org.junit.Assert.*;

import me.toddpickell.baristalog.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayway.android.robotium.solo.Solo;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

public class RobotiumTests extends ActivityInstrumentationTestCase2<MainActivity> {

	protected Solo solo;
	
	public RobotiumTests() {
		super(MainActivity.class);
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
