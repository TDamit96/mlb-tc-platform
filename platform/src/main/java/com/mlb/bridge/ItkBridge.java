package com.mlb.bridge;

public class ItkBridge {

    static {
        System.loadLibrary("libmlb_itk"); // loads libmlb_itk.dll
    }

    // Shared result type for structured responses
    public static class ItkResult {
        public int errorCode;
        public String json;
		
		public ItkResult(int errorCode, String json) {
			this.errorCode = errorCode;
			this.json = json;
		}
    }

}