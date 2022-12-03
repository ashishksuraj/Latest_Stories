
import java.io.*;
import java.net.*;
import java.util.*;

public class latest_stories {

  private final String USER_AGENT = "Mozilla/5.0";

  public static void main(String[] args) throws Exception {

    latest_stories http = new latest_stories();

    http.sendGet();

  }

  private void sendGet() throws Exception {

    Scanner s = new Scanner(System.in);
    System.out.println("enter the URL");
    String url = s.nextLine();

    URL obj = new URL("https://" + url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    con.setRequestMethod("GET");

    con.setRequestProperty("User-Agent", USER_AGENT);

    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'GET' request to URL : " + url);
    System.out.println("Response Code : " + responseCode);

    StringBuffer response;
    try ( var in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
      String inputLine;
      response = new StringBuffer();
      String str1 = "Latest Stories";
      boolean flag = false;
      String[] stra = new String[12];
      int k = 0;

      while ((inputLine = in.readLine()) != null) {
        if (inputLine.contains(str1) && flag == false) {
          flag = true;
        }
        if (flag == true) {
          response.append(inputLine);
          //System.out.println(inputLine);
          if (inputLine.contains("\"latest-stories__item\"")) {
            for (int i = 0; i < 2; i++) {
              inputLine = in.readLine();
              inputLine = inputLine.trim();
              if (inputLine.contains("<a href=\"") || inputLine.contains("\">") || inputLine.contains("</h3>") || inputLine.contains("<h3 class=\"latest-stories__item-headline")) {
                inputLine = inputLine.replace("<a href=\"", "");
                inputLine = inputLine.replace("\">", "");
                inputLine = inputLine.replace("</h3>", "");
                inputLine = inputLine.replace("<h3 class=\"latest-stories__item-headline", "");
              }
              if (k % 2 == 0) {
                inputLine = "Link: https://time.com" + inputLine;
              } else {
                inputLine = "Title: " + inputLine;
              }
              stra[k] = (inputLine);
              k++;
            }
          }
          if (inputLine.contains(",/section")) {
            break;
          }
        }
      }
      System.out.println("");
      for (int i = 1; i < 12; i = i + 2) {
        System.out.println(stra[i]);
        System.out.println(stra[i - 1] + "\n");
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}
