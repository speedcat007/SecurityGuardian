package cn.edu.njust.securityguardian.privacyprotection.filecrypt;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;

import cn.edu.njust.securityguardian.R;

public class AsyncCrypt extends AsyncTask<File, Integer, Boolean> {
	
	private final String TAG = this.getClass().getName();
	private FileExplorerActivity caller;
	private ProgressDialog waitDialog;
	File file;
	
	public AsyncCrypt(FileExplorerActivity call){
		this.caller = call;
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		if (waitDialog!=null&&waitDialog.isShowing()) {
			waitDialog.dismiss();
		}
		caller.listContents(file.getParentFile());
		
	}
	@Override
	protected Boolean doInBackground(File... params) {
		// TODO Auto-generated method stub
		Thread wait = new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				waitDialog = new ProgressDialog(caller);
				waitDialog.setTitle("wait~");
				waitDialog.setMessage(caller.getString(R.string.crypt_files));
				waitDialog.setIndeterminate(true);
				try {
					Thread.sleep(100);
					if(this.isInterrupted()){
						return ;
					}else {
						caller.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(waitDialog!=null)
									waitDialog.show();
							}
						});
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "cryptThread error!", e);
					e.printStackTrace();
				}
			}
			
		};
		caller.runOnUiThread(wait);
		file = params[0];
		String fileName = file.getName();
		if(fileName.endsWith(".fookey")){
			AES_RSA_Encryption.decryption(file,caller);
		}else {
			AES_RSA_Encryption.encryption(file,caller);
		}
		if(wait.isAlive()){
			try {
				wait.interrupt();
			} catch (Exception e2) {
				// TODO: handle exception
				Log.e(TAG, "在结束进程的时候出现错误！");
			}
		}
		return true;
	}

}
