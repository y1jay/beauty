package com.yijun.beauty.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * 로컬에 저장된 JSON 콘텐츠로 작업하기 위한 Utility class
 */
public class Json {

    /**
     * Loads a resource and creates a {@link JSONArray} object with the contents of the binary.
     * (= 리소스를로드하고 바이너리 콘텐츠로 {@link JSONArray} 객체를 만든다.)
     *
     * @param context  실행되는 곳.return a {@link JSONArray} stream 내용이 있는 객체
     * @param fileName binary 대상을 가리키는 경로.
     * @return a {@link JSONArray} stream 내용이 있는 객체.
     */
    public static JSONArray readFromFile(Context context, String fileName) {
        try {
            final InputStream inputStream = context.getAssets().open(fileName);
            return readFromInputStream(inputStream);

        } catch (Exception e) {
            return new JSONArray();
        }
    }

    /**
     * Loads a resource and creates a {@link JSONArray} object with the contents of the binary.
     * (= 리소스를로드하고 바이너리 콘텐츠로 {@link JSONArray} 객체를 만든다.)
     *
     * @param context  실행되는 곳.
     * @param resource 리소스 폴더의 바이너리 식별자.
     * @return a {@link JSONArray} object with the contents of the stream.
     */
    public static JSONArray readFromResources(Context context, int resource) {
        try {
            final InputStream inputStream = context.getResources().openRawResource(resource);
            return readFromInputStream(inputStream);

        } catch (Exception e) {
            return new JSONArray();
        }
    }

    /**
     *
     * {@link InputStream} 보유의 콘텐츠로 {@link JSONArray}를 만듭니다. JSON 정보.
     *
     * @param inputStream containing the JSON bytes.
     * @return a {@link JSONArray} object with the contents of the stream.
     * @throws JSONException 콘텐츠를 구문 분석 할 수 없는 경우.
     */
    private static JSONArray readFromInputStream(InputStream inputStream) throws JSONException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final String inputString = reader.lines().collect(Collectors.joining());
        return new JSONArray(inputString);
    }
}