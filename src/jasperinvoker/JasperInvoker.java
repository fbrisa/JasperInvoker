package jasperinvoker;

import de.cenote.jasperstarter.App;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author francesco
 */
public class JasperInvoker {
    public static void invoke(
            String folder,
            String jasperFile,
            String dstype,
            String host,
            String user,
            String pass,
            String dbname,
            String output,
            HashMap<String, String> parameters
    ) {
        ArrayList<String> arguments = createArguments(folder, jasperFile, dstype, host, user, pass, dbname, output);

        parameters.keySet().stream().forEach((key) -> {
            arguments.add(key + "=" + parameters.get(key));
        });

        callIt(arguments);
    }

    public static void invoke(
            String folder,
            String jasperFile,
            String dstype,
            String host,
            String user,
            String pass,
            String dbname,
            String output,
            String[] parameters
    ) {
        ArrayList<String> arguments = createArguments(folder, jasperFile, dstype, host, user, pass, dbname, output);

        arguments.addAll(Arrays.asList(parameters));

        callIt(arguments);
    }

    public static String dammiNomeFileSenzaEstensione(String nomeFile) {

        int i = nomeFile.lastIndexOf(".");
        if (i >= 0) {
            return nomeFile.substring(0, i);
        }

        return nomeFile;
    }

    private static ArrayList<String> createArguments(
            String folder,
            String jasperFile,
            String dstype,
            String host,
            String user,
            String pass,
            String dbname,
            String output
    ) {
        ArrayList<String> arguments = new ArrayList<>();

        String format = "pdf";

        String extension = "";
        int i = output.lastIndexOf('.');
        int p = Math.max(output.lastIndexOf('/'), output.lastIndexOf('\\'));

        if (i > p) {
            extension = output.substring(i + 1);
        }

        if (!extension.isEmpty()) {
            format = extension;
        }

        
        
        
        output=dammiNomeFileSenzaEstensione(output);
        
        
        
        arguments.add("process");
        arguments.add(jasperFile);
        arguments.add("-f");
        arguments.add(format);//pdf
        arguments.add("--jdbc-dir");
        arguments.add(".");
        arguments.add("-t");
        arguments.add(dstype);//mysql
        //arguments.add("--db-driver");arguments.add("org.gjt.mm.mysql.Driver");
        arguments.add("-H");
        arguments.add(host);//"localhost"
        arguments.add("-u");
        arguments.add(user);//"root"
        arguments.add("-p");
        arguments.add(pass == null ? "" : pass);
        arguments.add("-n");
        arguments.add(dbname);
        arguments.add("-o");
        arguments.add(output);
        arguments.add("-r");
        arguments.add(folder);
        
        return arguments;

    }

    private static void callIt(ArrayList<String> arguments) {
        String[] args = new String[arguments.size()];
        args = arguments.toArray(args);
        App.main(args);
    }

}
