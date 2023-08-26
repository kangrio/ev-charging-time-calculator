package com.kangrio.evbatcalculator;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

public class MainActivity extends Activity {
	
	private double percent = 0;
	private String kwh = "";
	private String timetocharge = "";
	
	private LinearLayout linear1;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear2;
	private EditText editKWH;
	private EditText editStartChargeTime;
	private EditText editPercent;
	private Button button2;
	
	private SharedPreferences data;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear2 = findViewById(R.id.linear2);
		editKWH = findViewById(R.id.editKWH);
		editStartChargeTime = findViewById(R.id.editStartChargeTime);
		editPercent = findViewById(R.id.editPercent);
		button2 = findViewById(R.id.button2);
		data = getSharedPreferences("data", Activity.MODE_PRIVATE);
		
		editKWH.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				double chargekwh = 0;
				DecimalFormat decimalF = new DecimalFormat("0.00");
				
				try{
					
					percent = Integer.parseInt(editPercent.getText().toString());
					chargekwh = Double.parseDouble(editKWH.getText().toString());
					
					
				}catch (Exception e){
					percent = 0;
				}
				
				int fullbat = 59;
				
				String startChargeTime = editStartChargeTime.getText().toString();
				
				DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
				
				LocalTime lt = LocalTime.parse(startChargeTime);
				
				
				kwh = decimalF.format(fullbat - (fullbat * percent / 100) ) + " kwh";
				
				
				
				
				//kwh = Double.toString(fullbat - (fullbat * percent / 100) ) + " kwh";
				
				double timeHuse = (fullbat - (fullbat * percent / 100))  / chargekwh;
				int H = (int)timeHuse;
				int M = (int)((timeHuse*60) % 60);
				
				String plusTime = df.format(lt.plusMinutes((long)(timeHuse*60)));
				
				timetocharge = plusTime;
				
				data.edit().putString("chargekwh", editKWH.getText().toString()).commit();
				textview1.setText(kwh);
				textview2.setText(timetocharge);
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		editPercent.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				double chargekwh = 0;
				DecimalFormat decimalF = new DecimalFormat("0.00");
				
				try{
					
					percent = Integer.parseInt(editPercent.getText().toString());
					chargekwh = Double.parseDouble(editKWH.getText().toString());
					
					
				}catch (Exception e){
					percent = 0;
				}
				
				int fullbat = 59;
				
				String startChargeTime = editStartChargeTime.getText().toString();
				
				DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
				
				LocalTime lt = LocalTime.parse(startChargeTime);
				
				
				
				
				
				kwh = decimalF.format(fullbat - (fullbat * percent / 100) ) + " kwh";
				
				//kwh = Double.toString(fullbat - (fullbat * percent / 100) ) + " kwh";
				
				double timeHuse = (fullbat - (fullbat * percent / 100))  / chargekwh;
				int H = (int)timeHuse;
				int M = (int)((timeHuse*60) % 60);
				
				String plusTime = df.format(lt.plusMinutes((long)(timeHuse*60)));
				
				timetocharge = plusTime;
				
				data.edit().putString("percentbat", editPercent.getText().toString()).commit();
				textview1.setText(kwh);
				textview2.setText(timetocharge);
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				percent = 0;
				percent = Integer.parseInt(editPercent.getText().toString());
				
				//editPercent.setText("");
				
				int fullbat  = 59;
				
				kwh = Double.toString(fullbat - (fullbat * percent / 100) ) + " kwh";
				
				//percent = Integer.parseInt(kwh) / 6;
				
				timetocharge = String.valueOf((fullbat - (fullbat * percent / 100))  / 6) + "ชั่วโมง";
				textview1.setText(kwh);
				textview2.setText(timetocharge);
			}
		});
	}
	
	private void initializeLogic() {
		try{
			percent = Double.parseDouble(data.getString("percentbat", ""));
			editKWH.setText(data.getString("chargekwh", ""));
			editPercent.setText(data.getString("percentbat", ""));
			DecimalFormat decimalF = new DecimalFormat("0.00");
			
			Double chargekwh = Double.parseDouble(data.getString("chargekwh", ""));
			
			try{
					
					percent = Integer.parseInt(editPercent.getText().toString());
					chargekwh = Double.parseDouble(editKWH.getText().toString());
					
					
			}catch (Exception e){
					percent = 0;
			}
			
			int fullbat = 59;
			
			String startChargeTime = editStartChargeTime.getText().toString();
			
			DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
			
			LocalTime lt = LocalTime.parse(startChargeTime);
			
			
			kwh = decimalF.format(fullbat - (fullbat * percent / 100) ) + " kwh";
			
			//kwh = Double.toString(fullbat - (fullbat * percent / 100) ) + " kwh";
			
			double timeHuse = (fullbat - (fullbat * percent / 100))  / chargekwh;
			int H = (int)timeHuse;
			int M = (int)((timeHuse*60) % 60);
			
			String plusTime = df.format(lt.plusMinutes((long)(timeHuse*60)));
			
			timetocharge = plusTime;
			
			//timetocharge = String.format("%d:%02d",H,M) + " ชั่วโมง";
			
			textview1.setText(kwh);
			textview2.setText(timetocharge);
		}catch (Exception e){
			    
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}