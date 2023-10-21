package com.fileUpload.service;

import com.fileUpload.model.Lead;
import com.fileUpload.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;



@Service
public class LeadService {
    @Autowired
    private LeadRepository leadRepository;

    @Transactional
    public void saveLeads(MultipartFile file) throws IOException {
        List<Lead> leads = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord csvRecord : csvParser) {
                Lead lead = new Lead();
                lead.setFirstName(csvRecord.get("first_name"));
                lead.setLastName(csvRecord.get("last_name"));
                // set other fields
                leads.add(lead);
            }
            leadRepository.saveAll(leads);
        }
    }
}

