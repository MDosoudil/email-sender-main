package utb.fai;

public class App {

    public static void main(String[] args) {
        // TODO: Implement input parameter processing
        String host = args[0];
        int port = Integer.parseInt( args[1]);
        String odesilatel = args[2];
        String prijemce = args[3];
        String subject = args[4];
        String obsah = args[5];

        try {
            EmailSender sender = new EmailSender(host, port);
            sender.send(odesilatel, prijemce, subject, obsah);
            sender.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
