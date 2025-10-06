package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {
    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host,port);
        out = socket.getOutputStream();
        in = socket.getInputStream();
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        sendCommand("EHLO localhost\r\n");
        readResponse();

        sendCommand("MAIL FROM:<"+from+">\r\n");
        readResponse();

        sendCommand("RCPT TO:<"+to+">\r\n");
        readResponse();

        sendCommand("DATA\r\n");
        readResponse();
        
        sendCommand("Subject: " + subject+"\r\n");
        sendCommand("From: " + from+"\r\n");
        sendCommand("To: " + to+"\r\n");
        sendCommand("\r\n");
        sendCommand(text+"\r\n");
        sendCommand("\r\n.\r\n");
        
        readResponse();
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close() throws IOException {
        sendCommand("QUIT\r\n");
        readResponse();
        socket.close();
    }

    private void readResponse() throws IOException {
        if (in.available() > 0){
            byte[] in_buffer = new byte[1024];
            int in_size = in.read(in_buffer);
            System.out.write(in_buffer,0,in_size);
        }
    }

    private void sendCommand(String command) throws IOException{
        byte[] out_buffer = command.getBytes();
        out.write(out_buffer,0,out_buffer.length);
        out.flush();
        System.out.println("<<" + command);
    }
}
