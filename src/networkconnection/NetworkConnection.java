package networkconnection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Richard Webb simple network tester and log creator
 *
 */
public class NetworkConnection {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        // needed variables for the ping
        final String HOSTNAME = "WWW.GOOGLE.COM";   // static hostname, can be modfied for different host/ip
        String echoRequest = Ping(HOSTNAME);    // Call the Ping method to perform the heavy lifting in the command line

        // Create a date and time for stamping in the log file, as well as in the name.
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
        String fileDate = df.format(date);

        // Create the file, if it exists, skip this step.
        File log = new File("ping_log_" + fileDate + ".txt");
        try {
            if (!log.exists()) {
                log.createNewFile();
            }
        // Create the file writer/buffered writer and append to the file

            FileWriter fileWriter = new FileWriter(log, true);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write("\r\n" + ts + "\r\n" + echoRequest);
            out.close();

        } catch (IOException e) {
            System.out.println(e);
        }

    }
    
    
    /**
     *
     * @param hostname run the ping command in the console
     * @return the results to echoResult
     *
     */
    public static String Ping(String hostname) {

        String echoResult = "";
        String pingCmd = "ping " + hostname;

        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(pingCmd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                echoResult += inputLine;
                echoResult += "\r\n";

            }
            in.close();

        } catch (IOException e) {
            System.out.println(e);

        }

        return echoResult;
    }

}
