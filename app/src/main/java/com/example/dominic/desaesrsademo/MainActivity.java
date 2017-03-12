package com.example.dominic.desaesrsademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.dominic.desaesrsademo.util.Aes;
import com.example.dominic.desaesrsademo.util.Des;
import com.example.dominic.desaesrsademo.util.RSACrypt;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String data = "我中了500亿大奖，不要告诉别人";
    private String password = "12345678";//对称加密密码，8位密码
    private TextView mResult;

    private boolean isDes;
    private boolean isAes;
    private boolean isRsa;

    private String mDesEncrypt;
    private String mDesDecrypt;
    private String mAesEncrypt;

    private String privateKey;
    private String publicKey;
    private byte[] mEncryptByPrivateKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        //初始化RSA密钥对
        try {
            Map<String, Object> keyPair = RSACrypt.genKeyPair();
            privateKey = RSACrypt.getPrivateKey(keyPair);   //得到公钥
            publicKey = RSACrypt.getPublicKey(keyPair);     //得到私钥

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initView() {
        mResult = (TextView) findViewById(R.id.tv_result);
    }

    public void des(View view) {
        if (!isDes) {
            try {
                mDesEncrypt = Des.encrypt(data, password);
                mResult.setText("DES加密：" + mDesEncrypt);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                mDesDecrypt = Des.decrypt(mDesEncrypt, password);
                mResult.setText("DES解密：" + mDesDecrypt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isDes = !isDes;
    }

    public void aes(View view) {
        if (!isAes){
            mAesEncrypt = Aes.encrypt(data, password);
            mResult.setText("AES加密" + mAesEncrypt);

        }else{
            String decrypt = Aes.decrypt(mAesEncrypt, password);
            mResult.setText(decrypt);
        }
        isAes =!isAes;
    }


    public void rsa(View view) {

        if (!isRsa){

            try {
                mEncryptByPrivateKey = RSACrypt.encryptByPrivateKey(data.getBytes(), privateKey);
              //  mResult.setText("RSA加密：" + new String(bytes)); //RSA加密内部使用Base64编码
                mResult.setText("RSA加密：" + RSACrypt.encode(mEncryptByPrivateKey));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            try {
                byte[] decryptByPublicKey = RSACrypt.decryptByPublicKey(mEncryptByPrivateKey, publicKey);
                mResult.setText("RSA解密：" + new String(decryptByPublicKey));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        isRsa = !isRsa;

    }


}
