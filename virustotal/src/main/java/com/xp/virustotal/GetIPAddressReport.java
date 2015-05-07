package com.xp.virustotal;
import com.kanishka.virustotal.dto.*;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;

/**
 * Created by kanishka on 12/23/13.
 */
public class GetIPAddressReport {
    public static void main(String[] args) {
        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(ApiDetails.API_KEY);
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();

            IPAddressReport report = virusTotalRef.getIPAddresReport("69.195.124.58");

            System.out.println("___IP Rport__");

            Sample[] communicatingSamples = report.getDetectedCommunicatingSamples();
            if (communicatingSamples != null) {
                System.out.println("Communicating Samples");
                for (Sample sample : communicatingSamples) {
                    System.out.println("SHA256 : " + sample.getSha256());
                    System.out.println("Date : " + sample.getDate());
                    System.out.println("Positives : " + sample.getPositives());
                    System.out.println("Total : " + sample.getTotal());
                }
            }

            Sample[] detectedDownloadedSamples = report.getDetectedDownloadedSamples();
            if (detectedDownloadedSamples != null) {
                System.out.println("Detected Downloaded Samples");
                for (Sample sample : detectedDownloadedSamples) {
                    System.out.println("SHA256 : " + sample.getSha256());
                    System.out.println("Date : " + sample.getDate());
                    System.out.println("Positives : " + sample.getPositives());
                    System.out.println("Total : " + sample.getTotal());
                }
            }

            URL[] urls = report.getDetectedUrls();
            if (urls != null) {
                System.out.println("Detected URLs");
                for (URL url : urls) {
                    System.out.println("URL : " + url.getUrl());
                    System.out.println("Positives : " + url.getPositives());
                    System.out.println("Total : " + url.getTotal());
                    System.out.println("Scan Date" + url.getScanDate());
                }
            }

            IPAddressResolution[] resolutions = report.getResolutions();
            if (resolutions != null) {
                System.out.println("Resolutions");
                for (IPAddressResolution resolution : resolutions) {
                    System.out.println("Host Name : " + resolution.getHostName());
                    System.out.println("Last Resolved : " + resolution.getLastResolved());
                }
            }

            Sample[] unDetectedDownloadedSamples = report.getUndetectedDownloadedSamples();
            if (unDetectedDownloadedSamples != null) {
                System.out.println("Undetected Downloaded Samples");
                for (Sample sample : unDetectedDownloadedSamples) {
                    System.out.println("SHA256 : " + sample.getSha256());
                    System.out.println("Date : " + sample.getDate());
                    System.out.println("Positives : " + sample.getPositives());
                    System.out.println("Total : " + sample.getTotal());
                }
            }

            Sample[] unDetectedCommunicatingSamples = report.getUndetectedCommunicatingSamples();
            if (unDetectedCommunicatingSamples != null) {
                System.out.println("Undetected Communicating Samples");
                for (Sample sample : unDetectedCommunicatingSamples) {
                    System.out.println("SHA256 : " + sample.getSha256());
                    System.out.println("Date : " + sample.getDate());
                    System.out.println("Positives : " + sample.getPositives());
                    System.out.println("Total : " + sample.getTotal());
                }
            }

            System.out.println("Response Code : " + report.getResponseCode());
            System.out.println("Verbose Message : " + report.getVerboseMessage());


        } catch (APIKeyNotFoundException ex) {
            System.err.println("API Key not found! " + ex.getMessage());
        } catch (UnauthorizedAccessException ex) {
            System.err.println("Invalid API Key " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Something Bad Happened! " + ex.getMessage());
        }
    }
}
