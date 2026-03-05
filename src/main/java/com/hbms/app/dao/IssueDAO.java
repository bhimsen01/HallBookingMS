package com.hbms.app.dao;

import com.hbms.app.model.Issue;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {
    private final String file="data/issues.txt";

    private String convertToLine(Issue issue){
        return issue.getIssueId() + ","
                + issue.getBookingId() + ","
                + issue.getRaisedBy() + ","
                + issue.getDescription() + ","
                + (issue.getAssignedStaffId() == null ? "" : issue.getAssignedStaffId()) + ","
                + (issue.getIssueCreatedAt() == null ? "" : issue.getIssueCreatedAt()) + ","
                + (issue.getIssueResolvedAt() == null ? "" : issue.getIssueResolvedAt()) + ","
                + (issue.getIssueManagerRemarks() == null ? "" : issue.getIssueManagerRemarks()) + ","
                + issue.getIssueStatus();
    }

    public void saveIssue(Issue issue){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file, true))){
            bw.write(convertToLine(issue));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save issue. ",e);
        }
    }

    public void saveAllIssues(List<Issue> issues){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(file))){
            for(Issue issue:issues){
                bw.write(convertToLine(issue));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save all issues. ",e);
        }
    }

    public List<Issue> getAllIssues(){
        List<Issue> issues=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while ((line=br.readLine())!=null){
                String[] parts=line.split(",");
                LocalDateTime createdAt = parts[5].isEmpty() ? null : LocalDateTime.parse(parts[5]);
                LocalDateTime resolvedAt = parts[6].isEmpty() ? null : LocalDateTime.parse(parts[6]);
                Issue issue=new Issue(parts[0], parts[1], parts[2], parts[3], parts[4], createdAt, resolvedAt, parts[7], Issue.IssueStatus.valueOf(parts[8]));
                issues.add(issue);
            }
            return issues;
        } catch (IOException e) {
            throw new RuntimeException("Failed to get all issues. ",e);
        }
    }

    public void editIssue(Issue updatedIssue){
        List<Issue> issues=getAllIssues();
        boolean found=false;

        for (int i=0;i<issues.size();i++){
            if(issues.get(i).getIssueId().equals(updatedIssue.getIssueId())){
                issues.set(i, updatedIssue);
                found=true;
                break;
            }
        }

        if(!found){
            throw new RuntimeException("Issue not found for update.");
        }

        saveAllIssues(issues);
    }

    public Issue findById(String id){
        return getAllIssues().stream().filter(issue -> issue.getIssueId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
