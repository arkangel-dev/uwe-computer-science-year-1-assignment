/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

// import static diner.program.DinerProgram.print;
import java.io.File;
// import java.io.FileWriter;
// import java.io.PrintWriter;
// import static java.lang.System.exit;
// import java.util.ArrayList;
// import java.util.Scanner;

// libraries needed for mail
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;


import java.util.ArrayList;
import java.text.DecimalFormat;


import java.io.IOException;


import org.ini4j.*;


/**
 *
 * @author Sam Ramirez
 */
public class networkManagement {

	static void sendEmail(String recieverEmail,String title, String subject, String body){
		try {
			Ini ini = new Ini(new File("config.ini"));
			final String fromEmail =  ini.get("email", "from_address"); //requires valid gmail id
			final String password =  ini.get("email", "password"); // correct password for gmail id
			final String toEmail = recieverEmail;// can be any email id 
			final String replyTo = ini.get("email", "reply_to");
			
			// System.out.println("TLSEmail Start");
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
			props.put("mail.smtp.port", "587"); //TLS Port
			props.put("mail.smtp.auth", "true"); //enable authentication
			props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
					//create Authenticator object to pass in Session.getInstance argument
			Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};
			Session session = Session.getInstance(props, auth);		
			EmailUtil.sendEmail(session, toEmail,subject, body, title, replyTo);
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}




