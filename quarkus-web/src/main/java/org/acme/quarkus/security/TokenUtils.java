package org.acme.quarkus.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

public class TokenUtils {

  /**
   * 根据jsonResName 产生Token, 用privateKey.pem中的私钥进行签名
   * @param jsonResName
   * @param timeClaims
   * @return
 * @throws IOException
 * @throws InvalidKeySpecException
 * @throws NoSuchAlgorithmException
   */
  public static String generateTokenString(String jsonResName, 
    Map<String, Long> timeClaims) 
    throws NoSuchAlgorithmException, InvalidKeySpecException, IOException{
    PrivateKey pk = getPKfromFile("/privateKey.pem");
    return generateTokenString(pk, "/privateKey.pem", jsonResName, timeClaims);
  }

  /**
   * 根据私钥返回Token
   * 输入:
   *   pk: java security PrivateKey对象
   *   kid: ?
   *   jsonRestName:
   *   timeClaims:
   * 返回： Token String
   *  
   */
  public static String generateTokenString(PrivateKey pk, String kid, 
    String jsonRestName, Map<String, Long> timeClaims){
      JwtClaimsBuilder claims = Jwt.claims(jsonRestName);
      long currentTimeInSecs = currentTimeInSecs();
      long exp = timeClaims != null && timeClaims.containsKey(Claims.exp.name())
        ? timeClaims.get(Claims.exp.name()): currentTimeInSecs + 300;
      claims.issuedAt(currentTimeInSecs);
      claims.claim(Claims.auth_time.name(), currentTimeInSecs);
      claims.expiresAt(exp);
      return claims.jws().signatureKeyId(kid).sign(pk);

  }

  
  public static PrivateKey getPKfromFile(final String pemResName) 
  throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
    try (InputStream contentIS = TokenUtils.class.getResourceAsStream(pemResName)){
      byte[] tmp = new byte[4096];
      int length = contentIS.read(tmp);
      return decodePrivateKey(new String (tmp, 0, length, "UTF-8"));
    }
  }

  /**
   * 输入私钥文件里面读到的内容
   * 返回java security里面的PrivateKey对象
   * 过程使用java security里面的KeyFactory 产生
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   * 
   */
  public static PrivateKey  decodePrivateKey(final String pemEncoded) 
    throws NoSuchAlgorithmException, InvalidKeySpecException{
    byte[] decodeBytes = decodedBytes(pemEncoded);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodeBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePrivate(keySpec);
  }
  /**
   * Base64解码
   */
  private static byte[] decodedBytes(final String pemEncoded){
    final String normalizedPem = removeBeginEnd(pemEncoded);
    return Base64.getDecoder().decode(normalizedPem);
  }
  private static String removeBeginEnd(String pem){
    pem = pem.replaceAll("-----BEGIN (.*)-----", "");
    pem = pem.replaceAll("-----END (.*)-----", "");
    pem = pem.replaceAll("\r\n", "");
    pem = pem.replaceAll("\n", "");
    return pem.trim();
  }
  public static int currentTimeInSecs() {
    long currentTimeMS = System.currentTimeMillis();
    return (int)(currentTimeMS/1000);
  }
}