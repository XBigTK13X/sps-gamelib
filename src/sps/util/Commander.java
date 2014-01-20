package sps.util;

import sps.core.Logger;

import java.io.*;

public class Commander {
    private class StreamGobbler extends Thread {
        InputStream is;
        String type;

        StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {

                    Logger.info(type + ">" + line);
                }
            }
            catch (IOException ioe) {
                Logger.exception(ioe);
            }
        }
    }

    private StreamGobbler _output;
    private StreamGobbler _error;
    private Process _process;

    public Commander(String command, String workingDir, boolean watchOutput) {
        try {
            _process = Runtime.getRuntime().exec(command, null, new File(workingDir));
            if (watchOutput) {
                _error = new StreamGobbler(_process.getErrorStream(), "ERROR");
                _output = new StreamGobbler(_process.getInputStream(), "OUTPUT");
                _error.start();
                _output.start();
            }
        }
        catch (IOException e) {
            Logger.exception(e);
        }
    }

    public boolean isFinished() {
        try {
            //If this doesn't throw an exception, then the process finished.
            _process.exitValue();
            return true;

        }
        catch (Exception swallow) {
            return false;
        }
    }
}
