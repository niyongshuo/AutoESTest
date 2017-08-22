package github;
/*
 * org.json.jar
 * json.jar
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.JSONException;
import org.json.JSONObject;

import autoTest.AutoTest;
import emailService.EmailService;

@WebServlet("/WebhooksReceiver")
public class WebhooksReceiver extends HttpServlet implements javax.servlet.ServletContextListener{
	private static final long serialVersionUID = 1L;
	private static BlockingQueue<String> queue = new LinkedBlockingDeque<String>();
	private static boolean isRunning = false;
	private static int testnum = 1;
	
	private Thread workerThread = new Thread() {
		public void run() {
			EmailService em = new EmailService();
	        em.sendEmail("yni4@sheffield.ac.uk","Thread runs");
			while(true) {
				if(queue.size()!=0) {									
					String text=queue.poll();
					try {
						mainExec(text);
					} catch (IOException | ServletException e) {
						e.printStackTrace();
					}
				} else {
					try {					
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	};
	
	
	public void contextInitialized(ServletContextEvent sce) {
	        if ((workerThread == null) || (!workerThread.isAlive())) {
	        	workerThread.start();
	        }
	    }

	public void contextDestroyed(ServletContextEvent sce){
	        try {
	        	workerThread.interrupt();
	        } catch (Exception ex) {
	        }
	}
	
    public WebhooksReceiver() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		StringBuilder builder = new StringBuilder();
	    String aux = "";

	    while ((aux = request.getReader().readLine()) != null) {
	        builder.append(aux);
	    }
	    String text = builder.toString();
	    
		queue.add(text);
		
	}
	
	public void mainExec(String text) throws IOException,ServletException{
	    
	    try {
	        JSONObject json = new JSONObject(text);
	        String html_url = json.getJSONObject("repository").getString("html_url");
	        String name = json.getJSONObject("repository").getString("name");
	        String email = json.getJSONObject("repository").getJSONObject("owner").getString("email");
	        String timestamp = json.getJSONObject("head_commit").getString("timestamp");
	        String forks_url = json.getJSONObject("repository").getString("forks_url");
	        String pulls_url = json.getJSONObject("repository").getString("pulls_url");
	        String ssh_url = json.getJSONObject("repository").getString("ssh_url");
	        String clone_url = json.getJSONObject("repository").getString("clone_url");
	        
	        RepoManager r = new RepoManager();
	        
	        try {
				r.fork(forks_url, name);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        	
	        try {
				 Git git = r.clone(clone_url, name);	
				 AutoTest a = new AutoTest();
			        // test
			        try {
						a.autoTest(name);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			        
			        try {
						r.push(name, git);
					} catch (TransportException e2) {
						e2.printStackTrace();
					} catch (GitAPIException e2) {
						e2.printStackTrace();
					}
					
					try {
						r.pullRequest(pulls_url, name);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				
			} catch (InvalidRemoteException e3) {
				e3.printStackTrace();
			} catch (TransportException e3) {
				e3.printStackTrace();
			} catch (GitAPIException e3) {
				e3.printStackTrace();
			}catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	            	        
	        testnum++;
	        
	        EmailService e = new EmailService();
	        e.sendEmail(email,"Your project is already tested by EvoSuite, please check your latest Pull Request: "+html_url);
	        
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
	    
	}
	
}
