package com.xp.virustotal;
import com.kanishka.virustotal.dto.FileScanReport;
import com.kanishka.virustotal.dto.VirusScanInfo;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by kanishka on 12/23/13.
 */
public class GetUrlReport {
    public static void main(String[] args) {
        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(ApiDetails.API_KEY);
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();

            String urls[] = {"www.yahoo.com","www.baidu.com"};
            FileScanReport[] reports = virusTotalRef.getUrlScanReport(urls, false);

            for (FileScanReport report : reports) {
                if(report.getResponseCode()==0){
                    System.out.println("Verbose Msg :\t" + report.getVerboseMessage());
                    continue;
                }
                System.out.println("MD5 :\t" + report.getMd5());
                System.out.println("Perma link :\t" + report.getPermalink());
                System.out.println("Resource :\t" + report.getResource());
                System.out.println("Scan Date :\t" + report.getScanDate());
                System.out.println("Scan Id :\t" + report.getScanId());
                System.out.println("SHA1 :\t" + report.getSha1());
                System.out.println("SHA256 :\t" + report.getSha256());
                System.out.println("Verbose Msg :\t" + report.getVerboseMessage());
                System.out.println("Response Code :\t" + report.getResponseCode());
                System.out.println("Positives :\t" + report.getPositives());
                System.out.println("Total :\t" + report.getTotal());

                Map<String, VirusScanInfo> scans = report.getScans();
                for (String key : scans.keySet()) {
                    VirusScanInfo virusInfo = scans.get(key);
                    System.out.println("Scanner : " + key);
                    System.out.println("\t\t Result : " + virusInfo.getResult());
                    System.out.println("\t\t Update : " + virusInfo.getUpdate());
                    System.out.println("\t\t Version :" + virusInfo.getVersion());
                }
            }

        } catch (APIKeyNotFoundException ex) {
            System.err.println("API Key not found! " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Unsupported Encoding Format!" + ex.getMessage());
        } catch (UnauthorizedAccessException ex) {
            System.err.println("Invalid API Key " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Something Bad Happened! " + ex.getMessage());
        }
    }
}
