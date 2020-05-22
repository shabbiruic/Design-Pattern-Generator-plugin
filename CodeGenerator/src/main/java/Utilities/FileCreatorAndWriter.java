package Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileCreatorAndWriter {

    String filename;
    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;
    File f;
    Configuration config = Configuration.getInstance();
    public static final Logger logger = LoggerFactory.getLogger(FileCreatorAndWriter.class.getName());

    public FileCreatorAndWriter(String filename,String outputFolderLocation) {
        this.filename = filename;
        this.f = new File(  outputFolderLocation+"\\"+this.filename + config.getValue("java.fileExtension"));
        System.out.print(f.getAbsolutePath());
        if (!f.exists()){
            try {
                fw = new FileWriter(f, true);
                bw = new BufferedWriter(fw);
                pw = new PrintWriter(bw);
            }
            catch (IOException io) {
                logger.error("error while creating file writer for {} file {}",f.getAbsoluteFile(),io.getMessage());
            }
        }
        else
        {
            try{
                throw new Exception("file with the same name already exists");
            }
            catch(Exception e)
            {
                logger.error("trying to create a duplicate file {}",e.getMessage());
            }
        }
        }

    public PrintWriter getPrintWriter()
    {
        return pw;
    }

    public void close()
    {
        if(pw!=null) {
            try {
                pw.flush();
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException io) {
                logger.error("error while closing a file {}",io.getMessage());
            }
        }
    }
}
