package com.bawei.webservice.demo;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WebServiceDemo2Activity extends Activity {
	private final String NAMESPACE = "http://WebXml.com.cn/";
	private final String METHOD_NAME = "getMobileCodeInfo";
	private final String URL = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
	private final String SOAP_ACTION = "http://WebXml.com.cn/getMobileCodeInfo";
	private EditText mTelEdit;
	private Button mQueryBtn;
	private TextView mResultText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
    }
    private void initViews(){
    	mTelEdit = (EditText)findViewById(R.id.telEdit);
    	mQueryBtn = (Button)findViewById(R.id.queryBtn);
    	mResultText = (TextView)findViewById(R.id.resultText);
    	mQueryBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String tel = mTelEdit.getText().toString().trim();
				if(tel.length()<7){
					mTelEdit.setError("输     入有误，请重新输入！");
					mTelEdit.setText("");
					return ;
				}
				new SoapAsyncTask().execute(tel);
			}
		});
    }
    /**
     * 通过SOAP协议访问webService 获取手机号码归属地信息
     * @param tel
     * @return
     */
    private String queryMobileBySoap(String tel){
    	String result = null;
    	//先构建SoapObject，用来组装参数
    	SoapObject rqp = new SoapObject(NAMESPACE, METHOD_NAME);
    	rqp.addProperty("mobileCode", tel);
    	rqp.addProperty("userID", "");
    	//构建信封实例
    	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    	envelope.bodyOut = rqp;
//    	envelope.setOutputSoapObject(rqp);
    	envelope.dotNet = true;
    	//构建传输信封的实例
    	HttpTransportSE trans = new HttpTransportSE(URL);
    	try {
    		//访问网络，传输信封
			trans.call(SOAP_ACTION, envelope);
			//获取响应数据的两种方式
			SoapObject resp = (SoapObject)envelope.bodyIn;
			result = resp.getProperty(0).toString();
			Log.d("TAG", "resp1: " + result);
			Log.d("TAG", "resp2: " + envelope.getResponse().toString());
		} catch (IOException e) {
			Log.e("TAG", "" + e.getMessage());
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			Log.e("TAG", "" + e.getMessage());
			e.printStackTrace();
		}
    	return result;
    }
    class SoapAsyncTask extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			return queryMobileBySoap(params[0]);
		}
    	@Override
    	protected void onPostExecute(String result) {
    		if(result == null){
    			Toast.makeText(WebServiceDemo2Activity.this, "请求数据失败！", Toast.LENGTH_SHORT).show();
    		}else{
    			mResultText.setText(result);
    		}
    		super.onPostExecute(result);
    	}
    }
}