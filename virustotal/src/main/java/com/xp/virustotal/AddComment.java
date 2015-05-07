package com.xp.virustotal;
import com.kanishka.virustotal.dto.GeneralResponse;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;

import java.io.UnsupportedEncodingException;

/**
 * Created by kanishka on 12/23/13.
 */
public class AddComment {
    public static void main(String[] args) {

        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(ApiDetails.API_KEY);
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();

            String resource = "44d88612fea8a8f36de82e1278abb02f";
            String comment = "Eicar file! considered a goodware :)";
            GeneralResponse gRespo = virusTotalRef.makeAComment(resource, comment);

            System.out.println("Response Code : " + gRespo.getResponseCode());
            System.out.println("Verbose Message : " + gRespo.getVerboseMessage());

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
