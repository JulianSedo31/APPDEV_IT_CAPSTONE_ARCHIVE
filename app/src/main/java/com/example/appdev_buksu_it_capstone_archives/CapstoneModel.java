package com.example.appdev_buksu_it_capstone_archives;

import java.util.List;
import java.util.Date;

public class CapstoneModel {
    private String id;
    private String title;
    private String authors;
    private String category;
    private String pdfUrl;
    private Date submissionDate;
    private String advisor;
    private String status;
    private List<String> keywords;

    public CapstoneModel(String id, String title, String authors, String category,
                        String pdfUrl, Date submissionDate, String advisor, String status,
                        List<String> keywords) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.category = category;
        this.pdfUrl = pdfUrl;
        this.submissionDate = submissionDate;
        this.advisor = advisor;
        this.status = status;
        this.keywords = keywords;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthors() { return authors; }
    public String getCategory() { return category; }
    public String getPdfUrl() { return pdfUrl; }
    public Date getSubmissionDate() { return submissionDate; }
    public String getAdvisor() { return advisor; }
    public String getStatus() { return status; }
    public List<String> getKeywords() { return keywords; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthors(String authors) { this.authors = authors; }
    public void setCategory(String category) { this.category = category; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }
    public void setSubmissionDate(Date submissionDate) { this.submissionDate = submissionDate; }
    public void setAdvisor(String advisor) { this.advisor = advisor; }
    public void setStatus(String status) { this.status = status; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }

    // Helper methods
    public boolean hasKeyword(String keyword) {
        return keywords != null && keywords.contains(keyword.toLowerCase());
    }

    public String getFormattedDate() {
        if (submissionDate == null) return "";
        return new java.text.SimpleDateFormat("MMMM dd, yyyy").format(submissionDate);
    }

}
