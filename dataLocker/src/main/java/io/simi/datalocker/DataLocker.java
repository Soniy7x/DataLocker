package io.simi.datalocker;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.MessageDigest;
import java.util.HashMap;

public class DataLocker {

	private static final String DATA_PREFERENCES_NAME = "DateLocker";
	private static final String DATA_PREFERENCES_VERIFICATION_NAME = "DateLockerVerification";
	
	private static DataLocker instance; 
	private static SharedPreferences mSharedPreferences;
	private static SharedPreferences mVerificationSharedPreferences;
	private static HashMap<String, Object> mDataHashMap = new HashMap<>();
	private static HashMap<String, String> mDataVerificationHashMap = new HashMap<>();

	private OnDataLockerListener onDataLockerListener;
	
	private DataLocker(Context context) {
		mSharedPreferences = context.getSharedPreferences(DATA_PREFERENCES_NAME, Context.MODE_PRIVATE);
		mVerificationSharedPreferences = context.getSharedPreferences(DATA_PREFERENCES_VERIFICATION_NAME, Context.MODE_PRIVATE);
	}
	
	public static void init(Context context, OnDataLockerListener onDataLockerListener) {
		if (instance == null) {
			instance = new DataLocker(context);
		}
		instance.onDataLockerListener = onDataLockerListener;
	}
	
	public static DataLocker getInstance() {
		if (instance == null) {
			throw new RuntimeException("DataLocker should be initialized in main thread.");
		} else {
			return instance;
		}
	}
	
	/**
	 * Save Integer Value
	 */
	public int applyIntValue(String key, int value) {
		mDataHashMap.put(key, value);
		mDataVerificationHashMap.put(key, encryptMD5(value));
		return value;
	}
	
	public int applyIntValueAndPersistence(String key, int value) {
		applyIntValue(key, value);
		mSharedPreferences.edit().putInt(key, value).apply();
		mVerificationSharedPreferences.edit().putString(key, encryptMD5(value)).apply();
		return value;
	}
	
	public int appendIntValue(String key, int value) {
		if (!encryptMD5(mDataHashMap.get(key)).equals(mDataVerificationHashMap.get(key))) {
			onDataLockerListener.onDataExistCheatActive(key);
			return value;
		}
		if (mDataHashMap.containsKey(key)) {
			value += (int)mDataHashMap.get(key);
		}
		return applyIntValue(key, value);
	}
	
	public int appendIntValueAndPersistence(String key, int value) {
		if (!encryptMD5(getIntValue(key, 0)).equals(mVerificationSharedPreferences.getString(key, ""))) {
			onDataLockerListener.onDataExistCheatActive(key);
			return value;
		}
		value = appendIntValue(key, value);
		return applyIntValueAndPersistence(key, value);
	}
	
	public int getIntValue(String key, int dafaultValue) {
		if (mDataHashMap.containsKey(key)) {
			return (int)mDataHashMap.get(key);
		} else {
			return mSharedPreferences.getInt(key, dafaultValue);
		}
	}
	
	/**
	 * Save Long Value
	 */
	public long applyLongValue(String key, long value) {
		mDataHashMap.put(key, value);
		return value;
	}
	
	public long applyLongValueAndPersistence(String key, long value) {
		applyLongValue(key, value);
		mSharedPreferences.edit().putLong(key, value).apply();
		return value;
	}
	
	public long appendLongValue(String key, long value) {
		if (!encryptMD5(mDataHashMap.get(key)).equals(mDataVerificationHashMap.get(key))) {
			onDataLockerListener.onDataExistCheatActive(key);
			return value;
		}
		if (mDataHashMap.containsKey(key)) {
			value += (long)mDataHashMap.get(key);
		}
		return applyLongValue(key, value);
	}
	
	public long appendLongValueAndPersistence(String key, long value) {
		if (!encryptMD5(getLongValue(key, 0)).equals(mVerificationSharedPreferences.getString(key, ""))) {
			onDataLockerListener.onDataExistCheatActive(key);
			return value;
		}
		value = appendLongValue(key, value);
		return applyLongValueAndPersistence(key, value);
	}
	
