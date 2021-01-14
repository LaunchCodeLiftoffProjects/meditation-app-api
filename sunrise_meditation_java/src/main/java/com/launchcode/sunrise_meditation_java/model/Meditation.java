package com.launchcode.sunrise_meditation_java.model;

import javax.persistence.*;

@Entity
@Table(name = "MEDITATION_LOG")
public class Meditation {

        @Column(name = "USER_ID")
        private Long userId;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @Column(name = "TIME_LOG")
        private String time_log;


        @Column(name = "CREATED_TIMESTAMP")
        private String created_timestamp;


        @Column(name = "END_TIMESTAMP")
        private String end_timestamp;

    public Meditation() {
    }

    public Meditation(Long userId, Long id, String created_timestamp,  String time_log,String end_timestamp) {
        this.userId = userId;
        this.id = id;
        this.created_timestamp = created_timestamp;
        this.time_log = time_log;
        this.end_timestamp = end_timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getTime_log() {
        return time_log;
    }

    public void setTime_log(String time_log) {
        this.time_log = time_log;
    }

    public String getCreated_timestamp() {
        return created_timestamp;
    }

    public void setCreated_timestamp(String created_timestamp) {
        this.created_timestamp = created_timestamp;
    }

    public String getEnd_timestamp() {
        return end_timestamp;
    }

    public void setEnd_timestamp(String end_timestamp) {
        this.end_timestamp = end_timestamp;
    }

   /* @Override
    public String toString() {
        return  "UserId: " + String.valueOf(this.getUserId()) + "\n"+
                "Created: " + String.valueOf(this.getCreated_timestamp()) + "\n"
                + "End: " + String.valueOf(this.getEnd_timestamp()) + "\n"
                + "Time Log: " + this.getTime_log() + "\n";
    }*/
}
