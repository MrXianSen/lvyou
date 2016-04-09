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
 * DESCRIPTION: 使客户端连入互联网，获取数据
 * 
 * @author 张建国
 * 
 * @since 2016.3.2
 * 
 * @version 1.0
 * 
 *************************************************************************/
public class HttpUtil {
	/*************************************************
	 * 获取连接对象
	 * 
	 * @param url
	 *            URL地址
	 * @return 返回HttpURLConnection对象
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
	 * DESCRIPTION: 封装发送请求数据
	 * 
	 * @param params
	 *            Map类型的数据集
	 * @param encode
	 *            编码方式
	 * @return 返回StringBuilder对象实例
	 *************************************************/
	private static StringBuffer _getRequestData(Map<String, String> params,
			String encode) {
		StringBuffer stringBuffer = new StringBuffer(); // 存储封装好的请求体信息
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1); // 删除最后的一个"&"
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}

	/*************************************************
	 * DESCRIPTION: 处理服务器返回的数据结果
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
	 * DESCRIPTION: 向服务器中发送数据并返回对象
	 * 
	 * @param params
	 *            请求的参数
	 * @param encode
	 *            参数的编码格式
	 * @param url
	 *            请求的路径
	 * @return 返回JSONArray对象
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
				// 返回处理后的数据
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
		// 上传地址
		if (type == 0)
			hostUrl = Constants.BASE_URL + Constants.UPLOAD_CIRCLE_URL;
		else
			hostUrl = Constants.BASE_URL + Constants.EDIT_PROFILE_URL;
		HttpUtils http = new HttpUtils();
		// 向服务器发送上传请求和参数
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
						// 删除文件夹
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
						// 删除文件夹
						FileUtils.deleteDir();
						Bimp.bmp.clear();
						Bimp.drr.clear();
						Bimp.max = 0;
					}
				});
	}
}
