package cn.edu.njust.securityguardian.privacyprotection.filecrypt;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AES_RSA_Encryption {
	
	public static final int KEYSIZE = 1024;
	private final static String TAG = AES_RSA_Encryption.class.getName();
	public static boolean encryption(File file,FileExplorerActivity app) {
		

		try {
			init(file,app);
			
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			SecretKey key=keyGen.generateKey();
			ObjectInputStream keyIn =new ObjectInputStream(app.openFileInput("pub_key"));
			Key publicKey=(Key)keyIn.readObject();
			keyIn.close();	
			Cipher cipher= Cipher.getInstance("RSA");
			cipher.init(Cipher.WRAP_MODE,publicKey);
			byte[] wrappedKey=cipher.wrap(key);
			
			DataOutputStream out=new DataOutputStream(new FileOutputStream(file.getPath()+".fookey"));
			out.writeInt(wrappedKey.length);
			out.write(wrappedKey);
			InputStream in=new FileInputStream(file);
			cipher= Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE,key);
			crypt(in, out, cipher);
			in.close();
			out.close();
			file.delete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "encryption()发生错误");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean decryption(File file,FileExplorerActivity app) {
		String fileName = file.getName();
		fileName = fileName.substring(0, fileName.length()-7);
		try {
			
			DataInputStream in =new DataInputStream(new FileInputStream(file));
			int length=in.readInt();
			byte[] wrappedKey= new byte[length];
			in.read(wrappedKey, 0, length);	
			ObjectInputStream keyIn =new ObjectInputStream(app.openFileInput("pri_key"));
			Key privateKey=(Key)keyIn.readObject();
			keyIn.close();
			Cipher cipher= Cipher.getInstance("RSA");
			cipher.init(Cipher.UNWRAP_MODE,privateKey);
			Key key =cipher.unwrap (wrappedKey,"AES", Cipher.SECRET_KEY);
			OutputStream out=new FileOutputStream(file.getParent()+file.separator+fileName);
			cipher= Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE,key);
			crypt(in,out,cipher);
			in.close();
			out.close();
			file.delete();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static void init(File file,FileExplorerActivity app) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		try {
			KeyPairGenerator keyGen = null;
			keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = new SecureRandom();
			keyGen.initialize(KEYSIZE, random);
			KeyPair keypair = keyGen.generateKeyPair();
			
			//File f = new File("pub_key");
			//ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("pub_key"));
			FileOutputStream o =app.openFileOutput("pub_key",app.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(o);
			out.writeObject(keypair.getPublic());
			out.close();
			o.close();

			out = new ObjectOutputStream(app.openFileOutput("pri_key", app.MODE_PRIVATE));
            PrivateKey privateKey=keypair.getPrivate();
			out.writeObject(privateKey);
			out.close();

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			Log.e(TAG, "crypt  init()出现错误！"+e.getMessage());
			e.printStackTrace();
		}
	}

	private static void crypt(InputStream in, OutputStream out, Cipher cipher)
			throws IOException, GeneralSecurityException {
		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(blockSize);
		byte[] inBytes = new byte[blockSize];
		byte[] outBytes = new byte[outputSize];
		int inLength = 0;
		boolean more = true;
		while (more)
		{
			inLength = in.read(inBytes);

			if (inLength != -1)
			{
				int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
				out.write(outBytes, 0, outLength);
			}
			else {
				outBytes = cipher.doFinal();
				out.write(outBytes);
				more = false;
			}
		}
	}


}
