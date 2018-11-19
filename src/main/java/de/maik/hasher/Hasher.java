package de.maik.hasher;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Hasher {

  public static void main(String[] args) {

    Console con = System.console();
    if (con == null) {
      System.out.println("Run in Real Console.");
    } else {
      System.out.print("Password: ");
      final String password = String.valueOf(con.readPassword());
      if (password.length() == 0) {
        System.out.println("Please provide the password");
      } else {
        MessageDigest messageDigest;
        Security.addProvider(new BouncyCastleProvider());
        try {
          messageDigest = MessageDigest.getInstance("SHA-512", "BC");
          byte[] digesta = messageDigest.digest(password.getBytes());

          String hexString = String.valueOf(Hex.encodeHex(digesta));

          StringSelection selection = new StringSelection(hexString);
          Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
          clipboard.setContents(selection, selection);
          System.out.println("Copied hashed password into your clipboard.");
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        } catch (NoSuchProviderException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