	static String formatEmail(ArrayList<billEntry> billData){
		try {
			Ini ini = new Ini(new File("config.ini"));
			final String image_url =  ini.get("email", "header_img");
			final String sign_off = ini.get("email", "sign_off");
			final String address = ini.get("email", "address");
			final String website = ini.get("email", "website");

			String firstPart = "<!DOCTYPE html>"+
			"<html>"+
			"<head>"+
			""+
			"  <meta charset=\"utf-8\">"+
			"  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">"+
			"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"+
			"  <style type=\"text/css\">"+
			"  /**"+
			"   * Google webfonts. Recommended to include the .woff version for cross-client compatibility."+
			"   */"+
			"  @media screen {"+
			"    @font-face {"+
			"      font-family: 'Source Sans Pro';"+
			"      font-style: normal;"+
			"      font-weight: 400;"+
			"      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');"+
			"    }"+
			""+
			"    @font-face {"+
			"      font-family: 'Source Sans Pro';"+
			"      font-style: normal;"+
			"      font-weight: 700;"+
			"      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');"+
			"    }"+
			"  }"+
			""+
			"  /**"+
			"   * Avoid browser level font resizing."+
			"   * 1. Windows Mobile"+
			"   * 2. iOS / OSX"+
			"   */"+
			"  body,"+
			"  table,"+
			"  td,"+
			"  a {"+
			"    -ms-text-size-adjust: 100%; /* 1 */"+
			"    -webkit-text-size-adjust: 100%; /* 2 */"+
			"  }"+
			""+
			"  /**"+
			"   * Remove extra space added to tables and cells in Outlook."+
			"   */"+
			"  table,"+
			"  td {"+
			"    mso-table-rspace: 0pt;"+
			"    mso-table-lspace: 0pt;"+
			"  }"+
			""+
			"  /**"+
			"   * Better fluid images in Internet Explorer."+
			"   */"+
			"  img {"+
			"    -ms-interpolation-mode: bicubic;"+
			"  }"+
			""+
			"  /**"+
			"   * Remove blue links for iOS devices."+
			"   */"+
			"  a[x-apple-data-detectors] {"+
			"    font-family: inherit !important;"+
			"    font-size: inherit !important;"+
			"    font-weight: inherit !important;"+
			"    line-height: inherit !important;"+
			"    color: inherit !important;"+
			"    text-decoration: none !important;"+
			"  }"+
			""+
			"  /**"+
			"   * Fix centering issues in Android 4.4."+
			"   */"+
			"  div[style*=\"margin: 16px 0;\"] {"+
			"    margin: 0 !important;"+
			"  }"+
			""+
			"  body {"+
			"    width: 100% !important;"+
			"    height: 100% !important;"+
			"    padding: 0 !important;"+
			"    margin: 0 !important;"+
			"  }"+
			""+
			"  /**"+
			"   * Collapse table borders to avoid space between cells."+
			"   */"+
			"  table {"+
			"    border-collapse: collapse !important;"+
			"  }"+
			""+
			"  a {"+
			"    color: #1a82e2;"+
			"  }"+
			""+
			"  img {"+
			"    height: auto;"+
			"    line-height: 100%;"+
			"    text-decoration: none;"+
			"    border: 0;"+
			"    outline: none;"+
			"  }"+
			"  </style>"+
			""+
			"</head>"+
			"<body style=\"background-color: #e9ecef;\">"+
			""+
			"  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">"+
			"    "+
			"  </div>"+
			"  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"+
			"    <tr>"+
			"      <td align=\"center\" bgcolor=\"#e9ecef\">"+
			"        <!--[if (gte mso 9)|(IE)]>"+
			"        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">"+
			"        <tr>"+
			"        <td align=\"center\" valign=\"top\" width=\"600\">"+
			"        <![endif]-->"+
			"        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"+
			"          <tr>"+
			"            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">"+
			"				<img src=\"" + image_url + "\" width=\"50%\"> " + 
			"            </td>"+
			"          </tr>"+
			"        </table>"+
			"        <!--[if (gte mso 9)|(IE)]>"+
			"        </td>"+
			"        </tr>"+
			"        </table>"+
			"        <![endif]-->"+
			"      </td>"+
			"    </tr>"+
			""+
			"    <tr>"+
			"      <td align=\"center\" bgcolor=\"#e9ecef\">"+
			"        <!--[if (gte mso 9)|(IE)]>"+
			"        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">"+
			"        <tr>"+
			"        <td align=\"center\" valign=\"top\" width=\"600\">"+
			"        <![endif]-->"+
			"        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"+
			"          <tr>"+
			"            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">"+
			"              <h1 align=\"center\" style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Hello! Here is your receipt.</h1>"+
			"            </td>"+
			"          </tr>"+
			"        </table>"+
			"        <!--[if (gte mso 9)|(IE)]>"+
			"        </td>"+
			"        </tr>"+
			"        </table>"+
			"        <![endif]-->"+
			"      </td>"+
			"    </tr>"+
			"    <tr>"+
			"      <td align=\"center\" bgcolor=\"#e9ecef\">"+
			"        <!--[if (gte mso 9)|(IE)]>"+
			"        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">"+
			"        <tr>"+
			"        <td align=\"center\" valign=\"top\" width=\"600\">"+
			"        <![endif]-->"+
			"        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"+
			""+
			"          <!-- start copy -->"+
			"          <tr>"+
			"            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">"+
			"			  <p style=\"margin: 0;\" align=\"center\">Thank you for doing business with us. Here is the receipt for the items you bought. Please come back soon.</p>"+
			"				<br>" +
			"			  <table align=\"center\" border=\"0\">"+
			"					<tr>"+
			"						<td align=\"left\" width=\"250pt\"><b>Item</b></td>"+
			"						<td align=\"center\" width=\"50px\"><b>Quantity</b></td>"+
			"						<td align=\"center\" width=\"90px\"><b>Price</b></td>"+
			"						<td align=\"center\" width=\"80px\"><b>Sub-total</b></td>"+
			"					</tr>";
				
			String secondPart = "</table>"+
			"            </td>"+
			"          </tr>"+
			"          <!-- end copy -->"+
			""+
			"          <!-- start button -->"+
			"          <tr>"+
			"            <td align=\"left\" bgcolor=\"#ffffff\">"+
			"              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"+
			"                <tr>"+
			"                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">"+
			"                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"+
			"                      <tr>"+
			"                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">"+
			"                          <a href=\"" + website + "\" target=\"_blank\" style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\">Come back soon</a>"+
			"                        </td>"+
			"                      </tr>"+
			"                    </table>"+
			"                  </td>"+
			"                </tr>"+
			"              </table>"+
			"            </td>"+
			"          </tr>"+
			"          <!-- end button -->"+
			""+
			""+
			""+
			"          <!-- start copy -->"+
			"          <tr>"+
			"            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">"+
			"              <p style=\"margin: 0;\">Cheers,<br> " + sign_off + "</p>"+
			"            </td>"+
			"          </tr>"+
			"          <!-- end copy -->"+
			""+
			"        </table>"+
			"        <!--[if (gte mso 9)|(IE)]>"+
			"        </td>"+
			"        </tr>"+
			"        </table>"+
			"        <![endif]-->"+
			"      </td>"+
			"    </tr>"+
			"    <!-- end copy block -->"+
			""+
			"    <!-- start footer -->"+
			"    <tr>"+
			"      <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">"+
			"        <!--[if (gte mso 9)|(IE)]>"+
			"        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">"+
			"        <tr>"+
			"        <td align=\"center\" valign=\"top\" width=\"600\">"+
			"        <![endif]-->"+
			"        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"+
			""+
			"          <!-- start permission -->"+
			"          <tr>"+
			"            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">"+
			"              <p style=\"margin: 0;\">You received this email because we received a request for a receipt. If you didn't request a receipt you can safely delete this email.</p>"+
			"            </td>"+
			"          </tr>"+
			"          <!-- end permission -->"+
			""+
			"          <!-- start unsubscribe -->"+
			"          <tr>"+
			"            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">"+
			"              <!-- <p style=\"margin: 0;\">If you don't want to recieve any more emails, well big whoop! There is nothing you can do about it.</p>-->"+
			"              <p style=\"margin: 0;\">" + address + "<p>"+
			"            </td>"+
			"          </tr>"+
			"          <!-- end unsubscribe -->"+
			""+
			"        </table>"+
			"        <!--[if (gte mso 9)|(IE)]>"+
			"        </td>"+
			"        </tr>"+
			"        </table>"+
			"        <![endif]-->"+
			"      </td>"+
			"    </tr>"+
			"    <!-- end footer -->"+
			""+
			"  </table>"+
			"  <!-- end body -->"+
			""+
			"</body>"+
			"</html>";
			
			
			String returnData = "";
			returnData += firstPart;
			
			double total = 0.0;

			for (int i = 0; i < billData.size(); i++){
				String tempData = String.format("<tr>"+
													"<td align=\"left\">%s</td>" +
													"<td align=\"center\">%s</td>" +
													"<td align=\"center\">$%s</td>" +
													"<td align=\"center\">$%s</td>" +
												"</tr>", billData.get(i).name, billData.get(i).quantity, billData.get(i).price, billData.get(i).finalp);
				returnData += tempData;
				total += billData.get(i).finalp;
			}
			double gst = total * 0.06;
			double grandtotal = gst + total;

			DecimalFormat df = new DecimalFormat("####0.00");
			returnData += "<tr><br></tr><tr>"+
								"<td></td>"+
								"<td></td>"+
								"<td align=\"center\" style=\"font-size: 11pt\"><b>Total</b></td>"+
								"<td align=\"center\">$" + df.format(total) + "</td>"+
							"</tr><tr>"+
								"<td></td>"+
								"<td></td>"+
								"<td align=\"center\" style=\"font-size: 11pt\"><b>6% GST</b></td>"+
								"<td align=\"center\">$" + df.format(gst) + "</td>"+
							"</tr><tr>"+
								"<td></td>"+
								"<td></td>"+
								"<td align=\"center\" style=\"font-size: 11pt\"><b>Grand Total</b></td>"+
								"<td align=\"center\">$" + df.format(grandtotal) + "</td>"+
							"</tr>";

			returnData+=secondPart;
			return(returnData);
	} catch (IOException e){

	}

	return("error");
	}
}

