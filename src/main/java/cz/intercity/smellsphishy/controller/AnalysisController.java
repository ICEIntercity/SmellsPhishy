package cz.intercity.smellsphishy.controller;

import cz.intercity.smellsphishy.analysis.Link;
import cz.intercity.smellsphishy.analysis.Message;
import cz.intercity.smellsphishy.analysis.remote.VirusTotalResult;
import cz.intercity.smellsphishy.common.exception.InvalidFormatException;
import cz.intercity.smellsphishy.common.exception.RemoteAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AnalysisController {

    private String virusTotalURLEndpoint = "https://www.virustotal.com/vtapi/v2/url/report";
    private String virusTotalAttachmentEndpoint = "https://www.virustotal.com/vtapi/v2/file/report";

    private String virusTotalAPIKey = "d858fc83145896a11b8cc1d6b7e311e9d98f2579abddfd9b845d81012d6894ad";

    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String analyze(Model model, @RequestParam("file") MultipartFile file){

        try{
            System.out.println("UPLOAD: Uploading file '" + file.getName() + "'");

            String fileName = file.getName();
            InputStream is = file.getInputStream();

            Message msg = new Message(is);
            model.addAttribute("message", msg);

            //Run link analysis
            RestTemplate restTemplate = new RestTemplate();
            List<VirusTotalResult> resultList = new ArrayList<>();

            for(Link l : msg.getLinks()){
                try {
                    VirusTotalResult result = restTemplate.getForObject(virusTotalURLEndpoint +
                            "?apikey=" + virusTotalAPIKey +
                            "&resource="+l.getTarget(),
                            VirusTotalResult.class);

                    if(result == null){
                        throw new RemoteAPIException("Failed to load VirusTotal data");
                    }

                    System.out.println("INFO: Successful VirusTotal scan for URL=" +l.getTarget());
                    l.setScanResults(result);

                }
                catch(Exception e){
                    System.out.println("WARNING: Exception while retrieving VirusTotal data: " + e.getMessage());
                    l.setScanResults(null);
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
            model.addAttribute("stackTrace", e.getMessage());
            return "error";
        }


        return "analysis";
    }
}
