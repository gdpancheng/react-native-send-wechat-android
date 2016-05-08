package com.j010wdz.rnsendwechat;

import android.content.Intent;
import android.content.ComponentName;
import android.net.Uri;

import java.util.ArrayList;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

public class RNSendWeChatModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext reactContext;

    public RNSendWeChatModule(ReactApplicationContext reactContext) {
      super(reactContext);
      this.reactContext = reactContext;
    }

    @Override
    public String getName() {
	  /*
		这个方法的返回值就是JavaScript中调用的名称
		var {NativeModules}=require('react-native');
		var rnSendWeChatAndroid = NativeModules.SendWeChatAndroid;
	  */
      return "SendWeChatAndroid";
    }
	
	/**
     * 微信分享到朋友圈(单张图片及描述)
     * */
	@ReactMethod
	public void sendPictureToTimeLine(String picturePath, String description) throws Exception {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(picturePath));
        intent.putExtra("Kdescription", description);
        
		//Check that an app exists to receive the intent
		if (intent.resolveActivity(this.reactContext.getPackageManager()) != null) {
			this.reactContext.startActivity(intent);
		}
    }

    /**
     * 把ReadableArray里的图片路径字符转化为Uri list
     * @param array
     * @return
     */
    protected ArrayList<Uri> changePathStrToUriList(ReadableArray array) {

        ArrayList<Uri> list = new ArrayList<Uri>();
        int count = array.size();
        for (int i = 0; i < count; i ++) {
            list.add(Uri.parse(array.getString(i)));
        }
        return list;
    }
	/**
     * 微信分享到朋友圈(多张图片及描述)
     *
     *
     * */
	@ReactMethod
	public void sendPicturesToTimeLine(ReadableArray picturePaths, String description) throws Exception {

        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, changePathStrToUriList(picturePaths));
        intent.putExtra("Kdescription", description);
		
		//Check that an app exists to receive the intent
		if (intent.resolveActivity(this.reactContext.getPackageManager()) != null) {
			this.reactContext.startActivity(intent);
		}
    }
	
	/**
     * 微信分享给好友(单张图片)
     * 注：图片描述无法同时分享
     *
     * */
	@ReactMethod
	public void sendPictureToFriend(String picturePath) throws Exception {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(picturePath));
        
		//Check that an app exists to receive the intent
		if (intent.resolveActivity(this.reactContext.getPackageManager()) != null) {
			this.reactContext.startActivity(intent);
		}
    }
	
	/**
     * 微信分享给好友(多张图片)
     * 注：图片描述无法同时分享
     *
     * */
	@ReactMethod
	public void sendPicturesToFriend(ReadableArray picturePaths) throws Exception {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, changePathStrToUriList(picturePaths));
        
		//Check that an app exists to receive the intent
		if (intent.resolveActivity(this.reactContext.getPackageManager()) != null) {
			this.reactContext.startActivity(intent);
		}
    }
}