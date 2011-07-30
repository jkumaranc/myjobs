package org.jk.myjobs.utils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ELFunctions {
    private static final Logger LOGGER = Logger.getLogger(ELFunctions.class.getName());

    public static void showSource(FacesContext ctx, String file) {
        ExternalContext extCtx = ctx.getExternalContext();
        BufferedReader r = new BufferedReader(new InputStreamReader(extCtx
                .getResourceAsStream(file)));
        StringWriter w = new StringWriter();
        PrintWriter pw = new PrintWriter(w);

        try {
            for (String s = r.readLine(); s != null; s = r.readLine()) {
                pw.write(s);
                pw.write('\n');
            }
            ctx.getResponseWriter().writeText(w.toString(), null);
        } catch (IOException ioe) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE, ioe.toString(), ioe);
            }
        }
    }
}