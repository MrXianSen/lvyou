package com.qipilang.lvyouplatform.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.publish.Bimp;
import com.qipilang.lvyouplatform.publish.FileUtils;

/**************************************************************************
 * 
 * DESCRIPTION: ʹ�ͻ������뻥��������ȡ����
 * 
 * @author �Ž���
 * 
 * @since 2016.3.2
 * 
 * @version 1.0
 * 
 *************************************************************************/
public class HttpUtil {
	/*************************************************
	 * ��ȡ���Ӷ���
	 * 
	 * @param url
	 *            URL��ַ
	 * @return ����HttpURLConnection����
	 *************************************************/
	private static HttpURLConnection getHttpUrlConnection(String url) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) (new URL(url).openConnection());
			conn.setConnectTimeout(1000 * 10);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/*************************************************
	 * DESCRIPTION: ��װ������������
	 * 
	 * @param params
	 *            Map���͵����ݼ�
	 * @param encode
	 *            ���뷽ʽ
	 * @return ����StringBuilder����ʵ��
	 *************************************************/
	private static StringBuffer _getRequestData(Map<String, String> params,
			String encode) {
		StringBuffer stringBuffer = new StringBuffer(); // �洢��װ�õ���������Ϣ
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1); // ɾ������һ��"&"
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}

	/*************************************************
	 * DESCRIPTION: ������������ص����ݽ��
	 *************************************************/
	private static String dealResponseResult(InputStream inputStream) {
		String result = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int length = 0;
		try {
			while ((length = inputStream.read(data)) != -1) {
				byteArrayOutputStream.write(data, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		result = new String(byteArrayOutputStream.toByteArray());
		return result;
	}

	/*************************************************
	 * DESCRIPTION: ��������з������ݲ����ض���
	 * 
	 * @param params
	 *            ����Ĳ���
	 * @param encode
	 *            �����ı����ʽ
	 * @param url
	 *            �����·��
	 * @return ����JSONArray����
	 *************************************************/
	public static String _requestAndGetResponse(Map<String, String> params,
			String encode, String url) {
		byte[] data = _getRequestData(params, encode).toString().getBytes();
		int responseCode = 0;
		HttpURLConnection httpUrlConnection = getHttpUrlConnection(url);
		try {
			OutputStream ouputStream = httpUrlConnection.getOutputStream();
			ouputStream.write(data);
			responseCode = httpUrlConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = httpUrlConnection.getInputStream();
				// ���ش���������
				return dealResponseResult(inputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CastUtil.castString(responseCode);
	}

	public static void uploadMethod(RequestParams params,
			final Handler handler, int type) {
		String hostUrl = "";
		// �ϴ���ַ
		if (type == 0)
			hostUrl = Constants.BASE_URL + Constants.UPLOAD_CIRCLE_URL;
		else
			hostUrl = Constants.BASE_URL + Constants.EDIT_PROFILE_URL;
		HttpUtils http = new HttpUtils();
		// ������������ϴ�����Ͳ���
		http.send(HttpRequest.HttpMethod.POST, hostUrl, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
					}

					@Override
					public void onStart() {
						super.onStart();
					}

					@Override
					public void onFailure(HttpException exception, String msg) {
						Bundle data = new Bundle();
						Message message = new Message();
						data.putInt("uploadResult", Constants.UPLOAD_ERROR);
						message.setData(data);
						handler.sendMessage(message);
						// ɾ���ļ���
						FileUtils.deleteDir();
						Bimp.bmp.clear();
						Bimp.drr.clear();
						Bimp.max = 0;
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Bundle data = new Bundle();
						Message msg = new Message();
						data.putInt("uploadResult", Constants.UPLOAD_SUCCESS);
						msg.setData(data);
						handler.sendMessage(msg);
						// ɾ���ļ���
						FileUtils.deleteDir();
						Bimp.bmp.clear();
						Bimp.drr.clear();
						Bimp.max = 0;
					}
				});
	}
}
