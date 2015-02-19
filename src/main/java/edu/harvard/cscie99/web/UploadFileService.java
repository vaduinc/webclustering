package edu.harvard.cscie99.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import edu.harvard.cscie99.clustering.Cluster;
import edu.harvard.cscie99.clustering.util.InputParamEnum;

@Path("/file")
public class UploadFileService {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("algo") String algo,
			@FormDataParam("typeInput") String typeInput,
			@FormDataParam("numNeigh") String numNeigh,
			@FormDataParam("comNeigh") String comNeigh,
			@FormDataParam("minDist") String minDist
			) {

		//String uploadedFileLoc  ation = "d://uploaded/"+ fileDetail.getFileName();
		String uploadedFileLocation = "./" + fileDetail.getFileName();
		String outputFileLocation = uploadedFileLocation+"OUT";

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		//-mtxfile ./HW1CodeData/testdata/iris.txt -outpath @display -algorithm jarvis -numNeighbors 5 -commonNeighbors 1
		String[] args = new String[12];
		args[0]= InputParamEnum.IN_OUTPATH.value();
		args[1]= outputFileLocation;
		args[2]= InputParamEnum.IN_ALGO.value();
		args[3]= algo;
		if (typeInput.equalsIgnoreCase(InputParamEnum.FP_TYPE)){
			args[4]= InputParamEnum.FP_FILE_TYPE;
		}else{
			args[4]= InputParamEnum.MTX_FILE_TYPE;
		}
		args[5]= uploadedFileLocation;
		args[6]= InputParamEnum.IN_NUM_NEIGH.value();
		args[7]= numNeigh;
		args[8]= InputParamEnum.IN_COMM_NEIGH.value();
		args[9]= comNeigh;
		args[10]= InputParamEnum.IN_MIN_DIST.value();
		args[11]= minDist;
		
		
		String output = "Where is the file?";
		try {
			output = cluster(args,outputFileLocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity(output).build();
		}

		return Response.status(200).entity(output).build();

	}

	
	public String cluster(String args[], String output) throws IOException{
		
		Cluster.main(args);
		return readFile(output);
	}
	
	
	public String readFile(String fileName) throws IOException {

        Scanner s = null;
        StringBuffer strb = new StringBuffer("RESULTS <br>");
        try {
            s = new Scanner(new BufferedReader(new FileReader(fileName)));

            while (s.hasNextLine()) {
                //System.out.println(s.next());
                strb.append(s.nextLine() + "<br>");
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
        
        return strb.toString();
    }
	
	
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}