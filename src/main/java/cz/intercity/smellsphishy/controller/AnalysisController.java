package cz.intercity.smellsphishy.controller;

import cz.intercity.smellsphishy.analysis.Link;
import cz.intercity.smellsphishy.analysis.Message;
import cz.intercity.smellsphishy.analysis.ReceivedEntry;
import cz.intercity.smellsphishy.analysis.remote.IPLocation;
import cz.intercity.smellsphishy.analysis.remote.VirusTotalResult;
import cz.intercity.smellsphishy.common.exception.InvalidFormatException;
import cz.intercity.smellsphishy.common.exception.RemoteAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@SessionAttributes("message")
public class AnalysisController {

    Logger log = LoggerFactory.getLogger(AnalysisController.class);

    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String analyze(Model model, @RequestParam("file") MultipartFile file) {

        try {


            String fileName = file.getOriginalFilename();

            log.info("Uploading file '" + fileName + "'");

            InputStream is = file.getInputStream();

            Message msg = new Message(is);
            model.addAttribute("message", msg);

            //Run link analysis & get IP location data
            RestTemplate VTRestTemplate = new RestTemplate();
            RestTemplate IPLocRestTemplate = new RestTemplate();

            for (ReceivedEntry recv : msg.getHeader().getReceived()) {
                if (recv.getSourceIP() != null) {

                    try {
                        IPLocation location = IPLocRestTemplate.getForObject("http://ip-api.com/json/"
                                + recv.getSourceIP() +
                                "?fields=26141", IPLocation.class);
                        if(location == null){
                            throw new RemoteAPIException("Failed to load location data");
                        }

                        if(location.getStatus().equals("success")) {
                            recv.setSourceLocation(location);
                            log.info("Successful IP trace for " + recv.getSourceIP());
                        }
                    }
                    catch (Exception e){
                        log.warn("Exception while retrieving IP Location data for '"
                                + recv.getSourceIP() + "': "
                                + e.getMessage());
                    }


                }
            }

            String virusTotalAttachmentEndpoint = "https://www.virustotal.com/vtapi/v2/file/report";
            String virusTotalURLEndpoint = "https://www.virustotal.com/vtapi/v2/url/report";
            String virusTotalAPIKey = "d858fc83145896a11b8cc1d6b7e311e9d98f2579abddfd9b845d81012d6894ad";

            for (Link l : msg.getLinks()) {
                try {
                    VirusTotalResult result = VTRestTemplate.getForObject(virusTotalURLEndpoint +
                                    "?apikey=" + virusTotalAPIKey +
                                    "&resource=" + l.getTarget(),
                            VirusTotalResult.class);

                    log.info("Successful VirusTotal scan for URL " + l.getTarget());
                    l.setScanResults(result);

                    if (result == null) {
                        throw new RemoteAPIException("Failed to load VirusTotal data");
                    }

                } catch (Exception e) {
                    log.warn("Exception while retrieving VirusTotal data: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("Exception while performing e-mail analysis: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("stackTrace", e.getMessage());
            return "error";
        }


        return "analysis";
    }

    @GetMapping("/generateTicket")
    public String generateTicket(@ModelAttribute("message") Message message, Model model){
        message.debug();
        return "ticketForm";
    }
}
