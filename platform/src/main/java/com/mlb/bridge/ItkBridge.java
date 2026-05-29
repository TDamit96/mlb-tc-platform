package com.mlb.bridge;

public class ItkBridge {

    static {
        System.loadLibrary("libmlb_itk"); // loads libmlb_itk.dll
    }

    public native ItkResult fetchUrl(String url);

    public static class ItkResult {
        public int errorCode;
        public String json;
		
		public ItkResult(int errorCode, String json) {
			this.errorCode = errorCode;
			this.json = json;
		}
    }

    /**public static void main(String[] args) {
        ItkBridge bridge = new ItkBridge();
        ItkResult result = bridge.fetchUrl("https://statsapi.mlb.com/api/v1/schedule?sportId=1");
        System.out.println("Error code: " + result.errorCode);
        System.out.println("JSON: " + result.json);
    }**/
}