package com.hbms.app.dao;

import com.hbms.app.model.Issue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {
    private final String file="data/issues.txt";

    public void saveIssue(Issue issue){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file, true))){
            String line=issue.getIssueId()+","
                    +issue.getBookingId()+","
                    +issue.getRaisedBy()+","
                    +issue.getDescription()+","
                    +issue.getAssignedStaffId()+","
                    +issue.getIssueCreatedAt()+","
                    +issue.getIssueResolvedAt()+","
                    +issue.getIssueManagerRemarks()+","
                    + issue.getIssueStatus();
            bw.write(line);
            bw.newLine();
            System.out.println("Issue saved successfully.");
        } catch (Exception e) {
            System.out.println("Issued saving failed."+e.getMessage());
        }
    }

    public List<Issue> getAllIssues(){
        List<Issue> issues=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while ((line=br.readLine())!=null){
                String[] parts=line.split(",");
                Issue issue=new Issue(parts[0], parts[1], parts[2], parts[3], parts[4], LocalDateTime.parse(parts[5]), LocalDateTime.parse(parts[6]), parts[7], Issue.IssueStatus.valueOf(parts[8]));
                issues.add(issue);
            }
            return issues;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Issue findById(String id){
        return getAllIssues().stream().filter(issue -> issue.getIssueId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
