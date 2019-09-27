package com.github.tnerevival.core;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.core.version.ReleaseType;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class UpdateChecker {
  private String url;
  private String build;
  private String currentBuild;

  TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
    public X509Certificate[] getAcceptedIssuers(){return null;}
    public void checkClientTrusted(X509Certificate[] certs, String authType){}
    public void checkServerTrusted(X509Certificate[] certs, String authType){}
  }};

  public UpdateChecker(String url, String currentBuild) {
    this.url = url;
    this.currentBuild = currentBuild;
    getLatestBuild();
  }

  public void getLatestBuild() {
    build = currentBuild;
    try {
      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, trustAllCerts, new SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

      URL url = new URL(this.url);
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      this.build = in.readLine();
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
      TNELib.instance().getLogger().info("Unable to contact update server!");
    }
  }

  public ReleaseType getRelease() {
    Integer latest = Integer.valueOf(build.replace(".", ""));
    Integer current = Integer.valueOf(currentBuild.replace(".", ""));

    if(latest < current) return ReleaseType.PRERELEASE;
    if(latest.equals(current)) return ReleaseType.LATEST;
    return ReleaseType.OUTDATED;
  }

  public String getBuild() {
    return build;
  }

  public String getCurrentBuild() {
    return currentBuild;
  }
}