	public long getLongValue(String key, long dafaultValue) {
		if (mDataHashMap.containsKey(key)) {
			return (long)mDataHashMap.get(key);
		} else {
			return mSharedPreferences.getLong(key, dafaultValue);
		}
	}
	
	/**
	 * Save Float Value
	 */
	public float applyFloatValue(String key, float value) {
		mDataHashMap.put(key, value);
		return value;
	}
	
	public float applyFloatValueAndPersistence(String key, float value) {
		applyFloatValue(key, value);
		mSharedPreferences.edit().putFloat(key, value).apply();
		return value;
	}
	
	public float appendFloatValue(String key, float value) {
		if (!encryptMD5(mDataHashMap.get(key)).equals(mDataVerificationHashMap.get(key))) {
			onDataLockerListener.onDataExistCheatActive(key);
			return value;
		}
		if (mDataHashMap.containsKey(key)) {
			value += (float)mDataHashMap.get(key);
		}
		return applyFloatValue(key, value);
	}
	
	public float appendFloatValueAndPersistence(String key, float value) {
		if (!encryptMD5(getFloatValue(key, 0)).equals(mVerificationSharedPreferences.getString(key, ""))) {
			onDataLockerListener.onDataExistCheatActive(key);
			return value;
		}
		value = appendFloatValue(key, value);
		return applyFloatValueAndPersistence(key, value);
	}

	public float getFloatValue(String key, float dafaultValue) {
		if (mDataHashMap.containsKey(key)) {
			return (float)mDataHashMap.get(key);
		} else {
			return mSharedPreferences.getFloat(key, dafaultValue);
		}
	}
	
	/**
	 * Save Boolean Value
	 */
	public boolean applyBooleanValue(String key, boolean value) {
		mDataHashMap.put(key, value);
		return value;
	}
	
	public boolean applyBooleanValueAndPersistence(String key, boolean value) {
		applyBooleanValue(key, value);
		mSharedPreferences.edit().putBoolean(key, value).apply();
		return value;
	}
	
	public boolean getBooleanValue(String key, boolean dafaultValue) {
		if (mDataHashMap.containsKey(key)) {
			return (boolean)mDataHashMap.get(key);
		} else {
			return mSharedPreferences.getBoolean(key, dafaultValue);
		}
	}
	/**
	 * Save String Value
	 */
	public String applyStringValue(String key, String value) {
		mDataHashMap.put(key, value);
		return value;
	}
	
	public String applyStringValueAndPersistence(String key, String value) {
		applyStringValue(key, value);
		mSharedPreferences.edit().putString(key, value).apply();
		return value;
	}
	
	public String appendStringValue(String key, String value) {
		if (!encryptMD5(mDataHashMap.get(key)).equals(mDataVerificationHashMap.get(key))) {
			onDataLockerListener.onDataExistCheatActive(key);
			return value;
		}
		if (mDataHashMap.containsKey(key)) {
			value += (String)mDataHashMap.get(key);
		}
		return applyStringValue(key, value);
	}
	
	public String appendStringValueAndPersistence(String key, String value) {
		if (!encryptMD5(getStringValue(key, "")).equals(mVerificationSharedPreferences.getString(key, ""))) {
			onDataLockerListener.onDataExistCheatActive(key);
			return value;
		}
		value = appendStringValue(key, value);
		return applyStringValueAndPersistence(key, value);
	}
	
	public String getStringValue(String key, String dafaultValue) {
		if (mDataHashMap.containsKey(key)) {
			return (String)mDataHashMap.get(key);
		} else {
			return mSharedPreferences.getString(key, dafaultValue);
		}
	}
	
	private String encryptMD5(Object object) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(String.valueOf(object).getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("数据加密出错：" + e.toString());
        }
        byte[] byteArray = messageDigest.digest();
        StringBuilder md5StrBuff = new StringBuilder();
        for (byte b : byteArray) {
            if (Integer.toHexString(0xFF & b).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & b));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & b));
        }
        return md5StrBuff.toString();
    }
}
