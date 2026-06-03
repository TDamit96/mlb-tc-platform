#include <string>
#include <curl/curl.h>
#include <jni.h>
#include <cstring>

extern "C" {

// Simple struct to hold the result of our HTTP fetch
struct ItkResult {
    int errorCode;
    const char* json;
};

static size_t WriteCallback(void* contents, size_t size, size_t nmemb, void* userp) {
    ((std::string*)userp)->append((char*)contents, size * nmemb);
    return size * nmemb;
}

// Function to fetch data from the MLB API and return the result as an ItkResult struct
ItkResult fetchUrl(const char* url) {
    CURL* curl = curl_easy_init();
    std::string buffer;

    if (!curl) {
        return { 1, nullptr };
    }

    curl_easy_setopt(curl, CURLOPT_URL, url);
    curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
    curl_easy_setopt(curl, CURLOPT_WRITEDATA, &buffer);

    CURLcode res = curl_easy_perform(curl);
    curl_easy_cleanup(curl);

    if (res != CURLE_OK) {
        return { 2, nullptr };
    }

    char* result = strdup(buffer.c_str());
    return { 0, result };
}

}

// JNI function called from Java to get the game schedule for a specific date
extern "C" JNIEXPORT jobject JNICALL
Java_com_mlb_bridge_ScheduleBridge_getScheduleForDate(JNIEnv* env, jobject obj, jstring date) {
    const char* nativeDate = env->GetStringUTFChars(date, nullptr);

    std::string url = "https://statsapi.mlb.com/api/v1/schedule?sportId=1&date=";
    url += nativeDate;

    env->ReleaseStringUTFChars(date, nativeDate);

    // Reuse your existing fetchUrl logic
    ItkResult result = fetchUrl(url.c_str());

    // Find the ItkResult Java class
    jclass resultClass = env->FindClass("com/mlb/bridge/ItkBridge$ItkResult");
    if (!resultClass) return nullptr;

    // Get the constructor (int, String)
    jmethodID ctor = env->GetMethodID(resultClass, "<init>", "(ILjava/lang/String;)V");
    if (!ctor) return nullptr;

    // Convert C string to Java String
    jstring jsonString = result.json ? env->NewStringUTF(result.json) : nullptr;

    // Create and return the Java object
    jobject resultObj = env->NewObject(resultClass, ctor, result.errorCode, jsonString);
    return resultObj;
}

// JNI function called from Java to get the current standings
extern "C" JNIEXPORT jobject JNICALL
Java_com_mlb_bridge_StandingsBridge_getStandingsJson(JNIEnv* env, jobject obj) {

    // MLB standings endpoint (you can refine this later)
    const char* url = "https://statsapi.mlb.com/api/v1/standings?leagueId=103,104";

    // Reuse your existing HTTP engine
    ItkResult result = fetchUrl(url);

    // Find the Java ItkResult class
    jclass resultClass = env->FindClass("com/mlb/bridge/ItkBridge$ItkResult");
    if (!resultClass) return nullptr;

    // Get the constructor (int, String)
    jmethodID ctor = env->GetMethodID(resultClass, "<init>", "(ILjava/lang/String;)V");
    if (!ctor) return nullptr;

    // Convert C string to Java String
    jstring jsonString = result.json ? env->NewStringUTF(result.json) : nullptr;

    // Create and return the Java ItkResult object
    jobject resultObj = env->NewObject(resultClass, ctor, result.errorCode, jsonString);
    return resultObj;
}
